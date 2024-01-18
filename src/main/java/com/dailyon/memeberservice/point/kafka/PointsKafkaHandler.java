package com.dailyon.memeberservice.point.kafka;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.member.sqs.UserCreatedSqsProducer;
import com.dailyon.memeberservice.member.sqs.dto.RawNotificationData;
import com.dailyon.memeberservice.member.sqs.dto.SQSNotificationDto;
import com.dailyon.memeberservice.point.api.request.PointSource;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.kafka.dto.RefundDTO;
import com.dailyon.memeberservice.point.kafka.dto.ReviewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dailyon.memeberservice.point.service.PointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import dailyon.domain.order.kafka.OrderDTO;
import dailyon.domain.order.kafka.enums.OrderEvent;

import static com.dailyon.memeberservice.member.sqs.UserCreatedSqsProducer.PointEarnedSnsNotificationQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointsKafkaHandler {
    private final PointService pointService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MemberRepository memberRepository;
    private final UserCreatedSqsProducer userCreatedSqsProducer;


    @Transactional
    @KafkaListener(topics = "create-order-use-coupon")
    public void usePoints(String message, Acknowledgment ack) {
        OrderDTO orderDto = null;
        try {
            orderDto = objectMapper.readValue(message, OrderDTO.class);
            Member member = memberRepository.findById(orderDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));
          
            if(orderDto.getUsedPoints() !=0)
            {
                PointHistory pointHistory = PointHistory.builder()
                        .member(member)
                        .status(true)
                        .amount((long) orderDto.getUsedPoints())
                        .source(PointSource.BUY)
                        .utilize("제품구매")
                        .build();

                pointService.usePointKafka(pointHistory);
            }

            if(orderDto.getReferralCode() != null && !member.getCode().equals(orderDto.getReferralCode())) {
                Long fixedPointAmount = 100L;

                Member refMember = memberRepository.findByCode(orderDto.getReferralCode());

                PointHistory pointHistory = PointHistory.builder()
                        .member(refMember)
                        .status(false)
                        .amount(fixedPointAmount)
                        .source(PointSource.PARTNERS)
                        .utilize("")
                        .build();
                pointService.addPointKafka(pointHistory);

                RawNotificationData rawNotificationData = RawNotificationData.forPointEarnedByReferralCode(fixedPointAmount);
                SQSNotificationDto sqsNotificationDto = SQSNotificationDto.of(refMember.getId(), rawNotificationData);
                userCreatedSqsProducer.produce(PointEarnedSnsNotificationQueue, sqsNotificationDto);
            }

            producePointUseSuccessMessage(orderDto);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            rollbackTransaction(orderDto);
            e.printStackTrace();
        } finally {
            ack.acknowledge();
        }
    }

        @KafkaListener(topics = "create-review")
        @Transactional
        public void addPoints (String message, Acknowledgment ack){
            ReviewDto reivewDto = null;

            try {
                reivewDto = objectMapper.readValue(message, ReviewDto.class);
                Member member = memberRepository.findById(reivewDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));

                PointHistory pointHistory = PointHistory.builder()
                        .member(member)
                        .status(false)
                        .amount(100L)
                        .source(PointSource.REVIEW)
                        .utilize("")
                        .build();

                pointService.addPointKafka(pointHistory);
                ack.acknowledge();
            }  catch (JsonProcessingException e) {
                e.printStackTrace();
            }   catch(Exception e ) {
                e.printStackTrace();
        }
    }

        @KafkaListener(topics = "cancel-order")
        public void cancelPoints(String message, Acknowledgment ack) {
            OrderDTO orderDto = null;
            try {
                orderDto = objectMapper.readValue(message, OrderDTO.class);

                if (OrderEvent.PAYMENT_FAIL.equals(orderDto.getOrderEvent())) {
                    pointService.rollbackUsePoints(orderDto);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch(Exception e ) {
                e.printStackTrace();
            } finally {
                ack.acknowledge();
            }
        }

        @KafkaListener(topics = "create-refund")
        public void refundPoints(String message, Acknowledgment ack) {
            RefundDTO refundDto = null;
            try {
                refundDto = objectMapper.readValue(message, RefundDTO.class);
                if(refundDto.getRefundPoints() !=0) {
                    pointService.refundUsePoints(refundDto);
                }
                ack.acknowledge();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch(Exception e ) {
                e.printStackTrace();
            }
        }

    public void rollbackTransaction(OrderDTO orderDto) {
        try {
            orderDto.setOrderEvent(OrderEvent.POINT_FAIL);
            kafkaTemplate.send("cancel-order", objectMapper.writeValueAsString(orderDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void producePointUseSuccessMessage(OrderDTO orderDto){
        try{
            String data = objectMapper.writeValueAsString(orderDto);
            kafkaTemplate.send("use-member-points", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

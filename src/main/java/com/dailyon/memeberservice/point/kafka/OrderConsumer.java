package com.dailyon.memeberservice.point.kafka;

import com.dailyon.memeberservice.common.exception.InsufficientQuantityException;
import com.dailyon.memeberservice.point.api.request.PointSource;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.kafka.dto.OrderDto;

import com.dailyon.memeberservice.point.kafka.dto.enums.OrderEvent;
import com.dailyon.memeberservice.point.service.PointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final PointService pointService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @KafkaListener(topics = "create-order-use-coupon")
    public void usePoints(String message, Acknowledgment ack) {
        OrderDto orderDto = null;

        try {
            orderDto = objectMapper.readValue(message, OrderDto.class);

            PointHistory pointHistory = PointHistory.builder()
                    .memberId(orderDto.getMemberId())
                    .status(true)
                    .amount((long) orderDto.getUsedPoints())
                    .source(PointSource.valueOf("BUY"))
                    .utilize("제품구매")
                        .build();

            pointService.usePointKafka(pointHistory);

        } catch (InsufficientQuantityException e) {
            rollbackTransaction(orderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

        @KafkaListener(topics = "create-review")
        public void addPoints (String message, Acknowledgment ack){
            OrderDto orderDto = null;
            try {
                orderDto = objectMapper.readValue(message, OrderDto.class);

                PointHistory pointHistory = PointHistory.builder()
                        .memberId(orderDto.getMemberId())
                        .status(false)
                        .amount((long) orderDto.getUsedPoints())
                        .source(PointSource.valueOf("Review"))
                        .utilize("제품구매")
                        .build();

                pointService.addPointKafka(pointHistory);
            } catch (InsufficientQuantityException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        //TODO : 캔슬 필터링 적용해서 받아야함
        @KafkaListener(topics = "cancle-order")
        public void ddPoints (String message, Acknowledgment ack){

        }


    public void rollbackTransaction(OrderDto orderDto) {
        try {
            orderDto.setOrderEvent(OrderEvent.POINT_FAIL);
            kafkaTemplate.send("order-cancel", objectMapper.writeValueAsString(orderDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
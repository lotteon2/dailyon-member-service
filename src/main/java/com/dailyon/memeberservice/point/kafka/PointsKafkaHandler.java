package com.dailyon.memeberservice.point.kafka;

import com.dailyon.memeberservice.point.api.request.PointSource;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.kafka.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dailyon.memeberservice.point.kafka.dto.enums.OrderEvent;
import com.dailyon.memeberservice.point.service.PointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
public class PointsKafkaHandler {
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
                producePointAddSuccessMessage(orderDto);
            }  catch (JsonProcessingException e) {
                e.printStackTrace();
            }   catch(Exception e ) {
                e.printStackTrace();
            } finally {
                ack.acknowledge();
            }
        }

        @KafkaListener(topics = "cancel-order")
        public void cancelPoints(String message, Acknowledgment ack) {
            OrderDto orderDto = null;
            try {
                orderDto = objectMapper.readValue(message, OrderDto.class);

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


    public void rollbackTransaction(OrderDto orderDto) {
        try {
            orderDto.setOrderEvent(OrderEvent.POINT_FAIL);
            kafkaTemplate.send("order-cancel", objectMapper.writeValueAsString(orderDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch(Exception e ) {
            e.printStackTrace();
        }
    }

    public void producePointUseSuccessMessage(OrderDto orderDto){
       try{
           String data = objectMapper.writeValueAsString(orderDto);
           kafkaTemplate.send("use-member-points", data);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public void producePointAddSuccessMessage(OrderDto orderDto){
        try{
            String data = objectMapper.writeValueAsString(orderDto);
            kafkaTemplate.send("add-member-points", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
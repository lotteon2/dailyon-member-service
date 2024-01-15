package com.dailyon.memeberservice.member.sqs;

import com.dailyon.memeberservice.member.sqs.dto.SQSNotificationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedSqsProducer {
    private final QueueMessagingTemplate sqsTemplate;
    private final ObjectMapper objectMapper;
    public static final String UserCreatedNotificationQueue = "user-created-queue";
    public static final String PointEarnedSnsNotificationQueue = "points-earned-sns-notification-queue";

    public void produceUserCreatedQueue(Long memberId) {
            try {
                Message<String> message = MessageBuilder.withPayload(memberId.toString()).build();
                sqsTemplate.send(UserCreatedNotificationQueue, message);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    public void produce(String queueName, SQSNotificationDto sqsNotificationDto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(sqsNotificationDto);
            Message<String> message = MessageBuilder.withPayload(jsonMessage).build();
            sqsTemplate.send(queueName, message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

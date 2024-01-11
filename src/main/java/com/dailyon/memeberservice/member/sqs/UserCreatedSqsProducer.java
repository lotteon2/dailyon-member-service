package com.dailyon.memeberservice.member.sqs;

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
    public static final String UserCreatedNotificationQueue = "user-created-queue";

    public void produce(Long memberId) {
            try {
                Message<String> message = MessageBuilder.withPayload(memberId.toString()).build();
                sqsTemplate.send(UserCreatedNotificationQueue, message);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
}

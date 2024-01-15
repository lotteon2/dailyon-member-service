package com.dailyon.memeberservice.member.sqs.dto;

import com.dailyon.memeberservice.member.sqs.dto.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawNotificationData {
    private String message;
    private Map<String, String> parameters;
    private NotificationType notificationType; // 알림 유형

    public static RawNotificationData forPointEarnedByReferralCode(Long pointEarned) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("pointEarned", String.valueOf(pointEarned));

        return new RawNotificationData(
                null,
                parameters,
                NotificationType.POINTS_EARNED_SNS
        );
    }
}
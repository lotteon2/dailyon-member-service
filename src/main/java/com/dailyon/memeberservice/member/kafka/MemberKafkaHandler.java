package com.dailyon.memeberservice.member.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import dailyon.domain.common.KafkaTopic;
import dailyon.domain.sns.kafka.dto.MemberCreateDTO;
import dailyon.domain.sns.kafka.dto.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberKafkaHandler {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void memberCreateUseSuccessMessage(MemberCreateDTO memberCreateDTO){
        try{
            String data = objectMapper.writeValueAsString(memberCreateDTO);
            kafkaTemplate.send(KafkaTopic.CREATE_MEMBER_FOR_SNS, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void memberUpdateUseSuccessMessage(MemberUpdateDTO memberUpdateDTO){
        try{
            String data = objectMapper.writeValueAsString(memberUpdateDTO);
            kafkaTemplate.send(KafkaTopic.UPDATE_MEMBER_FOR_SNS, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
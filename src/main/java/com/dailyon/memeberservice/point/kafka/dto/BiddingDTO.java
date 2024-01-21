package com.dailyon.memeberservice.point.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiddingDTO {
    private Long memberId;
    private Long usePoints;
}

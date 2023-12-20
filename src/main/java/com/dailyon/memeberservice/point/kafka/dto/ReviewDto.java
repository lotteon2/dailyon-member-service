package com.dailyon.memeberservice.point.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private String orderDetailNo;
    private Long productId;
    private Long memberId;
    private int point;
    private double ratingAvg;

}

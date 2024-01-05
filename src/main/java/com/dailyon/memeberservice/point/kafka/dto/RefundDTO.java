package com.dailyon.memeberservice.point.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundDTO {

    private ProductInfo productInfo;
    private Long couponInfoId;
    private PaymentInfo paymentInfo;
    private String orderNo;
    private Long memberId;
    private int refundPoints;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductInfo {
        private Long productId;
        private Long sizeId;
        private Long quantity;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentInfo {
        private String orderNo;
        private int cancelAmount;
    }
}
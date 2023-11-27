package com.dailyon.memeberservice.point.entity;

import com.dailyon.memeberservice.point.api.request.PointSource;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private PointSource source;

    private String utilize;

    @Column(nullable = false)
    private String createdAt;

    @Builder
    private PointHistory(
            Long id,
            Long memberId,
            Long amount,
            boolean status,
            PointSource source,
            String utilize,
            String createdAt
    ){
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
        this.status = status;
        this.source = source;
        this.utilize = utilize;
        this.createdAt = createdAt;
    }

    public PointHistory() {

    }
}

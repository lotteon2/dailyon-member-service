package com.dailyon.memeberservice.point.entity;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.point.api.request.PointSource;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PointSource source;

    private String utilize;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Builder
    private PointHistory(
            Member member,
            boolean status,
            Long amount,
            PointSource source,
            String utilize,
            LocalDateTime createdAt
    ){
        this.member = member;
        this.status = status;
        this.amount = amount;
        this.source = source;
        this.utilize = utilize;
        this.createdAt = createdAt;
    }

    public PointHistory() {

    }
}

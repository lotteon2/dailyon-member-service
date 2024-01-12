package com.dailyon.memeberservice.point.service;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.api.request.PointSource;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.kafka.dto.RefundDTO;
import com.dailyon.memeberservice.point.repository.PointRepository;
import dailyon.domain.order.kafka.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addPoint(Long memberId, PointHistoryRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        PointHistory pointHistory = PointHistory.builder()
                .member(member)
                .status(false)
                .amount(request.getAmount())
                .source(request.getSource())
                .utilize(request.getUtilize())
                .build();

        pointRepository.save(pointHistory);

        member.changePoint(request.getAmount());
    }

    @Transactional
    public void usePoint(Long memberId, PointHistoryRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        PointHistory pointHistory = PointHistory.builder()
                .member(member)
                .status(true)
                .amount(request.getAmount())
                .source(request.getSource())
                .utilize(request.getUtilize())
                .build();

        pointRepository.save(pointHistory);

        member.changePoint(-request.getAmount());
    }

    @Transactional
    public void usePointKafka(PointHistory request) throws Exception {
        pointRepository.save(request);
        Member member = request.getMember();

        if (member.getPoint() < request.getAmount()) {
            throw new Exception("Insufficient points for member: " + member.getId());
        }
        member.changePoint(-request.getAmount());
    }


    @Transactional
    public void addPointKafka(PointHistory pointHistory ) {
        pointRepository.save(pointHistory);

        Member member = pointHistory.getMember();
        member.changePoint(pointHistory.getAmount());
    }

    public void rollbackUsePoints(OrderDTO orderDto) {
        Member member = memberRepository.findById(orderDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));


        PointHistory pointHistory = PointHistory.builder()
                .member(member)
                .status(false)
                .amount((long) orderDto.getUsedPoints())
                .source(PointSource.valueOf("CANCLE"))
                .utilize("제품구매 취소")
                .build();

        addPointKafka(pointHistory);
    }


    @Transactional
    public Page<GetPointHistory> getPointHistory(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Page<PointHistory> points = pointRepository.findByMemberId(member.getId(), pageable);

        Page<GetPointHistory> pointResponses = points.map(point -> new GetPointHistory(
                point.getAmount(),
                point.isStatus(),
                point.getSource(),
                point.getUtilize(),
                point.getCreatedAt()
        ));

        return pointResponses;
    }

    public void refundUsePoints(RefundDTO refundDto) {
        Member member = memberRepository.findById(refundDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));


        PointHistory pointHistory = PointHistory.builder()
                .member(member)
                .status(false)
                .amount((long)  refundDto.getRefundPoints())
                .source(PointSource.valueOf("REFUND"))
                .utilize("포인트 사용 취소")
                .build();

        addPointKafka(pointHistory);
    }
}

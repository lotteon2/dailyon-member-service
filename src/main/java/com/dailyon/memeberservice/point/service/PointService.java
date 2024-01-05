package com.dailyon.memeberservice.point.service;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.member.service.MemberService;
import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.api.request.PointSource;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.kafka.dto.OrderDto;
import com.dailyon.memeberservice.point.kafka.dto.RefundDTO;
import com.dailyon.memeberservice.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addPoint(Long memberId, PointHistoryRequest request) {
        PointHistory pointHistory = PointHistory.builder()
                .memberId(memberId)
                .status(false)
                .amount(request.getAmount())
                .source(request.getSource())
                .utilize(request.getUtilize())
                .build();

        pointRepository.save(pointHistory);

        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changePoint(request.getAmount());
    }

    @Transactional
    public void usePoint(Long memberId, PointHistoryRequest request) {
        PointHistory pointHistory = PointHistory.builder()
                .memberId(memberId)
                .status(true)
                .amount(request.getAmount())
                .source(request.getSource())
                .utilize(request.getUtilize())
                .build();

        pointRepository.save(pointHistory);


        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changePoint(-request.getAmount());
    }

    @Transactional
    public void usePointKafka(PointHistory request) throws Exception {
        pointRepository.save(request);
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        if (member.getPoint() < request.getAmount()) {
            throw new Exception("Insufficient points for member: " + member.getId());
        }
        member.changePoint(-request.getAmount());
    }


    @Transactional
    public void addPointKafka(PointHistory pointHistory ) {
        pointRepository.save(pointHistory);

        Member member = memberRepository.findById(pointHistory.getId()).orElseThrow();
        member.changePoint(pointHistory.getAmount());
    }

    public void rollbackUsePoints(OrderDto orderDto) {
        PointHistory pointHistory = PointHistory.builder()
                .memberId(orderDto.getMemberId())
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
        PointHistory pointHistory = PointHistory.builder()
                .memberId( refundDto.getMemberId())
                .status(false)
                .amount((long)  refundDto.getRefundPoints())
                .source(PointSource.valueOf("REFUND"))
                .utilize("포인트 사용 취소")
                .build();

        addPointKafka(pointHistory);
    }
}

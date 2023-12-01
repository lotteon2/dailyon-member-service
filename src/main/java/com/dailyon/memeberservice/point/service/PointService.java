package com.dailyon.memeberservice.point.service;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
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
    public List<GetPointHistory> getPointHistory(Long memberId) {
        return pointRepository.findByMemberId(memberId);

    }
}

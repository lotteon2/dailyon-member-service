package com.dailyon.memeberservice.point.service;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional
@Service
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addPoint(PointHistoryRequest request){
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        PointHistory pointHistory = PointHistory.builder()
                .memberId(request.getMemberId())
                .status(false)
                .source(request.getSource())
                .utilize(request.getUtilize())
                .createdAt(createdAt)
                .build();

        pointRepository.save(pointHistory);

        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        member.changePoint(request.getAmount());
    }

    @Transactional
    public void usePoint(PointHistoryRequest request){
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        PointHistory pointHistory = PointHistory.builder()
                .memberId(request.getMemberId())
                .status(true)
                .source(request.getSource())
                .utilize(request.getUtilize())
                .createdAt(createdAt)
                .build();

        pointRepository.save(pointHistory);


        Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
        member.changePoint(-request.getAmount());

    }
}

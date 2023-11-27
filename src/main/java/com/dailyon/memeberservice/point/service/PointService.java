package com.dailyon.memeberservice.point.service;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional
@Service
public class PointService {
    private final PointRepository pointRepository;

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
        changePoint(request.getAmount());
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
        changePoint(request.getAmount());
    }

    @Transactional
    public void changePoint(long amount){
        Member member = new Member();
        member.changePoint(amount);

    }
}

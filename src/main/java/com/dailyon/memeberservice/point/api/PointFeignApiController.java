package com.dailyon.memeberservice.point.api;

import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/members/points")
@CrossOrigin(origins = "*")
public class PointFeignApiController {

    private final PointService pointService;

    public PointFeignApiController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping("/use")
    public ResponseEntity<Object>usePoint(@RequestHeader(name = "memberId") Long memberId, @RequestBody PointHistoryRequest request){
        pointService.usePoint(memberId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addPoint(@RequestHeader(name = "memberId") Long memberId, @RequestBody PointHistoryRequest request){
        pointService.addPoint(memberId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

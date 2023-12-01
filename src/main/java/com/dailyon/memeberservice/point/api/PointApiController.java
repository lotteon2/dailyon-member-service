package com.dailyon.memeberservice.point.api;

import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
@CrossOrigin(origins = "*")
public class PointApiController {

    private final PointService pointService;

    public PointApiController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("")
    public ResponseEntity<List<GetPointHistory>> getPointHistory(@RequestHeader(name = "memberId") Long memberId){
       return ResponseEntity.status(HttpStatus.OK).body(pointService.getPointHistory(memberId));
    }
}

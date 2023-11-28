package com.dailyon.memeberservice.point.api;

import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.service.PointService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@CrossOrigin(origins = "*")
public class PointApiController {

    private final PointService pointService;

    public PointApiController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping("/use")
    public void usePoint(@RequestBody PointHistoryRequest request){
        pointService.usePoint(request);
    }
    @PostMapping("/add")
    public void addPoint(@RequestBody PointHistoryRequest request){
        pointService.addPoint(request);
    }
}
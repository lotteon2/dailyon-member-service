package com.dailyon.memeberservice.point.api;

import com.dailyon.memeberservice.point.api.request.PointHistoryRequest;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.service.PointService;
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

    @GetMapping("/{id}")
    public List<GetPointHistory> getPointHistory(@PathVariable Long id){
        return pointService.getPointHistory(id);
    }
}

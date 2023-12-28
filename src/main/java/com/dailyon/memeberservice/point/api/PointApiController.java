package com.dailyon.memeberservice.point.api;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.service.PointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
@CrossOrigin(origins = "*")
public class PointApiController {

    private final PointService pointService;

    public PointApiController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("")
    public ResponseEntity<Page<GetPointHistory>> getPointHistory(@RequestHeader Long memberId, Pageable pageable){
       return ResponseEntity.status(HttpStatus.OK).body(pointService.getPointHistory(memberId, pageable));
    }
}

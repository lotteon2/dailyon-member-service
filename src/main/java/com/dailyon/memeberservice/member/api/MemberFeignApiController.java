package com.dailyon.memeberservice.member.api;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.api.response.MemberGetResponse;
import com.dailyon.memeberservice.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/members")
@CrossOrigin(origins = "*")
public class MemberFeignApiController {
    private final MemberService memberService;

    public MemberFeignApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerMember(@RequestBody MemberCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(request));
    }

    @GetMapping("/points")
    public ResponseEntity<Long> getPoints(@RequestHeader Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getPoints(memberId));
    }
}

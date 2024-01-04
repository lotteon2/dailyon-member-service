package com.dailyon.memeberservice.member.api;

import com.amazonaws.auth.policy.Resource;
import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.response.MemberGetResponse;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "*")
@Slf4j
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerMember(@RequestBody MemberCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(request));
    }

    @GetMapping("")
    public ResponseEntity<MemberGetResponse> getMember(@RequestHeader Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.getMember(memberId));
    }

    @GetMapping("/check/{email}")
    public boolean duplicateCheck(@PathVariable String email) {
        return memberService.MemberDuplicateCheck(email);
    }

    @PutMapping("")
    public ResponseEntity<Long> modifyMember(@RequestBody MemberModifyRequest request, @RequestHeader Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.modifyMember(request, memberId));
    }

    @DeleteMapping("")
    public ResponseEntity<Long> deleteMember(@RequestHeader Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.softDelete(memberId));
    }

    @GetMapping("/profileimg")
    public ResponseEntity<String> preurl(@RequestHeader Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.geturl(memberId));
    }


}

package com.dailyon.memeberservice.member.api;

import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.api.request.MemberGetRequest;
import com.dailyon.memeberservice.member.api.request.MemberModifyRequest;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberApiController {
    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerMember(@RequestBody MemberCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberGetRequest> getMember(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.getMember(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> modifyMember(@RequestBody MemberModifyRequest request, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.modifyMember(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.softDelete(id));
    }
}

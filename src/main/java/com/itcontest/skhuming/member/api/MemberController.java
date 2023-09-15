package com.itcontest.skhuming.member.api;

import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import com.itcontest.skhuming.member.api.dto.response.MemberProfileResDto;
import com.itcontest.skhuming.member.application.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/join")
    public ResponseEntity<String> memberJoin(@RequestBody MemberSaveReqDto memberSaveReqDto) {
        memberService.memberJoin(memberSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public ResponseEntity<MemberDto> memberLogin(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        return new ResponseEntity<>(memberService.memberLogin(memberLoginReqDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/my-profile")
    public ResponseEntity<MemberProfileResDto> memberProfileResponse(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.memberProfileResponse(memberId), HttpStatus.OK);
    }

}

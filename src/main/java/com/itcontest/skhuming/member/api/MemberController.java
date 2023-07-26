package com.itcontest.skhuming.member.api;

import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import com.itcontest.skhuming.member.api.dto.response.MemberProfileResDto;
import com.itcontest.skhuming.member.application.MemberService;
import com.itcontest.skhuming.notice.api.dto.response.NoticeResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 유저 회원가입
     */
    @PostMapping("/api/join")
    public ResponseEntity<String> memberJoin(@RequestBody MemberSaveReqDto memberSaveReqDto) {
        memberService.memberJoin(memberSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    /**
     * 유저 로그인
     */
    @PostMapping("/api/login")
    public ResponseEntity<MemberDto> memberLogin(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        return new ResponseEntity<>(memberService.memberLogin(memberLoginReqDto), HttpStatus.OK);
    }

    /**
     * 유저 본인의 스크랩되어 있는 공지 리스트
     */
    @GetMapping("/user/api/scrap/list")
    public ResponseEntity<List<NoticeResDto>> myScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.scrapNoticeList(memberId), HttpStatus.OK);
    }

    /**
     * 유저 프로필 응답
     */
    @GetMapping("/user/api/my-profile")
    public ResponseEntity<MemberProfileResDto> memberProfileResponse(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.memberProfileResponse(memberId), HttpStatus.OK);
    }


}

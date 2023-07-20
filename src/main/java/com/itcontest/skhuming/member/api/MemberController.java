package com.itcontest.skhuming.member.api;

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
//    @PostMapping("/api/join")
//    public ResponseEntity<String> memberJoin(@RequestBody MemberSaveReqDto memberSaveReqDto) {
//        memberService.memberSave(memberSaveReqDto);
//        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//    }

    /**
     * 유저 본인의 스크랩되어 있는 공지 리스트
     */
    @GetMapping("/api/scrap/list")
    public ResponseEntity<List<NoticeResDto>> myScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.scrapNoticeList(memberId), HttpStatus.OK);
    }

}

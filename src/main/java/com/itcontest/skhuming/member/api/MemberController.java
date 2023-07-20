package com.itcontest.skhuming.member.api;

import com.itcontest.skhuming.member.application.MemberService;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.notice.api.dto.response.NoticeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 유저 본인의 스크랩되어 있는 공지 리스트
     */
    @GetMapping("/api/scrap/list")
    public ResponseEntity<List<NoticeDto>> myScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.scrapNoticeList(memberId), HttpStatus.OK);
    }

}

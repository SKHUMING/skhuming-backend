package com.itcontest.skhuming.member.api;

import com.itcontest.skhuming.member.application.MemberService;
import com.itcontest.skhuming.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * mainPage 1~3등 랭킹 리스트
     */
    @GetMapping("/api/main")
    public ResponseEntity<List<Member>> mainRankingList() {
        return new ResponseEntity<>(memberService.mainPageRanking(), HttpStatus.OK);
    }

}

package com.itcontest.skhuming.main.api;

import com.itcontest.skhuming.main.application.MainService;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /**
     * mainPage 1~3등 랭킹 리스트
     */
    @GetMapping("/api/main")
    public ResponseEntity<List<MemberDto>> mainRankingList() {
        return new ResponseEntity<>(mainService.mainPageRanking(), HttpStatus.OK);
    }
}

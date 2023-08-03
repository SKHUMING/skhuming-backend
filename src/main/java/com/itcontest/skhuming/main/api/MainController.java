package com.itcontest.skhuming.main.api;

import com.itcontest.skhuming.main.application.MainService;
import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
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

    @GetMapping("/api/main")
    public ResponseEntity<List<MemberRankResDto>> mainRankingList() {
        return new ResponseEntity<>(mainService.mainPageRanking(), HttpStatus.OK);
    }

}
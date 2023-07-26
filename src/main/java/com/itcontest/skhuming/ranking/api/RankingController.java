package com.itcontest.skhuming.ranking.api;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.ranking.application.RankingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    /**
     * 랭킹 페이지의 유저들의 랭킹
     */
    @GetMapping("/api/ranking/list")
    public ResponseEntity<List<MemberRankResDto>> memberRankingList() {
        return new ResponseEntity<>(rankingService.memberRanking(), HttpStatus.OK);
    }
}

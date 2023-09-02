package com.itcontest.skhuming.ranking.api;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.ranking.application.RankingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/api/ranking/list")
    public ResponseEntity<Page<MemberRankResDto>> memberRankingList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(rankingService.memberRanking(page, size), HttpStatus.OK);
    }
}

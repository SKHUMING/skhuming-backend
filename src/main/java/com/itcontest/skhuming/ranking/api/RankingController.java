package com.itcontest.skhuming.ranking.api;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.ranking.application.RankingService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "마이 랭킹 정보",
            description = "전체 랭킹 마이 정보",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/user/ranking/my-ranking")
    public ResponseEntity<MemberRankResDto> memberRankingInformation(@RequestParam("memberId") Long memberId,
                                                                     @RequestParam("departmentNumber") int departmentNumber) {
        return new ResponseEntity<>(rankingService.memberRakingInformation(memberId, departmentNumber), HttpStatus.OK);
    }

    @Operation(
            summary = "랭킹 리스트",
            description = "랭킹 페이지 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/ranking/list")
    public ResponseEntity<Page<MemberRankResDto>> memberRankingList(@RequestParam("departmentNumber") int departmentNumber,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<MemberRankResDto> memberRanking;
        if (departmentNumber == 0) {
            memberRanking = rankingService.memberRanking(page, size);
        } else {
            memberRanking = rankingService.memberDepartmentRanking(departmentNumber, page, size);
        }

        return new ResponseEntity<>(memberRanking, HttpStatus.OK);
    }
}

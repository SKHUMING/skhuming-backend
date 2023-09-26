package com.itcontest.skhuming.main.api;

import com.itcontest.skhuming.main.application.MainService;
import com.itcontest.skhuming.member.api.dto.response.DepartmentRankResDto;
import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @Operation(summary = "메인페이지 랭킹 조회",
            description = "학부별 멤버 랭킹을 3위까지 불러옴",
            responses = {
                    @ApiResponse(responseCode = "200", description = "랭킹 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/main")
    public ResponseEntity<List<MemberRankResDto>> mainRankingList(@RequestParam("departmentNumber") int departmentNumber) {
        List<MemberRankResDto> mainPageRanking;
        if (departmentNumber == 0) {
            mainPageRanking = mainService.mainAllRanking();
        } else {
            mainPageRanking = mainService.mainDepartmentRanking(departmentNumber);
        }
        return new ResponseEntity<>(mainPageRanking, HttpStatus.OK);
    }

    @Operation(summary = "메인페이지 학부별 점수 랭킹 조회",
            description = "학부별 점수 랭킹을 불러옴",
            responses = {
                    @ApiResponse(responseCode = "200", description = "학부별 랭킹 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/main/department")
    public ResponseEntity<List<DepartmentRankResDto>> departmentByRankingList() {
        return new ResponseEntity<>(mainService.departmentByRanking(), HttpStatus.OK);
    }
}
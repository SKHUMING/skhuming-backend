package com.itcontest.skhuming.main.api;

import com.itcontest.skhuming.main.application.MainService;
import com.itcontest.skhuming.member.api.dto.response.DepartmentRankResDto;
import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
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

    @GetMapping("/api/main/department")
    public ResponseEntity<List<DepartmentRankResDto>> departmentByRankingList() {
        return new ResponseEntity<>(mainService.departmentByRanking(), HttpStatus.OK);
    }
}
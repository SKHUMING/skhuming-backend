package com.itcontest.skhuming.admin.api;

import com.itcontest.skhuming.admin.api.dto.request.NoticeSaveReqDto;
import com.itcontest.skhuming.admin.application.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 공지 등록
    @Operation(
            summary = "공지 등록",
            description = "어드민 공지 등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/admin/notice/save")
    public ResponseEntity<String> noticeSave(@RequestBody NoticeSaveReqDto noticeSaveReqDto) {
        adminService.noticeSave(noticeSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(
            summary = "공지 수정",
            description = "어드민 공지 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    // 공지 수정
    @PutMapping("/api/admin/notice/update")
    public ResponseEntity<String> noticeUpdate(@RequestParam("noticeId") Long noticeId, @RequestBody NoticeSaveReqDto noticeSaveReqDto) {
        adminService.noticeUpdate(noticeId, noticeSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(
            summary = "공지 삭제",
            description = "어드민 공지 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    // 공지 삭제
    @PostMapping("/api/admin/notice/delete")
    public ResponseEntity<String> noticeDelete(@RequestParam("noticeId") Long noticeId) {
        adminService.noticeDelete(noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

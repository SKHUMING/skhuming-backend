package com.itcontest.skhuming.admin.api;

import com.itcontest.skhuming.admin.api.request.NoticeSaveReqDto;
import com.itcontest.skhuming.admin.application.AdminService;
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
    @PostMapping("/admin/api/notice/save")
    public ResponseEntity<String> noticeSave(@RequestBody NoticeSaveReqDto noticeSaveReqDto) {
        adminService.noticeSave(noticeSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    // 공지 수정
    @PutMapping("/admin/api/notice/update")
    public ResponseEntity<String> noticeUpdate(@RequestParam("noticeId") Long noticeId, @RequestBody NoticeSaveReqDto noticeSaveReqDto) {
        adminService.noticeUpdate(noticeId, noticeSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    // 공지 삭제
    @PostMapping("/admin/api/notice/delete")
    public ResponseEntity<String> noticeDelete(@RequestParam("noticeId") Long noticeId) {
        adminService.noticeDelete(noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

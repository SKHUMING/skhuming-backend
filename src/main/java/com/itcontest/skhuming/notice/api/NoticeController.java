package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.application.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 공지 스크랩하기
     */
    @PostMapping("/user/api/notice/scrap")
    public ResponseEntity<String> addMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrap(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    /**
     * 공지 스크랩 취소
     */
    @PostMapping("/user/api/notice/scrap/cancel")
    public ResponseEntity<String> cancelMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrapCancel(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

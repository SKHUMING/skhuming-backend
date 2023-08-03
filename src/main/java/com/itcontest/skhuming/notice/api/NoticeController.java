package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.api.dto.response.DetailsNoticeResDto;
import com.itcontest.skhuming.notice.api.dto.response.NoticeListResDto;
import com.itcontest.skhuming.notice.application.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/api/notice/details")
    public ResponseEntity<DetailsNoticeResDto> detailsNoticeResponse(@RequestParam("noticeId") Long noticeId) {
        return new ResponseEntity<>(noticeService.detailsNoticeResponse(noticeId), HttpStatus.OK);
    }

    @GetMapping("/api/notice/list")
    public ResponseEntity<List<NoticeListResDto>> noticeList() {
        return new ResponseEntity<>(noticeService.noticeList(), HttpStatus.OK);
    }

    @GetMapping("/api/search-notice/list")
    public ResponseEntity<List<NoticeListResDto>> noticeSearchList(@RequestParam("searchKeyword") String searchKeyword) {
        List<NoticeListResDto> noticeSearchList;
        if (searchKeyword == null) {
            noticeSearchList = noticeService.noticeList();
        } else {
            noticeSearchList = noticeService.noticeSearchList(searchKeyword);
        }

        return new ResponseEntity<>(noticeSearchList, HttpStatus.OK);
    }

    @GetMapping("/user/api/scrap/list")
    public ResponseEntity<List<NoticeListResDto>> myScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(noticeService.myScrapNoticeList(memberId), HttpStatus.OK);
    }

    @PostMapping("/user/api/notice/scrap")
    public ResponseEntity<String> addMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrap(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/user/api/notice/scrap/cancel")
    public ResponseEntity<String> cancelMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrapCancel(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

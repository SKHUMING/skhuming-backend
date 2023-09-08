package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.api.dto.response.DetailsNoticeResDto;
import com.itcontest.skhuming.notice.api.dto.response.NoticeListResDto;
import com.itcontest.skhuming.notice.application.NoticeService;
import org.springframework.data.domain.Page;
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

    @GetMapping("/api/search-notice/list")
    public ResponseEntity<Page<NoticeListResDto>> noticeSearchList(@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<NoticeListResDto> noticeSearchPage;
        if (searchKeyword == null) {
            noticeSearchPage = noticeService.noticeList(page, size);
        } else {
            noticeSearchPage = noticeService.noticeSearchList(searchKeyword, page, size);
        }

        return new ResponseEntity<>(noticeSearchPage, HttpStatus.OK);
    }

    @GetMapping("/user/api/my-page/scrap/list")
    public ResponseEntity<List<NoticeListResDto>> myPageScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(noticeService.myPageScrapNoticeList(memberId), HttpStatus.OK);
    }

    @GetMapping("/user/api/scrap/list")
    public ResponseEntity<Page<NoticeListResDto>> myScrapList(@RequestParam("memberId") Long memberId,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(noticeService.myScrapNoticeList(memberId, page, size), HttpStatus.OK);
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

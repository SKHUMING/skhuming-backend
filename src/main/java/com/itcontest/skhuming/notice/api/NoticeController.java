package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.api.dto.response.DetailsNoticeResDto;
import com.itcontest.skhuming.notice.api.dto.response.NoticeListResDto;
import com.itcontest.skhuming.notice.application.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "세부 공지",
            description = "세부 공지 응답",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/notice/details")
    public ResponseEntity<DetailsNoticeResDto> detailsNoticeResponse(@RequestParam("noticeId") Long noticeId) {
        return new ResponseEntity<>(noticeService.detailsNoticeResponse(noticeId), HttpStatus.OK);
    }

    @Operation(
            summary = "공지 리스트",
            description = "공지 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
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

    @Operation(
            summary = "스크랩 공지 리스트",
            description = "마이 페이지 스크랩 공지 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/user/my-page/scrap/list")
    public ResponseEntity<List<NoticeListResDto>> myPageScrapList(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(noticeService.myPageScrapNoticeList(memberId), HttpStatus.OK);
    }

    @Operation(
            summary = "스크랩 공지 리스트",
            description = "마이 페이지 스크랩 공지 페이지 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/user/scrap/list")
    public ResponseEntity<Page<NoticeListResDto>> myScrapList(@RequestParam("memberId") Long memberId,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(noticeService.myScrapNoticeList(memberId, page, size), HttpStatus.OK);
    }

    @Operation(
            summary = "공지 스크랩 추가",
            description = "공지 스크랩 추가",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/user/notice/scrap")
    public ResponseEntity<String> addMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrap(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(
            summary = "공지 스크랩 취소",
            description = "공지 스크랩 취소",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/user/notice/scrap/cancel")
    public ResponseEntity<String> cancelMyScrap(@RequestParam("memberId") Long memberId, @RequestParam("noticeId") Long noticeId) {
        noticeService.noticeScrapCancel(memberId, noticeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

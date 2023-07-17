package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.api.dto.request.NoticeMemberIdGetReqDto;
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

    /*
    공지 스크랩하기
     */
    @PostMapping("/api/notice/scrap")
    public ResponseEntity<String> addMyScrap(@RequestBody NoticeMemberIdGetReqDto noticeMemberIdGetReqDto) {
        noticeService.noticeScrap(noticeMemberIdGetReqDto.getMemberId(), noticeMemberIdGetReqDto.getNoticeId());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}

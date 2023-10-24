package com.itcontest.skhuming.notice.api;

import com.itcontest.skhuming.notice.application.CrawlingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlingController {
    private final CrawlingService crawlingService;

    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @PostMapping("/api/admin/get-crawling")
    public ResponseEntity<String> crawlingSuccess() throws IOException {
        crawlingService.getNoticeData();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}

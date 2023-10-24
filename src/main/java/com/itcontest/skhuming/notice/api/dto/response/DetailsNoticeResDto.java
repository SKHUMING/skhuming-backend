package com.itcontest.skhuming.notice.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailsNoticeResDto {
    private Long noticeId;

    private String title;

    private String contents;

    private String createDate;

    private String links;

    private String author;

    private List<Long> memberId = new ArrayList<>();

    public DetailsNoticeResDto(Long noticeId, String title, String contents, String createDate, String links, String author, List<Long> memberId) {
        this.noticeId = noticeId;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.links = links;
        this.author = author;
        this.memberId = memberId;
    }
}

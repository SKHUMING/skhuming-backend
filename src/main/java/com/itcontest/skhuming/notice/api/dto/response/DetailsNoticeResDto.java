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

    private String schedule;

    private String contents;

    private int mileageScore;

    private String createDate;

    private String links;

    private List<Long> memberId = new ArrayList<>();

    public DetailsNoticeResDto(Long noticeId, String title, String schedule, String contents, int mileageScore, String createDate, String links, List<Long> memberId) {
        this.noticeId = noticeId;
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.createDate = createDate;
        this.links = links;
        this.memberId = memberId;
    }
}

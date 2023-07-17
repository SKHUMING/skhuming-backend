package com.itcontest.skhuming.notice.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDto {
    private Long noticeId;

    private String title;

    private String schedule;

    private String contents;

    private int mileageScore;

    private String img;


    public NoticeDto(Long noticeId, String title, String schedule, String contents, int mileageScore, String img) {
        this.noticeId = noticeId;
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.img = img;
    }
}

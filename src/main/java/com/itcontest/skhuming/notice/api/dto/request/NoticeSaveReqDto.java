package com.itcontest.skhuming.notice.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeSaveReqDto {

    private String title;

    private String schedule;

    private String contents;

    private int mileageScore;

    private String img;

    private int scrap;

    public NoticeSaveReqDto(String title, String schedule, String contents, int mileageScore, String img, int scrap) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.img = img;
        this.scrap = scrap;
    }
}

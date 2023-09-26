package com.itcontest.skhuming.admin.api.dto.request;

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

    public NoticeSaveReqDto(String title, String schedule, String contents, int mileageScore) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
    }
}

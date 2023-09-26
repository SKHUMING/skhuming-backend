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

    private String createDate;

    private String links;

    public NoticeSaveReqDto(String title, String schedule, String contents, int mileageScore, String createDate, String links) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.createDate = createDate;
        this.links = links;
    }
}

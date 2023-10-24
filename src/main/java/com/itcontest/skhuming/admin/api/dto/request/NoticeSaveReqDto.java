package com.itcontest.skhuming.admin.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeSaveReqDto {

    private String title;

    private String contents;

    private String createDate;

    private String author;

    private String links;

    public NoticeSaveReqDto(String title, String contents, String createDate, String author, String links) {
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.author = author;
        this.links = links;
    }
}

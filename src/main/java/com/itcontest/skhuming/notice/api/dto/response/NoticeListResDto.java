package com.itcontest.skhuming.notice.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeListResDto {
    private Long noticeId;
    private String title;
    private String createDate;
    private boolean status;

    public NoticeListResDto(Long noticeId, String title, String createDate, boolean status) {
        this.noticeId = noticeId;
        this.title = title;
        this.createDate = createDate;
        this.status = status;
    }
}

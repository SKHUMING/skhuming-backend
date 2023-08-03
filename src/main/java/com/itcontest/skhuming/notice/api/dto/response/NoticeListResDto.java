package com.itcontest.skhuming.notice.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeListResDto {
    private Long noticeId;
    private String title;
    private boolean end;

    public NoticeListResDto(Long noticeId, String title, boolean end) {
        this.noticeId = noticeId;
        this.title = title;
        this.end = end;
    }
}

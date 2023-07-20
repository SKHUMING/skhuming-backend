package com.itcontest.skhuming.notice.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeMemberIdGetReqDto {

    private Long memberId;
    private Long noticeId;
}

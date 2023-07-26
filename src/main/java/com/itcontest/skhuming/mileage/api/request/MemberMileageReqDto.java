package com.itcontest.skhuming.mileage.api.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageReqDto {
    private Long memberId;
    private String title;
    private int score;

    public MemberMileageReqDto(Long memberId, String title, int score) {
        this.memberId = memberId;
        this.title = title;
        this.score = score;
    }
}

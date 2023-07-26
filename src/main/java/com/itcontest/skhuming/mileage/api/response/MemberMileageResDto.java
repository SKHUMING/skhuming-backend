package com.itcontest.skhuming.mileage.api.response;

import com.itcontest.skhuming.member.domain.Tear;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageResDto {
    private Long memberId;
    private Tear tear;
    private String nickname;
    private int score;

    public MemberMileageResDto(Long memberId, Tear tear, String nickname, int score) {
        this.memberId = memberId;
        this.tear = tear;
        this.nickname = nickname;
        this.score = score;
    }
}

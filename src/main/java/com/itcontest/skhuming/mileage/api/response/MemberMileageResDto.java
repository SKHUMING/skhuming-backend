package com.itcontest.skhuming.mileage.api.response;

import com.itcontest.skhuming.member.domain.Tier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageResDto {
    private Long memberId;
    private Tier tier;
    private String nickname;
    private int score;

    public MemberMileageResDto(Long memberId, Tier tier, String nickname, int score) {
        this.memberId = memberId;
        this.tier = tier;
        this.nickname = nickname;
        this.score = score;
    }
}

package com.itcontest.skhuming.member.api.dto.response;

import com.itcontest.skhuming.member.domain.Tier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRankResDto {

    private Long memberId;
    private Tier tier;
    private int score;
    private String nickname;
    private String department;
    private int myRanking;

    public MemberRankResDto(Long memberId, Tier tier, int score, String nickname, String department, int myRanking) {
        this.memberId = memberId;
        this.tier = tier;
        this.score = score;
        this.nickname = nickname;
        this.department = department;
        this.myRanking = myRanking;
    }
}

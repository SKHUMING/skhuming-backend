package com.itcontest.skhuming.member.api.dto.response;

import com.itcontest.skhuming.member.domain.Tier;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRankResDto {
    private Long memberId;
    private Tier tier;
    private int score;
    private String nickname;
    private String department;

    public MemberRankResDto(Long memberId, Tier tier, int score, String nickname, String department) {
        this.memberId = memberId;
        this.tier = tier;
        this.score = score;
        this.nickname = nickname;
        this.department = department;
    }
}

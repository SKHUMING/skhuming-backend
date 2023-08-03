package com.itcontest.skhuming.member.api.dto.response;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.Tier;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
    private Long memberId;

    private String email;

    private String nickname;

    private String memberName;

    private String department;

    private String studentNumber;

    private int score;

    private Tier tier;

    private String token;

    protected MemberDto() {

    }

    @Builder
    private MemberDto(Long memberId, String email, String nickname, String memberName, String department, String studentNumber, int score, Tier tier, String token) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        this.score = score;
        this.tier = tier;
        this.token = token;
    }

    public MemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.memberName = member.getMemberName();
        this.department = member.getDepartment();
        this.studentNumber = member.getStudentNumber();
        this.score = member.getScore();
        this.tier = member.getTier();
    }
}

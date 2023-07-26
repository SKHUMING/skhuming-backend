package com.itcontest.skhuming.member.api.dto.response;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.Tear;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private Long memberId;
    private String email;
    private String pwd;
    private String nickname;
    private String memberName;
    private String department;
    private String studentNumber;

    private int score;

    private Tear tear;

    private String token;


    private MemberDto(Long memberId, String email, String pwd, String nickname, String memberName, String department, String studentNumber, int score, Tear tear, String token) {
        this.memberId = memberId;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        this.score = score;
        this.tear = tear;
        this.token = token;
    }

    public MemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.pwd = member.getPwd();
        this.nickname = member.getNickname();
        this.memberName = member.getMemberName();
        this.department = member.getDepartment();
        this.studentNumber = member.getStudentNumber();
        this.score = member.getScore();
        this.tear = member.getTear();
    }
}

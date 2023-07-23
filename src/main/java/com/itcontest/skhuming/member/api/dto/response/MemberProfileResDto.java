package com.itcontest.skhuming.member.api.dto.response;

/*
 마이페이지의 프로필 응답
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileResDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String memberName;
    private String department;
    private String studentNumber;

    public MemberProfileResDto(Long memberId, String email, String nickname, String memberName, String department, String studentNumber) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
    }
}

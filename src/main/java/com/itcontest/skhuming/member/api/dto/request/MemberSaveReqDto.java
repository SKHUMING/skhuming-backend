package com.itcontest.skhuming.member.api.dto.request;

/*
 회원가입 요청 시
 저장되는 데이터
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveReqDto {

    private String email;

    private String pwd;

    private String nickname;

    private String memberName;

    private int department;

    private String studentNumber;

    public MemberSaveReqDto(String email, String pwd, String nickname, String memberName, int department, String studentNumber) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
    }
}

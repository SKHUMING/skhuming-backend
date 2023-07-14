package com.itcontest.skhuming.member.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveReqDto {

    private String email;

    private String pwd;

    private String nickname;

    private String memberName;

    private String department;

    private String studentNumber;

    public MemberSaveReqDto(String email, String pwd, String nickname, String memberName, String department, String studentNumber) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
    }
}

package com.itcontest.skhuming.member.api.dto.request;

/*
 로그인 요청할 때
 받는 데이터
 */

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginReqDto {
    private String email;
    private String pwd;

    public MemberLoginReqDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}

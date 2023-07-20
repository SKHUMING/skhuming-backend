package com.itcontest.skhuming.member.api.dto.response;

import com.itcontest.skhuming.member.domain.Tear;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long memberId;
    private Tear tear;
    private String nickname;
    private String department;

    public MemberDto(Long memberId, Tear tear, String nickname, String department) {
        this.memberId = memberId;
        this.tear = tear;
        this.nickname = nickname;
        this.department = department;
    }
}

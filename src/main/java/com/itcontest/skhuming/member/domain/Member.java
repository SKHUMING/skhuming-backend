package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.notice.domain.Notice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String email;

    private String pwd;

    private String nickname;

    private String memberName;

    private String department;

    private String studentNumber;

    private int score;

    @Enumerated(EnumType.STRING)
    private Tear tear;

    @OneToMany(mappedBy = "member")
    private List<Notice> myScrap = new ArrayList<>();

    @Builder
    private Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        this.score = 0;
        this.tear = Tear.Un;
    }

    public static Member createMember(MemberSaveReqDto memberSaveReqDto) {
        return Member.builder()
                .email(memberSaveReqDto.getEmail())
                .pwd(memberSaveReqDto.getPwd())
                .nickname(memberSaveReqDto.getNickname())
                .memberName(memberSaveReqDto.getMemberName())
                .department(memberSaveReqDto.getDepartment())
                .studentNumber(memberSaveReqDto.getStudentNumber())
                .build();
    }

    // 비즈니스로직
    public void addMyScrap(Notice notice) {
        this.myScrap.add(notice);
    }

    public void plusMyScore(int score) {
        this.score += score;
    }
}

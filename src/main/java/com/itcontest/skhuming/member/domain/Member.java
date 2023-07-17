package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.notice.domain.Notice;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
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

    @ManyToMany
    @JoinTable(name = "member_scrap_notice",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns =  @JoinColumn(name = "notice_id")
    )
    private List<Notice> myScrap = new ArrayList<>();

    protected Member() {
    }

    private Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber, int score, Tear tear) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        this.score = score;
        this.tear = tear;
    }

    public Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber) {
        this(email, pwd, nickname, memberName, department, studentNumber, 0, Tear.Un);
    }

    // 비즈니스로직
    public List<Notice> getScrapNotices() {
        return Collections.unmodifiableList(myScrap);
    }

    public void plusMyScore(int score) {
        this.score += score;
    }
}

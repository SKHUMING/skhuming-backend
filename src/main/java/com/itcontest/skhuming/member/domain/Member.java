package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.jwt.Authority;
import com.itcontest.skhuming.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberScrapNotice> myScrap = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }

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

    @Builder
    public Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber) {
        this(email, pwd, nickname, memberName, department, studentNumber, 0, Tear.Un);
    }

    /**
     * 비즈니스로직
      */
    public void addScrapNotice(Notice notice) {
        MemberScrapNotice memberScrapNotice = new MemberScrapNotice(this, notice);
        myScrap.add(memberScrapNotice);
    }

    public void cancelScrapNotice(Notice notice) {
        MemberScrapNotice memberScrapNotice = findScrapNotice(notice);
        myScrap.remove(memberScrapNotice);
    }

    private MemberScrapNotice findScrapNotice(Notice notice) {
        return myScrap.stream()
                .filter(memberScrapNotice -> memberScrapNotice.getNotice().equals(notice))
                .findFirst()
                .orElse(null);
    }

    public List<Notice> getScrapNotices() {
        return myScrap.stream()
                .map(MemberScrapNotice::getNotice)
                .collect(Collectors.toList());
    }

    public void plusMyScore(int score) {
        this.score += score;
        updateTear();
    }

    private void updateTear() {
        if (this.score >= 500) {
            this.tear = Tear.SS;
        } else if (this.score >= 400) {
            this.tear = Tear.S;
        } else if (this.score >= 300) {
            this.tear = Tear.A;
        } else if (this.score >= 200) {
            this.tear = Tear.B;
        } else {
            this.tear = Tear.Un;
        }
    }

}

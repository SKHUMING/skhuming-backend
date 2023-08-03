package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.jwt.Authority;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
@Getter
public class Member {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@office\\.skhu\\.ac\\.kr$");
    private static final int MAX_NICKNAME_LENGTH = 10;

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
    private Tier tier;


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

    private Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber, int score, Tier tier) {
        validateEmail(email);
        validateNickname(nickname);

        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        this.score = score;
        this.tier = tier;
    }

    @Builder
    public Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber) {
        this(email, pwd, nickname, memberName, department, studentNumber, 0, Tier.Un);
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailAddressException();
        }
    }

    private void validateNickname(String nickname) {
        if (nickname.isEmpty() || nickname.length() >= MAX_NICKNAME_LENGTH) {
            throw new InvalidMemberException(String.format("닉네임은 1자 이상 %d자 이하여야 합니다.", MAX_NICKNAME_LENGTH));
        }
    }

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
        updateTier();
    }

    private void updateTier() {
        if (this.score >= 500) {
            this.tier = Tier.SS;
        } else if (this.score >= 400) {
            this.tier = Tier.S;
        } else if (this.score >= 300) {
            this.tier = Tier.A;
        } else if (this.score >= 200) {
            this.tier = Tier.B;
        } else {
            this.tier = Tier.Un;
        }
    }

}

package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
import com.itcontest.skhuming.global.jwt.domain.Authority;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.InvalidNickNameAddressException;
import com.itcontest.skhuming.mileage.domain.Mileage;
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
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]*$");
    private static final int MAX_STUDENTNUMBER_LENGTH = 9;
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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberScrapNotice> myScrap = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberHistoryMileage> mileageHistory = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    protected Member() {
    }

    private Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber, List<Authority> role, int score, Tier tier) {
        validateEmail(email);
        validateNickname(nickname);
        validateStudentNumber(studentNumber);

        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.memberName = memberName;
        this.department = department;
        this.studentNumber = studentNumber;
        addRoles(role);
        this.score = score;
        this.tier = tier;
    }

    @Builder
    public Member(String email, String pwd, String nickname, String memberName, String department, String studentNumber, List<Authority> role) {
        this(email, pwd, nickname, memberName, department, studentNumber, role, 0, Tier.Un);
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailAddressException();
        }
    }

    private void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);

        if (!matcher.matches()) {
            throw new InvalidNickNameAddressException();
        }

        if (nickname.isEmpty() || nickname.length() >= MAX_NICKNAME_LENGTH) {
            throw new InvalidMemberException(String.format("닉네임은 1자 이상 %d자 이하여야 합니다.", MAX_NICKNAME_LENGTH));
        }
    }

    private void validateStudentNumber(String studentNumber) {
        if (studentNumber.length() != MAX_STUDENTNUMBER_LENGTH) {
            throw new InvalidMemberException(String.format("학번은 %d자리여야 합니다.", MAX_STUDENTNUMBER_LENGTH));
        }
    }

    public void addRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
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

    public void addMileageHistory(Mileage mileage, String systemDate) {
        MemberHistoryMileage memberAddMileage = new MemberHistoryMileage(this, mileage, systemDate);
        mileageHistory.add(memberAddMileage);
    }

    public void cancelMileageHistory(Mileage mileage) {
        MemberHistoryMileage memberHistoryMileage = findMileageHistory(mileage);
        mileageHistory.remove(memberHistoryMileage);
    }

    private MemberHistoryMileage findMileageHistory(Mileage mileage) {
        return mileageHistory.stream()
                .filter(memberHistoryMileage -> memberHistoryMileage.getMileage().equals(mileage))
                .findFirst()
                .orElse(null);
    }

    public List<Mileage> getMileageHistory() {
        return mileageHistory.stream()
                .map(MemberHistoryMileage::getMileage)
                .collect(Collectors.toList());
    }

    public void plusMyScore(int score) {
        this.score += score;
        updateTier();
    }

    public void minusMyScore(int score) {
        this.score -= score;
        updateTier();
    }

    private void updateTier() {
        if (this.score >= 800) {
            this.tier = Tier.SS;
        } else if (this.score >= 600) {
            this.tier = Tier.S;
        } else if (this.score >= 400) {
            this.tier = Tier.A;
        } else if (this.score >= 200) {
            this.tier = Tier.B;
        } else {
            this.tier = Tier.Un;
        }
    }

}

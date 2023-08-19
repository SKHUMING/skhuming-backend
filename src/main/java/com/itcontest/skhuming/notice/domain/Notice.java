package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "Notice")
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    private String title;

    private String schedule;

    private String contents;

    private int mileageScore;


    @OneToMany(mappedBy = "notice")
    private List<MemberScrapNotice> member = new ArrayList<>();

    protected Notice() {
    }

    public Notice(String title, String schedule, String contents, int mileageScore) {
        this.title = title;
        this.schedule = Objects.requireNonNullElse(schedule, "1/1");
        this.contents = contents;
        this.mileageScore = mileageScore;
    }

    public List<Member> getScrapMember() {
        return member.stream()
                .map(MemberScrapNotice::getMember)
                .collect(Collectors.toList());
    }

    public void update(String title, String schedule, String contents, int mileageScore) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
    }

}


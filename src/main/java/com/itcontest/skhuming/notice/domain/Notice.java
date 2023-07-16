package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Notices")
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

    private String img;

    private int scrap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Notice() {

    }

    public Notice(String title, String schedule, String contents, int mileageScore, String img, int scrap) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.img = img;
        this.scrap = scrap;
    }
}


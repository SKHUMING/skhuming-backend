package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    @ManyToMany(mappedBy = "myScrap")
    private List<Member> member = new ArrayList<>();

    protected Notice() {

    }

    public Notice(String title, String schedule, String contents, int mileageScore, String img) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.img = img;
    }

}


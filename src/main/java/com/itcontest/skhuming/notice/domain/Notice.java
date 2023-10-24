package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
@Getter
public class Notice {

    private static final Pattern SCHEDULE_PATTERN = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}~\\d{4}-\\d{1,2}-\\d{1,2}");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    private String title;

    private String contents;

    private String links;

    private String createDate;

    private boolean status;

    private String author;

    @OneToMany(mappedBy = "notice")
    private List<MemberScrapNotice> member = new ArrayList<>();

    protected Notice() {
    }

    public Notice(String title, String contents, String createDate, String links, boolean status, String author) {
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.links = links;
        this.status = status;
        this.author = author;
    }

    public void update(String title, String contents, String createDate, String links, boolean status, String author) {
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.links = links;
        this.status = status;
        this.author = author;
    }

    public List<Member> getScrapMember() {
        return member.stream()
                .map(MemberScrapNotice::getMember)
                .collect(Collectors.toList());
    }

}


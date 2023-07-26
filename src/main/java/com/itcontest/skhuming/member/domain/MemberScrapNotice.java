package com.itcontest.skhuming.member.domain;

/*
 멤버 공지 스크랩 매핑테이블
 */

import com.itcontest.skhuming.notice.domain.Notice;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberScrapNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    protected MemberScrapNotice() {
    }

    public MemberScrapNotice(Member member, Notice notice) {
        this.member = member;
        this.notice = notice;
    }
}

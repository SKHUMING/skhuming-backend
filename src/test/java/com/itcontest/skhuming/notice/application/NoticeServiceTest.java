package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.TestConfig;
import com.itcontest.skhuming.member.application.MemberService;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.notice.api.dto.response.NoticeResDto;
import com.itcontest.skhuming.notice.domain.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(TestConfig.class)
class NoticeServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    NoticeService noticeService;

    @DisplayName("공지리스트 출력")
    @Test
    void noticeListPrint() {
        Notice notice = new Notice("title", "schedule", "contents", 20, "img");
        Notice notice1 = new Notice("title1", "schedule1", "contents1", 30, "img1");
        noticeService.noticeSave(notice);
        noticeService.noticeSave(notice1);

        List<Notice> noticeList = noticeService.noticeList();
        for (Notice notices : noticeList) {
            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
            System.out.println("notices.getTitle() = " + notices.getTitle());
            System.out.println("notices.getMileageScore() = " + notices.getMileageScore());
        }

        assertThatCode(() -> noticeService.noticeList())
                .doesNotThrowAnyException();
    }

    @DisplayName("공지 스크랩")
    @Test
    void noticeScrap() {
        Notice notice = new Notice("title", "schedule", "contents", 20, "img");
        Notice notice1 = new Notice("title1", "schedule1", "contents1", 30, "img1");
        noticeService.noticeSave(notice);
        noticeService.noticeSave(notice1);

        Member member = new Member(".com", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        memberService.memberSave(member);

//        System.out.println("member.getMemberId() = " + member.getMemberId());
//        System.out.println("member.getScore() = " + member.getScore());
//        System.out.println("member.getTear() = " + member.getTear());
//        System.out.println("member.getMyScrap() = " + member.getMyScrap());
//
//        List<Notice> noticeList = noticeService.noticeList();
//        for (Notice notices : noticeList) {
//            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
//            System.out.println("notices.getTitle() = " + notices.getTitle());
//        }
//
//        noticeService.noticeScrap(member.getMemberId(), notice.getNoticeId());
//
//        List<NoticeResDto> memberMyScarpList = memberService.scrapNoticeList(member.getMemberId());
//        for (NoticeResDto noticeMyList : memberMyScarpList) {
//            System.out.println("notices.getNoticeId() = " + noticeMyList.getNoticeId());
//            System.out.println("notices.getTitle() = " + noticeMyList.getTitle());
//        }

        assertThatCode(() -> noticeService.noticeScrap(member.getMemberId(), notice.getNoticeId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("공지 스크랩 취소")
    @Test
    void noticeScrapCancel () {
        Notice notice = new Notice("title", "schedule", "contents", 20, "img");
        Notice notice1 = new Notice("title1", "schedule1", "contents1", 30, "img1");
        noticeService.noticeSave(notice);
        noticeService.noticeSave(notice1);

        Member member = new Member(".com", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        memberService.memberSave(member);

        noticeService.noticeScrap(member.getMemberId(), notice.getNoticeId());
        noticeService.noticeScrap(member.getMemberId(), notice1.getNoticeId());

        List<NoticeResDto> noticeResDtos = memberService.scrapNoticeList(member.getMemberId());
        for (NoticeResDto notices : noticeResDtos) {
            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
            System.out.println("notices.getTitle() = " + notices.getTitle());
        }

        // 공지 스크랩 취소
        noticeService.noticeScrapCancel(member.getMemberId(), notice.getNoticeId());

        noticeResDtos = memberService.scrapNoticeList(member.getMemberId());
        for (NoticeResDto notices : noticeResDtos) {
            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
            System.out.println("notices.getTitle() = " + notices.getTitle());
        }

        assertThatCode(() -> noticeService.noticeScrapCancel(member.getMemberId(), notice.getNoticeId()))
                .doesNotThrowAnyException();
    }

}
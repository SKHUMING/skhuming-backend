package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.application.MemberService;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.notice.api.dto.request.NoticeSaveReqDto;
import com.itcontest.skhuming.notice.domain.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    NoticeService noticeService;
    
    @DisplayName("공지리스트 출력")
    @Test
    void noticeListPrint() {
        NoticeSaveReqDto noticeSaveReqDto = new NoticeSaveReqDto("title", "schedule", "contents", 20, "img", 0);
        NoticeSaveReqDto noticeSaveReqDto1 = new NoticeSaveReqDto("title1", "schedule1", "contents1", 30, "img1", 0);

        Notice notice = Notice.createNotice(noticeSaveReqDto);
        Notice notice1 = Notice.createNotice(noticeSaveReqDto1);

//        // when
//        noticeService.noticeSave(notice);
//        noticeService.noticeSave(notice1);

        List<Notice> noticeList = noticeService.noticeList();
        // then
        for (Notice notices : noticeList) {
            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
            System.out.println("notices.getTitle() = " + notices.getTitle());
            System.out.println("notices.getScrap() = " + notices.getScrap());
            System.out.println("notices.getMileageScore() = " + notices.getMileageScore());
        }
    }

    @DisplayName("공지 스크랩")
    @Test
    void noticeScarp() {
        // controller에서 매개변수로 요청을 받아서
        NoticeSaveReqDto noticeSaveReqDto = new NoticeSaveReqDto("title", "schedule", "contents", 20, "img", 0);
        NoticeSaveReqDto noticeSaveReqDto1 = new NoticeSaveReqDto("title1", "schedule1", "contents1", 30, "img1", 0);

        // 컨트롤러에서 공지 만들어서
        Notice notice = Notice.createNotice(noticeSaveReqDto);
        Notice notice1 = Notice.createNotice(noticeSaveReqDto1);

        // 컨트롤러에서 서비스로 넘김
        noticeService.noticeSave(notice);
        noticeService.noticeSave(notice1);

        // 이 위가 모두 컨트롤러에서 일어나는 일

        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto(".com", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        Member member = Member.createMember(memberSaveReqDto);

        memberService.memberSave(member);
        System.out.println("member.getMemberId() = " + member.getMemberId());
        System.out.println("member.getScore() = " + member.getScore());
        System.out.println("member.getTear() = " + member.getTear());
        System.out.println("member.getMyScrap() = " + member.getMyScrap());

        List<Notice> noticeList = noticeService.noticeList();
        for (Notice notices : noticeList) {
            System.out.println("notices.getNoticeId() = " + notices.getNoticeId());
            System.out.println("notices.getTitle() = " + notices.getTitle());
            System.out.println("notices.getScrap() = " + notices.getScrap());
        }

        noticeService.noticeScarp(member.getMemberId(), notice.getNoticeId());

        List<Notice> memberMyScarpList = memberService.memberMyScrapList(member.getMemberId());
        for (Notice noticeMyList : memberMyScarpList) {
            System.out.println("notices.getNoticeId() = " + noticeMyList.getNoticeId());
            System.out.println("notices.getTitle() = " + noticeMyList.getTitle());
            System.out.println("notices.getScrap() = " + noticeMyList.getScrap());
        }
    }

}
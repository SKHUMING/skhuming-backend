package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    public void noticeSave(Notice notice) {
        noticeRepository.save(notice);
    }

    // 공지 리스트
    public List<Notice> noticeList() {
        return noticeRepository.findAll();
    }

    // 공지 스크랩하기
    public void noticeScarp(Long memberId, Long noticeId) {
        Member member = memberRepository.findById(memberId).get();
        Notice notice = noticeRepository.findById(noticeId).get();

        member.addMyScrap(notice);
    }
}

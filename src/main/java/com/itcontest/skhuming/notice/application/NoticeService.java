package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.api.dto.response.NoticeDto;
import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public NoticeService(NoticeRepository noticeRepository, MemberRepository memberRepository) {
        this.noticeRepository = noticeRepository;
        this.memberRepository = memberRepository;
    }


    public void noticeSave(Notice notice) {
        noticeRepository.save(notice);
    }

    // 공지 리스트
    public List<Notice> noticeList() {
        return noticeRepository.findAll();
    }

    // 공지 스크랩하기
    public void noticeScrap(Long memberId, Long noticeId) {
        Member member = memberRepository.findById(memberId).get();
        Notice notice = noticeRepository.findById(noticeId).get();

        member.getMyScrap().add(notice);
        notice.getMember().add(member);

        memberRepository.save(member);
        noticeRepository.save(notice);
    }


}

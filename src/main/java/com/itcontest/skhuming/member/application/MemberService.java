package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.api.dto.response.NoticeDto;
import com.itcontest.skhuming.notice.domain.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void memberSave(Member member) {
        memberRepository.save(member);
    }

    /**
     * 유저 본인의 스크랩되어 있는 공지 리스트
     */
    public List<NoticeDto> scrapNoticeList(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        List<Notice> scrapNotices = member.getScrapNotices();

        List<NoticeDto> noticeDtos = new ArrayList<>();

        for (Notice notice : scrapNotices) {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setNoticeId(notice.getNoticeId());
            noticeDto.setTitle(notice.getTitle());
            noticeDto.setSchedule(notice.getSchedule());
            noticeDto.setContents(notice.getContents());
            noticeDto.setMileageScore(notice.getMileageScore());
            noticeDto.setImg(notice.getImg());

            noticeDtos.add(noticeDto);
        }

        return noticeDtos;
    }

}

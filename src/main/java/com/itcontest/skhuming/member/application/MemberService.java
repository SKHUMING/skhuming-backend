package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.api.dto.response.NoticeResDto;
import com.itcontest.skhuming.notice.domain.Notice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<NoticeResDto> scrapNoticeList(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isEmpty()) {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }

        Member member = memberOptional.get();
        List<Notice> scrapNotices = member.getScrapNotices();

        return scrapNotices.stream()
                .map(notice -> new NoticeResDto(
                        notice.getNoticeId(),
                        notice.getTitle(),
                        notice.getSchedule(),
                        notice.getContents(),
                        notice.getMileageScore(),
                        notice.getImg()
                ))
                .collect(Collectors.toList());
    }

}

package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void memberSave(Member member) {
        memberRepository.save(member);
    }

    // 유저본인이 스크랩한 공지 리스트
    public List<Notice> memberMyScrapList(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        return member.getMyScrap();
    }

}

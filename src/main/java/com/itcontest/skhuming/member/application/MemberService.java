package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void memberSave(Member member) {
        memberRepository.save(member);
    }

    /**
     * 메인페이지의 1~3등 유저 랭킹 리스트.
     * 3등까지 없더라도 리턴.
     */
    public List<Member> mainPageRanking() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        List<Member> memberRankingList = new ArrayList<>();
        int count = 0;
        for (Member member : memberList) {
            memberRankingList.add(member);
            count += 1;

            if (count == 3) {
                break;
            }
        }

        return memberRankingList;
    }

    /**
     * 유저본인이 스크랩한 공지 리스트
     */
    public List<Notice> memberMyScrapList(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        return member.getMyScrap();
    }

}

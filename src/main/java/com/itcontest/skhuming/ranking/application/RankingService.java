package com.itcontest.skhuming.ranking.application;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RankingService {

    private final MemberRepository memberRepository;

    public RankingService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 랭킹 페이지의 유저들의 랭킹
     */
    public List<MemberRankResDto> memberRanking() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        List<MemberRankResDto> memberRankingList = new ArrayList<>();
        for (Member member : memberList) {
            MemberRankResDto memberRankResDto = new MemberRankResDto(member.getMemberId(), member.getTear(), member.getNickname(), member.getDepartment());
            memberRankingList.add(memberRankResDto);
        }

        return memberRankingList;
    }
}

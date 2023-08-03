package com.itcontest.skhuming.main.application;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    private final MemberRepository memberRepository;

    public MainService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberRankResDto> mainPageRanking() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        List<MemberRankResDto> memberRankingList = new ArrayList<>();
        for (int i = 0; i < Math.min(3, memberList.size()); i++) {
            Member member = memberList.get(i);
            MemberRankResDto memberRankResDto = new MemberRankResDto(member.getMemberId(), member.getTier(), member.getScore(), member.getNickname(), member.getDepartment());
            memberRankingList.add(memberRankResDto);
        }

        return memberRankingList;
    }

}

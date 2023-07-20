package com.itcontest.skhuming.main.application;

import com.itcontest.skhuming.member.api.dto.response.MemberDto;
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

    /**
     * 메인페이지의 1~3등 유저 랭킹 리스트.
     * 3등까지 없더라도 리턴.
     */
    public List<MemberDto> mainPageRanking() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member member : memberList) {
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberId(member.getMemberId());
            memberDto.setTear(member.getTear());
            memberDto.setNickname(member.getNickname());
            memberDto.setDepartment(member.getDepartment());

            memberDtoList.add(memberDto);
        }

        List<MemberDto> memberRankingList = new ArrayList<>();
        int count = 0;
        for (MemberDto memberDto : memberDtoList) {
            memberRankingList.add(memberDto);
            count += 1;

            if (count == 3) {
                break;
            }
        }

        return memberRankingList;
    }

}

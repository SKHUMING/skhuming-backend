package com.itcontest.skhuming.ranking.application;

import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankingService {

    private final MemberRepository memberRepository;

    public RankingService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberRankResDto> memberRanking(int page, int size) {
        Page<Member> members = memberRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score")));

        return members.map(this::mapToMember);
    }

    private MemberRankResDto mapToMember(Member member) {
        return new MemberRankResDto(member.getMemberId(),
                member.getTier(),
                member.getScore(),
                member.getNickname(),
                member.getDepartment(),
                myRanking(member.getScore()));
    }

    private int myRanking(int score) {
        List<Member> rankedMembers = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        for (int i = 0; i < rankedMembers.size(); i++) {
            if (rankedMembers.get(i).getScore() == score) {
                return i + 1;
            }
        }

        return -1;
    }


}

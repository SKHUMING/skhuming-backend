package com.itcontest.skhuming.ranking.application;

import com.itcontest.skhuming.global.util.SecurityUtil;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.member.util.ChangeDepartment;
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
@Transactional(readOnly = true)
public class RankingService {

    private final MemberRepository memberRepository;

    public RankingService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 마이 랭킹 정보
     */
    public MemberRankResDto memberRakingInformation(Long memberId, int departmentNumber) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        int myRanking = member.getDepartment().equals(ChangeDepartment.departmentNumber(departmentNumber)) ?
                myDepartmentRanking(departmentNumber, member.getScore()) : myRanking(member.getScore());

        return new MemberRankResDto(member.getMemberId(),
                member.getTier(),
                member.getScore(),
                member.getNickname(),
                member.getDepartment(),
                myRanking);
    }

    /**
     * 전체 멤버 랭킹
     */
    public Page<MemberRankResDto> memberRanking(int page, int size) {
        Page<Member> members = memberRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score")));

        return members.map(this::mapToMember);
    }

    /**
     * 학부별 멤버 랭킹
     */
    public Page<MemberRankResDto> memberDepartmentRanking(int departmentNumber, int page, int size) {
        Page<Member> members = memberRepository.findByDepartment(
                ChangeDepartment.departmentNumber(departmentNumber), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score")));

        return members.map(member -> mapToDepartmentMember(member, departmentNumber));
    }

    private MemberRankResDto mapToMember(Member member) {
        return new MemberRankResDto(
                member.getMemberId(),
                member.getTier(),
                member.getScore(),
                member.getNickname(),
                member.getDepartment(),
                myRanking(member.getScore()));
    }

    private int myRanking(int score) {
        List<Member> rankedMembers = memberRepository.findAll(
                Sort.by(Sort.Direction.DESC, "score"));

        for (int i = 0; i < rankedMembers.size(); i++) {
            if (rankedMembers.get(i).getScore() == score) {
                return i + 1;
            }
        }

        return -1;
    }

    private MemberRankResDto mapToDepartmentMember(Member member, int departmentNumber) {
        return new MemberRankResDto(
                member.getMemberId(),
                member.getTier(),
                member.getScore(),
                member.getNickname(),
                member.getDepartment(),
                myDepartmentRanking(departmentNumber, member.getScore()));
    }

    private int myDepartmentRanking(int departmentNumber, int score) {
        List<Member> rankedMembers = memberRepository.findByDepartment(
                ChangeDepartment.departmentNumber(departmentNumber), Sort.by(Sort.Direction.DESC, "score"));

        for (int i = 0; i < rankedMembers.size(); i++) {
            if (rankedMembers.get(i).getScore() == score) {
                return i + 1;
            }
        }

        return -1;
    }

}

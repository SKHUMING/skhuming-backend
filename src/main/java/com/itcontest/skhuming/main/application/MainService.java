package com.itcontest.skhuming.main.application;

import com.itcontest.skhuming.member.util.ChangeDepartment;
import com.itcontest.skhuming.member.api.dto.response.DepartmentRankResDto;
import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MainService {
    private final MemberRepository memberRepository;

    public MainService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 메인페이지 전체 중 3명 랭킹
     */
    public List<MemberRankResDto> mainAllRanking() {
        List<Member> memberList = memberRepository.findAll(
                Sort.by(Sort.Direction.DESC, "score"));

        List<MemberRankResDto> memberRankingList = new ArrayList<>();
        for (int i = 0; i < Math.min(3, memberList.size()); i++) {
            Member member = memberList.get(i);
            MemberRankResDto memberRankResDto = new MemberRankResDto(
                    member.getMemberId(),
                    member.getTier(),
                    member.getScore(),
                    member.getNickname(),
                    member.getDepartment(),
                    0);
            memberRankingList.add(memberRankResDto);
        }

        return memberRankingList;
    }

    /**
     * 메인페이지 학부별 3명 랭킹
     */
    public List<MemberRankResDto> mainDepartmentRanking(int departmentNumber) {
        List<Member> memberList = memberRepository.findByDepartment(
                ChangeDepartment.departmentNumber(departmentNumber), Sort.by(Sort.Direction.DESC, "score"));

        List<MemberRankResDto> memberRankingList = new ArrayList<>();
        for (int i = 0; i < Math.min(3, memberList.size()); i++) {
            Member member = memberList.get(i);
            MemberRankResDto memberRankResDto = new MemberRankResDto(
                    member.getMemberId(),
                    member.getTier(),
                    member.getScore(),
                    member.getNickname(),
                    member.getDepartment(),
                    0);

            memberRankingList.add(memberRankResDto);
        }

        return memberRankingList;
    }

    /**
     * 학부별 총합 점수 랭킹
     */
    public List<DepartmentRankResDto> departmentByRanking() {
        HashMap<Integer, Integer> scoreMap = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            int sum = 0;
            List<Member> memberList = memberRepository.findByDepartment(
                    ChangeDepartment.departmentNumber(i), Sort.by(Sort.Direction.DESC, "score"));
            for (Member member : memberList) {
                sum += member.getScore();
            }

            scoreMap.put(i, sum);
        }

        List<Integer> scoreList = new ArrayList<>(scoreMap.keySet());
        scoreList.sort((o1, o2) -> scoreMap.get(o2).compareTo(scoreMap.get(o1)));

        Long idx = 1L;
        List<DepartmentRankResDto> departmentRankList = new ArrayList<>();
        for (Integer i : scoreList) {
            DepartmentRankResDto departmentRankResDto = new DepartmentRankResDto(
                    idx,
                    ChangeDepartment.departmentNumber(i),
                    scoreMap.get(i)
            );

            idx++;
            departmentRankList.add(departmentRankResDto);
        }

        return departmentRankList;
    }

}

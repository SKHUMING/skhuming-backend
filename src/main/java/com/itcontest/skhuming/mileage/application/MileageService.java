package com.itcontest.skhuming.mileage.application;

import com.itcontest.skhuming.mileage.api.response.MemberMileageResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.mileage.api.request.MemberMileageReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MileageService {

    private final MemberRepository memberRepository;

    public MileageService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 유저 마일리지 요청
     */
    public void memberMileageRequest(MemberMileageReqDto memberMileageReqDto) {
        Optional<Member> memberOptional = memberRepository.findById(memberMileageReqDto.getMemberId());

        Member member = memberOptional.get();
        member.plusMyScore(memberMileageReqDto.getScore());
    }

    /**
     * 유저 마일리지 응답
     */
    public MemberMileageResDto memberMileageResponse(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        Member member = memberOptional.get();

        return new MemberMileageResDto(member.getMemberId(),
                member.getTear(),
                member.getNickname(),
                member.getScore());
    }
}

package com.itcontest.skhuming.mileage.application;

import com.itcontest.skhuming.jwt.SecurityUtil;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.mileage.api.response.MemberMileageResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.mileage.api.request.MemberMileageReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MileageService {

    private final MemberRepository memberRepository;

    public MileageService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void memberMileageRequest(MemberMileageReqDto memberMileageReqDto) {
        Member member = memberRepository.findById(memberMileageReqDto.getMemberId()).orElseThrow(NotFoundMemberException::new);

        member.plusMyScore(memberMileageReqDto.getScore());
    }

    public MemberMileageResDto memberMileageResponse(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        return new MemberMileageResDto(member.getMemberId(),
                member.getTier(),
                member.getNickname(),
                member.getScore());
    }
}

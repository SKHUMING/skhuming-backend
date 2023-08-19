package com.itcontest.skhuming.mileage.application;

import com.itcontest.skhuming.jwt.SecurityUtil;
import com.itcontest.skhuming.member.domain.MemberHistoryMileage;
import com.itcontest.skhuming.member.domain.repository.MemberHistoryMileageRepository;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.mileage.api.response.MemberMileageResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.mileage.api.request.MemberMileageReqDto;
import com.itcontest.skhuming.mileage.api.response.MileageHistoryResDto;
import com.itcontest.skhuming.mileage.api.response.MileageResDto;
import com.itcontest.skhuming.mileage.domain.Mileage;
import com.itcontest.skhuming.mileage.domain.repository.MileageRepository;
import com.itcontest.skhuming.mileage.exception.ExistsMemberHistoryMileageException;
import com.itcontest.skhuming.mileage.exception.NotFoundMileageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MileageService {

    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;
    private final MemberHistoryMileageRepository memberHistoryMileageRepository;

    public MileageService(MemberRepository memberRepository, MileageRepository mileageRepository, MemberHistoryMileageRepository memberHistoryMileageRepository) {
        this.memberRepository = memberRepository;
        this.mileageRepository = mileageRepository;
        this.memberHistoryMileageRepository = memberHistoryMileageRepository;
    }

    public List<MileageResDto> mileageSelectBoxResponse() {
        List<Mileage> mileageList = mileageRepository.findAll();

        List<MileageResDto> mileageResDtoList = new ArrayList<>();
        for (Mileage mileage : mileageList) {
            MileageResDto mileageResDto = new MileageResDto(mileage.getMileageId(), mileage.getTitle(), mileage.getMileageScore());
            mileageResDtoList.add(mileageResDto);
        }

        return mileageResDtoList;
    }

    public void memberMileageRequest(MemberMileageReqDto memberMileageReqDto) {
        SecurityUtil.memberTokenMatch(memberMileageReqDto.getMemberId());

        Member member = memberRepository.findById(memberMileageReqDto.getMemberId()).orElseThrow(NotFoundMemberException::new);
        Mileage mileage = mileageRepository.findById(memberMileageReqDto.getMileageId()).orElseThrow(NotFoundMileageException::new);

        validateDuplicateMemberHistoryMileage(member, mileage);
        String systemDate = String.valueOf(LocalDate.now());

        member.plusMyScore(mileage.getMileageScore());
        member.addMileageHistory(mileage, systemDate);
        memberRepository.save(member);
    }

    private void validateDuplicateMemberHistoryMileage(Member member, Mileage mileage) {
        if (memberHistoryMileageRepository.existsByMemberAndMileage(member, mileage)) {
            throw new ExistsMemberHistoryMileageException();
        }
    }

    public void mileageHistoryCancel(Long memberId, Long mileageId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Mileage mileage = mileageRepository.findById(mileageId).orElseThrow(NotFoundMileageException::new);

        member.minusMyScore(mileage.getMileageScore());
        member.cancelMileageHistory(mileage);
        memberRepository.save(member);
    }

    public List<MileageHistoryResDto> mileageHistory(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        List<MileageHistoryResDto> mileageHistoryResDtoList = new ArrayList<>();
        for (Mileage mileage : member.getMileageHistory()) {
            MemberHistoryMileage memberHistoryMileage = memberHistoryMileageRepository.findByMemberAndMileage(member, mileage);

            MileageHistoryResDto mileageHistoryResDto = new MileageHistoryResDto(
                    mileage.getMileageId(),
                    mileage.getTitle(),
                    mileage.getMileageScore(),
                    memberHistoryMileage.getSystemDate()
            );

            mileageHistoryResDtoList.add(mileageHistoryResDto);
        }

        return mileageHistoryResDtoList;
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

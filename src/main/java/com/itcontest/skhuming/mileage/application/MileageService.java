package com.itcontest.skhuming.mileage.application;

import com.itcontest.skhuming.global.util.SecurityUtil;
import com.itcontest.skhuming.member.domain.MemberHistoryMileage;
import com.itcontest.skhuming.member.domain.repository.MemberHistoryMileageRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MileageService {

    private final List<String> messageList = new ArrayList<>();

    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;
    private final MemberHistoryMileageRepository memberHistoryMileageRepository;

    public MileageService(MemberRepository memberRepository, MileageRepository mileageRepository, MemberHistoryMileageRepository memberHistoryMileageRepository) {
        this.memberRepository = memberRepository;
        this.mileageRepository = mileageRepository;
        this.memberHistoryMileageRepository = memberHistoryMileageRepository;
    }

    /**
     * 디스플레이 보드 (전광판)
     */
    public List<String> messageListResponse() {
        if (messageList.size() > 3) {
            messageList.remove(0);
        }

        return messageList;
    }

    /**
     * 마일리지 셀렉트 박스
     */
    public List<MileageResDto> mileageSelectBoxResponse() {
        List<Mileage> mileageList = mileageRepository.findAll();
        final LocalDate now = LocalDate.now();

        List<MileageResDto> mileageResDtoList = new ArrayList<>();
        for (Mileage mileage : mileageList) {
            MileageResDto mileageResDto = new MileageResDto(
                    mileage.getMileageId(),
                    mileage.getTitle(),
                    mileage.getMileageScore(),
                    mileage.getEndSchedule());
            long daysDiff = ChronoUnit.DAYS.between(now, LocalDate.parse(mileage.getEndSchedule()));

            if (mileage.getMileageId() == 1 || daysDiff <= 14) {
                mileageResDtoList.add(mileageResDto);
            }
        }

        return mileageResDtoList;
    }

    /**
     * 마일리지 추가
     */
    @Transactional
    public void memberMileageRequest(MemberMileageReqDto memberMileageReqDto) {
        SecurityUtil.memberTokenMatch(memberMileageReqDto.getMemberId());

        Member member = memberRepository.findById(memberMileageReqDto.getMemberId()).orElseThrow(NotFoundMemberException::new);
        Mileage mileage = mileageRepository.findById(memberMileageReqDto.getMileageId()).orElseThrow(NotFoundMileageException::new);

        validateDuplicateMemberHistoryMileage(member, mileage);
        String systemDate = String.valueOf(LocalDate.now());

        int historyRanking = myRanking(member.getScore());
        member.plusMyScore(mileage.getMileageScore());
        int curRanking = myRanking(member.getScore());

        member.addMileageHistory(mileage, systemDate);
        memberRepository.save(member);

        // 추월 메시지 리스트 추가
        String message = String.valueOf(message(member, historyRanking, curRanking));

        if (!this.messageList.contains(message)) {
            this.messageList.add(message);
        }
        
    }

    /**
     * 추월 메시지 생성
     */
    private StringBuilder message(Member member, int historyRanking, int curRanking) {
        List<Member> memberList = memberRepository.findAll(
                Sort.by(Sort.Direction.DESC, "score"));
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < memberList.size() - 1; i++) {
            if (memberList.get(i).getNickname().equals(member.getNickname())) {
                Member downMember = memberList.get(i + 1);

                if (historyRanking > curRanking) {
                    return message.append(member.getNickname())
                            .append("님이 ")
                            .append(downMember.getNickname())
                            .append("님을 추월했습니다! (")
                            .append(historyRanking)
                            .append("등 → ")
                            .append(curRanking)
                            .append("등)🎉ㅤㅤㅤㅤㅤ🎉");
                }
            }
        }

        return message;
    }

    private void validateDuplicateMemberHistoryMileage(Member member, Mileage mileage) {
        if (memberHistoryMileageRepository.existsByMemberAndMileage(member, mileage)) {
            throw new ExistsMemberHistoryMileageException();
        }
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

    /**
     * 마일리지 추가한 것 취소
     */
    @Transactional
    public void mileageHistoryCancel(Long memberId, Long mileageId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Mileage mileage = mileageRepository.findById(mileageId).orElseThrow(NotFoundMileageException::new);

        member.minusMyScore(mileage.getMileageScore());
        member.cancelMileageHistory(mileage);
        memberRepository.save(member);
    }

    /**
     * 마일리지 추가 목록
     */
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
                    memberHistoryMileage.getSystemDate());

            mileageHistoryResDtoList.add(mileageHistoryResDto);
        }

        return mileageHistoryResDtoList;
    }

    /**
     * 유저 정보 응답
     */
    public MemberMileageResDto memberMileageResponse(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        return new MemberMileageResDto(member.getMemberId(),
                member.getTier(),
                member.getNickname(),
                member.getScore());
    }
}

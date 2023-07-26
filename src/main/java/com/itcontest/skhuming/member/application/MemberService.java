package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.jwt.Authority;
import com.itcontest.skhuming.jwt.JwtProvider;
import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import com.itcontest.skhuming.member.api.dto.response.MemberProfileResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.notice.api.dto.response.NoticeResDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /**
     * 유저 회원가입
     */
    public void memberJoin(MemberSaveReqDto memberSaveReqDto) {
        Member member = Member.builder()
                .email(memberSaveReqDto.getEmail())
                .pwd(passwordEncoder.encode(memberSaveReqDto.getPwd()))
                .nickname(memberSaveReqDto.getNickname())
                .memberName(memberSaveReqDto.getMemberName())
                .department(memberSaveReqDto.getDepartment())
                .studentNumber(memberSaveReqDto.getStudentNumber())
                .build();

        member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

        validateDuplicateNickname(member);
        memberRepository.save(member);
    }

    /**
     * 유저 로그인
     */
    public MemberDto memberLogin(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보"));

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .pwd(member.getPwd())
                .nickname(member.getNickname())
                .memberName(member.getMemberName())
                .department(member.getDepartment())
                .studentNumber(member.getStudentNumber())
                .score(member.getScore())
                .tear(member.getTear())
                .token(jwtProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    /**
     * nickname 중복 확인
     */
    private void validateDuplicateNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임 입니다.");
        }
    }

    /**
     * 유저 본인의 스크랩되어 있는 공지 리스트
     */
    public List<NoticeResDto> scrapNoticeList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("Member not found with ID: " + memberId));

        return member.getScrapNotices().stream()
                .map(noticeScrap -> new NoticeResDto(
                        noticeScrap.getNoticeId(),
                        noticeScrap.getTitle(),
                        noticeScrap.getSchedule(),
                        noticeScrap.getContents(),
                        noticeScrap.getMileageScore(),
                        noticeScrap.getImg()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 유저 프로필 응답
     */
    public MemberProfileResDto memberProfileResponse(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("Member not found with ID: " + memberId));

        return new MemberProfileResDto(member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getMemberName(),
                member.getDepartment(),
                member.getStudentNumber());
    }

}

package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.jwt.Authority;
import com.itcontest.skhuming.jwt.JwtProvider;
import com.itcontest.skhuming.jwt.SecurityUtil;
import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import com.itcontest.skhuming.member.api.dto.response.MemberProfileResDto;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.member.exception.InvalidMemberException;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

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

    public MemberDto memberLogin(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail()).orElseThrow(NotFoundMemberException::new);

        validatePassword(memberLoginReqDto.getPwd(), member.getPwd());

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .memberName(member.getMemberName())
                .department(member.getDepartment())
                .studentNumber(member.getStudentNumber())
                .score(member.getScore())
                .tier(member.getTier())
                .token(jwtProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    private void validateDuplicateNickname(Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new InvalidMemberException("이미 사용중인 닉네임 입니다.");
        }
    }

    private void validatePassword(CharSequence loginPwd, String memberPwd) {
        if (!passwordEncoder.matches(loginPwd, memberPwd)) {
            throw new InvalidMemberException("패스워드가 일치하지 않습니다.");
        }
    }

    public MemberProfileResDto memberProfileResponse(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        return new MemberProfileResDto(member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getMemberName(),
                member.getDepartment(),
                member.getStudentNumber());
    }

}

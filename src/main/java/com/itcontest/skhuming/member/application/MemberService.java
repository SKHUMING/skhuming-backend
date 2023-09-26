package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.global.jwt.domain.Authority;
import com.itcontest.skhuming.global.jwt.JwtProvider;
import com.itcontest.skhuming.member.util.ChangeDepartment;
import com.itcontest.skhuming.global.util.SecurityUtil;
import com.itcontest.skhuming.global.jwt.domain.repository.AuthorityRepository;
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
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void memberJoin(MemberSaveReqDto memberSaveReqDto) {
        Member member = Member.builder()
                .email(memberSaveReqDto.getEmail())
                .pwd(passwordEncoder.encode(memberSaveReqDto.getPwd()))
                .nickname(memberSaveReqDto.getNickname())
                .memberName(memberSaveReqDto.getMemberName())
                .department(ChangeDepartment.departmentNumber(memberSaveReqDto.getDepartment()))
                .studentNumber(memberSaveReqDto.getStudentNumber())
                .role(Collections.singletonList(Authority.builder().name("ROLE_USER").build()))
                .build();

        validateDuplicateEmail(member);
        validateDuplicateNickname(member);
        memberRepository.save(member);
    }

    @Transactional
    public MemberDto memberLogin(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail()).orElseThrow(NotFoundMemberException::new);
        Authority authority = authorityRepository.findById(member.getMemberId()).orElseThrow();

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
                .authorityName(authority.getName())
                .build();
    }

    private void validateDuplicateEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new InvalidMemberException("이미 사용중인 이메일 입니다.");
        }
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

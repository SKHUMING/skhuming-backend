//package com.itcontest.skhuming.member.application;
//
//import com.itcontest.skhuming.TestConfig;
//import com.itcontest.skhuming.email.exception.InvalidEmailAddressException;
//import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
//import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
//import com.itcontest.skhuming.member.domain.repository.MemberRepository;
//import com.itcontest.skhuming.member.exception.InvalidMemberException;
//import com.itcontest.skhuming.member.exception.NotFoundMemberException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Import(TestConfig.class)
//class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @DisplayName("회원가입 성공 테스트")
//    @Test
//    void joinSuccess() {
//        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto("joinSuccess@office.skhu.ac.kr", "pwd", "회원가입성공", "최기웅", "IT융합자율학부", "202014098");
//
//        assertThatCode(() -> memberService.memberJoin(memberSaveReqDto)).doesNotThrowAnyException();
//    }
//
//    @DisplayName("회원가입 실패 테스트(닉네임 중복)")
//    @Test
//    void jointFail() {
//        MemberSaveReqDto memberSaveReqDto1 = new MemberSaveReqDto("joinFail@office.skhu.ac.kr", "pwd1", "닉네임중복", "최기웅1", "IT융합자율학부1", "202014098");
//        MemberSaveReqDto memberSaveReqDto2 = new MemberSaveReqDto("joinFail2@office.skhu.ac.kr", "pwd2", "닉네임중복", "최기웅2", "IT융합자율학부2", "202014098");
//
//        memberService.memberJoin(memberSaveReqDto1);
//
//        assertThatThrownBy(() -> memberService.memberJoin(memberSaveReqDto2))
//                .isInstanceOf(InvalidMemberException.class)
//                .hasMessage("이미 사용중인 닉네임 입니다.");
//    }
//
//    @DisplayName("회원가입 실패 테스트(닉네임 길이 초과)")
//    @Test
//    void joinNicknameLengthFail() {
//        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto("joinNickname@office.skhu.ac.kr", "pwd", "닉네임길이제한10자이상넘어가면실패해요.", "최기웅", "소프트웨어공학과", "202014098");
//
//        assertThatThrownBy(() -> memberService.memberJoin(memberSaveReqDto))
//                .isInstanceOf(InvalidMemberException.class)
//                .hasMessage("닉네임은 1자 이상 10자 이하여야 합니다.");
//    }
//
//    @DisplayName("회원가입 실패 테스트(이메일 형식)")
//    @Test
//    void joinEmailFail() {
//        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto("joinEmail@gmail.com", "pwd", "이메일형식이달라요", "최기웅", "소프트웨어공학과", "202014098");
//
//        assertThatThrownBy(() -> memberService.memberJoin(memberSaveReqDto))
//                .isInstanceOf(InvalidEmailAddressException.class)
//                .hasMessage("이메일 형식이 올바르지 않습니다.");
//    }
//
//    @DisplayName("로그인 성공 테스트")
//    @Test
//    void loginSuccess() {
//        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto("loginSuccess@office.skhu.ac.kr", "pwd", "로그인성공테스트", "최기웅", "IT융합자율학부", "202014098");
//        memberService.memberJoin(memberSaveReqDto);
//
//        MemberLoginReqDto memberLoginReqDto = new MemberLoginReqDto(memberSaveReqDto.getEmail(), memberSaveReqDto.getPwd());
//
//        assertThatCode(() -> memberService.memberLogin(memberLoginReqDto)).doesNotThrowAnyException();
//    }
//
//    @DisplayName("로그인 실패 테스트(비밀번호 다름)")
//    @Test
//    void loginFail() {
//        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto("loginFail@office.skhu.ac.kr", "pwd", "로그인실패테스트", "최기웅", "IT융합자율학부", "202014098");
//        memberService.memberJoin(memberSaveReqDto);
//
//        MemberLoginReqDto memberLoginReqDto = new MemberLoginReqDto(memberSaveReqDto.getEmail(), "failPwd");
//
//        assertThatThrownBy(() -> memberService.memberLogin(memberLoginReqDto))
//                .isInstanceOf(InvalidMemberException.class)
//                .hasMessage("패스워드가 일치하지 않습니다.");
//    }
//
//    @DisplayName("로그인 실패 테스트(존재하지 않는 유저)")
//    @Test
//    void loginNotMemberFail() {
//        MemberLoginReqDto memberLoginReqDto = new MemberLoginReqDto("notFound@office.skhu.ac.kr", "존재하지않는유저");
//
//        assertThatThrownBy(() -> memberService.memberLogin(memberLoginReqDto))
//                .isInstanceOf(NotFoundMemberException.class)
//                .hasMessage("회원을 찾을 수 없습니다.");
//    }
//
//}
package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @DisplayName("메인페이지 1~3등 랭킹 리스트")
    @Test
    void mainPageRanking() {
        // 데이터 저장
        MemberSaveReqDto member1 = new MemberSaveReqDto("member1", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        MemberSaveReqDto member2 = new MemberSaveReqDto("member2", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        MemberSaveReqDto member3 = new MemberSaveReqDto("member3", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        MemberSaveReqDto member4 = new MemberSaveReqDto("member4", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        MemberSaveReqDto member5 = new MemberSaveReqDto("member5", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");

        Member memberA = Member.createMember(member1);
        Member memberB = Member.createMember(member2);
        Member memberC = Member.createMember(member3);
        Member memberD = Member.createMember(member4);
        Member memberE = Member.createMember(member5);
        memberA.plusMyScore(10);
        memberB.plusMyScore(80);
        memberC.plusMyScore(40);
        memberD.plusMyScore(100);

        memberService.memberSave(memberA);
        memberService.memberSave(memberB);
        memberService.memberSave(memberC);
        memberService.memberSave(memberD);
        memberService.memberSave(memberE);

        List<Member> memberList = memberService.mainPageRanking();

        for (Member member : memberList) {
            System.out.println("member.getMemberId() = " + member.getMemberId());
            System.out.println("member.getTear() = " + member.getTear());
            System.out.println("member.getNickname() = " + member.getNickname());
            System.out.println("member.getScore() = " + member.getScore());
        }

    }
}
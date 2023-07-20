package com.itcontest.skhuming.member.application;

import com.itcontest.skhuming.TestConfig;
import com.itcontest.skhuming.main.application.MainService;
import com.itcontest.skhuming.member.api.dto.response.MemberRankResDto;
import com.itcontest.skhuming.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(TestConfig.class)
class MemberServiceTest {

    @Autowired
    MainService mainService;
    @Autowired
    MemberService memberService;

    @DisplayName("메인페이지 1~3등 랭킹 리스트")
    @Test
    void mainPageRanking() {
        // 데이터 저장
        Member memberA = new Member("member1", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        Member memberB = new Member("member2", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        Member memberC = new Member("member3", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        Member memberD = new Member("member4", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");
        Member memberE = new Member("member5", "chlrldnd","너의 집앞 골목", "최기웅", "it융합자율학부", " 202014098");

        // 나중에 마일리지점수를 올리기 위해서는 memberId와 score를 매개변수로 가진 메서드를 만들어야 할 듯.
        memberA.plusMyScore(10);
        memberB.plusMyScore(80);
        memberC.plusMyScore(40);
        memberD.plusMyScore(100);

        memberService.memberSave(memberA);
        memberService.memberSave(memberB);
        memberService.memberSave(memberC);
        memberService.memberSave(memberD);
        memberService.memberSave(memberE);

        List<MemberRankResDto> memberList = mainService.mainPageRanking();

        for(MemberRankResDto member : memberList) {
            System.out.println("member.getMemberId() = " + member.getMemberId());
            System.out.println("member.getTear() = " + member.getTear());
            System.out.println("member.getNickname() = " + member.getNickname());
        }

    }
}
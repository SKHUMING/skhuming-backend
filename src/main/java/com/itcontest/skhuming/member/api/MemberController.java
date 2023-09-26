package com.itcontest.skhuming.member.api;

import com.itcontest.skhuming.member.api.dto.request.MemberLoginReqDto;
import com.itcontest.skhuming.member.api.dto.request.MemberSaveReqDto;
import com.itcontest.skhuming.member.api.dto.response.MemberDto;
import com.itcontest.skhuming.member.api.dto.response.MemberProfileResDto;
import com.itcontest.skhuming.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원 가입",
            description = "멤버 회원 가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "201", description = "멤버 추가 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/join")
    public ResponseEntity<String> memberJoin(@RequestBody MemberSaveReqDto memberSaveReqDto) {
        memberService.memberJoin(memberSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(
            summary = "로그인",
            description = "멤버 로그인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PostMapping("/api/login")
    public ResponseEntity<MemberDto> memberLogin(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        return new ResponseEntity<>(memberService.memberLogin(memberLoginReqDto), HttpStatus.OK);
    }

    @Operation(
            summary = "멤버 정보 응답",
            description = "멤버 정보 응답",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @GetMapping("/api/user/my-profile")
    public ResponseEntity<MemberProfileResDto> memberProfileResponse(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(memberService.memberProfileResponse(memberId), HttpStatus.OK);
    }

}

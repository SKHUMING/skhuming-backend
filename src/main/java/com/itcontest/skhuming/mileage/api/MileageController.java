package com.itcontest.skhuming.mileage.api;

import com.itcontest.skhuming.mileage.api.response.MemberMileageResDto;
import com.itcontest.skhuming.mileage.api.request.MemberMileageReqDto;
import com.itcontest.skhuming.mileage.application.MileageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MileageController {

    private final MileageService mileageService;

    public MileageController(MileageService mileageService) {
        this.mileageService = mileageService;
    }

    @PostMapping("/user/api/mileage/post")
    public ResponseEntity<String> memberMileageRequest(@RequestBody MemberMileageReqDto memberMileageReqDto) {
        mileageService.memberMileageRequest(memberMileageReqDto);
        memberMileageResponse(memberMileageReqDto.getMemberId());
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/user/api/mileage/get")
    public ResponseEntity<MemberMileageResDto> memberMileageResponse(@RequestParam("memberId") Long memberId) {
        return new ResponseEntity<>(mileageService.memberMileageResponse(memberId), HttpStatus.OK);
    }

}

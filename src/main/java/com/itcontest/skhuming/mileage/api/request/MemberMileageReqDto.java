package com.itcontest.skhuming.mileage.api.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMileageReqDto {
    private Long memberId;
    private Long mileageId;

    public MemberMileageReqDto(Long memberId,Long mileageId) {
        this.memberId = memberId;
        this.mileageId = mileageId;
    }
}

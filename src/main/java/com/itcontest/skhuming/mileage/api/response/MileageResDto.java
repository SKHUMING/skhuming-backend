package com.itcontest.skhuming.mileage.api.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MileageResDto {
    private Long mileageId;

    private String title;

    private int mileageScore;

    public MileageResDto(Long mileageId, String title, int mileageScore) {
        this.mileageId = mileageId;
        this.title = title;
        this.mileageScore = mileageScore;
    }
}

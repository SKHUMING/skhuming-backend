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

    private String endSchedule;

    public MileageResDto(Long mileageId, String title, int mileageScore, String endSchedule) {
        this.mileageId = mileageId;
        this.title = title;
        this.mileageScore = mileageScore;
        this.endSchedule = endSchedule;
    }
}

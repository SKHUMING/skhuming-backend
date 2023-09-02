package com.itcontest.skhuming.mileage.api.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MileageHistoryResDto {

    private Long mileageId;

    private String title;

    private int mileageScore;

    private String systemDate;

    public MileageHistoryResDto(Long mileageId, String title, int mileageScore, String systemDate) {
        this.mileageId = mileageId;
        this.title = title;
        this.mileageScore = mileageScore;
        this.systemDate = systemDate;
    }
}

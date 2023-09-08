package com.itcontest.skhuming.member.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentRankResDto {

    private Long departmentId;
    private String department;
    private int score;

    public DepartmentRankResDto(Long departmentId, String department, int score) {
        this.departmentId = departmentId;
        this.department = department;
        this.score = score;
    }
}

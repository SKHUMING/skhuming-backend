package com.itcontest.skhuming.member.util;

import com.itcontest.skhuming.member.exception.NotFoundDepartmentException;
import org.springframework.stereotype.Component;

@Component
public class ChangeDepartment {
    public static String departmentNumber(int departmentNumber) {
        validateDepartmentNumber(departmentNumber);
        return switch (departmentNumber) {
            case 1 -> "인문융합자율학부";
            case 2 -> "사회융합자율학부";
            case 3 -> "미디어콘텐츠융합자율학부";
            case 4 -> "IT융합자율학부";
            default -> null;
        };
    }

    private static void validateDepartmentNumber(int departmentNumber) {
        if (departmentNumber >= 5) {
            throw new NotFoundDepartmentException();
        }
    }
}

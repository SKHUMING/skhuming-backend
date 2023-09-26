package com.itcontest.skhuming.global.util;

import com.itcontest.skhuming.global.jwt.CustomUserDetails;
import com.itcontest.skhuming.global.jwt.exception.NotMatchTokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getMember().getMemberId();
    }

    /**
     * 해당 멤버의 토큰과 헤더에 요청온 토큰이 일치하는지.
     */
    public static void memberTokenMatch(Long memberId) {
        Long currentMemberId = getCurrentMemberId();

        if (!memberId.equals(currentMemberId)) {
            throw new NotMatchTokenException();
        }

    }
}

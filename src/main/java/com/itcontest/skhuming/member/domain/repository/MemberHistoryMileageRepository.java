package com.itcontest.skhuming.member.domain.repository;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberHistoryMileage;
import com.itcontest.skhuming.mileage.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberHistoryMileageRepository extends JpaRepository<MemberHistoryMileage, Long> {
    MemberHistoryMileage findByMemberAndMileage(Member member, Mileage mileage);
    boolean existsByMemberAndMileage(Member member, Mileage mileage);
}

package com.itcontest.skhuming.member.domain.repository;

import com.itcontest.skhuming.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    @Query("select m " +
            "from Member m " +
            "where m.department = :department")
    List<Member> findByDepartment(@Param("department") String department, Sort sort);

    @Query("select m " +
            "from Member m " +
            "where m.department = :department")
    Page<Member> findByDepartment(@Param("department") String department, PageRequest pageRequest);
}

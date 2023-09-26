package com.itcontest.skhuming.member.domain.repository;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import com.itcontest.skhuming.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberScrapNoticeRepository extends JpaRepository<MemberScrapNotice, Long> {
    List<MemberScrapNotice> findByNotice(Notice notice);

    @Query("select m " +
            "from MemberScrapNotice m " +
            "where m.member = :member " )
    List<MemberScrapNotice> findByMyScrapNotice(@Param("member") Member member);

    @Query("select m " +
            "from MemberScrapNotice m " +
            "where m.member = :member ")
    Page<MemberScrapNotice> findByMyScrapNotice(@Param("member") Member member, PageRequest pageRequest);
}

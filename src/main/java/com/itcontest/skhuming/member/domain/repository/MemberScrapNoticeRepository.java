package com.itcontest.skhuming.member.domain.repository;

import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import com.itcontest.skhuming.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberScrapNoticeRepository extends JpaRepository<MemberScrapNotice, Long> {
    List<MemberScrapNotice> findByNotice(Notice notice);
}

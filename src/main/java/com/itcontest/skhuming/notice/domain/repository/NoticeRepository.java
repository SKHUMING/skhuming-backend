package com.itcontest.skhuming.notice.domain.repository;

import com.itcontest.skhuming.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 게시글 검색기능 (findBy(컬럼이름)Containing -> 컬럼에서 키워드가 포함된 것을 찾겠다. 글자 한 글자만 검색해도 그 글자가 포함된 것은 다 나옴)
    Page<Notice> findByTitleContaining(String searchKeyword, PageRequest noticeId);
}

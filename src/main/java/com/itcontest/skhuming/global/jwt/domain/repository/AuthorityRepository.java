package com.itcontest.skhuming.global.jwt.domain.repository;

import com.itcontest.skhuming.global.jwt.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}

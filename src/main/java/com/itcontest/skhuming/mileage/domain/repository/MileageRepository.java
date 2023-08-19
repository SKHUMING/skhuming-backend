package com.itcontest.skhuming.mileage.domain.repository;

import com.itcontest.skhuming.mileage.domain.Mileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MileageRepository extends JpaRepository<Mileage, Long> {

}

package com.backend.dream.repository;

import com.backend.dream.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {

    List<Discount> findByActiveDateBeforeAndExpiredDateAfter(Date currentDate, Date currentDate1);
}

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
    @Query("SELECT d FROM Discount d WHERE d.category.id = :idCategory and current_date >= d.activeDate and current_date <= d.expiredDate")
    Optional<Discount> findByIDProduct(Long idCategory);


    List<Discount> findByActiveDateBeforeAndExpiredDateAfter(Date currentDate, Date currentDate1);
}

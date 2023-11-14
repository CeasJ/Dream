package com.backend.dream.repository;

import com.backend.dream.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM Voucher v WHERE CURRENT_DATE <= v.expireddate " +
            "AND v.status.id = 1")
    List<Voucher> findApplicableVouchers();
}
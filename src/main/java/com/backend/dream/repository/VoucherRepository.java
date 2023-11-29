package com.backend.dream.repository;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM Voucher v WHERE CURRENT_DATE < v.expiredDate " +
            "AND v.status.id = 1 AND v.account.id = :id_account")
    List<Voucher> findApplicableVouchers(@Param("id_account") Long id_account);

    @Query("SELECT v FROM Voucher v")
    List<Voucher> findAllVouchers();

    // Display vouchers based on chosen status
    List<Voucher> findByStatusId(Long statusId);

    // Searching voucher bu name
    @Query("SELECT v FROM Voucher v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Voucher> searchByName(@Param("name") String name);


}
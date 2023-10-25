package com.backend.dream.repository;

import com.backend.dream.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Report extends JpaRepository<OrderDetails,Long> {
    int orderStatus = 4;

    @Query(value = "SELECT SUM(od.quantity * od.price) FROM OrderDetails od WHERE od.orders.status = ?1")
    Double getRevenue();
}

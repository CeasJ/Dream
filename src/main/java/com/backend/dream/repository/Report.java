package com.backend.dream.repository;

import com.backend.dream.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Report extends JpaRepository<OrderDetails,Long> {
    @Query(value = "SELECT SUM(od.quantity * od.price) FROM OrderDetails od WHERE od.orders.status.id = ?1")
    Double getRevenue(int orderStatus);
    @Query(value = "SELECT count(o.id) FROM Orders o WHERE o.status.id = ?1")
    Double getTotalOrder(int orderStatus);
    @Query(value = "SELECT sum(od.quantity) FROM OrderDetails od WHERE od.orders.status.id =?1")
    Integer totalProductHasSold(int orderStatus);
}

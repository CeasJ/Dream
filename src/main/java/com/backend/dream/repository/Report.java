package com.backend.dream.repository;

import com.backend.dream.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Report extends JpaRepository<OrderDetails,Long> {
    @Query(value = "SELECT SUM(od.quantity * od.price) FROM OrderDetails od WHERE od.orders.status.id = ?1")
    Double getRevenue(int orderStatus);
    @Query(value = "SELECT count(o.id) FROM Orders o WHERE o.status.id = ?1")
    Double getTotalOrder(int orderStatus);
    @Query(value = "SELECT sum(od.quantity) FROM OrderDetails od WHERE od.orders.status.id =?1")
    Integer totalProductHasSold(int orderStatus);
    @Query(value = "SELECT p.id, p.name, sum(od.quantity) "
            + "FROM OrderDetails od "
            + "JOIN od.product p "
            + "JOIN od.orders o "
            + "WHERE o.status.id = ?1 "
            + "GROUP BY p.id, p.name "
            + "ORDER BY sum(od.quantity) DESC")
    List<Object[]> countProductSold(int orderStatus);
    @Query(value = "SELECT o.createDate, sum(od.quantity * od.price) "
            + "FROM OrderDetails od "
            + "JOIN od.orders o "
            + "WHERE o.status.id = ?1 "
            + "GROUP BY o.createDate")
    List<Object[]> getTotalRevenue(int orderStatus);


    @Query(value = "SELECT o.createDate, sum(od.quantity * od.price) "
            + "FROM OrderDetails od "
            + "JOIN od.orders o "
            + "WHERE o.status.id = ?1 "
            + "AND o.createDate BETWEEN ?2 AND ?3 "
            + "GROUP BY o.createDate")
    List<Object[]> getTotalRevenueByDateAndStatus(int orderStatus, Date startDate, Date endDate);
}

package com.backend.dream.repository;

import com.backend.dream.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Report extends JpaRepository<OrderDetails,Long> {
    @Query(value = "SELECT SUM(o.totalAmount) FROM OrderDetails od JOIN od.orders o WHERE od.orders.status.id = ?1")
    Double getRevenue(int orderStatus);
    @Query(value = "SELECT count(o.id) FROM Orders o WHERE o.status.id = ?1")
    Double getTotalOrder(int orderStatus);
    @Query(value = "SELECT sum(od.quantity) FROM OrderDetails od WHERE od.orders.status.id =?1")
    Integer totalProductHasSold(int orderStatus);
    @Query(value = "SELECT count(o.account.id) FROM OrderDetails od JOIN od.orders o")
    Integer totalAccount();
    @Query(value = "SELECT p.name, sum(od.quantity) "
            + "FROM OrderDetails od "
            + "JOIN od.product p "
            + "JOIN od.orders o "
            + "WHERE o.status.id = ?1 "
            + "GROUP BY p.id, p.name "
            + "ORDER BY sum(od.quantity) DESC")
    List<Object[]> countProductSold(int orderStatus);
    @Query(value = "SELECT o.createDate, sum(o.totalAmount) "
            + "FROM OrderDetails od "
            + "JOIN od.orders o "
            + "WHERE o.status.id = ?1 "
            + "GROUP BY o.createDate")
    List<Object[]> getDailyRevenue(int orderStatus);

//    @Query(value = "SELECT sum(od.quantity * od.price) "
//            + "FROM OrderDetails od "
//            + "JOIN od.orders o "
//            + "WHERE o.status.id = ?1 "
//            + "AND o.createDate BETWEEN current_date AND ?3 "
//            + "GROUP BY o.createDate")
//    Double getTotalRevenueLastWeekAndStatus(int orderStatus, Date endDate);
//
//        @Query(value = "SELECT sum(od.quantity * od.price) "
//                + "FROM OrderDetails od "
//                + "JOIN od.orders o "
//                + "WHERE o.status.id = ?1 "
//                + "AND o.createDate BETWEEN current_date AND ?3 "
//                + "GROUP BY o.createDate")
//        Double getTotalRevenueLastMonthAndStatus(int orderStatus);

    @Query(value = "SELECT cate.name, sum(o.totalAmount) "
            + "FROM OrderDetails od "
            + "JOIN od.orders o "
            + "JOIN od.product p "
            + "JOIN od.product.category cate "
            + "WHERE o.status.id = ?1 "
            + "GROUP BY cate.name")
    List<Object[]> getProductHasSoldByCategory(int orderStatus);

    @Query(value = "SELECT a.fullname, SUM(o.totalAmount - COALESCE(v.percent,0) + o.distance * 4) , a.phone, a.address "
            + "FROM OrderDetails od "
            + "JOIN od.orders o "
            + "JOIN od.orders.account a "
            + "LEFT JOIN od.orders.voucher v "
            + "WHERE o.status.id =?1 "
            + "GROUP BY a.fullname,a.phone,a.address "
            + "ORDER BY SUM(o.totalAmount - COALESCE(v.percent,0) + o.distance * 4) DESC")
    List<Object[]> getAmountPaidByAccount(int orderStatus);
}
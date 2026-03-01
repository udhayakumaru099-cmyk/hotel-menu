package com.hotel.emenu.repository;

import com.hotel.emenu.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

       Optional<Order> findByOrderNumber(String orderNumber);

       List<Order> findByOrderStatusIn(List<Order.OrderStatus> statuses);

       List<Order> findByOrderStatusOrderByOrderDateDesc(Order.OrderStatus status);

       @Query("SELECT o FROM Order o WHERE o.orderStatus NOT IN ('COMPLETED', 'CANCELLED') " +
                     "ORDER BY o.orderDate ASC")
       List<Order> findActiveOrders();

       @Query("SELECT o FROM Order o WHERE o.orderDate >= :startDate AND o.orderDate <= :endDate " +
                     "ORDER BY o.orderDate DESC")
       List<Order> findOrdersByDateRange(@Param("startDate") LocalDateTime startDate,
                     @Param("endDate") LocalDateTime endDate);

       @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'COMPLETED' " +
                     "AND CAST(o.orderDate AS date) = CURRENT_DATE")
       Long countTodayCompletedOrders();

       @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'COMPLETED' " +
                     "AND CAST(o.orderDate AS date) = CURRENT_DATE")
       Double getTodayRevenue();

       List<Order> findTop10ByOrderByOrderDateDesc();
}

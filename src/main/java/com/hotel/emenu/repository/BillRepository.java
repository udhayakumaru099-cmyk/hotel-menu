package com.hotel.emenu.repository;

import com.hotel.emenu.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    
    Optional<Bill> findByBillNumber(String billNumber);
    
    Optional<Bill> findByOrderOrderId(Long orderId);
    
    Optional<Bill> findByOrderOrderNumber(String orderNumber);
}

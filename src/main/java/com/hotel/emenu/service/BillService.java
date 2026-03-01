package com.hotel.emenu.service;

import com.hotel.emenu.model.Bill;
import com.hotel.emenu.model.Order;
import com.hotel.emenu.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class BillService {

    private final BillRepository billRepository;

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }

    public Bill getBillByOrderId(Long orderId) {
        return billRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Bill not found for order id: " + orderId));
    }

    public Bill getBillByOrderNumber(String orderNumber) {
        return billRepository.findByOrderOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Bill not found for order number: " + orderNumber));
    }

    public Bill generateBill(Order order, BigDecimal subtotal, BigDecimal tax, 
                            BigDecimal serviceCharge, BigDecimal totalAmount) {
        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setBillNumber(generateBillNumber());
        bill.setSubtotal(subtotal);
        bill.setTaxAmount(tax);
        bill.setServiceCharge(serviceCharge);
        bill.setTotalAmount(totalAmount);
        bill.setPaymentMethod(Bill.PaymentMethod.PENDING);
        
        return billRepository.save(bill);
    }

    public Bill updatePaymentMethod(Long billId, Bill.PaymentMethod paymentMethod) {
        Bill bill = getBillById(billId);
        bill.setPaymentMethod(paymentMethod);
        
        if (paymentMethod != Bill.PaymentMethod.PENDING) {
            bill.setPaidAt(LocalDateTime.now());
        }
        
        return billRepository.save(bill);
    }

    private String generateBillNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDateTime.now().format(formatter);
        int randomPart = new Random().nextInt(9999) + 1;
        return "BILL" + datePart + String.format("%04d", randomPart);
    }
}

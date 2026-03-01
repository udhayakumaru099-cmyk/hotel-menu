package com.hotel.emenu.service;

import com.hotel.emenu.model.*;
import com.hotel.emenu.repository.MenuItemRepository;
import com.hotel.emenu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final BillService billService;
    
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal SERVICE_CHARGE_RATE = new BigDecimal("0.10");

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getActiveOrders() {
        return orderRepository.findActiveOrders();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found with order number: " + orderNumber));
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByOrderStatusOrderByOrderDateDesc(status);
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findTop10ByOrderByOrderDateDesc();
    }

    public Order createOrder(Order order, List<OrderItem> items) {
        // Generate order number
        order.setOrderNumber(generateOrderNumber());
        
        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;
        
        for (OrderItem item : items) {
            MenuItem menuItem = menuItemRepository.findById(item.getMenuItem().getItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            
            item.setItemName(menuItem.getItemName());
            item.setUnitPrice(menuItem.getPrice());
            item.setSubtotal(menuItem.getPrice().multiply(new BigDecimal(item.getQuantity())));
            subtotal = subtotal.add(item.getSubtotal());
            
            order.addOrderItem(item);
        }
        
        // Calculate total with tax and service charge
        BigDecimal tax = subtotal.multiply(TAX_RATE);
        BigDecimal serviceCharge = subtotal.multiply(SERVICE_CHARGE_RATE);
        BigDecimal totalAmount = subtotal.add(tax).add(serviceCharge);
        
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        
        Order savedOrder = orderRepository.save(order);
        
        // Generate bill
        billService.generateBill(savedOrder, subtotal, tax, serviceCharge, totalAmount);
        
        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setOrderStatus(newStatus);
        
        if (newStatus == Order.OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
            order.setPaymentStatus(Order.PaymentStatus.PAID);
        }
        
        return orderRepository.save(order);
    }

    public Order updateOrderStatusByOrderNumber(String orderNumber, Order.OrderStatus newStatus) {
        Order order = getOrderByOrderNumber(orderNumber);
        return updateOrderStatus(order.getOrderId(), newStatus);
    }

    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if (order.getOrderStatus() == Order.OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel completed order");
        }
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public Long getTodayOrderCount() {
        return orderRepository.countTodayCompletedOrders();
    }

    public Double getTodayRevenue() {
        Double revenue = orderRepository.getTodayRevenue();
        return revenue != null ? revenue : 0.0;
    }

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersByDateRange(startDate, endDate);
    }

    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDateTime.now().format(formatter);
        int randomPart = new Random().nextInt(9999) + 1;
        return "ORD" + datePart + String.format("%04d", randomPart);
    }
}

package com.hotel.emenu.controller;

import com.hotel.emenu.dto.OrderRequest;
import com.hotel.emenu.model.Bill;
import com.hotel.emenu.model.Order;
import com.hotel.emenu.service.BillService;
import com.hotel.emenu.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final BillService billService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerPhone(orderRequest.getCustomerPhone());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setTableNumber(orderRequest.getTableNumber());
        order.setSpecialInstructions(orderRequest.getSpecialInstructions());

        Order createdOrder = orderService.createOrder(order, orderRequest.getItems());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<Order> getOrderByNumber(@PathVariable String orderNumber) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderNumber}/bill")
    public ResponseEntity<Bill> getBillByOrderNumber(@PathVariable String orderNumber) {
        Bill bill = billService.getBillByOrderNumber(orderNumber);
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{orderNumber}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String orderNumber,
            @RequestParam String status) {
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        Order updatedOrder = orderService.updateOrderStatusByOrderNumber(orderNumber, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}

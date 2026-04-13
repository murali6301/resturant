package com.example.resturant.controller;

import com.example.resturant.model.OrderEntity;
import com.example.resturant.model.RestaurantTable;
import com.example.resturant.repository.OrderRepository;
import com.example.resturant.repository.TableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private TableRepository tableRepo;


    @GetMapping("/pending-orders")
    public List<OrderEntity> getPendingOrders() {

        return orderRepo.findByStatusIgnoreCase("pending");
    }

    @PutMapping("/mark-paid/{orderId}")
    public ResponseEntity<?> markAsPaid(@PathVariable Long orderId) {

        Optional<OrderEntity> optionalOrder = orderRepo.findById(orderId);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.badRequest().body("❌ Order not found");
        }

        OrderEntity order = optionalOrder.get();

        order.setStatus("paid");
        orderRepo.save(order);

        // 🔥 Free table
        Optional<RestaurantTable> optionalTable =
                tableRepo.findById(order.getTableId());

        if (optionalTable.isPresent()) {
            RestaurantTable table = optionalTable.get();
            table.setIsOccupied(false);
            tableRepo.save(table);
        }

        return ResponseEntity.ok("✅ Order marked as PAID & table freed");
    }
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        long totalOrders = orderRepo.count();

        long pendingOrders = orderRepo
                .findByStatusIgnoreCase("pending")
                .size();

        double totalRevenue = orderRepo.findAll()
                .stream()
                .filter(o -> "paid".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(OrderEntity::getTotalAmount)
                .sum();

        long freeTables = tableRepo.findAll()
                .stream()
                .filter(t -> t.getIsOccupied() == null || t.getIsOccupied().equals(false))
                .count();

        System.out.println("FREE TABLES COUNT = " + freeTables); // 🔥 debug

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", totalOrders);
        stats.put("pendingOrders", pendingOrders);
        stats.put("totalRevenue", totalRevenue);
        stats.put("freeTables", freeTables);

        return stats;
    }
}
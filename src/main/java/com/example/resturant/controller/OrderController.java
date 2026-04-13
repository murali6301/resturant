package com.example.resturant.controller;

import com.example.resturant.model.OrderEntity;
import com.example.resturant.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    private final ObjectMapper mapper = new ObjectMapper();

    // 🔥 CREATE ORDER
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> data) {

        try {
            if (data.get("tableId") == null || data.get("items") == null || data.get("totalAmount") == null) {
                return ResponseEntity.badRequest().body("Missing fields");
            }

            OrderEntity order = new OrderEntity();

            order.setTableId(Integer.parseInt(data.get("tableId").toString()));
            order.setItems(data.get("items").toString()); // already JSON string
            order.setTotalAmount(Double.parseDouble(data.get("totalAmount").toString()));

            order.setOrderCode("ORD" + System.currentTimeMillis());
            order.setStatus("pending");

            repo.save(order);

            Map<String, String> res = new HashMap<>();
            res.put("orderId", String.valueOf(order.getId()));
            res.put("tableId", String.valueOf(order.getTableId()));
            res.put("orderCode", order.getOrderCode());

            return ResponseEntity.ok(res);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 🔥 UPDATE ORDER (RESUME + ADD ITEMS)
    @PutMapping("/update/{code}")
    public ResponseEntity<?> updateOrder(@PathVariable String code,
                                         @RequestBody Map<String, Object> data) {

        try {
            OrderEntity order = repo.findByOrderCodeAndStatus(code, "pending");

            if (order == null) {
                return ResponseEntity.badRequest().body("Order not found");
            }

            String oldItems = order.getItems();
            String newItems = data.get("items").toString();

            List<Map<String, Object>> mergedItems = new ArrayList<>();

            // ✅ Parse old items
            if (oldItems != null && !oldItems.isEmpty()) {
                List<Map<String, Object>> oldList = mapper.readValue(
                        oldItems,
                        new TypeReference<List<Map<String, Object>>>() {}
                );
                mergedItems.addAll(oldList);
            }

            // ✅ Parse new items
            List<Map<String, Object>> newList = mapper.readValue(
                    newItems,
                    new TypeReference<List<Map<String, Object>>>() {}
            );

            mergedItems.addAll(newList);

            // ✅ Save merged JSON properly
            order.setItems(mapper.writeValueAsString(mergedItems));

            // ✅ Update total
            double oldTotal = order.getTotalAmount();
            double newTotal = Double.parseDouble(data.get("totalAmount").toString());
            order.setTotalAmount(oldTotal + newTotal);

            repo.save(order);

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 🔥 RESUME ORDER
    @GetMapping("/resume/{code}")
    public ResponseEntity<?> resumeOrder(@PathVariable String code) {

        try {
            OrderEntity order = repo.findByOrderCodeAndStatus(code, "pending");

            if (order == null) {
                return ResponseEntity.badRequest().body("Invalid Order Code");
            }

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 🔥 STATS
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<OrderEntity> orders = repo.findAll();

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOrders", orders.size());

        stats.put("pendingOrders",
                orders.stream().filter(o -> "pending".equals(o.getStatus())).count());

        stats.put("totalRevenue",
                orders.stream()
                        .filter(o -> "paid".equals(o.getStatus()))
                        .mapToDouble(OrderEntity::getTotalAmount)
                        .sum());

        stats.put("freeTables",
                orders.stream()
                        .filter(o -> "pending".equals(o.getStatus()))
                        .map(OrderEntity::getTableId)
                        .distinct()
                        .count());

        return stats;
    }
}
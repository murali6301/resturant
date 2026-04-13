package com.example.resturant.repository;

import com.example.resturant.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // 🔹 Find pending order by code
    OrderEntity findByOrderCodeAndStatus(String orderCode, String status);

    List<OrderEntity> findByStatusIgnoreCase(String status);
}
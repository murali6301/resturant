package com.example.resturant.repository;

import com.example.resturant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableRepository extends JpaRepository<RestaurantTable, Integer> {
    List<RestaurantTable> findAllByOrderByTableIdAsc();

    long countByIsOccupiedFalse();

    @Query(value = "SELECT COUNT(*) FROM tables WHERE is_occupied = 0", nativeQuery = true)
    long countFreeTables();
}
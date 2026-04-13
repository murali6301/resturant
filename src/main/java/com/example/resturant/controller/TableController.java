package com.example.resturant.controller;

import com.example.resturant.model.RestaurantTable;
import com.example.resturant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableRepository tableRepo;

    // Get all tables
    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return tableRepo.findAllByOrderByTableIdAsc();
    }

    // Select table
    @PostMapping("/select/{id}")
    public String selectTable(@PathVariable Integer id) {
        RestaurantTable table = tableRepo.findById(id).orElseThrow();
        if(table.getIsOccupied()) return "Occupied";
        table.setIsOccupied(true);
        tableRepo.save(table);
        return "Selected";
    }

    // Free table after billing
    @PostMapping("/free/{id}")
    public String freeTable(@PathVariable Integer id) {
        RestaurantTable table = tableRepo.findById(id).orElseThrow();
        table.setIsOccupied(false);
        tableRepo.save(table);
        return "Freed";
    }
}
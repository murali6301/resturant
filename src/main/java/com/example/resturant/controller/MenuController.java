package com.example.resturant.controller;
import java.util.List;

import com.example.resturant.model.Menu;
import com.example.resturant.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/menu")
@CrossOrigin("*")
public class MenuController {

 @Autowired
    private MenuRepository repo;

    @GetMapping
    public List<Menu> getMenu() {
        return repo.findAll();
    }
}
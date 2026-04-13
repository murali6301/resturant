package com.example.resturant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.resturant.model.Menu;



public interface MenuRepository extends JpaRepository<Menu, Long> {
}

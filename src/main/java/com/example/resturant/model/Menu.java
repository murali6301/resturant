package com.example.resturant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
    public class Menu {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        private int price;

        private double rating;

        @Column(name = "image_url")
        private String imageUrl;

        // 🔹 Default constructor
        public Menu() {
        }

        // 🔹 Parameterized constructor
        public Menu(Long id, String name, int price, double rating, String imageUrl) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.rating = rating;
            this.imageUrl = imageUrl;
        }

        // 🔹 Getters & Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }


package com.example.resturant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Integer tableId;

    @Column(name ="table_name")
    private String tableName;

    @Column(name = "is_occupied")
    private Boolean isOccupied;

    // Constructors
    public RestaurantTable() {}

    public RestaurantTable(String tableName, Boolean isOccupied) {
        this.tableName = tableName;
        this.isOccupied = isOccupied;
    }

    // Getters and Setters
    public Integer getTableId() { return tableId; }
    public void setTableId(Integer tableId) { this.tableId = tableId; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public Boolean getIsOccupied() { return isOccupied; }
    public void setIsOccupied(Boolean isOccupied) { this.isOccupied = isOccupied; }
}
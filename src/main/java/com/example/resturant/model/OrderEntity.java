package com.example.resturant.model;

import jakarta.persistence.*;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_id")
    private int tableId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "total_amount")
    private Double totalAmount;


    @Column(name = "items",columnDefinition = "TEXT", length = 2000)
    private String items;

    @Column(name = "status")
    private String status;

    // ✅ Getter & Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ✅ Getter & Setter for tableId
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    // ✅ Getter & Setter for orderCode
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    // ✅ Getter & Setter for totalAmount
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ✅ Getter & Setter for items
    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

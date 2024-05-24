package com.nathan.pharmacy.models;

import java.time.LocalDate;

//Medicament(medId, medName, medDesc, medPrice, medQuantity, #stockId)
public class Medicament {
    private int id;
    private String name;
    private String desc;
    private float price;
    private int quantity;
    private LocalDate expDate;
    private int stockId;

    public Medicament(int id, String name, String desc, float price, int quantity, int stockId,  LocalDate expDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.stockId = stockId;
        this.expDate = expDate;
    }
    public Medicament(String name, String desc, float price, int quantity, int stockId,  LocalDate expDate) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.stockId = stockId;
        this.expDate = expDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
}

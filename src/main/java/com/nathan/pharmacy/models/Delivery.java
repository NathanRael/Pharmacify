package com.nathan.pharmacy.models;

import java.time.LocalDate;

//Delivery( delId, #supId, #medId, delDate, delPrice, delQuantity)
public class Delivery {
    private int id;
    private LocalDate date;
    private float price;
    private int supId;
    private int medId;
    private int quantity;

    private String medName;
    private String supName;
    private LocalDate medExpDate;

    public Delivery(int id, LocalDate date, float price, int supId, int medId, int quantity) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
        this.quantity = quantity;
    }
    public Delivery(LocalDate date, float price, int supId, int medId, int quantity) {
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
        this.quantity = quantity;
    }

    public  Delivery(int id, LocalDate date, float price, int supId, int medId, int quantity, String medName, String supName){
        this.id = id;
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
        this.quantity = quantity;
        this.medName = medName;
        this.supName = supName;
    }
    public  Delivery(int id, LocalDate date, float price, int supId, int medId, int quantity, String medName, String supName, LocalDate medExpDate){
        this.id = id;
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
        this.quantity = quantity;
        this.medName = medName;
        this.supName = supName;
        this.medExpDate = medExpDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public LocalDate getMedExpDate() {
        return medExpDate;
    }

    public void setMedExpDate(LocalDate medExpDate) {
        this.medExpDate = medExpDate;
    }
}

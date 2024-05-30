package com.nathan.pharmacy.models;

import java.time.LocalDateTime;

//Purchase( purchaseId, #medId,purchaseDate, #patientId)
public class Purchase {
    private int id;
    private LocalDateTime date;
    private int quantity;
    private int medId;
    private int patientId;
    private  float totalPrice;

    private String medName;
    private String patName;


    public Purchase(int id, LocalDateTime date, int quantity, int medId, int patientId, float totalPrice) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.medId = medId;
        this.patientId = patientId;
        this.totalPrice = totalPrice;
    }
    public Purchase(LocalDateTime date, int quantity, int medId, int patientId, float totalPrice) {
        this.date = date;
        this.quantity = quantity;
        this.medId = medId;
        this.patientId = patientId;
        this.totalPrice = totalPrice;
    }
    public Purchase(int id, LocalDateTime date, int quantity, int medId, int patientId, float totalPrice, String medName, String patName) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.medId = medId;
        this.patientId = patientId;
        this.totalPrice = totalPrice;
        this.medName = medName;
        this.patName = patName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }
}

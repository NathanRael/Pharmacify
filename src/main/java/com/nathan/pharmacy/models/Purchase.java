package com.nathan.pharmacy.models;

import java.time.LocalDate;
import java.util.Date;

//Purchase( purchaseId, #medId,purchaseDate, #patientId)
public class Purchase {
    private int id;
    private LocalDate date;
    private int medId;
    private int patientId;
    private  float totalPrice;


    public Purchase(int id, LocalDate date, int medId, int patientId, float totalPrice) {
        this.id = id;
        this.date = date;
        this.medId = medId;
        this.patientId = patientId;
        this.totalPrice = totalPrice;
    }
    public Purchase(LocalDate date, int medId, int patientId, float totalPrice) {
        this.date = date;
        this.medId = medId;
        this.patientId = patientId;
        this.totalPrice = totalPrice;
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
}

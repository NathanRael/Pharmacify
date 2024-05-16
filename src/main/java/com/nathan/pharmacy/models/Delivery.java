package com.nathan.pharmacy.models;
//Delivery( delId, #supId, #medId, delDate, delPrice)
public class Delivery {
    private int id;
    private String date;
    private String price;
    private int supId;
    private int medId;

    public Delivery(int id, String date, String price, int supId, int medId) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
    }
    public Delivery( String date, String price, int supId, int medId) {
        this.date = date;
        this.price = price;
        this.supId = supId;
        this.medId = medId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
}

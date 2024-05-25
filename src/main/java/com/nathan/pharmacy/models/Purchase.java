package com.nathan.pharmacy.models;

import java.sql.Date;

//Purchase( purchaseId, #medId,purchaseDate, #patientId)
public class Purchase {
    private int id;
    private Date date;
    private int medId;


    public Purchase(int id, Date date, int medId) {
        this.id = id;
        this.date = date;
        this.medId = medId;
    }
    public Purchase(Date date, int medId) {
        this.date = date;
        this.medId = medId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }
}

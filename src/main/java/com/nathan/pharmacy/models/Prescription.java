package com.nathan.pharmacy.models;


import java.util.Date;

//Prescription (prescId, prescDate, prescDuration, prescDesc, #patientId)
public class Prescription {
    private int id;
    private Date date;
    private String duration;
    private String desc;
    private int patientId;


    public Prescription(int id, Date date, String duration, String desc, int patientId) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
    }
    public Prescription( Date date, String duration, String desc, int patientId) {
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}

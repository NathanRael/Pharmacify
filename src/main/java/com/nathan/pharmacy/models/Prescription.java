package com.nathan.pharmacy.models;


import java.time.LocalDateTime;

//Prescription (prescId, prescDate, prescDuration, prescDesc, #patientId)
public class Prescription {
    private int id;
    private LocalDateTime date;
    private String duration;
    private String desc;
    private int patientId;

    private String patientFName;


    public Prescription(int id, LocalDateTime date, String duration, String desc, int patientId) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
    }

    public Prescription( LocalDateTime date, String duration, String desc, int patientId) {
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
    }

    public Prescription(int id, LocalDateTime date, String duration, String desc, int patientId, String patientFName) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
        this.patientFName = patientFName;
    }
    public Prescription(LocalDateTime date, String duration, String desc, int patientId, String patientFName) {
        this.date = date;
        this.duration = duration;
        this.desc = desc;
        this.patientId = patientId;
        this.patientFName = patientFName;
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

    public String getPatientFName() {
        return patientFName;
    }

    public void setPatientFName(String patientFName) {
        this.patientFName = patientFName;
    }
}

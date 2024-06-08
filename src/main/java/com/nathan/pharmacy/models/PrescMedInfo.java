package com.nathan.pharmacy.models;

public class PrescMedInfo {
    private int morningQuantity;
    private int noonQuantity;
    private int afternoonQuantity;
    private String medName;
    private int duration;
    private int medPack;


    public PrescMedInfo(int morningQuantity, int noonQuantity, int afternoonQuantity, String medName, int duration, int medPack) {
        this.morningQuantity = morningQuantity;
        this.noonQuantity = noonQuantity;
        this.afternoonQuantity = afternoonQuantity;
        this.medName = medName;
        this.duration = duration;
        this.medPack = medPack;
    }

    public int getMorningQuantity() {
        return morningQuantity;
    }

    public void setMorningQuantity(int morningQuantity) {
        this.morningQuantity = morningQuantity;
    }

    public int getNoonQuantity() {
        return noonQuantity;
    }

    public void setNoonQuantity(int noonQuantity) {
        this.noonQuantity = noonQuantity;
    }

    public int getAfternoonQuantity() {
        return afternoonQuantity;
    }

    public void setAfternoonQuantity(int afternoonQuantity) {
        this.afternoonQuantity = afternoonQuantity;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMedPack() {
        return medPack;
    }

    public void setMedPack(int medPack) {
        this.medPack = medPack;
    }
}

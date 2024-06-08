package com.nathan.pharmacy.models;

import javafx.scene.control.TextField;

public class MedUsage {
    private TextField morningQuantity;
    private TextField noonQuantity;
    private TextField afternoonQuantity;

    private TextField medName;
    private TextField medPack;

    public MedUsage(TextField morningQuantity, TextField noonQuantity, TextField afternoonQuantity) {
        this.morningQuantity = morningQuantity;
        this.noonQuantity = noonQuantity;
        this.afternoonQuantity = afternoonQuantity;
    }
    public MedUsage(TextField morningQuantity, TextField noonQuantity, TextField afternoonQuantity, TextField medPack) {
        this.morningQuantity = morningQuantity;
        this.noonQuantity = noonQuantity;
        this.afternoonQuantity = afternoonQuantity;
        this.medPack = medPack;
    }

    
    public void clear(){
        morningQuantity.clear();
        afternoonQuantity.clear();
        noonQuantity.clear();
    }

    public String getMorningQuantity() {
        return morningQuantity.getText();
    }

    public TextField inputMorningQuantity(){
        return morningQuantity;
    }
    public TextField inputNoonQuantity(){
        return noonQuantity;
    }

    public TextField inputAfternoonQuantity(){
        return afternoonQuantity;
    }

    public void setMorningQuantity(int morningQuantity) {
        this.morningQuantity.setText(String.valueOf(morningQuantity));
    }

    public String getNoonQuantity() {
        return noonQuantity.getText();
    }

    public void setNoonQuantity(int noonQuantity) {
        this.noonQuantity.setText(String.valueOf(noonQuantity));
    }

    public String getAfternoonQuantity() {
        return afternoonQuantity.getText();
    }

    public void setAfternoonQuantity(int afternoonQuantity) {
        this.afternoonQuantity.setText(String.valueOf(afternoonQuantity));
    }

    public TextField medNameProperty() {
        return medName;
    }

    public TextField inputMedPack() {
        return medPack;
    }

    public String getMedPack(){
        return medPack.getText();
    }

    public void setMedPack(TextField medPack) {
        this.medPack = medPack;
    }
}

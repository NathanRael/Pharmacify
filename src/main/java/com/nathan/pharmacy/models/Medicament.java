package com.nathan.pharmacy.models;
//Medicament(medId, medName, medDesc, medPrice, medQuantity, #stockId)
public class Medicament {
    private int id;
    private String name;
    private String desc;
    private String price;
    private float quantity;
    private int stokId;

    public Medicament(int id, String name, String desc, String price, float quantity, int stokId) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.stokId = stokId;
    }
    public Medicament(String name, String desc, String price, float quantity, int stokId) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.stokId = stokId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getStokId() {
        return stokId;
    }

    public void setStokId(int stokId) {
        this.stokId = stokId;
    }
}

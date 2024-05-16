package com.nathan.pharmacy.models;
//Stock(stockId, srockName)
public class Stock {
    private int id;
    private String name;


    public Stock(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Stock( String name) {
        this.name = name;
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
}

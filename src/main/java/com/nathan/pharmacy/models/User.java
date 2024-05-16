package com.nathan.pharmacy.models;
//User(userId, userName,userPwd, userAddress, userPhone, #stockId)
public class User {
    private int id;
    private String name;
    private String pwd;
    private String address;
    private String phone;
    private int stockId;

    public User(int id, String name, String pwd, String address, String phone, int stockId) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.address = address;
        this.phone = phone;
        this.stockId = stockId;
    }
    public User(String name, String pwd, String address, String phone, int stockId) {
        this.name = name;
        this.pwd = pwd;
        this.address = address;
        this.phone = phone;
        this.stockId = stockId;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
}

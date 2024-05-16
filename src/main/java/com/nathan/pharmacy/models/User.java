package com.nathan.pharmacy.models;
//User(userId, userName,userPwd, userStatus, userPhone, #stockId)
public class User {
    private int id;
    private String name;
    private String pwd;
    private String status;
    private String phone;
    private int stockId;

    public User(int id, String name, String pwd, String status, String phone, int stockId) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.status = status;
        this.phone = phone;
        this.stockId = stockId;
    }
    public User(String name, String pwd,  String phone, int stockId, String status) {
        this.name = name;
        this.pwd = pwd;
        this.status = status;
        this.phone = phone;
        this.stockId = stockId;
    }
    public User(String name,  String phone, String pwd) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
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

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
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

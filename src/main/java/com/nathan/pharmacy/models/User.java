package com.nathan.pharmacy.models;
//User(userId, userName,userPwd, userStatus, userPhone, #stockId)
public class User {
    private int id;
    private String name;
    private String pwd;
    private String status;
    private String role;
    private String phone;
    private int stockId;
    private String email;

    private String stockName;


    public User(int id, String name, String status, String role, String phone, int stockId, String stockName, String email) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.role = role;
        this.phone = phone;
        this.stockId = stockId;
        this.stockName = stockName;
        this.email = email;
    }
    public User(String name,  String phone, String pwd, String email) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
    }

    public User(int id, String name, String pwd, String status, String phone, int stockId) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.status = status;
        this.phone = phone;
        this.stockId = stockId;
    }

/*    public User(String name, String pwd,  String phone, int stockId, String status) {
        this.name = name;
        this.pwd = pwd;
        this.status = status;
        this.phone = phone;
        this.stockId = stockId;
    }

    public User(String name, String status, String role, String phone, int stockId,  String stockName) {
        this.name = name;
        this.status = status;
        this.phone = phone;
        this.stockId = stockId;
        this.stockName = stockName;
        this.role = role;
    }*/

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

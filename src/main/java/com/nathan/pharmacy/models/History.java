package com.nathan.pharmacy.models;

import java.time.LocalDateTime;

public class History {
    private String date;
    private String userName;
    private String action;

    public History(String date, String userName, String action) {
        this.date = date;
        this.userName = userName;
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "History{" +
                "date=" + date +
                ", name='" + userName + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

}

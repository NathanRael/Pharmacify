package com.nathan.pharmacy.utils;

public class Session {
    private static Session instance = null;
    private String userName = null;
    private String userRole = null;
    private Session(){
        userRole = "admin";
    }

    public static synchronized Session getInstance(){
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void clear(){
        userRole = null;
        userName = null;
    }

    public boolean sessionExist(){
        return userRole != null && userName != null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}


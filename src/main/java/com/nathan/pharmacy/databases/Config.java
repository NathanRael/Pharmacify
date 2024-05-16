package com.nathan.pharmacy.databases;

public interface Config {
    public final String HOST = "localhost";
    public final int PORT = 3000;
    public final String DBNAME = "pharmacify";
    public final String URL = "jdbc:mysql://" + HOST + "/" + DBNAME;
    public final String USER  = "root";
    public final String PASSWORD = "";


}

package com.nathan.pharmacy.utils;

public class HistoryUtil {
    public static void pushHistory(String userName, String action){
        HistoryManager.getInstance().push(userName, action);
    }
}

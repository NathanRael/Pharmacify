package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.purchase.PurchaseModelController;

import java.sql.ResultSet;
import java.time.LocalDate;

public class PurchaseUtil {
    public static String getMostPurchasedMedicament() throws Exception {
        PurchaseModelController pc = new PurchaseModelController();
        ResultSet rs = pc.selectMostPurchasedProduct(1, LocalDate.now().toString());
        rs.next();
        return rs.getString("medName");
    }
}

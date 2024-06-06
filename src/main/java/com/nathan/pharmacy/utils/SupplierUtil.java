package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.supplier.SupplierModelController;

import java.sql.ResultSet;

public class SupplierUtil {

    public static int getSupId(String supName) throws Exception {
        SupplierModelController sc = new SupplierModelController();
        ResultSet rs = sc.selectBy("supName", supName);
        rs.next();
        return rs.getInt("supId");
    }

    public static String getFavoriteSupplier() throws Exception {
        SupplierModelController sc = new SupplierModelController();
        ResultSet rs = sc.selectFavoriteSupplier();
        rs.next();
        return  rs.getString("supName");
    }
}

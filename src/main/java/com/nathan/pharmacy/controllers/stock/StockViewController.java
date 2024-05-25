package com.nathan.pharmacy.controllers.stock;

import com.nathan.pharmacy.models.Stock;

import java.sql.ResultSet;
import java.util.List;

public class StockViewController {

    public static void setStockInfo(List<Stock> stockInfo){
        try {
            StockModelController sc = new StockModelController();
            ResultSet rs = sc.selectAll();

            while (rs.next()){
                stockInfo.add(new Stock(rs.getInt("stockId"), rs.getString("stockName")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

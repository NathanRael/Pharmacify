package com.nathan.pharmacy.interfaces;

import com.nathan.pharmacy.databases.ConnectionDb;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.utils.ValidationUtil;

import java.sql.ResultSet;

public interface ModelInterface<E> {
    public ResultSet selectAll() throws Exception;
    public ResultSet selectBy(String colName, String value) throws Exception;
    public void update(E obj) throws Exception;
    public int getCount() throws Exception;
    public void delete(int id) throws Exception;
    public void insert(E obj) throws Exception;
    public void updateBy(Object ...rows) throws Exception;
    public void deleteBy(String colName, String value) throws Exception;
    public default String purifyValue(Object value){
        return ValidationUtil.purifyValue(value);
    }

}

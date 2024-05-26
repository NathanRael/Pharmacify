package com.nathan.pharmacy.test;

import com.nathan.pharmacy.interfaces.ModelInterface;
import com.nathan.pharmacy.models.User;

import java.sql.ResultSet;

public class testViewController implements ModelInterface<User> {


    @Override
    public ResultSet selectAll() throws Exception {
        return null;
    }

    @Override
    public ResultSet selectBy(String colName, String value) throws Exception {
        return null;
    }

    @Override
    public void update(User obj) throws Exception {

    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public void delete(int id) throws Exception {

    }


    @Override
    public void insert(User obj) throws Exception {

    }

    @Override
    public void updateBy(Object... rows) throws Exception {

    }

    @Override
    public void deleteBy(String colName, String value) throws Exception {

    }
}

package com.nathan.pharmacy.test;

import com.nathan.pharmacy.controllers.user.UserController;
import com.nathan.pharmacy.controllers.form.classes.IsValidFields;
import com.nathan.pharmacy.controllers.form.classes.ValidPassword;
import com.nathan.pharmacy.controllers.form.classes.ValidPhone;
import com.nathan.pharmacy.controllers.form.classes.ValidText;
import com.nathan.pharmacy.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class UserTest {
    public UserTest(){};

    public static void main(String[] args) throws Exception {
        select();

    }
    public static void update(){

    }
    public static void select() throws Exception {
        UserController uc = new UserController();
        String[] tableRows = uc.getTableRows();
        int len = uc.getCount();
        ResultSet rs = uc.selectAll();
        Object[][] users = new Object[len][tableRows.length];
        ObservableList<User> allUser = FXCollections.observableArrayList();

        int i = 0;
        while (rs.next()){
            for (int j = 0; j < tableRows.length; j++){
                users[i][j] = rs.getString(tableRows[j]);
            }
//            allUser.add(new User());
            allUser.add(new User(rs.getInt("userId"),rs.getString("userName"), rs.getString("userPwd"),rs.getString("userStatus"), rs.getString("userPhone"), rs.getInt("userStatus")));
            i++;
        }

        for (User u : allUser){
            System.out.println(u.toString());
        }

    }

    public static void insert(){
        String name = "NathanBlast";
        String phone = "0341234560";
        String password = "admin1234";

        boolean allFieldValidated = IsValidFields.isValidFields(new ValidText(name), new ValidPhone(phone), new ValidPassword(password));
        if (allFieldValidated){
            try{
                User user = new User(name,phone, password);
                UserController uc = new UserController();

                uc.insert(user);
                System.out.println("Inscription réussie");

            }catch (Exception ex){

                System.err.println(ex);
            }
        }else {
            System.out.println("Erreur de champ");
        }
    }
}
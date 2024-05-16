package com.nathan.pharmacy.test;

import com.nathan.pharmacy.controllers.UserController;
import com.nathan.pharmacy.controllers.form.classes.IsValidFields;
import com.nathan.pharmacy.controllers.form.classes.ValidPassword;
import com.nathan.pharmacy.controllers.form.classes.ValidPhone;
import com.nathan.pharmacy.controllers.form.classes.ValidText;
import com.nathan.pharmacy.models.User;

public class UserTest {
    public UserTest(){};

    public static void main(String[] args) {
        isnert();

    }

    public static void isnert(){
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

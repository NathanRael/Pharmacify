package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.user.UserModelController;

import java.sql.ResultSet;

public class UserUtil {
    public static int getUserIdByEmail(String email) throws Exception {
        UserModelController uc = new UserModelController();
        ResultSet rs = uc.selectBy("userEmail", email);
        rs.next();
        return rs.getInt("userId");
    }

    public static boolean emailExist(String email){
        try {
            UserModelController uc = new UserModelController();
            ResultSet rs = uc.selectBy("userEmail", email);
            if (rs.next())
                return true;
        }catch (Exception ex){
            return false;
        }
        return false;
    }
}

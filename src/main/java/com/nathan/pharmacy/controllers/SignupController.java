package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.controllers.form.classes.IsValidFields;
import com.nathan.pharmacy.controllers.form.classes.ValidPassword;
import com.nathan.pharmacy.controllers.form.classes.ValidPhone;
import com.nathan.pharmacy.controllers.form.classes.ValidText;
import com.nathan.pharmacy.controllers.form.interfaces.FieldsController;
import com.nathan.pharmacy.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupController {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private PasswordField inputConfirm;

    @FXML
    private TextField inputName;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputPhone;

    @FXML
    private Label txtConfirm;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;

    @FXML
    private Label txtPhone;

    @FXML
    void login(ActionEvent event) throws Exception {
    }


    @FXML
    void signup(ActionEvent event) {
        String name = inputName.getText();
        String phone = inputPhone.getText().trim();
        String password = inputPassword.getText();
        String confirm = inputConfirm.getText();

        boolean allFieldValidated = IsValidFields.isValidFields(new ValidText(name), new ValidPhone(phone), new ValidPassword(password));

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        if (allFieldValidated){
            if (isPasswordConfirmed(password, confirm)){
                try{
                    User user = new User(name,phone, password);
                    UserController uc = new UserController();

                    uc.insert(user);
                    alert.setContentText("Inscription réussie");
                    alert.showAndWait();

                }catch (Exception ex){

                    System.err.println(ex);
                }

            }else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Le mot de passe ne correspond pas au précédent");
                alert.showAndWait();
            }
        }else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Vérifier vos champs");
            alert.showAndWait();
        }

    }

    public static boolean isPasswordConfirmed(String password, String confirm){
        return confirm.equals(password);
    }

}

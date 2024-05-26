package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.controllers.form.ValidPassword;
import com.nathan.pharmacy.controllers.form.ValidPhone;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.User;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController  implements Initializable {
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
    private AnchorPane signupAnchorPane;

    @FXML
    private Label txtConfirm;

    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;

    @FXML
    private Label txtPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void signup(ActionEvent event) {
        String name = inputName.getText();
        String phone = inputPhone.getText().trim();
        String password = inputPassword.getText();
        String confirm = inputConfirm.getText();

        boolean allFieldValidated = validText(inputName, new ValidText()) && validText(inputPhone, new ValidPhone()) && validText(inputPassword, new ValidPassword());

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        if (allFieldValidated){
            if (isPasswordConfirmed(password, confirm)){
                try{
                    User user = new User(name,phone, password);
                    UserModelController uc = new UserModelController();

                    uc.insert(user);
                    alert.setContentText("Inscription réussie");
                    alert.showAndWait();
                    switchSceneTo(ScenesName.LOGIN);

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

    @FXML
    void switchToLogin(ActionEvent event) {
        switchSceneTo(ScenesName.LOGIN);
    }

    public static boolean isPasswordConfirmed(String password, String confirm){
        return confirm.equals(password);
    }

    public void switchSceneTo(ScenesName scenesName) {
        Stage currentStage = (Stage)btnLogin.getScene().getWindow();
        SceneChanger.changeSceneTo(scenesName, currentStage);
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }

}

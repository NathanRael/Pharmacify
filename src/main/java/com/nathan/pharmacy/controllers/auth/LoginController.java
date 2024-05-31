package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.controllers.SceneChanger;
import com.nathan.pharmacy.controllers.Session;
import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidPassword;
import com.nathan.pharmacy.controllers.form.ValidPhone;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private TextField inputName;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private AnchorPane loginAnchorPane;


    @FXML
    private Label txtName;

    @FXML
    private Label txtPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<KeyEvent> keyEventEventHandler = event -> updateButtonState();
        inputName.setOnKeyTyped(keyEventEventHandler);
        inputPassword.setOnKeyTyped(keyEventEventHandler);
    }
    @FXML
    void login(ActionEvent event) throws Exception {
        boolean currentUserFound = false;
        String name = inputName.getText();
        String password = inputPassword.getText();
        Map<String, Object> userInfo = new HashMap<>();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");


        UserModelController uc = new UserModelController();
        ResultSet user;
        user = uc.selectBy("userName", name);
        while (user.next()){
            if (user.getString("userName").equalsIgnoreCase(name)){
                userInfo.put("id", user.getString("userId"));
                userInfo.put("name", user.getString("userName"));
                userInfo.put("password", user.getString("userPwd"));
                userInfo.put("role", user.getString("userRole"));
                currentUserFound = true;
                break;
            }
        }

        if (currentUserFound){
            if (password.equals(userInfo.get("password"))){
                alert.setContentText("Redirection ...");
                Session.getInstance().setUserName((String) userInfo.get("name"));
                Session.getInstance().setUserRole((String) userInfo.get("role"));
                alert.showAndWait();
                switchSceneTo(ScenesName.MAIN);
            }else{
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Mot de passe incorrect");
                alert.showAndWait();
            }
        }else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Utilisateur non trouvé");
            alert.showAndWait();
        }
    }

    @FXML
    void switchToSignup(ActionEvent event) {
       switchSceneTo(ScenesName.SIGNUP);
    }

    public void switchSceneTo(ScenesName scenesName) {
        Stage currentStage = (Stage)btnLogin.getScene().getWindow();
        SceneChanger.changeSceneTo(scenesName, currentStage);
    }
    private void updateButtonState(){
        boolean allFieldValidated = validText(inputName, new ValidName()) && validText(inputPassword, new ValidPassword());

        btnLogin.setDisable(!allFieldValidated);
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }


}

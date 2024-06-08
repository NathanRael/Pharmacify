package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.contstants.MessageStyle;
import com.nathan.pharmacy.utils.MessageField;
import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.utils.Session;
import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidPassword;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
    private Label txtMessage;

    @FXML
    private Label txtPassword;

    private  MessageField messageField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageField = new MessageField(txtMessage);

        txtMessage.setVisible(false);
        EventHandler<KeyEvent> keyEventEventHandler = event -> updateButtonState();

        inputName.setOnKeyTyped(keyEventEventHandler);
        inputPassword.setOnKeyTyped(keyEventEventHandler);


    }

    @FXML
    void handleKeyPressed(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER){
            try {
                login(new ActionEvent());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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
                userInfo.put("role", user.getInt("userRole"));
                userInfo.put("status", user.getInt("userStatus"));
                currentUserFound = true;
                break;
            }
        }

        if (currentUserFound){
            if ((int)userInfo.get("status") == 1){
                if (password.equals(userInfo.get("password"))){
                    messageField.setMessage("Redirection ...", MessageStyle.SUCCESS);
                    Session.getInstance().setUserName(userInfo.get("name").toString());
                    Session.getInstance().setUserRole(String.valueOf(userInfo.get("role")));
                    switchSceneTo(ScenesName.MAIN);
                }else{
                    messageField.setMessage("Mot de passe incorrecte", MessageStyle.ERROR);
                    alert.showAndWait();
                }
            }else {
                messageField.setMessage("Votre compte n'est pas encore approuvé", MessageStyle.ERROR);
                System.out.println("Your account is still waiting for approbation");
            }
        }else {
            messageField.setMessage("Utilisateur non trouvé", MessageStyle.ERROR);
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

        if (!allFieldValidated) {
            messageField.setMessage("Vérifier vos champs", MessageStyle.ERROR);
        }else {
            messageField.hide();
        }
        btnLogin.setDisable(!allFieldValidated);
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }


}

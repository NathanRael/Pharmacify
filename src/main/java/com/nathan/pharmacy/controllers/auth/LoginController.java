package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.controllers.form.ValidEmail;
import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.contstants.AlertType;
import com.nathan.pharmacy.contstants.MailType;
import com.nathan.pharmacy.contstants.MessageStyle;
import com.nathan.pharmacy.utils.*;
import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.form.ValidPassword;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.interfaces.FieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.util.concurrent.ThreadLocalRandom;

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

    //Forgotten password
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputValidationCode;
    @FXML
    private TextField inputNewPassword;
    @FXML
    private Button btnValidate;
    @FXML
    private Label textForgottenPassword;
    @FXML
    private AnchorPane forgottenPasswordPopup;
    @FXML
    private FontAwesomeIconView btnClose;
    @FXML
    private Button btnSendValidationCode;
    @FXML
    private Button btnVerifyCode;
    private String validationCode;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageField = new MessageField(txtMessage);
        forgottenPasswordPopup.setVisible(false);
        inputNewPassword.setDisable(true);
        inputValidationCode.setDisable(true);


        textForgottenPassword.setOnMouseClicked(event -> forgottenPasswordPopup.setVisible(true));
        btnClose.setOnMouseClicked(event -> forgottenPasswordPopup.setVisible(false));
        btnSendValidationCode.setOnAction(event -> sendValidationCode());
        btnValidate.setOnAction(event -> modifyPassword());
        btnVerifyCode.setOnAction(event -> verifyValidationCode());
        txtMessage.setVisible(false);
        handleKeyEvent();

    }

    private void sendValidationCode() {
        validationCode = generateValidationCode(5);
        String userEmail = inputEmail.getText();
        if (UserUtil.emailExist(userEmail)){
            System.out.println(validationCode);
            MailSender.sendMailTo(userEmail,"Code de validation", "Votre code de validation est : " + validationCode , MailType.TEXT_HTML  );
            inputEmail.setDisable(true);
            btnSendValidationCode.setDisable(true);

            btnVerifyCode.setDisable(false);
            inputValidationCode.setDisable(false);
        }else {
            AlertUtils.showAlert("L'email n'est pas dans la base de donnée", AlertType.ERROR);
        }
    }

    private String generateValidationCode(int length){
        StringBuilder code = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i< length; i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private void modifyPassword() {
        try {
            UserModelController uc = new UserModelController();
            int userId = UserUtil.getUserIdByEmail(inputEmail.getText());
            uc.updateBy("userPwd", inputNewPassword.getText(), "userId", userId);
            AlertUtils.showAlert("Mot de passe modifé avec succes", AlertType.SUCCESS);
            forgottenPasswordPopup.setVisible(false);
            updateButtonState();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void  verifyValidationCode(){
        String inputValidation = inputValidationCode.getText();
        if (inputValidation.equals(validationCode)){
            btnVerifyCode.setDisable(true);
            inputValidationCode.setDisable(true);
            inputNewPassword.setDisable(false);
        }else {
            AlertUtils.showAlert("Code invalide", AlertType.ERROR);
            btnVerifyCode.setDisable(false);
            btnValidate.setDisable(true);
            inputNewPassword.setDisable(true);
        }
    }

    private void handleKeyEvent(){
        EventHandler<KeyEvent> keyEventEventHandler = event -> updateButtonState();

        inputName.setOnKeyTyped(keyEventEventHandler);
        inputPassword.setOnKeyTyped(keyEventEventHandler);
        inputEmail.setOnKeyTyped(keyEventEventHandler);
        inputValidationCode.setOnKeyTyped(keyEventEventHandler);
        inputNewPassword.setOnKeyTyped(keyEventEventHandler);
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
                userInfo.put("email", user.getString("userEmail") );
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
        boolean validPasswordRecovery = validText(inputEmail, new ValidEmail()) && validText(inputValidationCode, new ValidNumber()) && validText(inputNewPassword, new ValidPassword());
        if (!allFieldValidated) {
            messageField.setMessage("Vérifier vos champs", MessageStyle.ERROR);
        }else {
            messageField.hide();
        }

        btnValidate.setDisable(!validPasswordRecovery);
        btnVerifyCode.setDisable(!validText(inputValidationCode, new ValidNumber()) || inputValidationCode.isDisabled());
        btnSendValidationCode.setDisable(!validText(inputEmail, new ValidEmail()) || inputEmail.isDisabled());
        btnLogin.setDisable(!allFieldValidated );
    }

    public boolean validText(TextField textField, FieldValidator fieldValidator){
        return ValidationUtil.validTextField(textField, fieldValidator);
    }


}

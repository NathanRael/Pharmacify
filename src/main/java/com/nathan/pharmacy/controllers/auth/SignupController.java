package com.nathan.pharmacy.controllers.auth;

import com.nathan.pharmacy.contstants.MessageStyle;
import com.nathan.pharmacy.utils.MessageField;
//import com.nathan.pharmacy.utils.QrCodeUtil;
import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.controllers.form.ValidName;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.controllers.form.ValidPassword;
import com.nathan.pharmacy.controllers.form.ValidPhone;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.contstants.ScenesName;
import com.nathan.pharmacy.interfaces.FieldValidator;
import com.nathan.pharmacy.models.User;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController  implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private Button btnContinue;

    @FXML
    private AnchorPane qrCodeContainer;

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

    @FXML
    private Label txtMessage;
    private MessageField messageField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageField = new MessageField(txtMessage);
        txtMessage.setVisible(false);
        qrCodeContainer.setVisible(false);
        btnContinue.setOnAction(event -> switchToLogin(null));

        EventHandler<KeyEvent> keyEventEventHandler = event -> updateButtonState();

        inputName.setOnKeyTyped(keyEventEventHandler);
        inputPhone.setOnKeyTyped(keyEventEventHandler);
        inputConfirm.setOnKeyTyped(keyEventEventHandler);
        inputPassword.setOnKeyTyped(keyEventEventHandler);
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
                    messageField.setMessage("Inscription réussie", MessageStyle.SUCCESS);
                    alert.setContentText("Inscription réussie");
                    alert.showAndWait();
//                    showPopupAndGenerateQrCode(user.getName() + ";" + user.getPwd(),200, 200, user.getName() + "QrCode" );
                    switchSceneTo(ScenesName.LOGIN);
                }catch (Exception ex){

                    System.err.println(ex);
                }

            }else {
                messageField.setMessage("Le mot de passe ne correspond pas au précédent", MessageStyle.ERROR);
            }
        }else {
            messageField.setMessage("Vérifier les champs", MessageStyle.SUCCESS);
        }

    }

    private void showPopupAndGenerateQrCode(String value, int width, int height, String fileName){
        String filePath = "QrCodeUtil.qrCodePath" + fileName;
        try {
//            QrCodeUtil.writeQRCodeImageToFile(value, width, height , filePath);
            qrCodeImageView.setImage(new Image(filePath));
            qrCodeContainer.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private void updateButtonState(){
        boolean allFieldValidated = validText(inputName, new ValidName()) && validText(inputPhone, new ValidPhone()) && validText(inputPassword, new ValidPassword()) && inputPassword.getText().equals(inputConfirm.getText());
        if (!allFieldValidated) {
            messageField.setMessage("Vérifier vos champs", MessageStyle.ERROR);
        }else {
            messageField.hide();
        }
        btnSignup.setDisable(!allFieldValidated);
    }

}

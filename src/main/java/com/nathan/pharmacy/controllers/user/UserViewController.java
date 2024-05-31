package com.nathan.pharmacy.controllers.user;

import com.nathan.pharmacy.controllers.form.ValidNumber;
import com.nathan.pharmacy.controllers.form.ValidText;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.stock.StockModelController;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.User;
import com.nathan.pharmacy.utils.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private TableColumn<User, Integer> colStockId;

    @FXML
    private TableColumn<User, String> colStockName;

    @FXML
    private TableColumn<User, Integer> colUserId;

    @FXML
    private TableColumn<User, String> colUserName;

    @FXML
    private TableColumn<User, String> colUserPhone;

    @FXML
    private TableColumn<User, String> colUserRole;

    @FXML
    private TableColumn<User, String> colUserStatus;

    @FXML
    private TextField inputUserSearch;

    @FXML
    private ChoiceBox<String> selectStockName;

    @FXML
    private ChoiceBox<String> selectUserFilter;

    @FXML
    private ChoiceBox<String> selectUserRole;

    @FXML
    private ChoiceBox<String> selectUserStatus;

    @FXML
    private TableView<User> tableUser;

    private List<User> currSelectedUserRow = new ArrayList<>();
    @FXML
    void handleKeyPressed(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEdit.setOnAction(event -> modifyUser());
        btnDelete.setOnAction(event -> deleteUser(currSelectedUserRow.get(0).getId()));


        initSelect();
        initTableView();
        listenTextFieldEvent();
        try {
            loadTableContent();
            tableUser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
                if (newSelection != null){
                    handleUserRowSelected(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void listenTextFieldEvent() {
        inputUserSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                try {
                    loadTableContent();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                searchUser();
            }
        });

    }


    private void initSelect(){
        selectUserFilter.getItems().addAll("id", "nom");
        selectUserRole.getItems().addAll("Utilisateur", "superUtilisateur");
        selectUserStatus.getItems().addAll("Approuvé", "En attente");

        selectUserFilter.getSelectionModel().select(0);

        try{
            selectStockName.getItems().clear();
            StockModelController sc = new StockModelController();
            ResultSet rs = sc.selectAll();
            while (rs.next()){
                selectStockName.getItems().add(rs.getString("stockName"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void modifyUser(){
        try{
            int userId = currSelectedUserRow.get(0).getId();
            int userRole = getRole(selectUserRole.getSelectionModel().getSelectedItem());
            int userStatus = getStatus(selectUserStatus.getSelectionModel().getSelectedItem());
            int stockId = getStockId(selectStockName.getSelectionModel().getSelectedItem());


            UserModelController uc = new UserModelController();
            uc.updateBy("userRole", userRole, "userStatus", userStatus, "stockId", stockId, "userId", userId);
            loadTableContent();
            currSelectedUserRow.clear();
            updateButtonState();

            System.out.println("user updated");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void searchUser() {
        String search = inputUserSearch.getText();
        String filterMode = selectUserFilter.getSelectionModel().getSelectedItem();

        try {
            ObservableList<User> users = FXCollections.observableArrayList();
            UserModelController uc = new UserModelController();
            ResultSet rs = null;
            switch (filterMode) {
                case "Id" -> rs = uc.searchJoin("userId", search);
                case "Nom" -> rs = uc.searchJoin("userName", search);
                default -> rs = uc.searchJoin("userId", search);
            }

            while(rs.next()){
                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String userPhone = rs.getString("userPhone");
                int stockId = rs.getInt("stockId");
                String userStatus = rs.getInt("userStatus") == 1 ? "Approuvé" : "En attente" ;
                String userRole = rs.getInt("userRole") == 0 ? "Admin" : "SuperAdmin";
                String stockName = rs.getString("stockName");
                users.add(new User(userId, userName, userStatus, userRole ,userPhone, stockId, stockName));
            }
            tableUser.setItems(users);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private int getStockId(String stockName){
        try{
            StockModelController sc = new StockModelController();
            ResultSet rs = sc.selectBy("stockName", stockName);
            rs.next();
            return rs.getInt("stockId");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    private int getStatus(String status){
        return status.equalsIgnoreCase("approuvé") ? 1 : 0;
    }

    private int getRole(String role){
        return role.equalsIgnoreCase("Utilisateur") ? 0 : 1;
    }

    private void deleteUser(int id){
        try{
            UserModelController uc = new UserModelController();
            uc.delete(id);
            loadTableContent();
            currSelectedUserRow.clear();
            System.out.println("User deleted");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void initTableView(){
        try{
            colUserId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
            colUserName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            colUserRole.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
            colUserPhone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
            colUserStatus.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
            colStockId.setCellValueFactory(new PropertyValueFactory<User, Integer>("stockId"));
            colStockName.setCellValueFactory(new PropertyValueFactory<User, String>("stockName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void loadTableContent() throws Exception {
        ObservableList<User> users = FXCollections.observableArrayList();
        UserModelController mc = new UserModelController();

        ResultSet rs = mc.selectJoin();

        while (rs.next()){
            int userId = rs.getInt("userId");
            String userName = rs.getString("userName");
            String userPhone = rs.getString("userPhone");
            int stockId = rs.getInt("stockId");
            String userStatus = rs.getInt("userStatus") == 1 ? "Approuvé" : "En attente" ;
            String userRole = rs.getInt("userRole") == 0 ? "Admin" : "SuperAdmin";
            String stockName = rs.getString("stockName");
            users.add(new User(userId, userName, userStatus, userRole ,userPhone, stockId, stockName));
        }

        tableUser.setItems(users);
    }

    private void handleUserRowSelected(User newSelection) {
        updateCurrSelectedUserRow(newSelection);
        setFieldsValue(newSelection);
        updateButtonState();
    }

    private void updateCurrSelectedUserRow(User newSelection){
        currSelectedUserRow.clear();
        currSelectedUserRow.add(new User(newSelection.getId(), newSelection.getName(), newSelection.getStatus(), newSelection.getRole(), newSelection.getPhone(), newSelection.getStockId(), newSelection.getStockName()));
    }
    private void setFieldsValue(User currentSelection){
        selectUserRole.setValue(currentSelection.getRole());
        selectUserStatus.setValue(currentSelection.getStatus());
        selectStockName.setValue(currentSelection.getStockName());
    }


    private void updateButtonState() {
        boolean canDelete = false;

        if ( !currSelectedUserRow.isEmpty())
            canDelete = currSelectedUserRow.get(0).getId() > 0;

        btnDelete.setDisable(!canDelete);
        btnEdit.setDisable(!canDelete);
    }
}

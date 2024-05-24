package com.nathan.pharmacy.controllers.medicament;

import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class MedicamentController implements Initializable {

    @FXML
    private Button btnAddMed;

    @FXML
    private Button btnDeleteMed;

    @FXML
    private Button btnEditMed;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Medicament, String> colDesc;

    @FXML
    private TableColumn<Medicament, LocalDate> colExpireDate;

    @FXML
    private TableColumn<Medicament, Integer> colId;

    @FXML
    private TableColumn<Medicament, String> colName;

    @FXML
    private TableColumn<Medicament, Float> colPrice;

    @FXML
    private TableColumn<Medicament, Integer> colQuantity;

    @FXML
    private TableColumn<Medicament, Integer> colStockId;

    @FXML
    private TextField inputMedDesc;

    @FXML
    private TextField inputMedName;

    @FXML
    private TextField inputMedPrice;

    @FXML
    private ChoiceBox<String> selectStockId;

    @FXML
    private TextField inputSearch;

    @FXML
    private ChoiceBox<String> selectMedFilter;

    @FXML
    private TableView<Medicament> tableMedicament;

    ObservableList<Medicament> medicaments = FXCollections.observableArrayList(
            new Medicament(1, "Paracetamol", "Blabla", 300, 120, 3, LocalDate.now()),
            new Medicament(2, "Paracetamol", "Blabla", 300, 120, 3, LocalDate.now())
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMedFilter.getItems().addAll("Prix", "Date");
        initTableView();

    }
    public void initTableView(){
        try{
            colId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("id"));
            colStockId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("stockId"));
            colName.setCellValueFactory(new PropertyValueFactory<Medicament, String>("name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<Medicament, Float>("price"));
            colDesc.setCellValueFactory(new PropertyValueFactory<Medicament, String>("desc"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("quantity"));
            colExpireDate.setCellValueFactory(new PropertyValueFactory<Medicament, LocalDate>("expDate"));
            tableMedicament.setItems(medicaments);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

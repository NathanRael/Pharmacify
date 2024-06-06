package com.nathan.pharmacy.controllers.history;

import com.nathan.pharmacy.models.History;
import com.nathan.pharmacy.utils.HistoryManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryViewController implements Initializable {

    @FXML
    private TableColumn<History, LocalDateTime> colHistoryDate;

    @FXML
    private TableColumn<History, String> colUserAction;

    @FXML
    private TableColumn<History, String> colUserName;

    @FXML
    private DatePicker inputHistoryDate;

    @FXML
    private TextField inputHistorySearch;

    @FXML
    private TableView<History> tableHistory;

    @FXML
    void handleKeyPressed(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputHistoryDate.getEditor().setDisable(true);
        inputHistoryDate.setValue(LocalDate.now());

        initTableView();
        loadHistory(LocalDate.now());
        listenTextFieldEvent();
    }

    private void listenTextFieldEvent() {
        inputHistorySearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                try {
                    loadHistory(LocalDate.now());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                searchHistory();
            }
        });

        inputHistoryDate.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if (!empty){
                    setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY){
                            searchHistory();
                        }
                    });
                }
            }
        });

    }

    private void initTableView(){
        colHistoryDate.setCellValueFactory(new PropertyValueFactory<History, LocalDateTime>("date"));
        colUserName.setCellValueFactory(new PropertyValueFactory<History, String>("userName"));
        colUserAction.setCellValueFactory(new PropertyValueFactory<History, String>("action"));
    }

    private void loadHistory(LocalDate date){

        List<History> histories = HistoryManager.getInstance().getHistory(date);

        for (History history : histories){
            tableHistory.getItems().add(new History(history.getDate(), history.getUserName(), history.getAction()));
        }
    }

    private void searchHistory(){
        tableHistory.getItems().clear();
        List<History> histories = HistoryManager.getInstance().getHistory(inputHistoryDate.getValue());

        for (History history : histories){
            if (history.getUserName().toLowerCase().contains(inputHistorySearch.getText().toLowerCase())){
                tableHistory.getItems().add(new History(history.getDate(), history.getUserName(), history.getAction()));
            }
        }
    }
}

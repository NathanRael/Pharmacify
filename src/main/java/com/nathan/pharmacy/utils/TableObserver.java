package com.nathan.pharmacy.utils;


import com.nathan.pharmacy.models.Medicament;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;

public class TableObserver {
    private final StringProperty medTableChanged;

    public TableObserver(){
        this.medTableChanged = new SimpleStringProperty("firstLoad");
    }

    public void observeMedicament(TableView<Medicament> tableView){
        getMedTableChangedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            try {
//                MedicamentViewController.loadTableContent(tableView);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public StringProperty getMedTableChangedProperty() {
        return medTableChanged;
    }


}

package com.nathan.pharmacy.controllers;

import com.nathan.pharmacy.controllers.medicament.MedicamentViewController;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Singleton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

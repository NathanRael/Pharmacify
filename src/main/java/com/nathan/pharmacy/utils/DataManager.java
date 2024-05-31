package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DataManager {
    public DataManager(){

    }

    public void clearUnusedData(){
        clearExpiredMedicament();
        clearOldHistory();
    }

    private void clearExpiredMedicament(){
        try {
            MedicamentModelController mc = new MedicamentModelController();
            mc.deleteMedicamentAt(LocalDate.now().toString());
            System.out.println("Expired medicament deleted");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void clearOldHistory(){
        HistoryManager.getInstance().remove(LocalDate.now().minusDays(3));
        System.out.println("Old history cleared");
    }
}

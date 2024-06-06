package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.models.Medicament;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicamentUtil {
    public static List<Medicament> getMedWithLowStock(int threshold) {
        List<Medicament> medicaments = new ArrayList<>();
        try {
            MedicamentModelController mc = new MedicamentModelController();
            ResultSet rs = mc.getMedicamentWithLowStock(threshold);
            while (rs.next()) {
                int medId = rs.getInt("medId");
                String medName = rs.getString("medName");
                String medDesc = rs.getString("medDesc");
                float medPrice = rs.getFloat("medPrice");
                int medQuantity = rs.getInt("medQuantity");
                medicaments.add(new Medicament(medId, medName, medQuantity));
            }
            return medicaments;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return medicaments;
    }


/*    public static ResultSet getLowQuantityMedicament() throws Exception {
        MedicamentModelController mc = new MedicamentModelController();
        return mc.getMedicamentWithLowStock(10);
    }*/


}

package com.nathan.pharmacy.controllers.dashboard;

import com.nathan.pharmacy.controllers.Session;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.patient.PatientModelController;
import com.nathan.pharmacy.controllers.purchase.PurchaseModelController;
import com.nathan.pharmacy.controllers.user.UserModelController;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DashboardViewController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private TableColumn<Purchase, Integer> colMedId;

    @FXML
    private TableColumn<Purchase, String> colMedName;

    @FXML
    private TableColumn<Purchase, Integer> colPatientId;

    @FXML
    private TableColumn<Purchase, String> colPatientName;

    @FXML
    private TableColumn<Purchase, LocalDateTime> colPurchaseDate;

    @FXML
    private TableColumn<Purchase, Integer> colPurchaseId;

    @FXML
    private TableColumn<Purchase, Integer> colQuantity;

    @FXML
    private TableColumn<Purchase, Float> colTotalPrice;

    @FXML
    private AnchorPane barChartContainer;

    @FXML
    private AnchorPane pieChartContainer;

    @FXML
    private TableView<Purchase> tablePurchase;

    @FXML
    private Text medicamentNumber;

    @FXML
    private Text patientNumber;

    @FXML
    private Text txtUserName;

    @FXML
    private Text userNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initTableView();
        updateDashboardPanel();
        createBarChart();
        creatingPieChart();

        try {
            loadTableContent();
        } catch (Exception e) {
            System.out.println("errorInDash" + e.getMessage());
            e.printStackTrace();
        }
    }



    private void updateDashboardPanel(){
        if (Session.getInstance().sessionExist()){
            txtUserName.setText(Session.getInstance().getUserName());
        }
        try {
            MedicamentModelController mc = new MedicamentModelController();
            PatientModelController pc = new PatientModelController();
            UserModelController uc = new UserModelController();

            medicamentNumber.setText(String.valueOf(mc.getCount()));
            patientNumber.setText(String.valueOf(pc.getCount()));
            userNumber.setText(String.valueOf(uc.getCount()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createBarChart(){
        Map<String, Double> dailyIncomes = new HashMap<>();

        try{

            PurchaseModelController pc = new PurchaseModelController();
            ResultSet rs = pc.selectDateBefore(LocalDate.now());
            while (rs.next()) {
                LocalDate date = rs.getDate("purchaseDate").toLocalDate();
                double price = rs.getInt("price");
                dailyIncomes.put(date.getDayOfWeek().toString(), price);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        double[] incomes = {1200, 1500, 1100, 1800, 1600, 1700, 1300};
        String[] daysOfWeek = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Jours");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenues (Ar)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Total des revenues journaliers");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");


        dailyIncomes.forEach((date, income) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>(date, income);
            series.getData().add(data);
        });

        barChart.getData().add(series);

        barChartContainer.getChildren().add(barChart);
    }

    private void creatingPieChart(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        );

        try{
            PurchaseModelController pc = new PurchaseModelController();
            ResultSet rs = pc.selectMostPurchasedProduct(3);

            pieChartData.clear();
            while (rs.next()){
                pieChartData.add(new PieChart.Data(rs.getString("medName"), rs.getInt("count")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Medicaments le plus vendus");

        pieChartContainer.getChildren().add(pieChart);
    }

    public void initTableView(){
        try{
            colPurchaseId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("id"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("totalPrice"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("quantity"));
            colPurchaseDate.setCellValueFactory(new PropertyValueFactory<Purchase, LocalDateTime>("date"));
            colMedId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("medId"));
            colMedName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("medName"));
            colPatientId.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("patientId"));
            colPatientName.setCellValueFactory(new PropertyValueFactory<Purchase, String>("patName"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void loadTableContent() throws Exception {
        ObservableList<Purchase> purchase = FXCollections.observableArrayList();
        PurchaseModelController pc = new PurchaseModelController();

        ResultSet rs = pc.selectJoin();

        while (rs.next()){
            int purchaseId = rs.getInt("purchaseId");
            int medId = rs.getInt("medId");
            int patientId = rs.getInt("patientId");
            LocalDateTime purchaseDate = rs.getDate("purchaseDate").toLocalDate().atTime(LocalTime.now());
            float totalPrice = rs.getFloat("totalPrice");
            String medName = rs.getString("medName");
            String patientName = rs.getString("patientFName");
            int purchaseQuantity = rs.getInt("purchaseQuantity");
            purchase.add(new Purchase(purchaseId, purchaseDate,purchaseQuantity,medId, patientId, totalPrice, medName, patientName ));
        }

        tablePurchase.setItems(purchase);
    }


}

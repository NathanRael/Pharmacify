package com.nathan.pharmacy.controllers.dashboard;

import com.nathan.pharmacy.contstants.DatePattern;
import com.nathan.pharmacy.contstants.MessageStyle;
import com.nathan.pharmacy.contstants.SubScenesName;
import com.nathan.pharmacy.models.Medicament;
import com.nathan.pharmacy.models.Singleton;
import com.nathan.pharmacy.utils.MedicamentUtil;
import com.nathan.pharmacy.utils.MessageField;
import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.utils.Session;
import com.nathan.pharmacy.controllers.medicament.MedicamentModelController;
import com.nathan.pharmacy.controllers.patient.PatientModelController;
import com.nathan.pharmacy.controllers.purchase.PurchaseModelController;
import com.nathan.pharmacy.controllers.supplier.SupplierModelController;
import com.nathan.pharmacy.models.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.util.MessageUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DashboardViewController implements Initializable {

    @FXML
    private Button btnSwitchToDeliver;

    @FXML
    private AnchorPane suppNumContainer;

    @FXML
    private AnchorPane medNumContainer;
    @FXML
    private AnchorPane patientNumContainer;

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
    private Text supplierNumber;

    @FXML
    private VBox notificationContainer;
    @FXML
    private DatePicker statisticaDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statisticaDate.getEditor().setDisable(true);
        statisticaDate.setValue(LocalDate.now());
        btnSwitchToDeliver.setOnAction(event -> switchSubsceneTo(SubScenesName.DELIVERY));
        listenEventKey();
        showNotification();
        initTableView();
        updateDashboardPanel();
        createBarChart(statisticaDate.getValue());
        creatingPieChart(statisticaDate.getValue());
        try {
            loadTableContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenEventKey(){
        statisticaDate.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color : rgba(226, 226, 226, 0.1)");
                }
                if (!empty){
                    setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY){
                            createBarChart(statisticaDate.getValue());
                            creatingPieChart(statisticaDate.getValue());
                        }
                    });
                }
            }
        });
    }
    private void showNotification() {
        List<Medicament> medicaments = MedicamentUtil.getMedWithLowStock(10);
        notificationContainer.getChildren().clear();
        if (!medicaments.isEmpty()){
            for (Medicament medicament : medicaments){
                Label notification = new Label();
                MessageField messageField = new MessageField(notification);
                messageField.setMessage(medicament.getQuantity() == 0 ? String.format("Medicament : %s est épuisé", medicament.getName()) : String.format("Rupture de stock imminente pour %s, quantité disponible %s", medicament.getName(), medicament.getQuantity()),MessageStyle.WARNING );

                notification.setStyle(notification.getStyle() + "-fx-padding : 8px 16px; -fx-font-size: 14px;-fx-font-family : Segoe UI; -fx-background-radius : 8px");

                notificationContainer.getChildren().add(notification);
            }
            btnSwitchToDeliver.setDisable(false);
        }else {
            btnSwitchToDeliver.setDisable(true);
            Label label = new Label();
            label.setText("Pas de notification pour l'instant");
            label.setStyle("-fx-font-weight: 700; -fx-font-size : 14px; -fx-text-fill : #e2e2e2");
            notificationContainer.getChildren().add(label);
        }
    }

    private void updateDashboardPanel(){
        if (Session.getInstance().sessionExist()){
            txtUserName.setText(Session.getInstance().getUserName());
        }
        try {
            MedicamentModelController mc = new MedicamentModelController();
            PatientModelController pc = new PatientModelController();
            SupplierModelController sc = new SupplierModelController();

            medicamentNumber.setText(String.valueOf(mc.getCount()));
            patientNumber.setText(String.valueOf(pc.getCount()));
            supplierNumber.setText(String.valueOf(sc.getCount()));

            medNumContainer.setOnMouseClicked(event -> switchSubsceneTo(SubScenesName.MEDICAMENT));
            patientNumContainer.setOnMouseClicked(event -> switchSubsceneTo(SubScenesName.PATIENT));
            suppNumContainer.setOnMouseClicked(event -> switchSubsceneTo(SubScenesName.SUPPLIER));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createBarChart(LocalDate localDate){
        barChartContainer.getChildren().clear();
        Map<String, Double> dailyIncomes = new HashMap<>();

        try{
            PurchaseModelController pc = new PurchaseModelController();
            ResultSet rs = pc.selectDateBefore(localDate);
            while (rs.next()) {
                LocalDate date = rs.getDate("purchaseDate").toLocalDate();
                double price = rs.getInt("price");
                dailyIncomes.put(date.getDayOfWeek().toString(), price);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


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

    private void creatingPieChart(LocalDate localDate){
        pieChartContainer.getChildren().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        );

        try{
            PurchaseModelController pc = new PurchaseModelController();
            ResultSet rs = pc.selectMostPurchasedProduct(3, localDate.toString());

            pieChartData.clear();
            while (rs.next()){
                pieChartData.add(new PieChart.Data(rs.getString("medName"), rs.getInt("purchaseQuantity")));
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
            String purchaseDate = rs.getDate("purchaseDate").toLocalDate().atTime(rs.getTime("purchaseDate").toLocalTime()).format(DatePattern.dateFormatPattern);
            float totalPrice = rs.getFloat("totalPrice");
            String medName = rs.getString("medName");
            String patientName = rs.getString("patientFName");
            int purchaseQuantity = rs.getInt("purchaseQuantity");
            purchase.add(new Purchase(purchaseId, purchaseDate,purchaseQuantity,medId, patientId, totalPrice, medName, patientName ));
        }

        tablePurchase.setItems(purchase);
    }

    public void switchSubsceneTo(SubScenesName subScenesName) {
        Singleton.getInstance().getViewFactory().getSelectedMenuItem().set(String.valueOf(subScenesName));
    }


}

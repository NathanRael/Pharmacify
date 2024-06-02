package com.nathan.pharmacy;


import com.nathan.pharmacy.utils.DataManager;
import com.nathan.pharmacy.utils.NotificationManager;
import com.nathan.pharmacy.utils.PdfManager;
import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.contstants.ScenesName;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

/*
    TODO :
        - Generate patient invoice according to the selected row ==> Done
        - Informing the SuperAdmin when a stock is almost out ==> Done
        - Adding the total amount to the patient invoice
        - Logging all changes
        - Sending the user medicament usage via email
        - Adding message label for error or success
        - Changing forgotten password via email code
        - Make the most selected item as the default selection for the choiceBox
        - Show the most purchased medicament according the selected date
*/
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneChanger.changeSceneTo(ScenesName.MAIN, stage);
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        int timerDelay = 0, period = 5 * 60 * 1000;

        //Executing task every x period
        TimerTask clearUnusedData = new TimerTask() {
            @Override
            public void run() {
                DataManager  dataManager = new DataManager();
                dataManager.clearUnusedData();
                System.out.println("clearing unusedData");
            }
        };

        //Executing task asynchronously
/*        Runnable task = () -> {
            try {
//                NotificationManager.checkMedQuantityAndNotify("ralaivoavy.natanael@gmail.com");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        };*/

        timer.scheduleAtFixedRate(clearUnusedData, timerDelay, period);
        launch();
    }
}

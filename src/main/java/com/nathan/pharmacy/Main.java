package com.nathan.pharmacy;


import com.nathan.pharmacy.utils.*;
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
        - Escape all of the String char in the modelController by using purifyString method ==> Done
        - Logging all changes => Done
        - Sending the user medicament usage via email => Done
        - Only selecting a medicament which has a quantity > 0 => Done
        - Adding message label for error or success => Done
        - Adding the total amount to the patient invoice => Pending
        - Changing forgotten password via email code
        - Make the most selected item as the default selection for the choiceBox
        - Show the most purchased medicament according the selected date
        - Adding qr code for login and user info
*/
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneChanger.changeSceneTo(ScenesName.MAIN, stage);
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        int timerDelay = 0, period = 5 * 60 * 1000;

        NotificationManager.checkMedQuantityAndNotify("ralaivoavy.natanael@gmail.com");
        //Executing task every x period
        TimerTask clearUnusedData = new TimerTask() {
            @Override
            public void run() {
                System.out.println("clearing unusedData");
            }
        };
        DataManager  dataManager = new DataManager();
        dataManager.clearUnusedData();
        timer.scheduleAtFixedRate(clearUnusedData, timerDelay, period);
        launch();
    }
}

package com.nathan.pharmacy;


import com.nathan.pharmacy.utils.DataManager;
import com.nathan.pharmacy.utils.SceneChanger;
import com.nathan.pharmacy.contstants.ScenesName;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneChanger.changeSceneTo(ScenesName.MAIN, stage);
    }
    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask clearUnusedData = new TimerTask() {
            @Override
            public void run() {
                DataManager  dataManager = new DataManager();
                dataManager.clearUnusedData();
                System.out.println("clearing unusedData");
            }
        };

        int delay = 0, period = 3 * 60 * 1000;

        timer.scheduleAtFixedRate(clearUnusedData, delay, period);

        launch();
    }
}

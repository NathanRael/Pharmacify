package com.nathan.pharmacy.utils;

import com.nathan.pharmacy.contstants.MessageStyle;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class MessageField {
    private Label label;

    public MessageField(Label label) {
        this.label = label;
    }

    public  void setMessage( String message , MessageStyle messageStyle){
        show();
        String newStyle = "";
        switch (messageStyle){
            case ERROR -> {
                newStyle = "-fx-background-color : rgba(212, 49, 49, 0.1);-fx-text-fill : rgba(212, 49, 49, 1);";
            }
            case SUCCESS -> {
                newStyle = "-fx-background-color : rgba(24, 173, 24, 0.1);-fx-text-fill : rgba(24, 173, 24,1);";
            }
            case WARNING -> {
                newStyle = "-fx-background-color : rgba(197, 180, 27, 0.1);-fx-text-fill : rgba(197, 180, 27, 1);";
            }

        }
        String style = label.getStyle() + newStyle;
        label.setStyle(style);
        label.setText(message);

/*        Timer timer = new Timer();
        int period = 5 * 60 * 1000;;
        TimerTask hideMessage = new TimerTask() {
            @Override
            public void run() {
                hide();
            }
        };
        timer.scheduleAtFixedRate(hideMessage,0,period);*/

    }

    public void hide(){
        label.setVisible(false);
    }

    public void show(){
        label.setVisible(true);
    }
}

package com.o4codes.controllers;

import com.jfoenix.controls.JFXProgressBar;
import com.o4codes.MainApp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {
    @FXML
    private JFXProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      SplashScreenTimeline().play();
    }

    private Timeline SplashScreenTimeline(){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10), new KeyValue(new SimpleIntegerProperty(10),0)));
        timeline.setOnFinished(event -> {
            try {
                MainApp.getMainStage().show();
                progressBar.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return timeline;
    }


}

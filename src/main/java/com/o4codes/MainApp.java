package com.o4codes;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApp extends Application {


    public void start(Stage stage) throws Exception {
       getSplashStage(stage).show();
//       ArrayList<Token> tokens =  Util.tokenizeString("I am a boy 2 today's; *<>= sat");
//       for (Token obj : tokens) {
//           System.out.println(obj.getTokenType()+" : "+obj.getToken());
//       }
    }

    public static Stage getSplashStage(Stage stage) throws IOException {
        System.out.println("Starting  application");
        String fxmlFile = "/fxml/splashScreen.fxml";
        System.out.println("Loading FXML for main view from: {} "+fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        Parent rootNode = loader.load();
        stage.setTitle("Lexical Analyzer");
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(rootNode);
        scene.setFill( null );
        stage.setScene(scene);


        stage.setOnCloseRequest(Event::consume);
        return stage;
    }

    public static Stage getMainStage() throws IOException {
        System.out.println("Starting  application");
        String fxmlFile = "/fxml/mainView.fxml";
        System.out.println("Loading FXML for main view from: {} "+fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation( MainApp.class.getResource( fxmlFile ) );
        AnchorPane rootNode = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Lexical Analyzer");
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);

        stage.setOnCloseRequest(Event::consume);
        return stage;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

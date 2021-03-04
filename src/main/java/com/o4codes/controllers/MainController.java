package com.o4codes.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.o4codes.helpers.Alerts;
import com.o4codes.helpers.Constants;
import com.o4codes.helpers.Util;
import com.o4codes.models.Token;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private HBox headerPane;

    @FXML
    private JFXButton minimizeWindowBtn;

    @FXML
    private JFXButton closeWindowBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private JFXTextArea sourceTextField;

    @FXML
    private JFXButton parseTextBtn;

    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXTextArea parsedTextFields;

    @FXML
    private JFXButton parseFileBtn;

    private Alerts alerts;

    private double xOffset, yOffset;

    public void initialize(URL location, ResourceBundle resources) {
        //variable initialization
        alerts = new Alerts();

        //windows events
        closeWindowBtn.setOnAction(event -> {
            alerts.materialConfirmAlert(stackPane, vBox, "Exit", "Proceed to exit");
            alerts.acceptBtn.setOnAction(e -> System.exit(0));
        });

        minimizeWindowBtn.setOnAction(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setIconified(true);
        });

        headerPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        headerPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void ParseTextEvent(ActionEvent event) throws IOException {
        String textToParse = sourceTextField.getText(); //get text to parse from text area
        parsedTextFields.clear();
        lockFields();
        Thread thread = new Thread(() -> { // perform text parsing in a background thread
            try {
                ArrayList<Token> tokens = Util.tokenizeString(textToParse, null);
                for (Token token : tokens) {
                    Platform.runLater(() -> parsedTextFields.appendText(token.getTokenType() + " : " + token.getToken() + "\n"));
                }
                Platform.runLater(this::unlockFields);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();


    }

    @FXML
    private void parseFileEvent(ActionEvent event) {
        lockFields();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Word Files", "*.docx"),
                new FileChooser.ExtensionFilter("Word Files", "*.doc")
        );
        Stage stage = (Stage) rootPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        Thread thread = new Thread(() -> {
            try {
                ArrayList<Token> tokensCollection = Util.tokenizeString(null, file);
                Util.writeOutputToFile(tokensCollection, file);
                Platform.runLater(() -> {
                    alerts.materialInfoAlert(stackPane, vBox, "Files Completely Analyzed",
                            "Output files located at " + file.getParent() + Constants.tokenizedFileName + " and "
                                    + file.getParent() + Constants.lexemeFileName);

                    unlockFields();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }

    private void lockFields() {
        spinner.setVisible(true);
        sourceTextField.setEditable(false);
        parsedTextFields.setEditable(false);
        parseTextBtn.setDisable(true);
        parseFileBtn.setDisable(true);
    }

    private void unlockFields() {
        spinner.setVisible(false);
        sourceTextField.setEditable(true);
        parsedTextFields.setEditable(true);
        parseTextBtn.setDisable(false);
        parseFileBtn.setDisable(false);
    }

}

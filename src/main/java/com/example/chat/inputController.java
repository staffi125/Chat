package com.example.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class inputController {

    @FXML
    private Button AuthSignInButton;

    @FXML
    private TextField Name_field;

    public void initialize() {
        AuthSignInButton.setOnMouseEntered(event -> {
            AuthSignInButton.setStyle("-fx-background-color: #bf5c3b;");
        });

        AuthSignInButton.setOnMouseExited(event -> {
            AuthSignInButton.setStyle("-fx-background-color: #2AB4FE;");
        });

        AuthSignInButton.setOnAction(event -> {

            AuthSignInButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/chat/mainPage.fxml"));

            if (isNameEntered()) {
                try {
                    loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } else {
                showAlert();
            }
        });

}

    private boolean isNameEntered(){
        String name = Name_field.getText();
        return name != null && !name.trim().isEmpty();
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please enter your name before proceeding.");
        alert.showAndWait();
    }

}

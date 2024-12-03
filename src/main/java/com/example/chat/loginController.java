package com.example.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {

    @FXML
    private Button LoginButton;

    @FXML
    private TextField Name_field;

    @FXML
    private TextField Password_field;

    @FXML
    private Button signUpButton;

    public void initialize() {
        // Додаємо обробник події при наведенні на кнопку
        LoginButton.setOnMouseEntered(e -> LoginButton.setStyle("-fx-background-color: #ff9706;"));
        LoginButton.setOnMouseExited(e -> LoginButton.setStyle("-fx-background-color: #000000;"));

        // Додаємо обробник події при натисканні на кнопку
        LoginButton.setOnAction(e -> validateFields());

        signUpButton.setOnMousePressed(e -> {
            signUpButton.setStyle("-fx-background-color: #F2F2F2; -fx-text-fill: #ff9706;"); // Change background and text color on press
        });

        signUpButton.setOnMouseReleased(e -> {
            // No need to revert background color; keep it as is
            signUpButton.setStyle("-fx-background-color: #F2F2F2; -fx-text-fill: #2ab4fe;");
        });
        signUpButton.setOnAction(e -> openSignUpPage());

    }

    private void validateFields() {
        String username = Name_field.getText().trim();
        String password = Password_field.getText().trim();

        if (username.length() < 4 || password.length() < 8) {
            // Show error alert if length is less than 4 characters
            showErrorAlert("Error", "Invalid username or password!");
        } else {

            openChatWindow();

        }
    }

    // Method to show an error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openChatWindow() {
        // Закриваємо поточне вікно
        LoginButton.getScene().getWindow().hide();

        try {
            // Завантажуємо FXML для основної сторінки чату
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chat/mainPage.fxml"));
            Parent root = loader.load();

            // Створюємо нову сцену для основної сторінки
            Stage stage = new Stage();
            stage.setTitle("Chat Room");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to open chat window.");
        }
    }

    private void openSignUpPage(){

            signUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/chat/signUp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
    }

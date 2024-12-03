package com.example.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private Button Accept_Button;

    @FXML
    private Button Close_Button;

    @FXML
    private TextField Gmail_field;

    @FXML
    private TextField Name_field;

    @FXML
    private TextField Password_field;

    @FXML
    public void initialize() {
        // Зміна кольору кнопок при наведенні курсора
        Accept_Button.setOnMouseEntered(e -> Accept_Button.setStyle("-fx-background-color: #ff9706;"));
        Accept_Button.setOnMouseExited(e -> Accept_Button.setStyle("-fx-background-color: #000000;"));

        Close_Button.setOnMouseEntered(e -> Close_Button.setStyle("-fx-background-color: #ff9706;"));
        Close_Button.setOnMouseExited(e -> Close_Button.setStyle("-fx-background-color: #000000;"));

        // Додаємо обробник події для кнопки Close
        Close_Button.setOnAction(e -> returnToLogin());
    }

    private void returnToLogin() {
        // Закриваємо поточне вікно SignUp
        Close_Button.getScene().getWindow().hide();

        // Завантажуємо форму входу (login.fxml)
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/chat/login.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Створюємо нове вікно для форми входу
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}

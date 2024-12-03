package com.example.chat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class mainController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Label chatTitle;

    @FXML
    private VBox messageBox;

    @FXML
    private TextField messageInput;

    private ObservableList<String> allUsers;
    private ObservableList<String> filteredUsers;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private final String SERVER_HOST = "127.0.0.1"; // Замініть на адресу вашого сервера
    private final int SERVER_PORT = 12345;          // Замініть на порт вашого сервера

    @FXML
    public void initialize() {
        // Ініціалізація списку користувачів
        allUsers = FXCollections.observableArrayList("User 1", "User 2", "User 3", "User 4", "User 5");
        filteredUsers = FXCollections.observableArrayList(allUsers);
        userListView.setItems(filteredUsers);

        // Додати слухача подій для пошуку
        searchBar.setOnKeyReleased(this::filterUserList);

        // Додати слухача подій для вибору користувача
        userListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                chatTitle.setText("Chat with " + newValue);
                messageBox.getChildren().clear();  // Очистити старі повідомлення
            }
        });

        // Підключення до сервера
        connectToServer();
    }

    private void connectToServer() {
        try {
            // Ініціалізуємо з'єднання з сервером
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Починаємо слухати повідомлення від сервера в окремому потоці
            Thread listenerThread = new Thread(this::listenToServer);
            listenerThread.setDaemon(true);
            listenerThread.start();
        } catch (IOException e) {
            showErrorAlert("Connection Error", "Failed to connect to the server.");
            e.printStackTrace();
        }
    }

    private void listenToServer() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                String finalMessage = message;
                Platform.runLater(() -> displayIncomingMessage(finalMessage));
            }
        } catch (IOException e) {
            showErrorAlert("Connection Error", "Connection to the server lost.");
            e.printStackTrace();
        }
    }

    private void displayIncomingMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 10; -fx-background-radius: 5;");
        messageBox.getChildren().add(messageLabel);
    }

    // Фільтрація списку користувачів на основі введення в пошуковий рядок
    private void filterUserList(KeyEvent event) {
        String filterText = searchBar.getText().toLowerCase();
        if (filterText.isEmpty()) {
            filteredUsers.setAll(allUsers);
        } else {
            filteredUsers.setAll(allUsers.filtered(user -> user.toLowerCase().contains(filterText)));
        }
    }

    // Відправка повідомлення
    @FXML
    private void sendMessage() {
        String message = messageInput.getText();
        if (!message.trim().isEmpty()) {
            writer.println(message); // Надсилання повідомлення на сервер
            displayOutgoingMessage(message);
            messageInput.clear();
        }
    }

    private void displayOutgoingMessage(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 5;");
        messageBox.getChildren().add(messageLabel);
    }

    private void showErrorAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Закриваємо підключення при виході
    public void closeConnection() {
        try {
            if (socket != null) socket.close();
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

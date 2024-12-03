package com.example.chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_HOST = "localhost"; // Адреса сервера
    private static final int SERVER_PORT = 1488; // Порт сервера

    private Socket clientSocket;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Scanner userInput;

    public Client() {
        try {
            // Підключення до сервера
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            outMessage = new PrintWriter(clientSocket.getOutputStream());
            inMessage = new Scanner(clientSocket.getInputStream());
            userInput = new Scanner(System.in);

            System.out.println("Connected to the server.");

            // Запускаємо окремий потік для отримання повідомлень від сервера
            new Thread(() -> {
                while (true) {
                    if (inMessage.hasNext()) {
                        String serverMessage = inMessage.nextLine();
                        System.out.println(serverMessage);
                    }
                }
            }).start();

            // Головний цикл для відправлення повідомлень
            while (true) {
                String userMessage = userInput.nextLine();
                outMessage.println(userMessage);
                outMessage.flush();
                if (userMessage.equalsIgnoreCase("END")) {
                    closeConnection();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (clientSocket != null) clientSocket.close();
            System.out.println("Connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

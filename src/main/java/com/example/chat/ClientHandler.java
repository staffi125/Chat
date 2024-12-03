package com.example.chat;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements  Runnable{

    private static int client_count = 0;

    private Server server;

    private PrintWriter outMessage;
    private Scanner inMessage;

    private Socket ClientSocket;

    public ClientHandler(Socket socket, Server server){
        try{
        client_count ++;
        this.server = server;
        this.ClientSocket = socket;
        this.outMessage = new PrintWriter(socket.getOutputStream());
        this.inMessage = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        server.removeClient(this);
        client_count--;
        server.sendMessageToAllClients("Clients in chat: " + client_count);
    }

    public void sendMessage(String message){
        outMessage.println(message);
        outMessage.flush();
    }

    @Override
    public void run() {
        try {
            // Надсилаємо повідомлення про нового клієнта один раз
            server.sendMessageToAllClients("New client");
            server.sendMessageToAllClients(client_count + "");

            // Основний цикл для обміну повідомленнями
            while (true) {
                String clientMessage = inMessage.nextLine();
                if (clientMessage.equals("END")) {
                    break;
                }
                System.out.println(clientMessage);
                server.sendMessageToAllClients(clientMessage);
            }

         Thread.sleep(100);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

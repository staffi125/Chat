package com.example.chat;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int port = 1488;

    public Server(){
        System.out.println("Waiting for clients...");
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = null;
            while (true){
                clientSocket = serverSocket.accept();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        Server server = new Server();
    }
}

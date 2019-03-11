package com.codecool.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[1]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        System.out.println("Git gud");
    }

}


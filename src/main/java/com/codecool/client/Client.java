package com.codecool.client;

import java.io.*;
import java.net.Socket;

public class Client {


    public Client(String[] args) throws IOException {
        String hostName = args[1];
        int portNumber = Integer.parseInt(args[2]);
        Socket clientSocket = new Socket(hostName, portNumber);
    }
}

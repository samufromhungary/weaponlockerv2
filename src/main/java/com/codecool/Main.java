package com.codecool;

import com.codecool.client.Client;
import com.codecool.common.Measurement;
import com.codecool.common.XMLHandler;
import com.codecool.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        try {
            if (args[0].equalsIgnoreCase("server")) {
                new Server(args);
            } else if (args[0].equalsIgnoreCase("client")) {
                new Client(args);
            } else {
                System.exit(-1);
            }
        } catch (IOException e) {
            // TODO: Handle Exception
            e.printStackTrace();
        }
    }
}

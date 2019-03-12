package com.codecool.server;

import com.codecool.common.Measurement;
import com.codecool.common.XMLHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(String[] args) throws IOException {
        runServer(args[1]);
    }

    private void runServer(String arg) throws IOException {
        int portNumber = Integer.parseInt(arg);
        ServerSocket ss = new ServerSocket(portNumber);
        System.out.println("Server online\n\n");
        while (true) {
            final Socket socket = ss.accept();
            new Thread(() -> {
                try {
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    XMLHandler xml = new XMLHandler();
                    Measurement m = (Measurement) is.readObject();
                    xml.handle(m);
                    os.writeObject(m);
                    System.out.println("Server is closed");
                    socket.close();

                } catch (IOException | ClassNotFoundException e ) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}


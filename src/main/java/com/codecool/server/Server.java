package com.codecool.server;


import com.codecool.common.XMLHandler;
import org.w3c.dom.Document;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(String[] args) throws IOException {
        runServer(args[1]);
    }

    private void runServer(String arg) throws IOException {
        int portNumber = Integer.parseInt(arg);
        ServerSocket ss = new ServerSocket(portNumber);
        System.out.println("Server is stable. Waiting for measurements: ");
        while (true) {
            final Socket socket = ss.accept();
            new Thread(() -> {
                try {
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                    Document measurementDoc = null;
                    while (true) {
                        try {
                            XMLHandler xml = new XMLHandler();
                            measurementDoc = (Document)is.readObject();
                            xml.handle(measurementDoc);
                        } catch (EOFException e) {
                            break;
                        }
                    }
                } catch (IOException | ClassNotFoundException e ) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}


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
        run(args[1]);
    }

    private void run(String arg) throws  IOException{
        int port = Integer.parseInt(arg);
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server is very STABIL");
        Socket socket = ss.accept();
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        XMLHandler xml = new XMLHandler();
        try {
            Measurement m = (Measurement) is.readObject();
            xml.handle(m);
            os.writeObject(m);
            socket.close();
        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }


}


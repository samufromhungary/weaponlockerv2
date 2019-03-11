package com.codecool.client;

import com.codecool.common.Measurement;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public Client(String[] args) throws IOException, FileNotFoundException {
        run(args);
    }

    private void run(String[] args) throws  IOException {
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        Socket clientSocket = new Socket(host, port);
        Scanner sc = new Scanner(System.in);
        int id = Integer.valueOf(sc.nextLine());
        int value = Integer.valueOf(sc.nextLine());
        String type = sc.nextLine();
        Measurement m = new Measurement(id, value, type);
        try{
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
            os.writeObject(m);
            Measurement rm = (Measurement) is.readObject();
            clientSocket.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}

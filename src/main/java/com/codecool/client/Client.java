package com.codecool.client;

import com.codecool.common.Measurement;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Client {


    public Client(String[] args) throws IOException, FileNotFoundException {
        run(args);
    }

    public Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    List<Measurement> ls = new ArrayList<>();

    public List<Measurement> generator() {
        System.out.println("Choose an id");
        int id = Integer.valueOf(sc.nextLine());
        System.out.println("Choose how many measurements you want to send");
        int n = Integer.valueOf(sc.nextLine());
        for (int i = 0; i < n ; i++) {
            ls.add(new Measurement(id, rand.nextInt(500), "mm"));
        }
        return ls;
    }

    private void run(String[] args) throws  IOException {
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        Socket clientSocket = new Socket(host, port);
        System.out.println("Choose an id");
        int id = Integer.valueOf(sc.nextLine());
        System.out.println("Choose how many measurements you want to send");
        int n = Integer.valueOf(sc.nextLine());
        try{
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
            for (int i = 0; i < n ; i++) {
                os.writeObject(new Measurement(id, rand.nextInt(500), "mm"));
                Measurement m = (Measurement) is.readObject();
            }

            //            os.writeObject();
//            Measurement rm = (Measurement) is.readObject();
            clientSocket.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}




/*
for (int i = 0; i < n ; i++){

}
 */

package com.codecool.client;

import com.codecool.common.ChartGenerator;
import com.codecool.common.Measurement;
import com.codecool.common.GenerateMeasurement;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


public class Client {
    private GenerateMeasurement mg;
    private Random rand = new Random();
    public Client(String[] args) throws IOException, InterruptedException {
        this.mg = new GenerateMeasurement();
        runClient(args);
    }

    public static String convertMillisIntoDate(long data){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date result = new Date(data);
        return sdf.format(result);
    }

    private void runClient(String[] args) throws IOException, InterruptedException {
        String hostName = args[1];
        int portNumber = Integer.parseInt(args[2]);
        Socket clientSocket = new Socket(hostName, portNumber);
        Scanner sc = new Scanner(System.in);
        System.out.println("What id do you want to use? Please, use integers only.");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println("How many measurements do you want to send?");
        int counter = Integer.parseInt(sc.nextLine());
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        for (int i = 0; i < counter; i++) {
            Measurement m = mg.generator(id);
            os.writeObject(m.convertToDocument());
            System.out.println("Measurement sent: (ID : " + m.getId() + " | Value: " + m.getValue() + m.getType() + " | At: " + convertMillisIntoDate(m.getTime()) + ")");
            Thread.sleep(rand.nextInt(5000 - 1001) + 1000);
        }
        System.out.println("Measurements sent, exiting.");
        try {
            String toGenerate = String.valueOf(id);
            ChartGenerator.generate(toGenerate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

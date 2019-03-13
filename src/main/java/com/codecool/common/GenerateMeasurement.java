package com.codecool.common;

import java.util.Random;

public class GenerateMeasurement {

    private Random rand = new Random();

    public Measurement generator(int id){
        return new Measurement(id, rand.nextInt(500), "mm");
    }


}

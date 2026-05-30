package creational;

import java.util.*;
import java.util.stream.Collectors;

enum Wheels {
    TWO_WHEELER(2),
    FOUR_WHEELER(4);

    private final int wheels;

    Wheels(int i) {
        this.wheels = i;
    }

    public int getWheels(){
        return wheels;
    }
}

public class Factory {
    Map<Integer, Vehicle> wheelVehicleMap = Map.of(2 , new Bike(), 4, new Car());

    public Vehicle getVehicle(int wheels){
        Vehicle vehicle = wheelVehicleMap.get(wheels);
        if(vehicle == null)
            throw new RuntimeException("No Vehicle found");
        return  vehicle;
    }
}

interface Vehicle {
    int getNumberOfTyres();
}

class Bike implements Vehicle {
    private static final String SUPPORTED_WHEELER = Wheels.TWO_WHEELER.name();
    public final String getSupportedWheeler() {
        return SUPPORTED_WHEELER;
    }

    public int getNumberOfTyres(){
        return Wheels.TWO_WHEELER.getWheels();
    }
}

class Car implements Vehicle {
    private static final String SUPPORTED_WHEELER = Wheels.FOUR_WHEELER.name();

    public final String getSupportedWheeler() {
        return SUPPORTED_WHEELER;
    }

    public int getNumberOfTyres(){
        return Wheels.FOUR_WHEELER.getWheels();
    }
}

class Main {
    public static void main(String[] args) {
        Factory fac = new Factory();
        System.out.println(fac.getVehicle(2).getNumberOfTyres());
        System.out.println(fac.getVehicle(4).getNumberOfTyres());
        System.out.println(fac.getVehicle(3).getNumberOfTyres());
    }
}
package creational;

/*
    FACTORY VS ABSTRACT FACTORY

    Aspect    -    Factory Design Pattern    -    Abstract Factory Design Pattern
----------------------------------------------------------------------------------------------------------------------------------------------------------------
    Purpose    -    Creates objects without specifying the exact class.    -    Creates families of related objects without specifying their concrete classes.

    Complexity    -    Less complex, focuses on a single product type.    -    More complex, involves multiple related products.

    Class Hierarchy    -    Involves a single factory method.    -    Involves multiple factory methods grouped under an abstract factory.
*/
/*
    FLOW DIAGRAM

                   +--------------------------+
                   |       Client Code        |
                   +--------------------------+

                                |
                                v
                   +--------------------------+
                   |       «interface»        |
                   |      VehicleFactory      |
                   +--------------------------+

                                |
       +------------------------+------------------------+
       |                                                 |
       v                                                 v
+--------------------------+                      +--------------------------+

|     BikeFactory          |                      |      CarFactory          |
+--------------------------+                      +--------------------------+

       |                                                 |
       +---------------+                                 +---------------+

       |               |                                 |               |
       v               v                                 v               v
 [createEngine]  [createTyres]                     [createEngine]  [createTyres]

       |               |                                 |               |
       | (calls)       | (calls)                         | (calls)       | (calls)
       v               v                                 v               v
+--------------+ +--------------+                 +--------------+ +--------------+

| EngineFactory| | TyreFactory  |                 | EngineFactory| | TyreFactory  |
+--------------+ +--------------+                 +--------------+ +--------------+

       |               |                                 |               |
       | passes "BIKE" | passes "BIKE"                   | passes "CAR"  | passes "CAR"
       v               v                                 v               v
   ( Switch )      ( Switch )                        ( Switch )      ( Switch )

       |               |                                 |               |
       v               v                                 v               v
+--------------+ +--------------+                 +--------------+ +--------------+

|  BikeEngine  | |   BikeTyre   |                 |  CarEngine   | |   CarTyre    |
+--------------+ +--------------+                 +--------------+ +--------------+

       |               |                                 |               |
       v               v                                 v               v
 [getEngine()]   [getTyre()]                       [getEngine()]   [getTyre()]

       |               |                                 |               |
       v               v                                 v               v
 "350 CC Bike   "170 mm Bike                      "180 HP V8 Car  "18 inch Car
   Engine"         Tyre"                             Engine"         Tyre"

 */

interface VehicleFactory {
    void createEngine();
    void createTyres();
}

interface Engine {
    void getEngine();
}

class EngineFactory {
    public void createEngine(String vehicle){
        Engine bikeEngine = new BikeEngine();
        Engine carEngine = new CarEngine();
        switch (vehicle){
            case "BIKE":
                bikeEngine.getEngine();
                break;
            case "CAR":
                carEngine.getEngine();
                break;
            default:
                System.out.println("Invalid vehicle type for engine " + vehicle);
        }
    }
}

class BikeEngine implements Engine {
    public void getEngine(){
        System.out.println("350 CC Bike Engine");
    }
}

class CarEngine implements Engine {
    public void getEngine(){
        System.out.println("180 HP V8 Car Engine");
    }
}


interface Tyre{
    void getTyre();
}

class TyreFactory {
    public void createTyre(String vehicle){
        Tyre bikeTyre = new BikeTyre();
        Tyre carTyre = new CarTyre();
        switch (vehicle){
            case "BIKE":
                bikeTyre.getTyre();
                break;
            case "CAR":
                carTyre.getTyre();
                break;
            default:
                System.out.println("Invalid vehicle type for tyres " + vehicle);
        }
    }
}

class BikeTyre implements Tyre {
    public void getTyre(){
        System.out.println("170 mm Bike Tyre");
    }
}

class CarTyre implements Tyre {
    public void getTyre(){
        System.out.println("18 inch Car Tyre");
    }
}

class CarFactory implements VehicleFactory {
    EngineFactory engineFactory = new EngineFactory();
    TyreFactory tyreFactory = new TyreFactory();

    @Override
    public void createEngine() {
        engineFactory.createEngine("CAR");
    }

    @Override
    public void createTyres() {
        tyreFactory.createTyre("CAR");
    }
}

class BikeFactory implements VehicleFactory {

    EngineFactory engineFactory = new EngineFactory();
    TyreFactory tyreFactory = new TyreFactory();

    @Override
    public void createEngine() {
        engineFactory.createEngine("BIKE");
    }

    @Override
    public void createTyres() {
        tyreFactory.createTyre("BIKE");
    }
}

public class AbstractFactory {
    public static void main(String[] args) {
        VehicleFactory carFactory = new CarFactory();
        VehicleFactory bikeFactory = new BikeFactory();

        carFactory.createEngine();
        bikeFactory.createEngine();
        carFactory.createTyres();
        bikeFactory.createTyres();
    }
}

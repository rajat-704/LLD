package creational;

interface Furniture {
    int getPrice();
    void setPrice(int price);
    Furniture clone();
}

class Chair implements Furniture {
    boolean easyToAssemble;
    int price;

    public Chair(){
    }

    public Chair(boolean easyToAssemble, int price){
        this.price=price;
        this.easyToAssemble = easyToAssemble;
    }

    public Chair(Chair chair){
        if(chair != null){
            this.price = chair.price;
            this.easyToAssemble = chair.easyToAssemble;
        }
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    public void setEasyToAssemble(boolean easyToAssemble) {
        this.easyToAssemble = easyToAssemble;
    }



    @Override
    public Furniture clone() {
        return new Chair(this);
    }
}

class Table implements Furniture {
    int price;
    int height;
    int length;
    int width;

    public Table(){
    }

    public Table(int price, int length, int width, int height){
        this.price = price;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Table(Table table){
        if(table != null){
            this.price = table.price;
            this.length = table.length;
            this.width = table.width;
            this.height = table.height;
        }
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Furniture clone() {
        return new Table(this);
    }
}

public class Prototype {
    public static void main(String[] args) {
        Furniture chair = new Chair(true, 10000);
        Furniture table = new Table(11500, 5,3, 3);
        Furniture cloneChair = chair.clone();
        Furniture cloneTable = table.clone();
        cloneChair.setPrice(5000);
        cloneTable.setPrice(15000);
        System.out.println(chair.getPrice());
        System.out.println(table.getPrice());
        System.out.println(cloneChair.getPrice());
        System.out.println(cloneTable.getPrice());
    }
}

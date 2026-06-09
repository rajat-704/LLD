package structural;

class SDMemoryCard {
    // in gb
    private int size;
    public int getSize() {
        return size;
    }
    public SDMemoryCard(){}
    public SDMemoryCard(int size){
        this.size = size;
    }
}


class MicroSDMemoryCard {
    // in gb
    private int size;
    public int getSize() {
        return size;
    }
    public MicroSDMemoryCard(){}
    public MicroSDMemoryCard(int size){
        this.size = size;
    }
}

class SDCardAdapter extends SDMemoryCard {
    MicroSDMemoryCard microSDMemoryCard;

    public SDCardAdapter(MicroSDMemoryCard microSDMemoryCard){
        this.microSDMemoryCard = microSDMemoryCard;
    }

    @Override
    public int getSize(){
        return microSDMemoryCard.getSize();
    }
}
class Camera {
    private final SDMemoryCard sdMemoryCard;
    public Camera(SDMemoryCard sdMemoryCard){
        this.sdMemoryCard = sdMemoryCard;
    }
    void insertSDCard(){
        System.out.println("Successfully inserted card of size : " + this.sdMemoryCard.getSize());
    }
}
public class Adapter {
    public static void main(String[] args) {
        MicroSDMemoryCard microSDMemoryCard = new MicroSDMemoryCard(128);
        SDMemoryCard sdMemoryCard = new SDCardAdapter(microSDMemoryCard);
        Camera camera = new Camera(sdMemoryCard);
        camera.insertSDCard();
    }
}

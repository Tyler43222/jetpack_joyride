package entities;

public class TylerCollect extends Collect{

    private static final String COLLECT_IMAGE_FILE = "assets/collect.gif";
    private static final int COLLECT_WIDTH = 60;
    private static final int COLLECT_HEIGHT = 60;

     public TylerCollect(int x, int y) {
        super(x, y, COLLECT_WIDTH, COLLECT_HEIGHT, COLLECT_IMAGE_FILE);
    }


}

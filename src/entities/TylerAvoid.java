package entities;

public class TylerAvoid extends Avoid{


    private static final String AVOID_IMAGE_FILE = "assets/avoid.gif";
    private static final int AVOID_WIDTH = 75;
    private static final int AVOID_HEIGHT = 55;

    public TylerAvoid(int x, int y) {
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);
    }


}

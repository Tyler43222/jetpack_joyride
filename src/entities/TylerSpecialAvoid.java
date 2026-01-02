package entities;

public class TylerSpecialAvoid extends SpecialAvoid{

    private static final String SAVOID_IMAGE_FILE = "assets/special_avoid.gif";
    private static final int SAVOID_WIDTH = 40;
    private static final int SAVOID_HEIGHT = 125;

    public TylerSpecialAvoid(int x, int y){
        super(x, y, SAVOID_WIDTH, SAVOID_HEIGHT, SAVOID_IMAGE_FILE);
    }


}

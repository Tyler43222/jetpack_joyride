package entities;

public class TylerSpecialCollect extends SpecialCollect{

    private static final String SCOLLECT_IMAGE_FILE = "assets/special_collect.gif";
    private static final int SCOLLECT_WIDTH = 50;
    private static final int SCOLLECT_HEIGHT = 50;

    public TylerSpecialCollect(int x, int y){
        super(x, y, SCOLLECT_WIDTH, SCOLLECT_HEIGHT, SCOLLECT_IMAGE_FILE);
    }


}

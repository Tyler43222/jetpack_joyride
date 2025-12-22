

public class statusBar extends Entity{


    public static final String FILL_FILE = "assets/fill_status.gif";
    public static final String EMPTY_FILE = "assets/empty_status.gif";
    private static final int WIDTH = 185;
    private static final int HEIGHT = 35;
    private static final int COOLDOWN_TICKS = 180;
    private int startTick = -1;


    public statusBar(int windowHeight, String imageName) {
        super(15, windowHeight - HEIGHT - 10, WIDTH, HEIGHT, imageName);
    }

    public void triggerCooldown(int currentTick) {
        if (!isOnCooldown(currentTick)) {
            setImg(EMPTY_FILE);
            startTick = currentTick;
        }
    }

    public void updateCooldown(int currentTick) {
        if (startTick != -1) {
            int elapsed = currentTick - startTick;
            if (elapsed >= COOLDOWN_TICKS) {
                setImg(FILL_FILE);
                startTick = -1;
            }
        }
    }

    public boolean isOnCooldown(int currentTick) {
        if(startTick != -1 && currentTick - startTick < COOLDOWN_TICKS){
            return true;
        }
        return false;
    }


}

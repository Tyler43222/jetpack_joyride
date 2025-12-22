import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TylerGame extends SimpleGame {

    public static final String INTRO_SPLASH_FILE2 = "assets/splash.gif";
    public static final String BACKGROUND_FILE = "assets/game_background.gif";
    private static final String PLAYER_IMAGE_FILE = "assets/player.gif";
    public static final int SPACEBAR = KeyEvent.VK_SPACE;//spacebar
    private int wipeCounter = 0;
    private statusBar bar;

    public TylerGame(){
            super();
    }


    protected void pregame(){
        this.setBackgroundColor(Color.BLACK);
        this.player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        this.player.setImg(PLAYER_IMAGE_FILE);
        this.toDraw.add(player);
        this.score = 0;
        super.setSplashImg(INTRO_SPLASH_FILE2);
        super.setBackgroundImg(BACKGROUND_FILE);
        this.bar = new statusBar(getWindowHeight(), statusBar.FILL_FILE);
        toDraw.add(this.bar);
    }


    protected void performScrolling(){
        //****   Implement me!   ****
        if (bar != null) {
            bar.updateCooldown(getTicksElapsed());
        }
        super.performScrolling();
    }


    protected void performSpawning(){
        //****   Implement me!   ****
        for (int i = 0; i < 4; i++) {
            spawnEntity(randomEntity());
        }
        spawnEntity(randomEntity());
    }


    protected Entity randomEntity(){
        int x = getWindowWidth();
        int y = 0;
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new TylerAvoid(x, y));
        entities.add(new TylerCollect(x, y));
        entities.add(new TylerSpecialAvoid(x, y));
        entities.add(new TylerSpecialCollect(x, y));
        int i = rand.nextInt(entities.size());
        return entities.get(i);
    }


    protected void playerMovement(int key){
        if(key == SPACEBAR && !bar.isOnCooldown(getTicksElapsed())){
            int currentX = player.getX();
            if(currentX < getWindowWidth()-this.player.getPlayerWidth()*4){
                int newX = currentX+(this.player.getPlayerWidth()*3);
                this.player.setX(newX);
            }
            else{
                int newX = getWindowWidth()-this.player.getPlayerWidth();
                this.player.setX(newX);
            }
            bar.triggerCooldown(getTicksElapsed());
        }
        super.playerMovement(key);
    }


    protected void wipeAvoids(){
        if(wipeCounter == 2){
            return;
        }
        for(int i = 0; i<toDraw.size();i++){
            Entity item = toDraw.get(i);
            if(item instanceof Avoid){
                item.flagForGC(true);
            }
        }
        wipeCounter++;
    }


    protected MouseEvent reactToMouseClick(MouseEvent click){
        if(super.isPaused == true){
            return click;
        }
        wipeAvoids();
        return click;//returns the mouse event for any child classes overriding this method
    }


}

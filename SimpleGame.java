import java.awt.*;
import java.awt.event.*;
import java.util.*;

//A Simple version of the scrolling game, featuring Avoids, Collects, SpecialAvoids, and SpecialCollects
//Players must reach a score threshold to win.
//If player runs out of HP (via too many Avoid/SpecialAvoid collisions) they lose.
public class SimpleGame extends SSGEngine {
    
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE_INTERVAL = 20;    
    
    public static final String INTRO_SPLASH_FILE = "media_files/splash.gif";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;

    
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();
    
    //player's current score
    protected int score;
    
    
    //Stores a reference to game's Player object for quick reference (Though this Player presumably
    //is also in the DisplayList, but it will need to be referenced often)
    protected Player player;
    
    
    public SimpleGame(){
        super();
    }
    
    public SimpleGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        this.setBackgroundColor(Color.BLACK);
        this.player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        this.toDraw.add(player); 
        this.score = 0;
        super.setSplashImg(INTRO_SPLASH_FILE);

    }
    
    //Called on each game tick
    protected void gameUpdate(){
        //scroll all AutoScroller Entities on the game board
        performScrolling();   
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){
            performSpawning();
            gcOffscreenEntities();
        }
        updateTitleText();
        playerCollision();

        
    }
    

    //Update the text at the top of the game window
    protected void updateTitleText(){
        if(checkForGameOver()){
            postgame();
        }
        if(super.getSplashImg() != null){
            setTitle("Super Scroller Game!");
        }
        if(super.getSplashImg() == null){
            setTitle("HP: " + player.getHP()+   ", Score: " +score);
        }
    }
    

    //Scroll all AutoScroller entities per their respective scroll speeds
    protected void performScrolling(){
        //****   Implement me!   ****
        for(int i = 0; i<toDraw.size();i++){
           Entity currentItem = toDraw.get(i);
           if(currentItem instanceof AutoScroller){
                ((AutoScroller) currentItem).scroll();
            }
        }
    }

    
    //Handles "garbage collection" of the entities
    //Flags entities in the displaylist that are no longer relevant
    //(i.e. will no longer need to be drawn in the game window).
    protected void gcOffscreenEntities(){

        //****   Implement me!   ****
        for(int i = 0; i<toDraw.size();i++){
           Entity currentItem = toDraw.get(i);
           if(currentItem.getX() + currentItem.getWidth() <= 0){
               currentItem.flagForGC(true);
            }
        }
        toDraw.performGC();
    }


    
    //Spawn new Entities on the right edge of the game board
    protected void performSpawning(){
        //****   Implement me!   ****

        spawnEntity(randomEntity());
        spawnEntity(randomEntity());
    }

    protected void spawnEntity(Entity toSpawn){
        toSpawn.setY(rand.nextInt(getWindowHeight()-toSpawn.getHeight()));
        if(super.findAllCollisions(toSpawn).isEmpty()){
            toDraw.add(toSpawn);
        }
    }

    protected Entity randomEntity(){
        int x = getWindowWidth();
        int y = 0;
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Avoid(x, y));
        entities.add(new Collect(x, y));
        entities.add(new SpecialAvoid(x, y));
        entities.add(new SpecialCollect(x, y));
        int i = rand.nextInt(entities.size());
        return entities.get(i);
    }

    protected void playerCollision(){
        Collection<Entity> collisions = super.findAllCollisions(player);
        if(!collisions.isEmpty()){
            for(Entity e: collisions){
                if(!(e instanceof Player)){
                    if (e instanceof CollisionReactive){
                        e.flagForGC(true);
                        player.setHP(((CollisionReactive)e).getHPChange()+player.getHP());
                        score += ((CollisionReactive)e).getScoreChange();
                    }
                }
            }
        }
    }

    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
        if(score >= SCORE_TO_WIN){
            super.setTitle("Game is over! You Won!");
        }
        else{
            super.setTitle("Game is over! You Lost!");
        }
    }
    
    //Returns a boolean indicating if the game is over (true) or not (false)
    //Game can be over due to either a win or lose state
    protected boolean checkForGameOver(){
        if(player.getHP() > 0){
            if(score < SCORE_TO_WIN){
                return false;
            }
        }
       return true;   //****   placeholder... Implement me!   ****
    }
    
    //Reacts to a single key press on the keyboard
    protected void reactToKeyPress(int key){
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImg() != null){
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImg(null);
            return;
        }
        if(key == KEY_PAUSE_GAME){
            pauseToggle();
            return;
        }
        if(super.isPaused){
            return;
        }
        playerMovement(key);
        changeSpeed(key);

    }

    protected void pauseToggle(){
        if(super.isPaused == false){
                super.isPaused = true;
            }
            else if(super.isPaused == true){
                super.isPaused = false;
            }
    }

    protected void playerMovement(int key){
        int currentY = this.player.getY();
        int currentX = this.player.getX();
        if(key == UP_KEY){
            if(currentY > 7){
                int newY = currentY-(this.player.getMoveSpeed());
                this.player.setY(newY);
            }
        }
        if(key == DOWN_KEY){
            if(currentY < getWindowHeight()-this.player.getPlayerHeight()-7){
                int newY = currentY+(this.player.getMoveSpeed());
                this.player.setY(newY);
            }
        }
        if(key == RIGHT_KEY){
            if(currentX < getWindowWidth()-this.player.getPlayerWidth()-7){
                int newX = currentX+(this.player.getMoveSpeed());
                this.player.setX(newX);
            }
        }
        if(key == LEFT_KEY){
            if(currentX > 0){
                int newX = currentX-(this.player.getMoveSpeed());
                this.player.setX(newX);
            }
        }
    }

    protected void changeSpeed(int key){
        if(key == SPEED_UP_KEY){
            if(super.getUniversalSpeed() <= 280){
                super.setUniversalSpeed(super.getUniversalSpeed()+20);
            }
        }
        if(key == SPEED_DOWN_KEY){
            if(getUniversalSpeed() >= 40){
                super.setUniversalSpeed(super.getUniversalSpeed()-20);
            }
        }
    }
    
    //Handles reacting to a single mouse click in the game window
    protected MouseEvent reactToMouseClick(MouseEvent click){
       
        //Mouse functionality is not used at all in the Starter game...
        //you may want to override this function for a CreativeGame feature though!

        return click;//returns the mouse event for any child classes overriding this method
    }



    
    
    
}

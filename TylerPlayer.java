

public class TylerPlayer extends Player {

   private static final String PLAYER_IMAGE_FILE = "assets/player.gif";
   private static final int PLAYER_WIDTH = 75;
   private static final int PLAYER_HEIGHT = 75;

   public TylerPlayer(int x, int y){
      super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE_FILE);
   }


}

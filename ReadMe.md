This game is the final project for my data structures & algorithms class and is based on the popular mobile game Jetpack Joyride.

## Game details

**Controls:**
- Movement: Arrow keys
- If user presses the space bar, the player jumps forward the distance of the width of three players. Allows user to jump over obstacles and avoid their effects. Status bar at the bottom of screen displays jump cooldown status.
- If the user presses the mouse anywhere in the game window, all on-screen avoid entities are erased. User can use this twice per game.

**Objectives:**
- Collect gold coins (+20 score) and rainbow coins (+20 score and +1 health)
- Avoid rockets (-50 score) and electric field (-50 score and -1 health)

**Game over conditions:**
- Win - Player reaches score of 300.
- Lose - Player reaches 0 health.

## How to run

From the project folder:
```
javac *.java

java SSGLauncher
```



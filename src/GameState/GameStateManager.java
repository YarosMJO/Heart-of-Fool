package GameState;

import heartoffool.GamePanel;
import javax.sound.sampled.Clip;

public class GameStateManager {

    private Clip clip;
    private GameState[] gameStates;
    private int currentState;

    public static final int NUMGAMESTATES = 11;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int LEVEL2STATE = 2;
    //  public static final int MAP = 3;

    private PauseState pauseState;
    private boolean paused = false;

    public GameStateManager() {

        gameStates = new GameState[NUMGAMESTATES];
        pauseState = new PauseState(this);

        currentState = MENUSTATE;
        loadState(currentState);

    }

    private void loadState(int state) {
        if (state == MENUSTATE) {
            gameStates[state] = new MenuState(this);
        } else if (state == LEVEL1STATE) {
            gameStates[state] = new Level1State(this);
        } else if (state == LEVEL2STATE) {
            gameStates[state] = new Level2State(this);
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    //запускає,оновлює,промальвує... вхідний стан
    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update() {
        if (paused) {
            pauseState.update();
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        if (paused) {
            pauseState.draw(g);
            return;
        }
        if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        } else {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(1, 1, GamePanel.PWIDTH, GamePanel.PHEIGHT);
        }
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public boolean getPaused() {
        return paused;
    }

    public void keyPressed(int k) {
        try {
            if (paused) {
                pauseState.keyPressed(k);
                return;
            }
            if (gameStates[currentState] != null) {
                gameStates[currentState].keyPressed(k);
            }
        } catch (Exception e) {

        }
    }

    public void keyReleased(int k) {
        try {
            if (paused) {
                pauseState.keyReleased(k);
                return;
            }
            if (gameStates[currentState] != null) {
                gameStates[currentState].keyReleased(k);
            }
        } catch (Exception e) {

        }

    }

}

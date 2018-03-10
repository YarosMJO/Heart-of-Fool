package GameState;

import Audio.AudioPlayer;
import Entity.Hero;

public abstract class GameState {

    protected static volatile boolean canResume;
    protected GameStateManager gsm;
    protected AudioPlayer bgMusic;
    protected Hero hero;

    public abstract void init();

    public abstract void update();

    public abstract void draw(java.awt.Graphics2D g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

}

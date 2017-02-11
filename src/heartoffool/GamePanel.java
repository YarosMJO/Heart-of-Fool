package heartoffool;

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
        implements Runnable, KeyListener {

    // dimensionsІ
    public static final int PWIDTH = 320;
    public static final int PHEIGHT = 240;
    public static final int SCALE = 2;

    // game thread
    private Thread thread;
    public static boolean running;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;//16.7;

    // image
    private BufferedImage image;
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;

    public GamePanel() {
        super();
        setPreferredSize(
                new Dimension(PWIDTH * SCALE, PHEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {

        super.addNotify();
        if (thread == null) {//
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {

        image = new BufferedImage(
                PWIDTH, PHEIGHT,
                BufferedImage.TYPE_INT_RGB
        );
        g = (Graphics2D) image.getGraphics();

        running = true;

        gsm = new GameStateManager();

    }

    @Override
    public void run() {

        init();

        long start;
        long elapsed;
        long wait;
        while (running) {
            start = System.nanoTime();

            update();
            // update();// х2
            draw();

            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {

                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void update() {
        gsm.update();
    }

    public void stopWork() {
        running = false;
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0,
                PWIDTH * SCALE, PHEIGHT * SCALE,
                null);
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }

}

package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseState extends GameState {

    private Background bg;
    private int currentChoice = 0;
    private final String[] options = {
        "Restart",
        "Menu",
        "Quit"
    };
   
    private Font font;
    private Font titleFont;
    private Color titleColor;
    public int k=0;

    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
 
        try {

            bg = new Background("/Backgrounds/waterfall.gif", 1);
            //bg.setVector(0,-0.1);
            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Century Gothic",
                    Font.PLAIN,
                    28);

            font = new Font("Arial", Font.PLAIN, 12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {
        bg.update();
       
    }

    @Override
    public void draw(Graphics2D g) {
        // draw bg
        bg.draw(g);

        //draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Heart Of Fool", 80, 70);
        // draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    private void select() {
        switch (currentChoice) {
            case 0:
                //restart
                //gsm.setState(currentChoice);
                break;
            case 1:
                gsm.setPaused(false);
                
                gsm.setState(GameStateManager.MENUSTATE);
                
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            gsm.setPaused(false);
            canResume=true;
            
             
           
        }
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }

}

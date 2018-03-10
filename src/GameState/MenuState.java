package GameState;

import TileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuState extends GameState {

    static boolean allowDownload = false;
    private static boolean drawContinueGray = false;
    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "   Continue ",
            "     Levels  ",
            "Select Hero ",
            "New  game ",
            "       Quit   "
    };
    private Color titleColor;
    private Font titleFont;
    private Font font;
    private MyPanel panel;
    private MAP map;
    private User u = new User();

    public MenuState(GameStateManager gsm) {

        this.gsm = gsm;
        try {
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);

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

    public void init() {
    }

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw title
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
            g.drawString(options[i], 125, 135 + i * 15);//145 x 140y
        }
        if (drawContinueGray == true) {
            g.setColor(Color.GRAY);
            g.drawString(options[0], 125, 135 + 0 * 15);
        }
    }

    private void select() {
        if (currentChoice == 0) {
            if (drawContinueGray == false) {
                try {
                    FileInputStream inputStream = new FileInputStream(System.getenv("APPDATA") + File.separator
                            + "Heart Of Fool" + File.separator + "Saved settings");

                    int data = inputStream.read();
                    if (data == -1) {
                        allowDownload = false;
                        drawContinueGray = true;
                        throw new FileNotFoundException();
                    } else {
                        allowDownload = true;
                        drawContinueGray = false;
                        u.load();

                        switch (u.props.getProperty("game_state")) {
                            case "Level1State":
                                gsm.setState(1);
                                break;
                            case "Level2State":
                                gsm.setState(2);
                                break;
                            //default:gsm.setState(1);
                        }
                    }

                } catch (FileNotFoundException e) {
                    drawContinueGray = true;
                    JOptionPane.showMessageDialog(null, "Opps! Saved game not found....\n" + "However you can start new game.");
                } catch (Exception e) {
                    drawContinueGray = true;
                    JOptionPane.showMessageDialog(null, "Opps! Whats happend....\n" + "Try to restart game.");
                }
            }

        }
        if (currentChoice == 1) {
            if (map == null) {
                map = new MAP(this);
            }
            map = null;
        }
        if (currentChoice == 2) {
            if (panel == null) {
                panel = new MyPanel();
            }
            panel = null;
        }
        if (currentChoice == 3) {
            Object[] buttons = {"Yes", "No"};
            int p = JOptionPane.showOptionDialog(null,
                    " Are you sure? All current achievement will be lost.\n"
                            + " If you dont wonted it, click NO to cancel",
                    " Start new game ",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, buttons, buttons[0]);

            if (p == 0) {
                allowDownload = false;
                drawContinueGray = false;
                gsm.setState(GameStateManager.LEVEL1STATE);

            }
        }
        if (currentChoice == 4) {
            System.exit(0);
        }

    }

    public void keyPressed(int k) {

        if (k == KeyEvent.VK_ENTER) {

            select();

        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (drawContinueGray == true) {
                if (currentChoice == 0) {
                    currentChoice = options.length - 1;
                }
            } else if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                if (drawContinueGray == true) {
                    currentChoice = 1;
                } else {
                    currentChoice = 0;
                }
            }
        }
    }

    public void keyReleased(int k) {
    }

}

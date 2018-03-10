package GameState;

import heartoffool.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPanel extends JFrame {

    public static int doz = 0;
    public boolean Hero1Select = false,
            Hero2Select = false,
            Hero3Select = false,
            check = false;

    public JButton button;
    public JPanel p1;
    public JLabel HeroLabel1, HeroLabel2, HeroLabel3;

    public int GPwidth = GamePanel.PWIDTH * 2 + GamePanel.PWIDTH / 2;
    public int GPheight = GamePanel.PHEIGHT * 2 + GamePanel.PHEIGHT / 2;
    KeyListener butlist = new KeyAdapter() {

        @Override
        public void keyTyped(KeyEvent e) {
            char key = e.getKeyChar();
            if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE) {
                dispose();
            }
        }
    };
    MouseListener HeroListener = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == button) {
                dispose();
            }
            if (e.getSource() == HeroLabel1) {
                CheckHero();
                HeroLabel1.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/AsianFonRed.png")));
                Hero1Select = true;
                doz = 1;
            }
            if (e.getSource() == HeroLabel2) {
                CheckHero();
                HeroLabel2.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/DragoFonRed.png")));
                Hero2Select = true;
                doz = 2;
            }

            if (e.getSource() == HeroLabel3) {
                CheckHero();
                HeroLabel3.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/LizardFonRed.png")));
                Hero3Select = true;
                doz = 3;
            }
        }

    };

    public MyPanel() {
        super("Select Hero");
        setSize(GPwidth, GPheight);
        setLayout(new BorderLayout());

        p1 = new JPanel(new GridLayout());
        p1.setPreferredSize(new Dimension(GPwidth + 10, GPheight - 110));
        p1.setBackground(Color.BLUE);
        button = new JButton("CONFIRM AND EXIT");
        button.addKeyListener(butlist);
        HeroLabel1 = new JLabel(new ImageIcon(getClass().getResource("/Backgrounds/AsianFon.png")));
        HeroLabel2 = new JLabel(new ImageIcon(getClass().getResource("/Backgrounds/DragoFon.png")));
        HeroLabel3 = new JLabel(new ImageIcon(getClass().getResource("/Backgrounds/LizardFon.png")));

        HeroLabel1.addMouseListener(HeroListener);
        HeroLabel2.addMouseListener(HeroListener);
        HeroLabel3.addMouseListener(HeroListener);
        button.addMouseListener(HeroListener);

        p1.add(HeroLabel1);
        p1.add(HeroLabel2);
        p1.add(HeroLabel3);


        add(p1, BorderLayout.NORTH);
        add(button);

//      setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));//missed
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static int getDoz() {
        return doz;
    }

    void CheckHero() {
        if (Hero1Select) {
            HeroLabel1.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/AsianFon.png")));
            Hero1Select = false;
        }
        if (Hero2Select) {
            HeroLabel2.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/DragoFon.png")));
            Hero2Select = false;
        }
        if (Hero3Select) {
            HeroLabel3.setIcon(new ImageIcon(getClass().getResource("/Backgrounds/LizardFon.png")));
            Hero3Select = false;
        }
    }

}

package GameState;

import heartoffool.GamePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MAP extends JFrame {

    public JButton L1, L2, L3;
    public JPanel p1;
    public int GPwidth = GamePanel.PWIDTH + GamePanel.PWIDTH / 2;
    public int GPheight = GamePanel.PHEIGHT + GamePanel.PHEIGHT / 2;
    public MenuState MS;

    public MAP(MenuState MS) {

        super("Select Hero");
        this.MS = MS;
        setSize(GPwidth, GPheight);
        setLayout(new BorderLayout());

        p1 = new JPanel(new GridLayout());
        p1.setPreferredSize(new Dimension(GPwidth, GPheight));
        p1.setBackground(Color.BLUE);

        L1 = new JButton("Level 1");
        L2 = new JButton("Level 2");
        L3 = new JButton("Level 3");

        L1.addKeyListener(butlist);
        L2.addKeyListener(butlist);
        L3.addKeyListener(butlist);

        L1.addMouseListener(HeroListener);
        L2.addMouseListener(HeroListener);
        L3.addMouseListener(HeroListener);

        p1.add(L1);
        p1.add(L2);
        p1.add(L3);

        add(p1, BorderLayout.NORTH);

        Image im = Toolkit.getDefaultToolkit().getImage("icon.png");
        setIconImage(im);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

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
            if (e.getSource() == L1) {
                MenuState.allowDownload=false;
                MS.gsm.setState(1);
                dispose();

            }
            if (e.getSource() == L2) {
                MenuState.allowDownload=false;
                MS.gsm.setState(2);
                dispose();
            }
            if (e.getSource() == L3) {
                MS.gsm.setState(0);
                dispose();
            }
        }
    };
}

package heartoffool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HeartOfFool {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame(" Heart of Fool");
            Image im = Toolkit.getDefaultToolkit().getImage("icon.png");
            window.setIconImage(im);
            window.setContentPane(new GamePanel());
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            window.setResizable(false);
            window.pack();
            window.setLocationRelativeTo(null);
            window.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent we) {
                    Object[] buttons = {"Yes", "No"};
                    int ans = JOptionPane.showOptionDialog(null,

                            "Really quit?",
                            "Leaving the game...",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, buttons, buttons[0]);
                    if (ans == 0) {

                        System.exit(0);
                    }

                }

            });
            window.setVisible(true);

        });

    }

}

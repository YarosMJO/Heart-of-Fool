package Entity.Enemies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Over {

    public BufferedImage image;

    public Over(String s) {

        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));

        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    public Over(BufferedImage image) {
        this.image = image;

    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) 100, (int) 100, null);
    }
}

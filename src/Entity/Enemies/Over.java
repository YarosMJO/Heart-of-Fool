package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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

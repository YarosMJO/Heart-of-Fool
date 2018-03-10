package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
	
	private Hero hero;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Hero hero) {
		this.hero = hero;
               
		try {
                     switch(hero.getClass().getSimpleName()){
                    case"Longway":
                        image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud_Longway.gif"));
                        break;
                    case"Drago":
                        image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud_Drago.gif"));
                        break;
                    case"Pricker":
                        image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud_Pricker.gif"));
                        break;
                }
			
			font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(
			hero.getHealth() + "/" + hero.getMaxHealth(),
			30,
			25
		);
                if (!"Longway".equals(hero.getClass().getSimpleName().toString()))
		g.drawString(
			hero.getFire() / 100 + "/" + hero.getMaxFire() / 100,
			30,
			45
		);
		
	}
	
}














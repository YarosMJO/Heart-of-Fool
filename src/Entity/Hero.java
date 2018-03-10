
package Entity;

import java.awt.*;
import java.util.ArrayList;

public interface Hero {
    
    public int getHealth();
    public int getMaxHealth();
    public void setHealth(int health);
    
    public int getFire();
    public int getMaxFire();
    
    public void setFiring();
    public void setScratching();
    public void setGliding(boolean b);
    
    public void checkAttack(ArrayList<Enemy> enemies);
    public void hit(int damage);
    public void getNextPosition();
    
    public void update();
    public void draw(Graphics2D g);
    
    public void setPosition(double x, double y);
    public void setLeft(boolean b);
    public void setRight(boolean b);
    public void setUp(boolean b);
    public void setDown(boolean b);
    public void setJumping(boolean b);
    
    public int getx();
    public int gety();


    public void setDead(boolean b);
    public void stop();
    public void reset();
}

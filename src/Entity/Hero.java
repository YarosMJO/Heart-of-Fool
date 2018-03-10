package Entity;

import java.awt.*;
import java.util.ArrayList;

public interface Hero {

    int getHealth();

    void setHealth(int health);

    int getMaxHealth();

    int getFire();

    int getMaxFire();

    void setFiring();

    void setScratching();

    void setGliding(boolean b);

    void checkAttack(ArrayList<Enemy> enemies);

    void hit(int damage);

    void getNextPosition();

    void update();

    void draw(Graphics2D g);

    void setPosition(double x, double y);

    void setLeft(boolean b);

    void setRight(boolean b);

    void setUp(boolean b);

    void setDown(boolean b);

    void setJumping(boolean b);

    int getx();

    int gety();


    void setDead(boolean b);

    void stop();

    void reset();
}

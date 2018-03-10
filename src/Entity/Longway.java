package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Longway extends MapObject implements Hero {

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int ICEBALL = 5;
    private static final int SCRATCHING = 6;
    private final int maxHealth;
    private final int maxFire = 0;
    private final int[] numFrames = {
            2, 7, 1, 1, 2, 23, 14
    };
    // player stuff
    private int health;
    private int fire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    // iceball
    private boolean firing;
    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;
    // gliding
    private boolean gliding;
    // animations
    private ArrayList<BufferedImage[]> sprites;

    public Longway(TileMap tm) {

        super(tm);

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 6;

        scratchDamage = 20;
        scratchRange = 30;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/Long_0.gif"
                    )
            );

            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 7; i++) {

                BufferedImage[] bi
                        = new BufferedImage[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {

                    bi[j] = spritesheet.getSubimage(
                            j * width,
                            i * height,
                            width,
                            height
                    );
                }

                sprites.add(bi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getFire() {
        return fire;
    }

    @Override
    public int getMaxFire() {
        return maxFire;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public void setFiring() {
        firing = true;
    }

    @Override
    public void setScratching() {
        scratching = true;
    }

    @Override
    public void setGliding(boolean b) {
        gliding = b;
    }

    @Override
    public void checkAttack(ArrayList<Enemy> enemies) {

        // loop through enemies
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // scratch attack
            if (scratching) {
                if (facingRight) {
                    if (e.getx() > x
                            && e.getx() < x + scratchRange
                            && e.gety() > y - height / 2
                            && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (e.getx() < x
                            && e.getx() > x - scratchRange
                            && e.gety() > y - height / 2
                            && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }

            // check enemy collision
            if (intersects(e)) {
                hit(e.getDamage());
            }

        }

    }

    @Override
    public void hit(int damage) {
        if (currentAction != ICEBALL) {
            if (flinching) {
                return;
            }
            stop();
            health -= damage;
            if (health < 0) {
                health = 0;
            }
            if (health == 0) {
                dead = true;
            }
            flinching = true;
            flinchTimer = System.nanoTime();
        }

    }

    @Override
    public void getNextPosition() {

        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        // cannot move while attacking, except in air
//        if ((currentAction == SCRATCHING || currentAction == ICEBALL)
//                && !(jumping || falling)) {
//            dx = 0;
//        }
        // jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling
        if (falling) {

            if (dy > 0 && gliding) {
                dy += fallSpeed * 0.1;
            } else {
                dy += fallSpeed;
            }

            if (dy > 0) {
                jumping = false;

            }
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }

            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }

        }

    }

    @Override
    public void update() {
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check attack has stopped
        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) {
                scratching = false;
            }
        }
        if (currentAction == ICEBALL) {
            if (animation.hasPlayedOnce()) {
                firing = false;
            }
        }

        // check done flinching
        if (flinching) {
            long elapsed
                    = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }

        // set animation
        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 30;
            }
        } else if (firing) {
            if (currentAction != ICEBALL) {
                currentAction = ICEBALL;
                animation.setFrames(sprites.get(ICEBALL));
                animation.setDelay(50);
                width = 30;
            }
        } else if (dy > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            } else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(120);
                width = 30;
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(60);
                width = 30;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(600);
                width = 30;
            }
        }

        animation.update();

        // set direction
        if (currentAction != SCRATCHING && currentAction != ICEBALL) {
            if (right) {
                facingRight = true;
            }
            if (left) {
                facingRight = false;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {

        setMapPosition();

        // draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }

    @Override
    public void reset() {
        health = maxHealth;
        facingRight = true;
        currentAction = IDLE;
        stop();
    }

    @Override
    public void stop() {

        left = right = up = down = flinching = jumping = scratching = false;

    }

}

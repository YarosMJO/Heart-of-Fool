package GameState;

import Audio.AudioPlayer;
import Entity.*;
import Entity.Enemies.Slugger;
import Entity.Enemies.Title;
import TileMap.Background;
import TileMap.TileMap;
import heartoffool.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level1State extends GameState {

    //private AudioPlayer bgMusic;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;

    private BufferedImage hageonText;
    private Title title;
    private Title subtitle;
    private Title over;

    private TileMap tileMap;
    private Background bg;

    private boolean eventDead;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private HUD hud;

    private boolean save = false;

    private User u = new User();

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;

        init();

    }

    @Override
    public void init() {
        u.load();
        if (!u.props.getProperty("game_state").equals(getState())) {
            MenuState.allowDownload = false;
        }
        if (MenuState.allowDownload == false) {
            tileMap = new TileMap(30);
            tileMap.loadTiles("/Tilesets/testtileset.gif");
            tileMap.loadMap("/Maps/lal0.map");
            tileMap.setPosition(0, 0);
            tileMap.setTween(1);
            bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

            this.hero = new Drago(tileMap);
            switch (MyPanel.getDoz()) {
                case 1:
                    this.hero = new Longway(tileMap);
                    break;
                case 2:
                    this.hero = new Drago(tileMap);
                    break;
                case 3:
                    this.hero = new Pricker(tileMap);
                    break;
            }

            this.hero.setPosition(100, 100);
            MenuState.allowDownload = true;
        } else {
            lastLoad();
            MenuState.allowDownload = true;
        }
        populateEnemies();
        explosions = new ArrayList<Explosion>();
        hud = new HUD(this.hero);
        if (bgMusic == null) {
            bgMusic = new AudioPlayer("/Music/Siberian.wav");
            bgMusic.play();
        } else {
            System.out.println("kaka");
        }

        try {
            hageonText = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/HageonTemple.gif")
            );
            title = new Title(hageonText.getSubimage(0, 0, 178, 20));
            title.sety(60);
            subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
            subtitle.sety(85);
        } catch (Exception e) {
            e.printStackTrace();
        }   // start event
        tb = new ArrayList<Rectangle>();

    }

    public void lastLoad() {

        u.load();

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/testtileset.gif");
        tileMap.loadMap("/Maps/lal0.map");
        tileMap.setPosition(Double.parseDouble(u.props.getProperty("tilemap_position_x")),
                Double.parseDouble(u.props.getProperty("tilemap_position_y")));
        tileMap.setTween(1);
        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        String heroName = u.props.getProperty("hero_type");
        switch (heroName) {
            case "Longway":
                hero = new Longway(tileMap);
                break;
            case "Drago":
                hero = new Drago(tileMap);
                break;
            case "Pricker":
                hero = new Pricker(tileMap);
                break;
        }
        hero.setPosition(Double.parseDouble(u.props.getProperty("hero_position_x")),
                Double.parseDouble(u.props.getProperty("hero_position_y")));

    }

    public void saveProp() {

        u.props.setProperty("hero_type", getHero());
        u.props.setProperty("hero_health", Integer.toString(hero.getHealth()));
        u.props.setProperty("hero_position_x", Integer.toString(hero.getx()));
        u.props.setProperty("hero_position_y", Integer.toString(hero.gety()));
        u.props.setProperty("game_state", getState());
        u.props.setProperty("tilemap_position_x", Double.toString(tileMap.getx()));
        u.props.setProperty("tilemap_position_y", Double.toString(tileMap.gety()));

        u.save();

    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Slugger s;
        Point[] points = new Point[]{
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)
        };
        for (int i = 0; i < points.length; i++) {
            s = new Slugger(tileMap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);

        }

    }

    @Override
    public void update() {
        if (canResume) {
            bgMusic.resume();
            canResume = false;
        }
        if (hero.getHealth() == 0 || hero.gety() > 200) {
            eventDead = true;
        }
        if (eventStart) {
            eventStart();
        }
        if (eventDead) {
            eventDead();
        }
        //if(eventFinish) eventFinish();
//        if (over != null) {
//            over.update();
//            if (over.shouldRemove()) {
//                over = null;
//            }
//        }
        // move title and subtitle
        if (title != null) {
            title.update();
            if (title.shouldRemove()) {
                title = null;
            }
        }
        if (subtitle != null) {
            subtitle.update();
            if (subtitle.shouldRemove()) {
                subtitle = null;
            }
        }

        // update player
        hero.update();
        tileMap.setPosition(
                GamePanel.PWIDTH / 2 - hero.getx(),
                GamePanel.PHEIGHT / 2 - hero.gety()
        );

        // set background
        bg.setPosition(tileMap.getx(), tileMap.gety());

        // attack enemies
        hero.checkAttack(enemies);

        // update all enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(
                        new Explosion(e.getx(), e.gety()));
            }
        }

        // update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        if (hero.getx() > 3190) {
            bgMusic.stop();
            MenuState.allowDownload = false;
            gsm.setState(2);
            MenuState.allowDownload = false;

        }

    }

    @Override
    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw player
        hero.draw(g);

        //hud draw
        hud.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition(
                    (int) tileMap.getx(), (int) tileMap.gety());
            explosions.get(i).draw(g);
        }
        // draw title
        if (over != null) {
            over.draw(g);
        }
        if (title != null) {
            title.draw(g);
        }
        if (subtitle != null) {
            subtitle.draw(g);
        }

        // draw transition boxes
        g.setColor(Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) {
            hero.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            hero.setRight(true);
        }
        if (k == KeyEvent.VK_UP) {
            hero.setUp(true);
        }
        if (k == KeyEvent.VK_DOWN) {
            hero.setDown(true);
        }
        if (k == KeyEvent.VK_W) {
            hero.setJumping(true);
        }
        if (k == KeyEvent.VK_E) {
            hero.setGliding(true);
        }
        if (k == KeyEvent.VK_R) {
            hero.setScratching();
        }
        if (k == KeyEvent.VK_F) {
            hero.setFiring();
        }

        if (k == KeyEvent.VK_ESCAPE) {
            gsm.setPaused(true);
            saveProp();

            bgMusic.pause();


        }

        if (k == KeyEvent.VK_CONTROL) {
            save = true;
        }
        if (k == KeyEvent.VK_C && save == true) {
            save = false;
        }

    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            hero.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            hero.setRight(false);
        }
        if (k == KeyEvent.VK_UP) {
            hero.setUp(false);
        }
        if (k == KeyEvent.VK_DOWN) {
            hero.setDown(false);
        }
        if (k == KeyEvent.VK_W) {
            hero.setJumping(false);
        }
        if (k == KeyEvent.VK_E) {
            hero.setGliding(false);
        }
        if (k == KeyEvent.VK_CONTROL) {
            save = false;
        }

    }

    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.PWIDTH, GamePanel.PHEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.PWIDTH / 2, GamePanel.PHEIGHT));
            tb.add(new Rectangle(0, GamePanel.PHEIGHT / 2, GamePanel.PWIDTH, GamePanel.PHEIGHT / 2));
            tb.add(new Rectangle(GamePanel.PWIDTH / 2, 0, GamePanel.PWIDTH / 2, GamePanel.PHEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;

        }

        if (eventCount == 30) {
            title.begin();

        }
        if (eventCount == 60) {
            eventStart = false;
            eventCount = 0;
            subtitle.begin();
            tb.clear();
        }
    }

    private void eventDead() {

        eventCount++;
        if (eventCount == 1) {
            hero.setDead(true);

            hero.reset();
        }

        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(
                    GamePanel.PWIDTH / 2, GamePanel.PHEIGHT / 2, 0, 0));

        }
        if (eventCount > 60) {

            tb.get(0).x -= 6;

            tb.get(0).y -= 4;

            tb.get(0).width += 12;

            tb.get(0).height += 8;

            //   gsm.setState(GameStateManager.MENUSTATE);
        }

        if (eventCount >= 120) {

            if (hero.getHealth() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
            } else {
                eventDead = false;
                eventCount = 0;
                reset();

            }

        }

    }

    private void reset() {
        hero.reset();
        hero.setPosition(300, 161);
        populateEnemies();
        eventCount = 0;
        eventStart = true;
        eventStart();

        title = new Title(hageonText.getSubimage(0, 0, 178, 20));//
        title.sety(60);
        subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));//
        subtitle.sety(85);

    }

    public String getHero() {
        return hero.getClass().getSimpleName();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getState() {
        return this.getClass().getSimpleName();
    }

    public int getXposition() {
        return hero.getx();
    }

    public int getYposition() {
        return hero.gety();
    }

}

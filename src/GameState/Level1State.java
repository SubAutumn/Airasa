package GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entity.Player;
import Entity.Title;
import Handlers.Keyboard;
import Main.GamePanel;
import TileMap.*;

import javax.imageio.ImageIO;

public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;

    private BufferedImage hageonText;
    private Title title;
    private Title subtitle;

    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;

    private Player player;

    public Level1State(GameStateManager gsm) {

        super(gsm);
        init();

    }

    public void init() {

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

        // title and subtitle
        try {
            hageonText = ImageIO.read(
                    getClass().getResourceAsStream("/HUD/level1title.gif")
            );
            title = new Title(hageonText.getSubimage(0, 0, 178, 25));
            title.setY(60);
            subtitle = new Title(hageonText.getSubimage(0, 31, 65, 15));
            subtitle.setY(85);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

    }
    public void update() {

        if(eventStart) eventStart();

        player.update();

        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY()
        );
        //tileMap.update();
        tileMap.fixBounds();
        handleInput();

        // move title and subtitle
        if(title != null) {
            title.update();
            if(title.shouldRemove()) title = null;
        }
        if(subtitle != null) {
            subtitle.update();
            if(subtitle.shouldRemove()) subtitle = null;
        }

    }
    public void draw(Graphics2D g) {

        bg.draw(g);

        //draw tilemap
        tileMap.draw(g);

        //draw the player.
        player.draw(g);

        //draw title/subtitle
        if(title != null) title.draw(g);
        if(subtitle != null) subtitle.draw(g);

    }
    public void handleInput() {
        player.setUp(Keyboard.keyState[Keyboard.UP]);
        player.setLeft(Keyboard.keyState[Keyboard.LEFT]);
        player.setDown(Keyboard.keyState[Keyboard.DOWN]);
        player.setRight(Keyboard.keyState[Keyboard.RIGHT]);
        player.setJumping(Keyboard.keyState[Keyboard.BUTTON1]);
        player.setGliding(Keyboard.keyState[Keyboard.BUTTON2]);
        if(Keyboard.isPressed(Keyboard.BUTTON3)) player.setScratching();
        if(Keyboard.isPressed(Keyboard.BUTTON4)) player.setFiring();
    }

    private void eventStart() {
        eventCount++;
        if(eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if(eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
        if(eventCount == 30) title.begin();
        if(eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            subtitle.begin();
            tb.clear();
        }

        if(player.getHealth() == 0 || player.getY() > tileMap.getHeight()){
            gsm.setState(GameStateManager.MENUSTATE);
            System.out.println("dead?");
        }
    }
}

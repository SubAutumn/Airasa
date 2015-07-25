package GameState;

import java.awt.*;

import Entity.Player;
import Handlers.Keyboard;
import Main.GamePanel;
import TileMap.*;

public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;

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

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);
    }
    public void update() {

        player.update();

        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY()
        );
        //tileMap.update();
        tileMap.fixBounds();

    }
    public void draw(Graphics2D g) {

        bg.draw(g);

        //draw tilemap
        tileMap.draw(g);

        //draw the player.
        player.draw(g);

    }
    public void handleInput() {
        player.setUp(Keyboard.keyState[Keyboard.UP]);
        player.setLeft(Keyboard.keyState[Keyboard.LEFT]);
        player.setDown(Keyboard.keyState[Keyboard.DOWN]);
        player.setRight(Keyboard.keyState[Keyboard.RIGHT]);
        player.setJumping(Keyboard.keyState[Keyboard.BUTTON1]);
        player.setGliding(Keyboard.keyState[Keyboard.BUTTON2]);
    }
}

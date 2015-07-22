package GameState;

import Main.GamePanel;
import TileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class MenuState extends GameState {

    private Background bg;

    private String titleName = "AIRASA";
    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Help",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {

        this.gsm = gsm;

        try {

            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.5, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Narkisim",
                    Font.PLAIN,
                    52);

            font = new Font("Arial", Font.PLAIN, 13);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {}

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(titleName, g);
        int x = ((GamePanel.WIDTH) - (int) r.getWidth()) / 2;
        int y = ((GamePanel.HEIGHT) - (int) r.getHeight()) / 2 + fm.getAscent() / 2 - 30;
        g.drawString(titleName, x, y);
        //g.drawString(titleName, (GamePanel.WIDTH) - stringWidth / 2, 70);

        // draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g.setColor(Color.BLACK);
            }
            else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }

    private void select() {
        if(currentChoice == 0) {
            // start
        }
        if(currentChoice == 1) {
            // help
        }
        if(currentChoice == 2) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int k) {}

}











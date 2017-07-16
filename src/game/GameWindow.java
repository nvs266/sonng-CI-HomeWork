package game;

import game.Players.Enemy;
import game.Players.Player;
import game.Players.PlayerSpell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sonng on 7/9/2017.
 */
public class GameWindow extends JFrame {

    private long currentTime;
    private long lastTimeUpdateBackground = -1;
    private long lastTimeUpdatePlayerSpell = 0;
    private long lastTimeUpdateEnemies = 0;
    private int heightWindow = 800;
    private int widthWindow = 800;
    private int backgroundY;
    private boolean xPressed = false;
    long startTime;

    BufferedImage background;
    BufferedImage backBufferImage;

    Graphics2D backBufferGraphics2D;

    private Player player;
    private ArrayList<PlayerSpell> playerSpells;
    private ArrayList<Enemy> enemies1;
    private ArrayList<Enemy> enemies2;

    public GameWindow() {
        setupWindow();
        loadImages();

        player = new Player(background.getWidth(), heightWindow);
        playerSpells = new ArrayList<PlayerSpell>();
        enemies1 = new ArrayList<Enemy>();
        enemies2 = new ArrayList<Enemy>();

        setupInputs();
        this.setVisible(true);

        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();

    }

    private void setupInputs() {

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                player.setKeyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_X) xPressed = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.setKeyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_X) xPressed = false;
            }
        });
    }

    public void loop() {
        backgroundY = this.getHeight() - background.getHeight();
        while (true) {
            if (lastTimeUpdateBackground == -1) {
                lastTimeUpdateBackground = System.currentTimeMillis();
            }

            currentTime = System.currentTimeMillis();
            if (currentTime - lastTimeUpdateBackground > 17) {
                update();
                render();
            }
            if (backgroundY >= 0) return;
        }
    }

    private void update() {
        //Background
        if (backgroundY < 0) backgroundY += 3;

        //Player
        player.updateLocation();
        player.updateImage();

        //PlayerSpells
        if (xPressed) {
            PlayerSpell playerSpell = new PlayerSpell(this.player);
            currentTime = System.currentTimeMillis();
            if (lastTimeUpdatePlayerSpell == 0 || currentTime - lastTimeUpdatePlayerSpell > 300) {
                playerSpells.add(playerSpell);
                lastTimeUpdatePlayerSpell = System.currentTimeMillis();
            }
        }
        for (int i = 0; i < playerSpells.size(); i++) {
            PlayerSpell playerSpell = playerSpells.get(i);
            playerSpell.update();
            if (playerSpell.getY() <= 0) playerSpells.remove(playerSpell);
        }
        //Enemies
        if (enemies1.size() == 0 || (enemies1.size() <= 10 && enemies1.get(enemies1.size()-1).getY() > 50)) {
            enemies1.add(new Enemy(100, 0));
            enemies2.add(new Enemy(200, 0));
        }
        for (int i = 0; i < enemies1.size(); i++) {
            enemies1.get(i).update();
            enemies2.get(i).update();
        }
    }

    private void render() {
        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        backBufferGraphics2D.drawImage(background, 0, backgroundY, null);
        player.render(backBufferGraphics2D);
        for (PlayerSpell playerSpell: playerSpells) {
            playerSpell.render(backBufferGraphics2D);
        }
        for (int i = 0; i < enemies1.size(); i++) {
            enemies1.get(i).render(backBufferGraphics2D);
            enemies2.get(i).render(backBufferGraphics2D);
        }

        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.drawImage(backBufferImage, 0, 0, null);
    }

    private void loadImages() {
        try {
            background = ImageIO.read(new File("assets\\images\\background\\0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupWindow() {
        this.setSize(widthWindow, heightWindow);
        this.setTitle("Touhou - Remade by sonng");
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                super.windowClosing(e);
            }
        });
    }

}

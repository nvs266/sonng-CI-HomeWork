package game;

import game.Players.Enemy;
import game.Players.Player;
import game.Players.PlayerSpell;
import game.bases.Common;
import game.bases.Contraints;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sonng on 7/9/2017.
 */
public class GameWindow extends JFrame implements Common{

    private int backgroundY;
    private boolean xPressed = false;

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

        player = new Player(background.getWidth(), this.getHeight(), new Contraints(50, this.getHeight() - 50, 10, background.getWidth() - 10));
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
            try {
                Thread.sleep(7);
                update();
                render();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        //Background
        if (backgroundY < 0) backgroundY += BACKGROUND_SPEED;

        //Player
        player.updateLocation();
//        player.updateImage();

        //PlayerSpells
        player.coolDown();
        if (xPressed == true) {
            player.castSpell(playerSpells);
            player.coolDown();
        }
        for (int i = 0; i < playerSpells.size(); i++) {
            PlayerSpell playerSpell = playerSpells.get(i);
            playerSpell.update();
            if (playerSpell.getPosition().y <= 0) playerSpells.remove(playerSpell);
        }
        //Enemies
        if (enemies1.size() == 0 || (enemies1.size() <= 10 && enemies1.get(enemies1.size()-1).getY() > 50)) {
            Random random = new Random();
            enemies1.add(new Enemy(random.nextInt(390), 0));
            enemies2.add(new Enemy(random.nextInt(390), 0));
        }
        for (int i = 0; i < enemies1.size(); i++) {
            enemies1.get(i).update1();
            enemies2.get(i).update2();
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
            Enemy enemy = enemies2.get(i);
            enemies1.get(i).render(backBufferGraphics2D);
            enemy.render(backBufferGraphics2D);
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
        this.setSize(Common.WIDTH, Common.HEIGHT);
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

package game;

import game.Enemies.BlackEnemy;
import game.Enemies.EnemySpawner;
import game.Players.Player;
import game.backgrounds.Background;
import game.bases.Setting;
import game.bases.GameObject;
import game.inputs.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/9/2017.
 */
public class GameWindow extends JFrame implements Setting {

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    InputManager inputManager = new InputManager();

    public GameWindow() {
        setupWindow();

        addBackground();
        addPlayer();
        GameObject.add(new EnemySpawner());

        setupInputs();
        this.setVisible(true);

        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();
    }

    private void addPlayer() {
        Player player = new Player(inputManager);
        GameObject.add(player);
    }

    private void addBackground() {
        Background background = new Background();
        GameObject.add(background);
    }

    private void setupInputs() {

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.keyRealeased(e);
            }
        });
    }

    public void loop() {
        while (true) {
            try {
                Thread.sleep(Setting.FRAME_DELAY);
                run();
                render();
                if (Player.life <= 0) {
                    Graphics2D g2d = (Graphics2D) this.getGraphics();
                    g2d.setColor(Color.MAGENTA);
                    g2d.setFont(new Font("serif", Font.BOLD, 40));
                    g2d.drawString("YOU LOSE, Score: "+ Player.score, 200, 300 );
                    break;
                }
                if (BlackEnemy.life <= 0) {
                    Graphics2D g2d = (Graphics2D) this.getGraphics();
                    g2d.setColor(Color.MAGENTA);
                    g2d.setFont(new Font("serif", Font.BOLD, 40));
                    g2d.drawString("YOU WIN :D", 200, 300 );
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void run() {
        GameObject.runAll();
    }

    private void render() {
        backBufferGraphics2D.setColor(Color.BLACK);
        backBufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        GameObject.renderAll(backBufferGraphics2D);

        Graphics2D g2d = (Graphics2D) this.getGraphics();
        g2d.drawImage(backBufferImage, 0, 0, null);
    }

    private void setupWindow() {
        this.setSize(Setting.WINDOW_WIDTH, Setting.WINDOW_HEIGHT);
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

package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sonng on 7/9/2017.
 */
public class GameWindow extends JFrame{

    BufferedImage background;
    BufferedImage player;
    private int playerX;
    private int playerY;
    private int height = 800;
    private int width = 800;

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    public GameWindow() {
        setupWindow();
        loadImages();

        playerX = background.getWidth()/2;
        playerY = this.getHeight() - player.getHeight() - 40;

        setupInputs();
        this.setVisible(true);

        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics2D = (Graphics2D) backBufferImage.getGraphics();

        //        this.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowIconified(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowDeiconified(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowActivated(WindowEvent e) {
//
//            }
//
//            @Override
//            public void windowDeactivated(WindowEvent e) {
//
//            }
//        });
        //        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setupInputs() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        if (playerX <= background.getWidth() - player.getWidth()) playerX += 5;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (playerX >= 0) playerX -= 5;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (playerY <= height - player.getHeight() - 40) playerY += 5;
                        break;
                    case KeyEvent.VK_UP:
                        if (playerY >= 30) playerY -= 5;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(17);
                backBufferGraphics2D.setColor(Color.BLACK);
                backBufferGraphics2D.fillRect(0, 0,this.getWidth(),this.getHeight());

                int hightBackground = this.getHeight() - background.getHeight();
                backBufferGraphics2D.drawImage(background,0, hightBackground, null);
                backBufferGraphics2D.drawImage(player, playerX, playerY, null);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImages() {
        try {
            background = ImageIO.read(new File("assets\\images\\background\\0.png"));
            player = ImageIO.read(new File("assets/images/players/straight/0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupWindow() {
        this.setSize(width, height);

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

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        //cast, convert
        g2d.drawImage(backBufferImage, 0, 0, null);
    }

}

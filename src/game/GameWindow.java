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
    private float playerX;
    private float playerY;
    private int heightWindow = 800;
    private int widthWindow = 800;
    private boolean checkUp = false;
    private boolean checkDown = false;
    private boolean checkRight = false;
    private boolean checkLeft = false;

    BufferedImage backBufferImage;
    Graphics2D backBufferGraphics2D;

    public GameWindow() {
        setupWindow();
        loadImages();

        playerX = background.getWidth()/2;
        playerY = this.heightWindow - player.getHeight();

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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        checkRight = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        checkLeft = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        checkDown = true;
                        break;
                    case KeyEvent.VK_UP:
                        checkUp = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        checkRight = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        checkLeft = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        checkDown = false;
                        break;
                    case KeyEvent.VK_UP:
                        checkUp = false;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(17);
                backBufferGraphics2D.setColor(Color.BLACK);
                backBufferGraphics2D.fillRect(0, 0,this.getWidth(),this.getHeight());

                int heightBackground = this.getHeight() - background.getHeight();
                for (int i = heightBackground; i <= 0 ; i++) {
                    backBufferGraphics2D.drawImage(background,0, i, null);
                    if (checkRight) {
                        if (playerX <= background.getWidth() - player.getWidth()) playerX += 2;
                    } else if (checkLeft) {
                        if (playerX >= 0) playerX -= 2;
                    }
                    if (checkUp) {
                        if (playerY >= 70) playerY -= 2;
                    } else if (checkDown) {
                        if (playerY <= heightWindow - player.getHeight()) playerY += 2;
                    }
                    backBufferGraphics2D.drawImage(player, (int)playerX, (int)playerY - 40, null);
                    Graphics2D g2d = (Graphics2D) this.getGraphics();
                    g2d.drawImage(backBufferImage, 0, 0, null);
                }

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

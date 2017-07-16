package game.Players;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player {

    private int x;
    private int y;
    private final int SPEED = 3;
    private int maxX;
    private int maxY;


    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private BufferedImage straightImgae;
    private BufferedImage currentImage;

    private boolean UP, DOWN, LEFT, RIGHT, STANDSTILL;

    public Player(int windowX, int windowY) {
        //Load Image
        leftImage = Utils.loadAssetImage("players/left/0.png");
        rightImage = Utils.loadAssetImage("players/right/0.png");
        straightImgae = Utils.loadAssetImage("players/straight/0.png");
        currentImage = straightImgae;

        //Set first location
        this.x = (windowX - currentImage.getWidth())/2;
        maxY = this.y = windowY - 2*currentImage.getHeight() ;
        maxX = windowX - currentImage.getWidth();

        //Set first status
        STANDSTILL = true;
        UP = false;
        RIGHT = false;
        LEFT = false;
        DOWN = false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void updateLocation() {
        int dx = 0;
        int dy = 0;
        if (RIGHT) if (this.x <= maxX) dx += SPEED;
        if (LEFT) if (this.x >= 0) dx -= SPEED;
        if (UP) if (this.y >= currentImage.getHeight()/2) dy -= SPEED;
        if (DOWN) if (this.y <= maxY) dy += SPEED;
        x += dx;
        y += dy;
    }

    public void updateImage() {
        if (STANDSTILL) currentImage = straightImgae;
        if (!LEFT && RIGHT) currentImage = rightImage;
        if (LEFT && !RIGHT) currentImage = leftImage;
    }

    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(currentImage, this.x, this.y, null);
    }

    public BufferedImage getImage() {
        return this.currentImage;
    }

    public void setKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                UP = true;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = true;
                break;
            case KeyEvent.VK_LEFT:
                LEFT = true;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = true;
                break;
            default:
                break;
        }
    }

    public void setKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                UP = false;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = false;
                break;
            case KeyEvent.VK_LEFT:
                LEFT = false;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = false;
                break;
            default:
                break;
        }
    }
}

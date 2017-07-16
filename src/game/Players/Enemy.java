package game.Players;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by sonng on 7/16/2017.
 */
public class Enemy {
    private int x;
    private int y;
    private BufferedImage image;
    private int SPEED;

    public Enemy(int x, int y) {
        this.image = Utils.loadAssetImage("enemies/level0/pink/0.png");
        this.x = x;
        this.y = y;
        SPEED = 2;
    }

    public boolean isOutOfMap(int backGroundX, int backGroundY) {
        if (this.x < 0) return true;
        if (this.x > backGroundX - this.image.getWidth()) return true;
        if (this.y < 0) return true;
        if (this.y > backGroundY) return true;
        return false;
    }

    public void render(Graphics2D g2d) {
        if (!this.isOutOfMap(400, 800)) g2d.drawImage(this.image, this.x, this.y, null);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void update1() {
        if (this.y > 400) this.x -= SPEED;
        else this.y += SPEED;
    }

    public void update2() {
        if (this.y > 400) this.x += SPEED;
        else this.y += SPEED;
    }
}

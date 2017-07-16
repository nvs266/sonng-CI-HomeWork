package game.Players;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell {

    private int x;
    private int y;
    private BufferedImage image;
    private int SPEED;

    public PlayerSpell(Player player) {
        image = Utils.loadAssetImage("player-spells/a/1.png");
        this.x = player.getX() + (player.getImage().getWidth()-this.image.getWidth())/2 ;
        this.y = player.getY() - 30;
        this.SPEED = 5;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void update() {
        this.y -= SPEED;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(image, this.x, this.y, null);
    }
}

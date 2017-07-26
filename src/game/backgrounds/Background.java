package game.backgrounds;

import game.bases.*;
import game.bases.renderers.Animation;
import game.bases.renderers.ImageRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/18/2017.
 */
public class Background extends GameObject implements Setting {

    private static Background instanceBackground;
    private BufferedImage image;
    public static Background getInstanceBackground() {
        return instanceBackground;
    }

    public Background() {
        super();
        image = Utils.loadAssetImage("background/0.png");
        imageRenderer = new Animation(0,image);
        set(0, WINDOW_HEIGHT - image.getHeight());
        instanceBackground = this;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (this.getY() <= 0) this.addUp(0, BACKGROUND_SPEED);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, (int) this.getY(), null);
    }
}

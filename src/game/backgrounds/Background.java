package game.backgrounds;

import game.bases.Common;
import game.bases.GameObject;
import game.bases.ImageRenderer;
import game.bases.Utils;
import sun.plugin.com.event.COMEventListener;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/18/2017.
 */
public class Background extends GameObject implements Common {

    private static Background instanceBackground;

    public static Background getInstanceBackground() {
        return instanceBackground;
    }

    public Background() {
        super();
        this.imageRenderer = new ImageRenderer(Utils.loadAssetImage("background/0.png"));
        set(0, HEIGHT - this.imageRenderer.image.getHeight());
        instanceBackground = this;
    }

    @Override
    public void run() {
        if (this.getY() <= 0) this.addUp(0, BACKGROUND_SPEED);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(imageRenderer.image, 0, (int) this.getY(), null);
    }
}

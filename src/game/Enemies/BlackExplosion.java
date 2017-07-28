package game.Enemies;

import game.GameWindow;
import game.bases.GameObject;
import game.bases.Utils;
import game.bases.Vector2D;
import game.bases.renderers.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackExplosion extends GameObject {
    private BufferedImage[] images;

    public BlackExplosion() {
        super();
        images = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            images[i] = Utils.loadAssetImage(String.format("enemies/explosion-big/%d.png", i));
        }
        imageRenderer = new Animation(10, images);
    }

    public void init(Vector2D position) {
        this.set(position);
        imageRenderer = new Animation(10, images);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
        if (imageRenderer.getIndex() == images.length - 1) {
            this.active = false;
            GameWindow.inGame = false;
        }
    }
}

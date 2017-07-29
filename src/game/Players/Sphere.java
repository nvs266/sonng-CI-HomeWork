package game.Players;

import game.bases.*;
import game.bases.renderers.Animation;
import game.bases.renderers.ImageRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sphere extends GameObject {

    private BufferedImage[] images;
    public static int status;

    public Sphere() {
        super();

        images = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            images[i] = Utils.loadAssetImage(String.format("sphere/%d.png", i));
        }
        imageRenderer = new Animation(7, images);
    }

    @Override
    public void run(Vector2D parentPosition) {
        if (this.y == 0) status = 0;
        else status = 1;
        super.run(parentPosition);
    }

    public void shoot() {
        if (active) {
            SphereBullet sphereBullet = GameObjectPool.recycle(SphereBullet.class);
            sphereBullet.set(this.screenPosition);
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
    }
}

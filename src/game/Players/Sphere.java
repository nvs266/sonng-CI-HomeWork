package game.Players;

import game.bases.*;
import game.bases.renderers.Animation;
import game.bases.renderers.ImageRenderer;

import java.awt.image.BufferedImage;

public class Sphere extends GameObject {

    private BufferedImage[] images;
    private FrameCounter frameCounterChangeImage;

    public Sphere() {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        images = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            images[i] = Utils.loadAssetImage(String.format("sphere/%d.png", i));
        }
        imageRenderer = new Animation(7, images);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
    }

    public void shoot() {
        SphereBullet sphereBullet = GameObjectPool.recycle(SphereBullet.class);
        sphereBullet.set(this.screenPosition);
    }
}

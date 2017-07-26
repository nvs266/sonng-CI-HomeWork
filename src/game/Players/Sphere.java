package game.Players;

import game.Enemies.BlackEnemy;
import game.bases.*;

public class Sphere extends GameObject {

    private ImageRenderer[] imageRenderers;
    private FrameCounter frameCounterChangeImage;

    public Sphere() {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        imageRenderers = new ImageRenderer[4];
        for (int i = 0; i < 4; i++) {
            imageRenderers[i] = new ImageRenderer(Utils.loadAssetImage(String.format("sphere/%d.png", i)));
        }
        imageRenderer = imageRenderers[0];

    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
    }

    @Override
    public void updatePicture() {
        if (frameCounterChangeImage.run()) {
            for (int i = 0; i < 4; i++) {
                if (imageRenderer == imageRenderers[i]) {
                    switch(i) {
                        case 3:
                            imageRenderer = imageRenderers[0];
                            break;
                        default:
                            imageRenderer = imageRenderers[i+1];
                            break;
                    }
                    break;
                }
            }
            frameCounterChangeImage.reset();
        }
    }

    public void shoot() {
        SphereBullet sphereBullet = GameObjectPool.recycle(SphereBullet.class);
        sphereBullet.set(this.screenPosition);
    }
}

package game.Enemies;

import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends GameObject implements Common {

    private ImageRenderer imageRenderer;

    private FrameCounter frameCounter;

    private float dx;
    private float dy;

    public EnemyBullet(float dx, float dy, Vector2D vector2D) {
        super();
        this.set(vector2D);

        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/bullets/pink.png"));

        this.dx = dx;
        this.dy = dy;

        frameCounter = new FrameCounter(30);
        frameCounter.reset();
    }

    @Override
    public void run() {
        if (frameCounter.run()) {
            this.addUp(2*dx, 2*dy);
        } else this.addUp(dx,dy);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (this.x < WIDTH/2 - imageRenderer.image.getWidth()) this.imageRenderer.render(graphics2D, this.getPosition());
    }
}

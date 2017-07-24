package game.Enemies;

import game.bases.*;

import java.awt.*;
import java.util.Vector;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends GameObject implements Setting {

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
    public void run(Vector2D parentPosiotion) {
        super.run(parentPosiotion);
        if (frameCounter.run()) {
            this.addUp(2*dx, 2*dy);
        } else this.addUp(dx,dy);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (this.x < WINDOW_WIDTH /2 - imageRenderer.image.getWidth()) this.imageRenderer.render(graphics2D, this.getPosition());
    }
}

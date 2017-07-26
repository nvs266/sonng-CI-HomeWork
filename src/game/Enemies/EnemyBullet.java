package game.Enemies;

import game.Players.Player;
import game.bases.*;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends GameObject implements Setting {

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

        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosiotion) {
        super.run(parentPosiotion);
        if (frameCounter.run()) {
            this.addUp(4*dx, 4*dy);
        } else this.addUp(dx,dy);
        if (isOutOfMap()) active = false;
    }
}

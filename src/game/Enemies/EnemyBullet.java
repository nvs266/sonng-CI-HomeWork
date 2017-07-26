package game.Enemies;

import game.Players.Player;
import game.bases.*;
import game.bases.physics.Physics;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends GameObject implements Setting {

    private FrameCounter frameCounter;

    private float dx;
    private float dy;

    public EnemyBullet() {
        super();

        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/bullets/pink.png"));

        frameCounter = new FrameCounter(30);
        frameCounter.reset();

        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    public void set(float dx, float dy, Vector2D vector2D) {
        this.dx = dx;
        this.dy = dy;
        this.set(vector2D);
        frameCounter.reset();
    }

    @Override
    public void run(Vector2D parentPosiotion) {
        super.run(parentPosiotion);
        if (frameCounter.run()) {
            this.addUp(4*dx, 4*dy);
        } else this.addUp(dx,dy);
        if (isOutOfMap()) active = false;
        hitPlayer();
    }

    private void hitPlayer() {
        Player hitPlayer = Physics.bodyInRect(this.boxCollider, Player.class);
        if (hitPlayer != null) {
            Player.life--;
            this.active = false;
        }
    }
}

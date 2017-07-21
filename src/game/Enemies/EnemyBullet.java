package game.Enemies;

import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends GameObject implements Common {

    private ImageRenderer imageRenderer;
    private float dx;
    private float dy;

    public EnemyBullet(float dx, float dy, Vector2D vector2D) {
        super();
        this.set(vector2D);
        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/bullets/pink.png"));
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void run() {
        this.addUp(2*dx, 2*dy);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (this.x < WIDTH/2 - imageRenderer.image.getWidth()) this.imageRenderer.render(graphics2D, this.getPosition());
    }
}

package game.Players;

import game.bases.*;

import java.awt.*;
import java.time.temporal.ValueRange;

/**
 * Created by sonng on 7/18/2017.
 */
public class EnemyBullet extends Vector2D implements Common {

    private ImageRenderer imageRenderer;
    Vector2D vector2D;

    public EnemyBullet(Vector2D vector2D, Vector2D vector2D1) {
        super();
        this.set(vector2D1);
        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/bullets/pink.png"));
        this.vector2D = vector2D.copy();
    }

    public void update() {
        Vector2D newVector2d = this.copy();
        this.set(this.x * 2 - vector2D.x, this.y * 2 - vector2D.y);
        this.vector2D = newVector2d;
    }

    public void  render(Graphics2D graphics2D) {
        if (this.x < WIDTH/2 - imageRenderer.image.getWidth()) this.imageRenderer.render(graphics2D, this.getPosition());
    }
}

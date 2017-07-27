package game.Players;

import game.Enemies.BlackEnemy;
import game.Enemies.Item;
import game.Enemies.PinkEnemy;
import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.Animation;
import game.bases.renderers.ImageRenderer;

import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends GameObject implements Setting, PhysicsBody {

    private BufferedImage[] images;
    private FrameCounter frameCounterChangeImage;

    public PlayerSpell() {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        images = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            images[i] = Utils.loadAssetImage(String.format("player-spells/a/%d.png", i));
        }
        imageRenderer = new Animation(7, images);
        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.y -= SPELL_SPEED;
        hitEnemy();
        if (isOutOfMap()) active = false;
    }

    private void hitEnemy() {
        PinkEnemy hitPinkEnemy = Physics.bodyInRect(this.boxCollider, PinkEnemy.class);
        if (hitPinkEnemy != null) {
            Player.score++;
            Item item = GameObjectPool.recycle(Item.class);
            item.set(hitPinkEnemy.getPosition());
            hitPinkEnemy.setActive(false);
            this.active = false;
        }
        BlackEnemy hitBlackEnemy = Physics.bodyInRect(this.boxCollider, BlackEnemy.class);
        if (hitBlackEnemy != null) {
            Player.score++;
            BlackEnemy.life--;
            this.active = false;
        }
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

package game.Players;

import game.Enemies.BlackEnemy;
import game.Enemies.PinkExplosion;
import game.Enemies.PinkEnemy;
import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.Animation;
import tklibs.AudioUtils;

import java.awt.image.BufferedImage;

public class SphereBullet extends GameObject implements Setting, PhysicsBody{
    private BufferedImage[] images;
    private FrameCounter frameCounterChangeImage;
    public Vector2D velocity;
    private BoxCollider boxCollider;

    public SphereBullet() {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        images = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            images[i] = Utils.loadAssetImage(String.format("sphere-bullets/%d.png", i));
        }
        imageRenderer = new Animation(7, images);

        velocity = new Vector2D(0,0);

        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (BlackEnemy.intanceBlack != null) {
            Vector2D tartget =  BlackEnemy.intanceBlack.getPosition();
            velocity = tartget.subtract(this.getPosition()).nomalize().multiply(Setting.SPHERE_BULLET_SPEED);
            this.getPosition().addUp(velocity);
        } else if (PinkEnemy.intancePinkEnemy != null) {
            Vector2D tartget =  PinkEnemy.intancePinkEnemy.getPosition();
            velocity = tartget.subtract(this.getPosition()).nomalize().multiply(Setting.SPHERE_BULLET_SPEED);
            this.getPosition().addUp(velocity);
        } else this.getPosition().addUp(0, -Setting.SPHERE_BULLET_SPEED);
        hitEnemy();
        if (isOutOfMap()) active = false;
    }

    private void hitEnemy() {
        PinkEnemy hitPinkEnemy = Physics.bodyInRect(this.boxCollider, PinkEnemy.class);
        if (hitPinkEnemy != null) {
            Player.score++;
            hitPinkEnemy.active = false;
            PinkExplosion explosion = GameObjectPool.recycle(PinkExplosion.class);
            explosion.init(hitPinkEnemy.getPosition());
            AudioUtils.playMedia("assets/music/sfx/enemy-explosion.wav");
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

package game.Enemies;

import game.Players.Player;
import game.bases.*;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/19/2017.
 */
public class BlackEnemy extends GameObject implements PhysicsBody{
    public static BlackEnemy instance = null;
    public static int health = 100;
    public static float alpha = 0;
    public FrameCounter frameCounterBullet;
    public FrameCounter frameCounterMove;
    public FrameCounter frameCounter;
    public BoxCollider boxCollider;
    public boolean bullet1Disable = true;
    public boolean bulletDisable = true;
    public boolean left = false;
    private BufferedImage[] images;
    private Animation leftAnmimation;
    private Animation rightAnimation;
    private Animation straightAnimation;

    public BlackEnemy() {
        super();

        leftAnmimation = new Animation(0, Utils.loadAssetImage("enemies/level0/black/6.png"));
        rightAnimation = new Animation(0, Utils.loadAssetImage("enemies/level0/black/5.png"));

        images = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            images[i] = Utils.loadAssetImage(String.format("enemies/level0/black/%d.png", i));
        }
        straightAnimation = new Animation(7, images);
        imageRenderer = straightAnimation;

        this.set(200, -images[0].getHeight());

        frameCounterBullet = new FrameCounter(150);
        frameCounterMove = new FrameCounter(300);
        frameCounter = new FrameCounter(5);

        instance = this;

        this.boxCollider = new BoxCollider(images[0].getWidth(), images[0].getHeight());
        this.children.add(boxCollider);
    }


    @Override
    public void run(Vector2D parentPosition) {
        if (health <= 0) {
            BlackExplosion blackExplosion = new BlackExplosion();
            blackExplosion.init(this.getPosition());
            GameObject.add(blackExplosion);
            this.active = false;
        }

        super.run(parentPosition);
        imageRenderer = straightAnimation;
        if (this.y <= 100) this.addUp(0, Setting.BLACK_SPEED);
        else {
            if (frameCounterMove.run()) {
                bulletDisable = true;
                if (!left && this.x >= 100) {
                    moveLeft();
                } else if (left && this.x <= 300) {
                    if (this.x <= 300) {
                        moveRight();
                    }
                }
            } else {
                bulletDisable = false;
            }
        }
        if (frameCounter.run()) {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();
            if (alpha <= 2 * Math.PI) {
                alpha += Math.PI / 20;
            } else {
                alpha = (float) (Math.PI / 20);
            }
            EnemyBullet enemyBullet = GameObjectPool.recycle(EnemyBullet.class);
            enemyBullet.set((float) (newVector2d.x * Math.cos(alpha) - newVector2d.y * Math.sin(alpha)) * 2,
                    (float) (newVector2d.x * Math.sin(alpha) + newVector2d.y * Math.cos(alpha)) * 2, this.getPosition());
            frameCounter.reset();
        }
        if(this.frameCounterBullet.run() && !bulletDisable) {
            setBullet();
        }

    }

    private void moveRight() {
        imageRenderer = rightAnimation;
        this.addUp(Setting.BLACK_SPEED, 0);
        if (this.x >= 300) {

            left = false;
            frameCounterMove.reset();
        }
    }

    private void moveLeft() {
        imageRenderer = leftAnmimation;
        this.addUp(-Setting.BLACK_SPEED, 0);
        if (this.x <= 100) {
            left = true;
            frameCounterMove.reset();
        }
    }

    private void setBullet() {

        if (bullet1Disable) {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();

            for (float i = 0; i < 2 * Math.PI; i += Math.PI / 30) {
                EnemyBullet enemyBullet = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet.set((float) (newVector2d.x * Math.cos(i) - newVector2d.y * Math.sin(i)), (float) (newVector2d.x * Math.sin(i) + newVector2d.y * Math.cos(i)), this.getPosition());
                enemyBullet.setImageRenderer("enemies/bullets/cyan.png");
            }
            bullet1Disable = false;
            frameCounterBullet.reset();
        } else {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();

            for (float i = 4; i >= 3; i -= 0.05) {
                EnemyBullet enemyBullet = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet.set(newVector2d.x * i, newVector2d.y * i, this.getPosition());
                enemyBullet.setImageRenderer("enemies/bullets/red.png");

                EnemyBullet enemyBullet1 = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet1.set((float) (newVector2d.x * Math.cos(Math.PI / 30) - newVector2d.y * Math.sin(Math.PI / 30)) * i,
                        (float) (newVector2d.x * Math.sin(Math.PI / 30) + newVector2d.y * Math.cos(Math.PI / 30)) * i,
                            this.getPosition());
                enemyBullet1.setImageRenderer("enemies/bullets/green.png");

                EnemyBullet enemyBullet2 = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet2.set((float) (newVector2d.x * Math.cos(-Math.PI / 30) - newVector2d.y * Math.sin(-Math.PI / 30)) * i,
                        (float) (newVector2d.x * Math.sin(-Math.PI / 30) + newVector2d.y * Math.cos(-Math.PI / 30)) * i,
                        this.getPosition());
                enemyBullet2.setImageRenderer("enemies/bullets/yellow.png");
            }
            bullet1Disable = true;
            frameCounterBullet.reset();
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

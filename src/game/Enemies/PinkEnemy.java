package game.Enemies;

import com.sun.corba.se.spi.activation.ORBPortInfoListHelper;
import game.Players.Player;
import game.bases.*;
import game.bases.physics.PhysicsBody;

import java.awt.*;
import java.util.Random;

/**
 * Created by sonng on 7/16/2017.
 */
public class PinkEnemy extends GameObject implements Setting, PhysicsBody {

    public static PinkEnemy intancePinkEnemy = null;
    private ImageRenderer[] currentImageRenderer;

    private FrameCounter frameCounterStillStand;
    private FrameCounter frameCounterChangePicture;
    private FrameCounter frameCounterShoot;

    public boolean bulletDiasable = true;
    private int maxY;
    private int rand = new Random().nextInt(2);

    public PinkEnemy() {
        super();

        currentImageRenderer = new ImageRenderer[4];
        for (int i = 0; i < 4; i++) {
            currentImageRenderer[i] = new ImageRenderer(Utils.loadAssetImage(String.format("enemies/level0/pink/%d.png", i)));
        }
        imageRenderer = currentImageRenderer[0];

        set(x - imageRenderer.image.getWidth() / 2, y);

        maxY = new Random().nextInt(WINDOW_HEIGHT / 3) + 50;

        frameCounterChangePicture = new FrameCounter(5);
        frameCounterChangePicture.reset();
        frameCounterChangePicture.run();


        frameCounterStillStand = new FrameCounter(50);
        frameCounterStillStand.reset();
        bulletDiasable = true;

        frameCounterShoot = new FrameCounter(25);
        frameCounterShoot.reset();

        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }


    @Override
    public void updatePicture() {
        if (frameCounterChangePicture.run()) {
            for (int i = 0; i < 4; i++) {
                if (imageRenderer == currentImageRenderer[i]) {
                    switch (i) {
                        case 3:
                            imageRenderer = currentImageRenderer[0];
                            break;
                        default:
                            imageRenderer = currentImageRenderer[i+1];
                            break;
                    }
                    break;
                }
            }
            frameCounterChangePicture.reset();
        }
    }

    @Override
    public void run(Vector2D parentPositon) {
        super.run(parentPositon);
        if (y >= maxY && bulletDiasable && active) {
            if (frameCounterShoot.run()) {

                Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);

                newVector2d = newVector2d.nomalize();
                Vector2D vector2D1 = new Vector2D((float)(newVector2d.x*Math.cos(3.1416/9)) - (float)(newVector2d.y*Math.sin(3.1416/9)), (float)(newVector2d.x*Math.sin(3.1416/9)) + (float)(newVector2d.y*Math.cos(3.1416/9)) );
                Vector2D vector2D2 = new Vector2D((float)(newVector2d.x*Math.cos(-3.1416/9)) - (float)(newVector2d.y*Math.sin(-3.1416/9)), (float)(newVector2d.x*Math.sin(-3.1416/9)) + (float)(newVector2d.y*Math.cos(-3.1416/9)) );

                EnemyBullet enemyBullet = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet.set(newVector2d.x, newVector2d.y , this.getPosition());

                EnemyBullet enemyBullet1 = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet1.set(vector2D1.x, vector2D1.y, this.getPosition());

                EnemyBullet enemyBullet2 = GameObjectPool.recycle(EnemyBullet.class);
                enemyBullet2.set(vector2D2.x, vector2D2.y, this.getPosition());

                bulletDiasable = false;

            }
        }
        if (this.y > maxY) {
            if (frameCounterStillStand.run()) {
                switch (rand) {
                    case 0:
                        this.addUp(-PINK_SPEED, 0);
                        break;
                    case 1:
                        this.addUp(PINK_SPEED, 0);
                        break;
                    default:
                        break;
                }
            }
        }
        else this.addUp(0, PINK_SPEED);
        intancePinkEnemy = this;
        if (isOutOfMap()) active = false;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (this.x < WINDOW_WIDTH /2 - this.imageRenderer.image.getWidth())imageRenderer.render(graphics2D, this.getPosition());
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

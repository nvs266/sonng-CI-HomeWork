package game.Players;

import game.bases.*;

import javax.rmi.CORBA.Util;
import java.awt.*;
import java.util.Random;

/**
 * Created by sonng on 7/16/2017.
 */
public class Enemy extends Vector2D implements Common{

    private ImageRenderer imageRenderer1;
    private ImageRenderer imageRenderer2;
    private ImageRenderer currentImageRenderer;

    private FrameCounter frameCounter;
    private FrameCounter frameCounterChangePicture;

    private EnemyBullet[] enemyBullets;

    private boolean bulletDiasable = true;
    private int maxY;

    public Enemy(int x, int y) {
        super(x, y);
        this.imageRenderer1 = new ImageRenderer(Utils.loadAssetImage("enemies/level0/pink/0.png"));
        this.imageRenderer2 = new ImageRenderer(Utils.loadAssetImage("enemies/level0/pink/2.png"));

        this.currentImageRenderer = imageRenderer2;
        frameCounterChangePicture = new FrameCounter(5);
        frameCounterChangePicture.reset();
        frameCounterChangePicture.run();

        maxY = new Random().nextInt(HEIGHT) + 50;

        frameCounter = new FrameCounter(COOLDOWN_BULLET);
        frameCounter.reset();
        frameCounter.run();
        bulletDiasable = true;
    }

    public void render(Graphics2D g2d) {
        if (!bulletDiasable) {
            for (EnemyBullet enemyBullet: enemyBullets) {
                enemyBullet.render(g2d);
            }
        }

        if (frameCounterChangePicture.run()) {
            if (currentImageRenderer == imageRenderer1) currentImageRenderer = imageRenderer2;
            else currentImageRenderer = imageRenderer1;
            frameCounterChangePicture.reset();
        }
        if (this.x < WIDTH/2 - this.currentImageRenderer.image.getWidth()) currentImageRenderer.render(g2d, this.getPosition());
    }

    public void update1() {
        if (frameCounter.run() && bulletDiasable) {
            enemyBullets = new EnemyBullet[3];
            enemyBullets[0] = new EnemyBullet(getPosition() ,getPosition().add(0, BULLET_SPEED));
            enemyBullets[1] = new EnemyBullet(getPosition() ,getPosition().add(-1, BULLET_SPEED));
            enemyBullets[2] = new EnemyBullet(getPosition() , getPosition().add(1, BULLET_SPEED));
            bulletDiasable = false;
        }
        if (!bulletDiasable) {
            for (EnemyBullet enemyBullet: enemyBullets) {
                enemyBullet.update();
            }
        }
        if (this.y > maxY) this.addUp(-ENEMY_SPEED, 0);
        else this.addUp(0, ENEMY_SPEED);
    }

    public void update2() {
        if (frameCounter.run() && bulletDiasable) {
            enemyBullets = new EnemyBullet[3];
            enemyBullets[0] = new EnemyBullet(getPosition() ,getPosition().add(0, BULLET_SPEED));
            enemyBullets[1] = new EnemyBullet(getPosition() ,getPosition().add(-1, BULLET_SPEED));
            enemyBullets[2] = new EnemyBullet(getPosition() , getPosition().add(1, BULLET_SPEED));
            bulletDiasable = false;
        }
        if (!bulletDiasable) {
            for (EnemyBullet enemyBullet: enemyBullets) {
                enemyBullet.update();
            }
        }
        if (this.y > maxY) this.addUp(ENEMY_SPEED, 0);
        else this.addUp(0, ENEMY_SPEED);
    }
}

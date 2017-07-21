package game.Enemies;

import game.Players.Player;
import game.bases.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by sonng on 7/16/2017.
 */
public class PinkEnemy extends GameObject implements Common{

    private ImageRenderer imageRenderer1;
    private ImageRenderer imageRenderer2;

    private FrameCounter frameCounterStillStand;
    private FrameCounter frameCounterChangePicture;
    private FrameCounter frameCounterShoot;

    private boolean bulletDiasable = true;
    private int maxY;
    private int rand = new Random().nextInt(2);

    public PinkEnemy(int x, int y) {
        super();

        this.imageRenderer1 = new ImageRenderer(Utils.loadAssetImage("enemies/level0/pink/0.png"));
        this.imageRenderer2 = new ImageRenderer(Utils.loadAssetImage("enemies/level0/pink/2.png"));
        imageRenderer = imageRenderer2;

        set(x - imageRenderer.image.getWidth() / 2, y);

        maxY = new Random().nextInt(HEIGHT / 3) + 50;

        frameCounterChangePicture = new FrameCounter(5);
        frameCounterChangePicture.reset();
        frameCounterChangePicture.run();


        frameCounterStillStand = new FrameCounter(50);
        frameCounterStillStand.reset();
        bulletDiasable = true;

        frameCounterShoot = new FrameCounter(25);
        frameCounterShoot.reset();
    }

    @Override
    public void updatePicture() {
        if (frameCounterChangePicture.run()) {
            if (imageRenderer == imageRenderer1) imageRenderer = imageRenderer2;
            else imageRenderer = imageRenderer1;
            frameCounterChangePicture.reset();
        }
    }

    @Override
    public void run() {
        if (y >= maxY && bulletDiasable) {
            if (frameCounterShoot.run()) {

                Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);

                newVector2d = newVector2d.nomalize();
                Vector2D vector2D1 = new Vector2D((float)(newVector2d.x*Math.cos(3.1416/9)) - (float)(newVector2d.y*Math.sin(3.1416/9)), (float)(newVector2d.x*Math.sin(3.1416/9)) + (float)(newVector2d.y*Math.cos(3.1416/9)) );
                Vector2D vector2D2 = new Vector2D((float)(newVector2d.x*Math.cos(-3.1416/9)) - (float)(newVector2d.y*Math.sin(-3.1416/9)), (float)(newVector2d.x*Math.sin(-3.1416/9)) + (float)(newVector2d.y*Math.cos(-3.1416/9)) );

                GameObject.add(new EnemyBullet(newVector2d.x, newVector2d.y , this.getPosition()));
                GameObject.add(new EnemyBullet(vector2D1.x, vector2D1.y, this.getPosition()));
                GameObject.add(new EnemyBullet(vector2D2.x, vector2D2.y, this.getPosition()));


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
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (this.x < WIDTH/2 - this.imageRenderer.image.getWidth())imageRenderer.render(graphics2D, this.getPosition());
    }
}

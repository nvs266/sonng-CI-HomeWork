package game.Enemies;

import game.Players.Player;
import game.backgrounds.Background;
import game.bases.*;

/**
 * Created by sonng on 7/19/2017.
 */
public class BlackEnemy extends GameObject{

    public FrameCounter frameCounterBullet;
    public FrameCounter frameCounterMove;

    public boolean bullet1Disable = true;
    public boolean bulletDisable = true;

    public boolean left = false;

    public BlackEnemy() {
        super();

        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/level0/black/0.png"));

        this.set(Background.getInstanceBackground().imageRenderer.image.getWidth() / 2, -imageRenderer.image.getHeight());

        frameCounterBullet = new FrameCounter(150);
        frameCounterBullet.reset();

        frameCounterMove = new FrameCounter(300);
    }

    @Override
    public void run() {
        if (this.y <= 100) this.addUp(0, Common.BLACK_SPEED);
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
        if(this.frameCounterBullet.run() && !bulletDisable) {
            setBullet();
        }
    }

    private void moveRight() {
        this.addUp(Common.BLACK_SPEED, 0);
        if (this.x >= 300) {
            left = false;
            frameCounterMove.reset();
        }
    }

    private void moveLeft() {
        this.addUp(-Common.BLACK_SPEED, 0);
        if (this.x <= 100) {
            left = true;
            frameCounterMove.reset();
        }
    }

    private void setBullet() {
        if (bullet1Disable) {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();

            for (float i = 0; i < 2*Math.PI; i += Math.PI/25) {
                GameObject.add(new EnemyBullet((float)(newVector2d.x*Math.cos(i) - newVector2d.y*Math.sin(i)),(float)(newVector2d.x*Math.sin(i) + newVector2d.y*Math.cos(i)), this.getPosition()));
            }
            bullet1Disable = false;
            frameCounterBullet.reset();
        } else {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();

            for (float i = 4; i >= 3; i -= 0.005) {
                GameObject.add(new EnemyBullet(newVector2d.x*i, newVector2d.y*i, this.getPosition()));
                GameObject.add(new EnemyBullet((float)(newVector2d.x*Math.cos(Math.PI / 20) - newVector2d.y*Math.sin(Math.PI / 20))*i, (float)(newVector2d.x*Math.sin(Math.PI / 20) + newVector2d.y*Math.cos(Math.PI / 20))*i, this.getPosition()));
                GameObject.add(new EnemyBullet((float)(newVector2d.x*Math.cos(-Math.PI / 20) - newVector2d.y*Math.sin(-Math.PI / 20))*i, (float)(newVector2d.x*Math.sin(-Math.PI / 20) + newVector2d.y*Math.cos(-Math.PI / 20))*i, this.getPosition()));
            }
            bullet1Disable = true;
            frameCounterBullet.reset();
        }
    }
}

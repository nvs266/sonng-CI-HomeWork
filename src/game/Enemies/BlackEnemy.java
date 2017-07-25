package game.Enemies;

import game.Players.Player;
import game.backgrounds.Background;
import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/19/2017.
 */
public class BlackEnemy extends GameObject{
    public static int life = 500;

    public static BlackEnemy intanceBlack;

    public FrameCounter frameCounterBullet;
    public FrameCounter frameCounterMove;
    public FrameCounter frameCounter;

    public BoxCollider boxCollider;

    public boolean bullet1Disable = true;
    public boolean bulletDisable = true;

    public boolean left = false;
    public static float alpha = 0;

    public BlackEnemy() {
        super();

        imageRenderer = new ImageRenderer(Utils.loadAssetImage("enemies/level0/black/0.png"));

        this.set(Background.getInstanceBackground().imageRenderer.image.getWidth() / 2, -imageRenderer.image.getHeight());

        frameCounterBullet = new FrameCounter(150);
        frameCounterMove = new FrameCounter(300);
        frameCounter = new FrameCounter(5);

        boxCollider = new BoxCollider(this.imageRenderer.image.getWidth() - 10, this.imageRenderer.image.getHeight() - 10);
        children.add(boxCollider);

        intanceBlack = this;

        subject = SUBJECTS.BLACK_ENEMY;
        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
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
            if (alpha < 2 * Math.PI) {
                alpha += Math.PI / 20;
            } else {
                alpha = (float) (Math.PI / 20);
            }
            GameObject.add(new EnemyBullet((float) (newVector2d.x * Math.cos(alpha) - newVector2d.y * Math.sin(alpha)) * 2,
                    (float) (newVector2d.x * Math.sin(alpha) + newVector2d.y * Math.cos(alpha)) * 2, this.getPosition()));
            frameCounter.reset();
        }
        if(this.frameCounterBullet.run() && !bulletDisable) {
            setBullet();
        }

    }

    private void moveRight() {
        this.addUp(Setting.BLACK_SPEED, 0);
        if (this.x >= 300) {
            left = false;
            frameCounterMove.reset();
        }
    }

    private void moveLeft() {
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
                GameObject.add(new EnemyBullet((float) (newVector2d.x * Math.cos(i) - newVector2d.y * Math.sin(i)), (float) (newVector2d.x * Math.sin(i) + newVector2d.y * Math.cos(i)), this.getPosition()));
            }
            bullet1Disable = false;
            frameCounterBullet.reset();
        } else {
            Vector2D newVector2d = new Vector2D(Player.instancePlayer.x - this.x, Player.instancePlayer.y - this.y);
            newVector2d = newVector2d.nomalize();

            for (float i = 4; i >= 3; i -= 0.05) {
                GameObject.add(new EnemyBullet(newVector2d.x * i, newVector2d.y * i, this.getPosition()));
                GameObject.add(new EnemyBullet((float) (newVector2d.x * Math.cos(Math.PI / 30) - newVector2d.y * Math.sin(Math.PI / 30)) * i, (float) (newVector2d.x * Math.sin(Math.PI / 30) + newVector2d.y * Math.cos(Math.PI / 30)) * i, this.getPosition()));
                GameObject.add(new EnemyBullet((float) (newVector2d.x * Math.cos(-Math.PI / 30) - newVector2d.y * Math.sin(-Math.PI / 30)) * i, (float) (newVector2d.x * Math.sin(-Math.PI / 30) + newVector2d.y * Math.cos(-Math.PI / 30)) * i, this.getPosition()));
            }
            bullet1Disable = true;
            frameCounterBullet.reset();
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
        graphics2D.setColor(Color.BLUE);
        graphics2D.setFont(new Font("serif", Font.BOLD, 30));
        graphics2D.drawString("BOSS: "+ life, 500, 100);
    }
}

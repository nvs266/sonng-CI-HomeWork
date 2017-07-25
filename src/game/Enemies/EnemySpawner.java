package game.Enemies;

import game.backgrounds.Background;
import game.bases.FrameCounter;
import game.bases.GameObject;
import game.bases.Vector2D;

import java.util.Random;

/**
 * Created by sonng on 7/19/2017.
 */
public class EnemySpawner extends GameObject{

    FrameCounter frameCounter = new FrameCounter(20);
    boolean blackEnemyDisable = true;
    public EnemySpawner() {
        frameCounter.reset();
        for (int i = 1; i <= 19; i++) {
            frameCounter.run();
        }
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (frameCounter.run() && Background.getInstanceBackground().y <= -200) {
            GameObject.add(new PinkEnemy((int) new Random().nextInt(Background.getInstanceBackground().imageRenderer.image.getWidth()), 0));
            frameCounter.reset();
        }

        if (Background.getInstanceBackground().y >= - 50 && blackEnemyDisable) {
            GameObject.add(new BlackEnemy());
            blackEnemyDisable = false;
        }
    }
}

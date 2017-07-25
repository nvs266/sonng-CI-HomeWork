package game.bases;

/**
 * Created by sonng on 7/17/2017.
 */
public interface Setting {
    // window, frame
    int WINDOW_WIDTH = 800;
    int WINDOW_HEIGHT = 700;
    int FRAME_DELAY = 7;
    // speed
    int PINK_SPEED = 1;
    int PLAYER_SPEED = 3;
    int SPELL_SPEED = 6;
    int BACKGROUND_SPEED = 2;
    int BLACK_SPEED = 2;
    // coolDown
    int COOLDOWN_SPELL = 10;
    int COOLDOWN_BULLET = 20;
    int SPHERE_BULLET_SPEED = 4;
    // Subjects
    enum SUBJECTS {PLAYER, BLACK_ENEMY, PINK_ENEMY, BLACK_BULLET, ENEMY_BULLET, BACKGROUND, SPHERE,
        SPHERE_BNULLET, PLAYER_SPELL};
    // images path

}

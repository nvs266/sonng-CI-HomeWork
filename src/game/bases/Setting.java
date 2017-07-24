package game.bases;

/**
 * Created by sonng on 7/17/2017.
 */
public interface Setting {
    // window, frame
    public final int WINDOW_WIDTH = 800;
    public final int WINDOW_HEIGHT = 700;
    public final int FRAME_DELAY = 7;
    // speed
    public final int PINK_SPEED = 1;
    public final int PLAYER_SPEED = 3;
    public final int SPELL_SPEED = 6;
    public final int BACKGROUND_SPEED = 2;
    public final int BLACK_SPEED = 2;
    // coolDown
    public final int COOLDOWN_SPELL = 10;
    public final int COOLDOWN_BULLET = 20;
    public final int SPHERE_BULLET_SPEED = 4;
    // Subjects
    public static enum SUBJECTS {PLAYER, BLACK_ENEMY, PINK_ENEMY, BLACK_BULLET, PINK_BULLET, BACKGROUND, SPHERE,
        SPHERE_BNULLET, PLAYER_SPELL};
}

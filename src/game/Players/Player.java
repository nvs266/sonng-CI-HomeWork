package game.Players;

import game.bases.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player extends Vector2D implements Common{

    private ImageRenderer leftImage;
    private ImageRenderer rightImage;
    private ImageRenderer straightImgae;
    private ImageRenderer currentImage;
    Contraints contraints;
    FrameCounter coolDownCounter;

    boolean spellDisable = false;
    private boolean UP, DOWN, LEFT, RIGHT, STANDSTILL;

    public Player(float boundX, float boundY, Contraints contraints) {
        //Set first location
        super();
        this.contraints = contraints;

        //Load Image
        this.leftImage = new ImageRenderer(Utils.loadAssetImage("players/left/0.png"));
        this.rightImage = new ImageRenderer(Utils.loadAssetImage("players/right/0.png"));
        this.straightImgae = new ImageRenderer(Utils.loadAssetImage("players/straight/0.png"));
        this.currentImage = this.straightImgae;

        this.set(boundX / 2, boundY - currentImage.image.getHeight());
        //Set first status
        STANDSTILL = true;
        UP = false;
        RIGHT = false;
        LEFT = false;
        DOWN = false;

        this.coolDownCounter = new FrameCounter(COOLDOWN_SPELL);
    }

    public void updateLocation() {
        int dx = 0;
        int dy = 0;
        if (RIGHT) dx += PLAYER_SPEED;
        if (LEFT) dx -= PLAYER_SPEED;
        if (UP) dy -= PLAYER_SPEED;
        if (DOWN) dy += PLAYER_SPEED;
        this.addUp(dx, dy);
        contraints.make(this.getPosition());
    }

    public void render(Graphics2D g2D) {
        if (STANDSTILL) currentImage = straightImgae;
        if (!LEFT && RIGHT) currentImage = rightImage;
        if (LEFT && !RIGHT) currentImage = leftImage;
        currentImage.render(g2D, this.getPosition());
    }

    public void setKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                UP = true;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = true;
                break;
            case KeyEvent.VK_LEFT:
                LEFT = true;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = true;
                break;
            default:
                break;
        }
    }

    public void setKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                UP = false;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = false;
                break;
            case KeyEvent.VK_LEFT:
                LEFT = false;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = false;
                break;
            default:
                break;
        }
    }

    public void castSpell(ArrayList<PlayerSpell> playerSpells) {
        if (!spellDisable) {
            PlayerSpell playerSpell = new PlayerSpell(this);
            playerSpells.add(playerSpell);
            spellDisable = true;
        }
    }

    public void coolDown() {
        if (spellDisable) {
            if (coolDownCounter.run()) {
                spellDisable = false;
                coolDownCounter.reset();
            }
        }
    }
}

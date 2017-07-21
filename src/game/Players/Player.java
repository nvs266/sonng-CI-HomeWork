package game.Players;

import game.bases.*;
import game.inputs.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player extends GameObject implements Common{

    private ImageRenderer leftImage;
    private ImageRenderer rightImage;
    private ImageRenderer straightImgae;

    public static Player instancePlayer;

    InputManager inputManager;
    Contraints contraints;
    FrameCounter coolDownCounter;

    boolean spellDisable = true;

    public Player(int width, int height, InputManager inputManager) {
        //Set first location
        super();
        this.inputManager = inputManager;

        //Load Image
        this.leftImage = new ImageRenderer(Utils.loadAssetImage("players/left/0.png"));
        this.rightImage = new ImageRenderer(Utils.loadAssetImage("players/right/0.png"));
        this.straightImgae = new ImageRenderer(Utils.loadAssetImage("players/straight/0.png"));
        this.imageRenderer = this.straightImgae;

        this.set((width - imageRenderer.image.getWidth()) / 2, HEIGHT - imageRenderer.image.getHeight() / 2);
        this.contraints = new Contraints(imageRenderer.image.getHeight(), HEIGHT - imageRenderer.image.getHeight(),  imageRenderer.image.getHeight() / 4, width - imageRenderer.image.getWidth() / 4);

        this.coolDownCounter = new FrameCounter(COOLDOWN_SPELL);

        instancePlayer = this;
    }

    @Override
    public void run() {
        move();
        coolDown();
        if (inputManager.xPressed) castSpell();
    }

    private void castSpell() {
        if (!spellDisable) {
            PlayerSpell playerSpell = new PlayerSpell(this);
            GameObject.add(playerSpell);

            spellDisable = true;
        }
    }


    @Override
    public void updatePicture() {
        if (inputManager.leftPressed) imageRenderer = leftImage;
        else if (inputManager.rightPressed) imageRenderer = rightImage;
        else imageRenderer = straightImgae;
    }

    private void move() {
        if (inputManager.upPressed) {
            this.y -= PLAYER_SPEED;
        }
        if (inputManager.downPressed) {
            this.y += PLAYER_SPEED;
        }
        if (inputManager.leftPressed) {
            this.x -= PLAYER_SPEED;
        }
        if (inputManager.rightPressed) {
            this.x += PLAYER_SPEED;
        }
        this.contraints.make(this.getPosition());
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

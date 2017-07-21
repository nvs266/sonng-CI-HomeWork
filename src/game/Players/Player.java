package game.Players;

import game.bases.*;
import game.inputs.InputManager;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player extends GameObject implements Common{

    private ImageRenderer[] leftImages;
    private ImageRenderer[] rightImages;
    private ImageRenderer[] straightImgaes;

    public static Player instancePlayer;

    InputManager inputManager;
    Contraints contraints;
    FrameCounter coolDownCounter;
    FrameCounter frameCounterChangeImage;

    boolean spellDisable = true;

    public Player(int width, int height, InputManager inputManager) {
        //Set first location
        super();
        this.inputManager = inputManager;

        //Load Image
        leftImages = new ImageRenderer[6];
        rightImages = new ImageRenderer[6];
        straightImgaes = new ImageRenderer[7];
        for (int i = 0; i < 6; i++) {
            leftImages[i] = new ImageRenderer(Utils.loadAssetImage(String.format("players/left/%d.png", i)));
            rightImages[i] = new ImageRenderer(Utils.loadAssetImage(String.format("players/right/%d.png", i)));
            straightImgaes[i] = new ImageRenderer(Utils.loadAssetImage(String.format("players/straight/%d.png", i)));
        }
        straightImgaes[6] = new ImageRenderer(Utils.loadAssetImage("players/straight/6.png"));
        this.imageRenderer = this.straightImgaes[0];
        frameCounterChangeImage = new FrameCounter(7);

        this.set((width - imageRenderer.image.getWidth()) / 2, HEIGHT - imageRenderer.image.getHeight() / 2);
        this.contraints = new Contraints(imageRenderer.image.getHeight(), HEIGHT - imageRenderer.image.getHeight() / 2,  imageRenderer.image.getHeight() / 4, width - imageRenderer.image.getWidth() / 4);

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
        boolean checkImage = false;
        if (inputManager.leftPressed) {
            for (int i = 0; i < 6; i++) {
                if (imageRenderer == leftImages[i]) {
                    checkImage = true;
                    if (!frameCounterChangeImage.run()) break;
                    switch (i) {
                        case 5:
                            imageRenderer = leftImages[0];
                            break;
                        default:
                            imageRenderer = leftImages[i+1];
                            break;
                    }
                    frameCounterChangeImage.reset();
                    break;
                }
            }
            if (!checkImage) imageRenderer = leftImages[0];
        } else if (inputManager.rightPressed) {
            for (int i = 0; i < 6; i++) {
                if (imageRenderer == rightImages[i]) {
                    checkImage = true;
                    if (!frameCounterChangeImage.run()) break;
                    switch (i) {
                        case 5:
                            imageRenderer = rightImages[0];
                            break;
                        default:
                            imageRenderer = rightImages[i+1];
                            break;
                    }
                    frameCounterChangeImage.reset();
                    break;
                }
            }
            if (!checkImage) imageRenderer = rightImages[0];
        } else {
            for (int i = 0; i < 7; i++) {
                if (imageRenderer == straightImgaes[i]) {
                    checkImage = true;
                    if (!frameCounterChangeImage.run()) break;
                    switch (i) {
                        case 6:
                            imageRenderer = straightImgaes[0];
                            break;
                        default:
                            imageRenderer = straightImgaes[i+1];
                            break;
                    }
                    frameCounterChangeImage.reset();
                    break;
                }
            }
            if (!checkImage) imageRenderer = straightImgaes[0];
        }
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

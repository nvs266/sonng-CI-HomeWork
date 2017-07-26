package game.Players;

import game.Enemies.BlackEnemy;
import game.Enemies.PinkEnemy;
import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.inputs.InputManager;

import java.awt.*;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player extends GameObject implements Setting, PhysicsBody {
    public static int life;
    public static int score;

    private ImageRenderer[] leftImages;
    private ImageRenderer[] rightImages;
    private ImageRenderer[] straightImgaes;

    public Sphere sphereLeft;
    public Sphere sphereRight;
    public static Player instancePlayer;

    InputManager inputManager;
    Contraints contraints;
    FrameCounter coolDownSpell;
    FrameCounter coolDownBullet;
    FrameCounter frameCounterChangeImage;

    boolean spellDisable = true;
    boolean bulletDisable = true;

    public Player(int width, int height, InputManager inputManager) {
        super();
        this.inputManager = inputManager;
        life = 100;
        score = 0;

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

        //Set first location
        this.set((width - imageRenderer.image.getWidth()) / 2, WINDOW_HEIGHT - imageRenderer.image.getHeight() / 2);
        //  Contraints
        this.contraints = new Contraints(imageRenderer.image.getHeight(), WINDOW_HEIGHT - imageRenderer.image.getHeight() / 2,  imageRenderer.image.getHeight() / 4, width - imageRenderer.image.getWidth() / 4);

        this.coolDownSpell = new FrameCounter(COOLDOWN_SPELL);
        this.coolDownBullet = new FrameCounter(40);

        instancePlayer = this;

        // sphere
        sphereLeft = GameObjectPool.recycle(Sphere.class);
        sphereLeft.set(-20, 0);
        this.children.add(sphereLeft);
        sphereRight = GameObjectPool.recycle(Sphere.class);
        sphereRight.set(20, 0);
        this.children.add(sphereRight);

        boxCollider = new BoxCollider(imageRenderer.getWidth() / 2, imageRenderer.getHeight() / 2);
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        move();
        hitEnemy();
        coolDown();
        if (inputManager.xPressed) castSpell();
    }

    private void hitEnemy() {
        PinkEnemy hitPinkEnemy = Physics.bodyInRect(this.boxCollider, PinkEnemy.class);
        BlackEnemy hitBlackEnemy = Physics.bodyInRect(this.boxCollider, BlackEnemy.class);
        if (hitBlackEnemy != null || hitPinkEnemy != null) {
            Player.life--;
        }
    }

    private void castSpell() {
        if (!spellDisable) {
            PlayerSpell playerSpell = GameObjectPool.recycle(PlayerSpell.class);
            playerSpell.set(this.x, this.y - imageRenderer.getHeight());
            spellDisable = true;
        }
        if (!bulletDisable) {
            sphereLeft.shoot();
            sphereRight.shoot();
            bulletDisable = true;
        }
    }


    @Override
    public void updatePicture() {
        super.updatePicture();
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
            if (coolDownSpell.run()) {
                spellDisable = false;
                coolDownSpell.reset();
            }
        }
        if (bulletDisable) {
            if (coolDownBullet.run()) {
                bulletDisable = false;
                coolDownBullet.reset();
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
        graphics2D.setColor(Color.red);
        graphics2D.setFont(new Font("serif", Font.BOLD, 30));
        if (life < 0) life = 0;
        graphics2D.drawString("Life: " + life, 500, 150);
        graphics2D.drawString("Score: " + score, 500, 200);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

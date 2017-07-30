package game.Players;

import game.Enemies.BlackEnemy;
import game.Enemies.Item;
import game.Enemies.PinkEnemy;
import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.Animation;
import game.inputs.InputManager;
import tklibs.AudioUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sonng on 7/12/2017.
 */
public class Player extends GameObject implements Setting, PhysicsBody {
    public int life;
    public int health;
    public int score;
    public int itemPoint;
    public boolean sphereActive;

    private Animation leftAnimation;
    private Animation rightAnimation;
    private Animation straightAnimation;

    private BufferedImage[] images;

    public Sphere sphereLeft;
    public Sphere sphereRight;
    public static Player instancePlayer;

    InputManager inputManager;
    Contraints contraints;
    FrameCounter coolDownSpell;
    FrameCounter coolDownBullet;

    boolean spellDisable = true;
    boolean bulletDisable = true;

    public Player(InputManager inputManager) {
        super();
        this.inputManager = inputManager;
        score = 0;
        life = 3;
        health = 5;
        itemPoint = 0;
        sphereActive= false;

        //Load Image
        images = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            images[i] = Utils.loadAssetImage(String.format("players/left/%d.png", i));
        }
        leftAnimation = new Animation(7, images);

        images = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            images[i] = Utils.loadAssetImage(String.format("players/right/%d.png", i));
        }
        rightAnimation = new Animation(7, images);

        images = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            images[i] = Utils.loadAssetImage(String.format("players/straight/%d.png", i));
        }
        straightAnimation = new Animation(7, images);

        imageRenderer = straightAnimation;

        //Set first location
        this.set(200, WINDOW_HEIGHT - images[0].getHeight() / 2);
        //  Contraints
        this.contraints = new Contraints(images[0].getHeight(),
                WINDOW_HEIGHT - images[0].getHeight() / 2,
                images[0].getWidth() / 2, 400 - images[0].getWidth() / 2);

        this.coolDownSpell = new FrameCounter(COOLDOWN_SPELL);
        this.coolDownBullet = new FrameCounter(40);

        instancePlayer = this;

        boxCollider = new BoxCollider(imageRenderer.getWidth() / 3, imageRenderer.getHeight() / 4);
        this.children.add(boxCollider);
    }

    public void init() {
        health = 5;
        itemPoint = 0;
        active = true;
    }

    @Override
    public void run(Vector2D parentPosition) {
        if (health <= 0) {
            PlayerExplosion playerExplosion = new PlayerExplosion();
            playerExplosion.init(this.getPosition());
            GameObject.add(playerExplosion);
            this.active = false;
        }
        super.run(parentPosition);
        move();
        hitCheck();
        coolDown();
        if (inputManager.xPressed) castSpell();
        GameObject.add(new Trail((int)x, (int)y, 0.05f, imageRenderer.getImage()));
    }

    private void hitCheck() {
        PinkEnemy hitPinkEnemy = Physics.bodyInRect(this.boxCollider, PinkEnemy.class);
        BlackEnemy hitBlackEnemy = Physics.bodyInRect(this.boxCollider, BlackEnemy.class);
        if (hitBlackEnemy != null || hitPinkEnemy != null) {
            this.health--;
        }
        Item hitItem = Physics.bodyInRect(this.boxCollider ,Item.class);
        if (hitItem != null) {
            hitItem.setActive(false);
            this.itemPoint++;
            AudioUtils.playMedia("assets/music/sfx/item-collect.wav");
        }
        if (!sphereActive && this.itemPoint >= 3) {
            this.sphereLeft = new Sphere();
            sphereLeft.set(-20, 0);
            this.children.add(sphereLeft);

            sphereRight = new Sphere();
            sphereRight.set(20, 0);
            this.children.add(sphereRight);

            AudioUtils.playMedia("assets/music/sfx/powerup.wav");

            sphereActive = true;
        }

    }

    private void castSpell() {
        if (!spellDisable) {
            AudioUtils.playMedia("assets/music/sfx/player-shoot.wav");
            PlayerSpell playerSpell = GameObjectPool.recycle(PlayerSpell.class);
            playerSpell.set(this.x, this.y - imageRenderer.getHeight());
            spellDisable = true;
        }
        if (!bulletDisable && sphereActive) {
            sphereLeft.shoot();
            sphereRight.shoot();
            bulletDisable = true;
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

        if (inputManager.shiftPressed && sphereActive) {
            sphereLeft.set(-15, -15);
            sphereRight.set(15, -15);
        } else if (!inputManager.shiftPressed && sphereActive) {
            sphereLeft.set(-20, 0);
            sphereRight.set(20, 0);
        }

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

    private void checkStatus() {
        imageRenderer = straightAnimation;
        if (inputManager.leftPressed && !inputManager.rightPressed) {
            imageRenderer = leftAnimation;
        } else if (inputManager.rightPressed && !inputManager.leftPressed) {
            imageRenderer = rightAnimation;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        checkStatus();
        super.render(graphics2D);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }

    public void destruction() {
        life--;
        active = false;
        if (sphereActive) {
            sphereActive = false;
            sphereLeft.setActive(false);
            sphereRight.setActive(false);
        }
        itemPoint = 0;
    }
}

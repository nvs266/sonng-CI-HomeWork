package game.Players;

import game.Enemies.PinkEnemy;
import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends GameObject implements Setting, PhysicsBody {

    private ImageRenderer[] imageRenderers;
    private FrameCounter frameCounterChangeImage;

    public PlayerSpell(Player player) {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        imageRenderers = new ImageRenderer[4];
        for (int i = 0; i < 4; i++) {
            imageRenderers[i] = new ImageRenderer(Utils.loadAssetImage(String.format("player-spells/a/%d.png", i)));
        }
        imageRenderer = imageRenderers[0];

        set(player.getPosition().add(0, - Player.instancePlayer.imageRenderer.image.getHeight()));

        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.y -= SPELL_SPEED;
        hitEnemy();
        if (isOutOfMap()) active = false;
    }


    @Override
    public void updatePicture() {
        if (frameCounterChangeImage.run()) {
            for (int i = 0; i < 4; i++) {
                if (imageRenderer == imageRenderers[i]) {
                    switch(i) {
                        case 3:
                            imageRenderer = imageRenderers[0];
                            break;
                        default:
                            imageRenderer = imageRenderers[i+1];
                            break;
                    }
                    break;
                }
            }
            frameCounterChangeImage.reset();
        }
    }

    private void hitEnemy() {
        PinkEnemy enemy = Physics.bodyInRect(this.getBoxCollider(), PinkEnemy.class);
        if (enemy != null) {
            enemy.active = false;
            this.active = false;
        }
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

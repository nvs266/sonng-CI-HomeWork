package game.Players;

import game.bases.*;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends GameObject implements Setting {

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

        subject = SUBJECTS.PLAYER_SPELL;
        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.y -= SPELL_SPEED;
        if (isOutOfMap()) visible = false;
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

    @Override
    public void checkHitBox(GameObject other) {
        if (other.subject == SUBJECTS.PINK_ENEMY && this.boxCollider.collideWith(other.boxCollider)) {
            Player.score++;
            other.visible = false;
        }
    }
}

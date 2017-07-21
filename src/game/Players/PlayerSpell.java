package game.Players;

import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends GameObject implements Common {

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
    }

    public void run() {
        this.y -= SPELL_SPEED;
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
}

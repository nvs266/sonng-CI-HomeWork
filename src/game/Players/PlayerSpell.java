package game.Players;

import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends GameObject implements Common {

    public PlayerSpell(Player player) {
        super();
        imageRenderer = new ImageRenderer(Utils.loadAssetImage("player-spells/a/1.png"));
        set(player.getPosition().add(0, - Player.instancePlayer.imageRenderer.image.getHeight()));
    }

    public void run() {
        this.y -= SPELL_SPEED;
    }

}

package game.Players;

import game.bases.Common;
import game.bases.ImageRenderer;
import game.bases.Utils;
import game.bases.Vector2D;

import java.awt.*;

/**
 * Created by sonng on 7/12/2017.
 */
public class PlayerSpell extends Vector2D implements Common {

    private ImageRenderer imageRenderer;

    public PlayerSpell(Player player) {
        super();
        imageRenderer = new ImageRenderer(Utils.loadAssetImage("player-spells/a/1.png"));
        set(player.getPosition().add(0, -20));
    }

    public void update() {
        this.y -= SPELL_SPEED;
    }

    public void render(Graphics2D g2d) {
        imageRenderer.render(g2d, this.getPosition());
    }
}

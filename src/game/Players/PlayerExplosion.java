package game.Players;

import game.GameWindow;
import game.bases.GameObject;
import game.bases.Utils;
import game.bases.Vector2D;
import game.bases.renderers.Animation;
import javafx.scene.media.MediaPlayer;
import tklibs.AudioUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerExplosion extends GameObject {
    private BufferedImage[] images;

    public PlayerExplosion() {
        super();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/music/sfx/player-dead.wav");
        mediaPlayer.setAutoPlay(true);
        Player.instancePlayer.destruction();
        images = new BufferedImage[16];
        int i = 0;
        for (i = 0; i < 8; i++) {
            images[i] = Utils.loadAssetImage(String.format("players/explosions/%d.png", i));
        }
        for (int j = 7; j >= 0 ; j--) {
            images[i] = Utils.loadAssetImage(String.format("players/explosions/%d.png", j));
            i++;
        }
        imageRenderer = new Animation(5, images);
    }

    public void init(Vector2D position) {
        this.set(position);
        imageRenderer = new Animation(5, images);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
        if (imageRenderer.getIndex() == images.length - 1) {
            this.active = false;
            if (Player.instancePlayer.life > 0) {
                Player.instancePlayer.init();
            } else {
                GameWindow.inGame = false;
            }
        }
    }
}

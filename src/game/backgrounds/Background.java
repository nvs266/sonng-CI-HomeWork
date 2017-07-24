package game.backgrounds;

import game.bases.*;

import java.awt.*;

/**
 * Created by sonng on 7/18/2017.
 */
public class Background extends GameObject implements Setting {

    private static Background instanceBackground;

    public static Background getInstanceBackground() {
        return instanceBackground;
    }

    public Background() {
        super();
        this.imageRenderer = new ImageRenderer(Utils.loadAssetImage("background/0.png"));
        set(0, WINDOW_HEIGHT - this.imageRenderer.image.getHeight());
        instanceBackground = this;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (this.getY() <= 0) this.addUp(0, BACKGROUND_SPEED);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(imageRenderer.image, 0, (int) this.getY(), null);
    }

    @Override
    public void setStatus(boolean parentEnable) {
        visible = true;
    }
}

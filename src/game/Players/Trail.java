package game.Players;

import game.bases.GameObject;
import game.bases.Utils;
import game.bases.Vector2D;
import game.bases.renderers.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Trail extends GameObject {
    private float alpha = 1;

    private BufferedImage image;

    private float life;

    public Trail(int x, int y, float life, BufferedImage image) {
        super();
        set(x, y);
        this.image = image;
        this.life = life;
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (alpha > life){
            alpha -= (life - 0.000000000000000000000000000000000000000000001f);
        }
        else active = false;
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        g2d.setComposite(makeTransparent(alpha));

        g2d.drawImage(image, (int)x - image.getWidth()/2, (int)y - image.getHeight()/2, null);
//        g2d.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

}

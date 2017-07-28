package game.bases.renderers;

import game.bases.FrameCounter;
import game.bases.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Animation implements Renderer {
    private List<BufferedImage> images;
    private int imageIndex;
    FrameCounter frameCounter;

    public Animation(int delayFrame, BufferedImage... imagesArr) {
        this.images = Arrays.asList(imagesArr);
        frameCounter = new FrameCounter(delayFrame);
    }

    private void changeIndex() {
        imageIndex++;
        if (imageIndex == images.size()) imageIndex = 0;
    }

    @Override
    public void render(Graphics g, Vector2D position) {
        if (frameCounter.run()) {
            changeIndex();
            frameCounter.reset();
        }
        BufferedImage image = images.get(imageIndex);
        g.drawImage(image,
                (int)(position.x - image.getWidth() / 2),
                (int)(position.y - image.getHeight() / 2), null);
    }

    @Override
    public int getIndex() {
        return imageIndex;
    }

    @Override
    public float getHeight() {
        return images.get(imageIndex).getHeight();
    }

    @Override
    public float getWidth() {
        return images.get(imageIndex).getWidth();
    }
}

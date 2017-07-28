package game.bases.renderers;

import game.bases.Vector2D;

import java.awt.*;

public interface Renderer {
    void render(Graphics g, Vector2D position);
    public int getIndex();
    float getWidth();
    float getHeight();
}

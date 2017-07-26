package game.bases;

import game.backgrounds.Background;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.ImageRenderer;
import game.bases.renderers.Renderer;

import java.awt.*;
import java.util.Vector;

/**
 * Created by sonng on 7/18/2017.
 */
public class GameObject extends Vector2D implements Setting{
    public BoxCollider boxCollider;

    public Vector2D screenPosition;


    public Vector<GameObject> children;

    private static Vector<GameObject> gameObjects = new Vector<>();

    private static Vector<GameObject> newGameObjects = new Vector<>();
    public GameObject() {
        super();
        this.imageRenderer = null;
        children = new Vector<GameObject>();
        screenPosition = new Vector2D();
    }


    public boolean active = true;

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public static void add(GameObject gameObject) {
        newGameObjects.add(gameObject);
        if (gameObject instanceof PhysicsBody) {
            Physics.add((PhysicsBody) gameObject);
        }
    }

    public void run(Vector2D parentPosition) {
        this.screenPosition = parentPosition.add(this.getPosition());
        for (GameObject child: children) {
            child.run(screenPosition);
        }
    }

    public static void runAll() {
        for (GameObject gameObject: gameObjects) {
            if (gameObject.active) {
                gameObject.run(Vector2D.ZERO);
            }
        }
        gameObjects.addAll(newGameObjects);
        newGameObjects.clear();
    }

    public Renderer imageRenderer;

    public void render(Graphics2D graphics2D) {
        if (imageRenderer != null && active) {
            imageRenderer.render(graphics2D, this.screenPosition);
            for (GameObject child: children) {
                child.render(graphics2D);
            }
        }
    }

    public static void renderAll(Graphics2D graphics2D) {
        for (GameObject gameObject: gameObjects) {
            if (gameObject.isActive()) {
                gameObject.render(graphics2D);
            }
        }

    }

    public boolean isOutOfMap() {
        if (getPosition().x + imageRenderer.getWidth() / 2 < 0 ||
                getPosition().x + imageRenderer.getWidth() / 2 > Background.getInstanceBackground().imageRenderer.getWidth() ||
                getPosition().y + imageRenderer.getHeight() / 2 < 0 ||
                getPosition().y - imageRenderer.getHeight() / 2 > WINDOW_HEIGHT) return true;
        return false;
    }
}

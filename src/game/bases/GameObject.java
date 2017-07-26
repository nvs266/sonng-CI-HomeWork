package game.bases;

import game.backgrounds.Background;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;

import java.awt.*;
import java.util.Iterator;
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
            gameObject.run(Vector2D.ZERO);
        }
        gameObjects.addAll(newGameObjects);
        newGameObjects.clear();
    }


    public ImageRenderer imageRenderer;
    public void render(Graphics2D graphics2D) {
        if (imageRenderer != null) {
            imageRenderer.render(graphics2D, this.screenPosition);
        }
        for (GameObject child: children) {
            child.render(graphics2D);
        }
    }

    public static void renderAll(Graphics2D graphics2D) {
        for (GameObject gameObject: gameObjects) {
            gameObject.render(graphics2D);
        }

    }

    public void updatePicture() {
        for (GameObject child: children) {
            child.updatePicture();
        }
    }

    public static void updateAllPicture() {
        for (GameObject gameObject: gameObjects) {
            gameObject.updatePicture();
        }
    }

    private static Vector<GameObject> removeAllObjects = new Vector<>();

    public  void removeObject(boolean parentVisible) {
        if (!parentVisible) {
            for (GameObject child: this.children) {
                child.removeObject(false);
            }
            removeAllObjects.add(this);
        }
    }

    public static void removeObjects() {
        for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext();) {
            GameObject gameObject = iterator.next();
            gameObject.removeObject(gameObject.active);
        }
        gameObjects.removeAll(removeAllObjects);
        removeAllObjects.clear();
    }

    public boolean isOutOfMap() {
        if (getPosition().x + imageRenderer.getWidth() / 2 < 0 ||
                getPosition().x + imageRenderer.getWidth() / 2 > Background.getInstanceBackground().imageRenderer.getWidth() ||
                getPosition().y + imageRenderer.getHeight() / 2 < 0 ||
                getPosition().y - imageRenderer.getHeight() / 2 > WINDOW_HEIGHT) return true;
        return false;
    }

}

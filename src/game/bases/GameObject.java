package game.bases;

import game.backgrounds.Background;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by sonng on 7/18/2017.
 */
public class GameObject extends Vector2D{

    public ImageRenderer imageRenderer;

    private static Vector<GameObject> gameObjects = new Vector<>();
    private static Vector<GameObject> newGameObjects = new Vector<>();

    public GameObject() {
        super();
        this.imageRenderer = null;
    }

    public static void renderAll(Graphics2D graphics2D) {
        for (GameObject gameObject: gameObjects) {
            gameObject.render(graphics2D);
        }

    }

    public static void runAll() {
        for (GameObject gameObject: gameObjects) {
            gameObject.run();
        }
        gameObjects.addAll(newGameObjects);
        newGameObjects.clear();
    }

    public static void updateAllPicture() {
        for (GameObject gameObject: gameObjects) {
            gameObject.updatePicture();
        }
    }

    public static void add(GameObject gameObject) {
        newGameObjects.add(gameObject);
    }

    public void render(Graphics2D graphics2D) {
        if (imageRenderer != null) {
            imageRenderer.render(graphics2D, this.getPosition());
        }
    }

    public void updatePicture() {

    }

    public void run() {
    }

}

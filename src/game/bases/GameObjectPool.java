package game.bases;

import java.util.Vector;

public class GameObjectPool {
    private static Vector<GameObject> pool = new Vector<>();
    public static <T extends GameObject> T recycle(Class<T> tClass) {
        for (GameObject gameObject: pool) {
            if (!gameObject.isActive() && gameObject.getClass() == tClass) {
                gameObject.setActive(true);
                return (T)gameObject;
            }
        }
        try {
            T newGameObject = tClass.newInstance();
            pool.add(newGameObject);
            GameObject.add(newGameObject);
            return newGameObject;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void clearAll() {
        pool.clear();
    }
}

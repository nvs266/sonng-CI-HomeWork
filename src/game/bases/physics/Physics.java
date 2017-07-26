package game.bases.physics;

import game.bases.BoxCollider;
import game.bases.GameObject;

import java.util.Vector;

public class Physics {
    public static Vector<PhysicsBody> bodies = new Vector<>();
    
    public static void add(PhysicsBody body) {
        bodies.add(body);
    }
    
    public static <T extends PhysicsBody> T bodyInRect(BoxCollider other, Class<T> tClass) {
        for (PhysicsBody body: bodies) {
            if (body.getBoxCollider().collideWith(other) && body.getClass() == tClass && body.isActive()) {
                return (T)body;
            }
        }
        return null;
    }
}

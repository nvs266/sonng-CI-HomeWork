package game.bases.physics;

import game.bases.BoxCollider;

public interface PhysicsBody {
    BoxCollider getBoxCollider();
    boolean isActive();
}

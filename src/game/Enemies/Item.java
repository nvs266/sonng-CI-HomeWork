package game.Enemies;

import game.bases.*;
import game.bases.physics.Physics;
import game.bases.physics.PhysicsBody;
import game.bases.renderers.Animation;

public class Item extends GameObject implements PhysicsBody{
    public Item() {
        imageRenderer = new Animation(0, Utils.loadAssetImage("items/power-up-red.png"));
        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.addUp(0, 3);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.boxCollider;
    }
}

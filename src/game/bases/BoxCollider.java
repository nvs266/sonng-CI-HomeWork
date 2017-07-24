package game.bases;

public class BoxCollider extends GameObject{
    public float width;
    public float height;

    public BoxCollider(float width, float height) {
        super();
        this.width = width;
        this.height = height;
    }


    public BoxCollider() {
        this(0, 0);
    }

    public boolean collideWith(BoxCollider other) {
        return ((Math.abs(this.screenPosition.x - other.screenPosition.x) < (this.width + other.width) / 2) &&
                (Math.abs(this.screenPosition.y - other.screenPosition.y) < (this.height + other.height) / 2) &&
                other.screenPosition.x > 0 && other.screenPosition.y > 0 && this.screenPosition.x > 0 && this.screenPosition.y > 0);
    }

    @Override
    public String toString() {
        return "BoxCollider{" +
                "width=" + width +
                ", height=" + height +
                ", screenPosition=" + screenPosition +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

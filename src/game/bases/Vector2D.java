package game.bases;

import java.util.Vector;

/**
 * Created by sonng on 7/17/2017.
 */
public class Vector2D {

    public float x;
    public float y;

    public Vector2D getPosition() {
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public  Vector2D() {
        this(0, 0);
    }

    public void addUp(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void addUp(Vector2D vector2D) {
        this.addUp(vector2D.x, vector2D.y);
    }

    public Vector2D add(float x, float y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D add(Vector2D vector2D) {
        return add(vector2D.x, vector2D.y);
    }

    public float magnitude() {
        return (float) Math.sqrt(x*x + y*y);
    }

    public void multiplyBy(int a) {
        this.x *= a;
        this.y *= a;
    }

    public Vector2D multiply(int a) {
        return new Vector2D(this.x * a, this.y * a);
    }

    public Vector2D nomalize() {
        float mag = this.magnitude();
        return new Vector2D(this.x / mag, this.y / mag);
    }

    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    public Vector2D invert() {
        return new Vector2D(this.x * (-1), this.y * (-1));
    }

    public Vector2D subtract(Vector2D vector2D) {
        return new Vector2D(this.x - vector2D.x, this.y - vector2D.y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D vector2D) {
        set(vector2D.x, vector2D.y);
    }

}

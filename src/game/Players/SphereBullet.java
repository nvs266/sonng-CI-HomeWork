package game.Players;

import game.Enemies.BlackEnemy;
import game.bases.*;

public class SphereBullet extends GameObject implements Setting{
    private ImageRenderer[] imageRenderers;
    private FrameCounter frameCounterChangeImage;
    public Vector2D velocity;
    private BoxCollider boxCollider;

    public SphereBullet(Sphere sphere) {
        super();
        frameCounterChangeImage = new FrameCounter(5);

        imageRenderers = new ImageRenderer[4];
        for (int i = 0; i < 4; i++) {
            imageRenderers[i] = new ImageRenderer(Utils.loadAssetImage(String.format("sphere-bullets/%d.png", i)));
        }
        imageRenderer = imageRenderers[0];

        set(sphere.screenPosition.add(0, - sphere.imageRenderer.image.getHeight()));

        velocity = new Vector2D(0,0);

        boxCollider = new BoxCollider(this.imageRenderer.image.getWidth(), this.imageRenderer.image.getHeight());
        this.children.add(boxCollider);

        subject = SUBJECTS.SPHERE_BNULLET;
        boxCollider = new BoxCollider(imageRenderer.getWidth(), imageRenderer.getHeight());
        this.children.add(boxCollider);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (BlackEnemy.intanceBlack.visible) {
            Vector2D tartget =  BlackEnemy.intanceBlack.getPosition();
            velocity = tartget.subtract(this.getPosition()).nomalize().multiply(Setting.SPHERE_BULLET_SPEED);
            this.getPosition().addUp(velocity);
            if (boxCollider.collideWith(BlackEnemy.intanceBlack.boxCollider)) {
                this.visible = false;
                BlackEnemy.life--;
            }
        } else this.getPosition().addUp(0, -Setting.SPHERE_BULLET_SPEED);
        if (isOutOfMap()) visible = false;
    }

    @Override
    public void updatePicture() {
        if (frameCounterChangeImage.run()) {
            for (int i = 0; i < 4; i++) {
                if (imageRenderer == imageRenderers[i]) {
                    switch(i) {
                        case 3:
                            imageRenderer = imageRenderers[0];
                            break;
                        default:
                            imageRenderer = imageRenderers[i+1];
                            break;
                    }
                    break;
                }
            }
            frameCounterChangeImage.reset();
        }
    }
}

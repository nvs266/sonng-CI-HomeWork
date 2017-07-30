package game.inputs;

import com.sun.org.apache.regexp.internal.REProgram;
import game.GameWindow;
import game.bases.GameObject;
import game.bases.GameObjectPool;
import game.bases.physics.Physics;

import java.awt.event.KeyEvent;

/**
 * Created by sonng on 7/18/2017.
 */
public class InputManager {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean xPressed;
    public boolean enterPressed;
    public boolean shiftPressed;

    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_X:
                xPressed = true;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = true;
                break;
            case KeyEvent.VK_SHIFT:
                shiftPressed = true;
                break;
            default:
                break;
        }
    }

    public void keyRealeased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_X:
                xPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_SHIFT:
                shiftPressed = false;
                break;
            default:
                break;
        }
    }

}

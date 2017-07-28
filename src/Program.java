import com.sun.media.jfxmedia.MediaPlayer;
import game.GameWindow;
import tklibs.AudioUtils;

/**
 * Created by sonng on 7/4/2017.
 */
public class Program {

    public static void main(String[] args) {
//        AudioUtils.initialize();
//        AudioUtils.playMedia("assets/music/1.mp3");
        GameWindow gameWindow = new GameWindow();
        gameWindow.loop();
    }
}


package game.Players;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by sonng on 7/12/2017.
 */
public class Utils {
    public static BufferedImage loadImage(String url) throws Exception {
        try {
            return ImageIO.read(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage loadAssetImage(String url) {
        try {
            return loadImage("assets/images/" + url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

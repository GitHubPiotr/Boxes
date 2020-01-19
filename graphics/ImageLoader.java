
package boxes.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
    
    BufferedImage iamge = null;
    
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}

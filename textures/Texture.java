
package boxes.textures;

import java.awt.image.BufferedImage;

public class Texture {
	
    public static BufferedImage getTexture(int i) {
        if(i == 0) return VoidTexture.getVoidTexture();
        else if(i == 1) return WallTexture.getWallTexture();
        else if(i == 2) return FloorTexture.getFloorTexture();
        else return VoidTexture.getVoidTexture();
    }

}

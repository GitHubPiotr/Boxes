package boxes.graphics;

import boxes.worlds.LoadWorld;
import java.awt.image.BufferedImage;

public class Assets {

    private static final int TEXTURE_SIZE = 32;
    private static LoadWorld[] map;
    
    public static BufferedImage 
            player, 
            wall, floor, floor_s, box, box_s, void_s,
            menu, options, levelOptions, title,
            gameLoaded, gameNLoaded, gameSaved, won, done, speaker, speakerM;
    public static BufferedImage [] could = new BufferedImage[6];

    public static void init() {
        menu = ImageLoader.loadImage("/textures/menu.png");
        options = ImageLoader.loadImage("/textures/options.png");
        levelOptions = ImageLoader.loadImage("/textures/leveloptions.png");
        title = ImageLoader.loadImage("/textures/title.png");
        won = ImageLoader.loadImage("/textures/won.png");
        done = ImageLoader.loadImage("/textures/done.png");
        
        SpriteSheet textureSheet = new SpriteSheet(ImageLoader.loadImage("/textures/graphics.png"));
        player = textureSheet.crop(0, 0, TEXTURE_SIZE, TEXTURE_SIZE);
        wall = textureSheet.crop(TEXTURE_SIZE, 0, TEXTURE_SIZE, TEXTURE_SIZE);
        floor = textureSheet.crop(0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
        floor_s = textureSheet.crop(TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
        box = textureSheet.crop(0, TEXTURE_SIZE * 2, TEXTURE_SIZE, TEXTURE_SIZE);
        box_s = textureSheet.crop(TEXTURE_SIZE, TEXTURE_SIZE * 2, TEXTURE_SIZE, TEXTURE_SIZE);
        void_s = textureSheet.crop(0, TEXTURE_SIZE * 3, TEXTURE_SIZE, TEXTURE_SIZE);
        SpriteSheet couldSheet = new SpriteSheet(ImageLoader.loadImage("/textures/coulds.png"));
        could[0] = couldSheet.crop(0,     0, 200, 75);
        could[1] = couldSheet.crop(200,   0, 200, 75);
        could[2] = couldSheet.crop(0,    75, 200, 75);
        could[3] = couldSheet.crop(200,  75, 200, 75);
        could[4] = couldSheet.crop(0,   150, 200, 75);
        could[5] = couldSheet.crop(200, 150, 200, 75);
        SpriteSheet gameLS = new SpriteSheet(ImageLoader.loadImage("/textures/loadsave.png"));
        gameSaved = gameLS.crop(0, 0, 300, 75);
        gameLoaded = gameLS.crop(0, 75, 300, 75);
        gameNLoaded = gameLS.crop(0, 150, 300, 75);
        SpriteSheet speakerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/speaker.png"));
        speaker = speakerSheet.crop(0, 0, 30, 30);
        speakerM = speakerSheet.crop(0, 30, 30, 30);
    }
}

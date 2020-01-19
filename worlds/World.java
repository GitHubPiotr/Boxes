package boxes.worlds;

import boxes.engine.Engine;
import java.awt.Graphics;
import boxes.textures.Texture;

public class World {

    private Engine engine;
    public static final int TEXTURE_SIZE = 32;
    private int intTexture;

    public World(Engine engine) {
        this.engine = engine;
    }
    
    public void tick() {

    }
    // RENDER THE LEVEL
    public void render(Graphics g, int MAP_ID) {
        intTexture = 0;
        for (int i = 0; i < LoadWorld.getX() * TEXTURE_SIZE; i += TEXTURE_SIZE) {
            for (int j = 0; j < LoadWorld.getY() * TEXTURE_SIZE; j += TEXTURE_SIZE, intTexture++) {
                g.drawImage(Texture.getTexture(engine.getMap(MAP_ID).getArrayList(MAP_ID).get(intTexture)), j, i, null);
            }
        }
    }
}

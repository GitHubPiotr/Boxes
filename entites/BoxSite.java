package boxes.entites;

import java.awt.Graphics;
import boxes.engine.Engine;
import boxes.graphics.Assets;

public class BoxSite extends Entity {

    public BoxSite(Engine engine, int x, int y) {
        super(engine);
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.floor_s, x, y, null);
    }
    
    public void boxLocated(Graphics g) {
        g.drawImage(Assets.box_s, x, y, null);
    }
    
    @Override
    public void reset() {
        
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

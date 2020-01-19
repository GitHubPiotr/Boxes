package boxes.entites;

import java.awt.Graphics;
import boxes.engine.Engine;
import boxes.graphics.Assets;

public class Box extends Entity {

    public Box(Engine engine, int x, int y) {
        super(engine);
        this.x = copyX = x;
        this.y = copyY = y;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.box, x, y, null);
    }
 
    static void moveBox(Box box, int direction) {
        if (direction == 0) box.y--;
        if (direction == 1) box.y++;
        if (direction == 2) box.x--;
        if (direction == 3) box.x++;
    }

    @Override
    public void reset() {
        x = copyX;
        y = copyY;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}

package boxes.entites;

import java.awt.Graphics;
import boxes.engine.Engine;

public abstract class Entity {

    protected int x, y;
    protected int copyX, copyY;
    protected Engine engine;

    public Entity(Engine engine) {
        this.engine = engine;
    }

    abstract void tick();

    abstract void render(Graphics g);
    
    abstract void reset();
}

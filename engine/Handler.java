package boxes.engine;

import boxes.worlds.LoadWorld;
import java.awt.Canvas;
import javax.swing.JFrame;

public class Handler {

    private Engine engine;
    
    Handler(Engine engine) {
        this.engine = engine;
    }
    
    public JFrame getFrame() {
        return engine.getFrame();
    }

    public Canvas getCanvas() {
        return engine.getCanvas();
    }

    public MouseManager getMouseManager() {
        return engine.getMouseManager();
    }

    public KeyManager getKeyManager() {
        return engine.getKeyManager();
    }

    public LoadWorld getMap(int index) {
        return engine.getMap(index);
    }

    public SoundPlayer getClickSound() {
        return engine.getClickSound();
    }

    public SoundPlayer getMusicSound() {
        return engine.getMusicSound();
    }
}

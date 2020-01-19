package boxes.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean[] control = new boolean[4];
    public boolean r, t, esc;

    public KeyManager() {
        keys = new boolean[256];
    }

    public void tick() {
        control[0] = keys[KeyEvent.VK_W];
        control[1] = keys[KeyEvent.VK_S];
        control[2] = keys[KeyEvent.VK_A];
        control[3] = keys[KeyEvent.VK_D];
        r = keys[KeyEvent.VK_R];
        t = keys[KeyEvent.VK_T];
        esc = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

}

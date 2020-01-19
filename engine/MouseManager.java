package boxes.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean[] buttons;
    public boolean left, right;
    private int mouseX, mouseY;
    
    public MouseManager() {
        buttons = new boolean[5];
    }

    public void tick() {
        left = buttons[MouseEvent.BUTTON1];
        right = buttons[MouseEvent.BUTTON2];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public int getX() {
        return mouseX;
    }

    public int getY() {
        return mouseY;
    }
}

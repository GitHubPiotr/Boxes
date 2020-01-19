package boxes.states;

import java.awt.Graphics;

public abstract class State {

    private static State currentState;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    protected abstract void init();

}

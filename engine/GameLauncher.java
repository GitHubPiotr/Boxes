package boxes.engine;

public class GameLauncher {

    public static final String TITLE = "Boxes";
    public static final int WIDTH = 960;
    public static final int HEIGHT = 640;

    public static void main(String[] args) {
        Engine engine = new Engine(TITLE, WIDTH+6, HEIGHT+29);
        engine.init();
    }

}

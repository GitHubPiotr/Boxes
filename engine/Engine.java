package boxes.engine;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;
import boxes.graphics.Assets;
import boxes.states.MainMenuState;
import boxes.states.State;
import boxes.worlds.LoadWorld;
import java.awt.Canvas;
import javax.swing.JFrame;

public class Engine implements ActionListener {

    // WINDOW
    private final String title;
    private final int width, height;
    private Window window;

    // HANDLER
    private Handler handler;
    
    // STATES
    private State state;

    // MAPS
    private LoadWorld [] map;

    // KEY & MOUSE MANAGER
    private MouseManager mouseManager;
    private KeyManager keyManager;

    // DRAWING
    private BufferStrategy bufferStrategy;
    private Graphics g;

    // TIMER
    private Timer timer;

    // SOUNDS
    private SoundPlayer clickSound = new SoundPlayer("/sounds/click.wav");
    private SoundPlayer musicSound = new SoundPlayer("/sounds/music.wav");
    
    Engine(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    void init() {
        window = new Window(title, width, height);
        window.createWindow();
        
        handler = new Handler(this);
        
        addKeyAndMouseListeners();
        Assets.init();
        loadLevels();
        
        state = new MainMenuState(this);
        State.setState(state);
        
        timer = new Timer(10, this);
        timer.start();
    }

    // MAIN GAME LOOP
    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
        render();    
    }
    
    private void tick() {
        mouseManager.tick();
        keyManager.tick();

        if (State.getState() != null) {
            State.getState().tick();
        }
    }

    private void render() {
        bufferStrategy = window.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        if (State.getState() != null) {
            State.getState().render(g);
        }

        bufferStrategy.show();
        g.dispose();
    }

    private void addKeyAndMouseListeners() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        window.getJFrame().addKeyListener(keyManager);
        window.getCanvas().addMouseListener(mouseManager);
        window.getCanvas().addMouseMotionListener(mouseManager);
    }
    
    private void loadLevels() {
        map = new LoadWorld[18];
        map[0] = new LoadWorld("/levels/maps/lvl1.txt");
        map[1] = new LoadWorld("/levels/maps/lvl2.txt");
        map[2] = new LoadWorld("/levels/maps/lvl3.txt");
        map[3] = new LoadWorld("/levels/maps/lvl4.txt");
        map[4] = new LoadWorld("/levels/maps/lvl5.txt");
        map[5] = new LoadWorld("/levels/maps/lvl6.txt");
        map[6] = new LoadWorld("/levels/maps/lvl7.txt");
        map[7] = new LoadWorld("/levels/maps/lvl8.txt");
        map[8] = new LoadWorld("/levels/maps/lvl9.txt");
        map[9] = new LoadWorld("/levels/maps/lvl10.txt");
        map[10] = new LoadWorld("/levels/maps/lvl11.txt");      
        map[11] = new LoadWorld("/levels/maps/lvl12.txt");
    }
    
    public JFrame getFrame() {
        return window.getJFrame();
    } 
    
    public Canvas getCanvas() {
        return window.getCanvas();
    }
    
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public LoadWorld getMap(int index) {
        return map[index - 1];
    }
    
    public SoundPlayer getClickSound() {
        return clickSound;
    }
    
    public SoundPlayer getMusicSound() {
        return musicSound;
    }
    
}

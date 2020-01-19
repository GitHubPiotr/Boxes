package boxes.states;

import java.awt.Graphics;
import java.awt.Rectangle;
import boxes.engine.Engine;
import boxes.engine.GameLauncher;
import boxes.graphics.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainMenuState extends State {

    private final Engine engine;
    private static MainMenuState currentState;
    private State levelMenu;
    private final int countOfCoulds = 6;
    private int buttonPosisionX, buttonPosisionY, couldPosisionY, titlePosisionY, menuPosisionY;
    private double couldPosisionX;
    private final Rectangle [] button = new Rectangle[3];
    private final Rectangle speaker = new Rectangle(900, 20, 30, 30);
    private boolean dir, buttonPressed, mutedSpeaker;
    private float alpha = 1f;
    private int boxCount, x, y;
    private enum Notification { CANT_LOAD, LOADED, EMPTY }
    private Notification n;
    
    public MainMenuState(Engine engine) {
        this.engine = engine;
        currentState = this;
        n = Notification.EMPTY;
        engine.getMusicSound().play(true);
        init();       
    }

    @Override
    public void tick() {
        // MENU ANIMATIONS
        animations();
        // START MENU ANIMATION IF START BUTTON WAS PRESSED
        if (engine.getMouseManager().left == true && button[0].contains(engine.getMouseManager().getX(), engine.getMouseManager().getY()) || buttonPressed == true) {
            if(!buttonPressed) engine.getClickSound().play(false);
            buttonPressed = true;
            menuPosisionY-=10;
            buttonPosisionY-=10;
            couldPosisionY-=10;
            titlePosisionY-=12;
            if(menuPosisionY <= 0) {
                buttonPressed = false;
                titlePosisionY = 500; 
                buttonPosisionY = 0; couldPosisionY = 0; couldPosisionX = 0;
                menuPosisionY = Assets.menu.getHeight()-960;
                State.setState(levelMenu);
            }
        }
       
        // LOAD A GAME, IF LOAD BUTTON WAS PRESSED 
        if (engine.getMouseManager().left == true && button[1].contains(engine.getMouseManager().getX(), engine.getMouseManager().getY())) {
            engine.getClickSound().play(false);
            loadGame();
        }         
        // EXIT A GAME, IF EXIT BUTTON WAS PRESSED
        if (engine.getMouseManager().left == true && button[2].contains(engine.getMouseManager().getX(), engine.getMouseManager().getY())) {
            engine.getClickSound().play(false);
            System.exit(0);
        }
        
        if (engine.getMouseManager().left == true && speaker.contains(engine.getMouseManager().getX(), engine.getMouseManager().getY())) {
            engine.getMusicSound().setVolume();
            mutedSpeaker = !mutedSpeaker;
        }
    }

    @Override
    public void render(Graphics g) {
        // BACKGROUND AND OPTIONS(BUTTONS) GRAPHICS
        background(g);
        // COULDS GRAPHICS
        coulds(g);
        // SPEAKER
        g.drawImage(Assets.speaker, 900, 20, null);
        if(mutedSpeaker) g.drawImage(Assets.speakerM, 900, 20, null);
        // ANIMATION AFTER CLICKED START BUTTON
        if(buttonPressed == true) g.drawImage(Assets.levelOptions, GameLauncher.WIDTH / 2 - Assets.levelOptions.getWidth() / 2, menuPosisionY, null);
        // LOAD/N'LOAD NOTIFICATIONS
        notifications(g);
    }
    
    private void background(Graphics g) {
        g.drawImage(Assets.menu, 0, -GameLauncher.HEIGHT*2 + menuPosisionY, null);
        g.drawImage(Assets.title, 0, (int)titlePosisionY-Assets.title.getHeight(), null);
        g.drawImage(Assets.options, -Assets.options.getWidth()-15 + buttonPosisionX, 450 + buttonPosisionY, null);
    }

    private void coulds(Graphics g) {
        g.drawImage(Assets.could[0], (int)couldPosisionX-Assets.could[0].getWidth()-500, couldPosisionY+60, null);
        g.drawImage(Assets.could[1], (int)(GameLauncher.WIDTH-couldPosisionX       +50), couldPosisionY+75, null);
        g.drawImage(Assets.could[2], (int)(GameLauncher.WIDTH-(couldPosisionX)*2  +100), couldPosisionY+40, null);
        g.drawImage(Assets.could[3], (int)couldPosisionX-Assets.could[3].getWidth() -50, couldPosisionY+50, null);
        g.drawImage(Assets.could[4], (int)couldPosisionX-Assets.could[4].getWidth()-300, couldPosisionY+10, null);
        g.drawImage(Assets.could[5], (int)(GameLauncher.WIDTH-(couldPosisionX)*2  +300), couldPosisionY+ 5, null);
    }
    
    private void notifications(Graphics g) {
        if(n == Notification.LOADED || n == Notification.CANT_LOAD) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(ac);
            if(n == Notification.LOADED) {
                g2d.drawImage(Assets.gameLoaded, 333, 250, null);
                alpha-=0.01;
                if(alpha < 0.0f) { alpha = 1f; n = Notification.EMPTY; }
            }
            else if (n == Notification.CANT_LOAD) {
                g2d.drawImage(Assets.gameNLoaded, 333, 250, null);
                alpha-=0.01;
                if(alpha < 0.0f) { alpha = 1f; n = Notification.EMPTY; 
                }    
            }  
        }
    }
    
    @Override
    protected void init() {
        button[0] = new Rectangle();
        button[1] = new Rectangle();
        button[2] = new Rectangle();
        levelMenu = new LevelMenuState(engine);
    }

    private void animations() {
        if (menuPosisionY < Assets.menu.getHeight()-960) {
            menuPosisionY += 5;
        } 
        else if (buttonPosisionX < (GameLauncher.WIDTH / 2 + Assets.options.getWidth() / 2)) {
            buttonPosisionX += 10;
            button[0].setBounds(new Rectangle(-Assets.options.getWidth()-15 + buttonPosisionX, 450 + buttonPosisionY, Assets.options.getWidth(), Assets.options.getHeight() / 3));
            button[1].setBounds(new Rectangle(-Assets.options.getWidth()-15 + buttonPosisionX, 450 + buttonPosisionY + Assets.options.getHeight() / 3, Assets.options.getWidth(), Assets.options.getHeight() / 3));
            button[2].setBounds(new Rectangle(-Assets.options.getWidth()-15 + buttonPosisionX, 450 + buttonPosisionY + 2*(Assets.options.getHeight() / 3), Assets.options.getWidth(), Assets.options.getHeight() / 3));
        }
        if(titlePosisionY<500) titlePosisionY+=2;
        for(int i=0; i<countOfCoulds; i++) {
            if(couldPosisionX < 1700 && dir == false) couldPosisionX+=0.25;
            else dir = true;         
            if(couldPosisionX > 0 && dir == true) couldPosisionX-=0.25;
            else dir = false;
        }    
    }
    
    private void loadGame() {     
        BufferedReader reader;
        
        File f = new File("save.txt");
        if(!f.exists()) try { f.createNewFile();
        } catch (IOException ex) { ex.getMessage(); }

        try {
            FileReader fileReader = new FileReader(f);
            reader = new BufferedReader(fileReader);
              
            String [] posXY;
            String s;

            for(int level = 0; level<12; level++) 
            {
                s = reader.readLine();
                if(s == null) { n = Notification.CANT_LOAD; 
                    try { Thread.sleep(150); } catch (InterruptedException ex) { ex.getMessage(); }
                    return; 
                }
                boxCount = Integer.parseInt(s);
                for(int i = 0; i< boxCount; i++)
                { 
                    s = reader.readLine();
                    posXY = s.split(" ");
                    x = Integer.parseInt(posXY[0]);
                    y = Integer.parseInt(posXY[1]);
                    LevelMenuState.getLevel(level).box[i].setX(x);
                    LevelMenuState.getLevel(level).box[i].setY(y);
                }
                
                for(int i = 0; i< boxCount; i++) 
                { 
                    s = reader.readLine();
                    posXY = s.split(" ");
                    x = Integer.parseInt(posXY[0]);
                    y = Integer.parseInt(posXY[1]);
                    LevelMenuState.getLevel(level).boxSite[i].setX(x);
                    LevelMenuState.getLevel(level).boxSite[i].setY(y);
                }
                
                s = reader.readLine();
                posXY = s.split(" ");
                x = Integer.parseInt(posXY[0]);
                y = Integer.parseInt(posXY[1]);
                LevelMenuState.getLevel(level).player.setX(x);
                LevelMenuState.getLevel(level).player.setY(y);
                
                s = reader.readLine();
                x = Integer.parseInt(s);
                LevelMenuState.getLevel(level).player.setMovesCount(x);
            }
            reader.close();   
            n = Notification.LOADED;
            try { Thread.sleep(100); } catch (InterruptedException ex) { ex.getMessage(); }
            } catch (IOException e) { System.out.println(e.getMessage());
            
        }
    }
    
    public static State getMainMenuState() {
        return currentState;
    } 
}

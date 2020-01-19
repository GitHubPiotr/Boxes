package boxes.states;

import boxes.states.levelpack1.*;
import boxes.states.levelpack2.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import boxes.engine.Engine;
import boxes.engine.GameLauncher;
import boxes.entites.Box;
import boxes.entites.BoxSite;
import boxes.graphics.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class LevelMenuState extends State {

    private final Engine engine;
    private static LevelMenuState currentState;
    private final int levelTextureOption = 128;
    private int optionsPosisionY, titlePosisionY = -780, buttonPosisionY = -830;
    private static final LevelState [] level = new LevelState[12];
    private final Rectangle [] button = new Rectangle[12];
    private final Rectangle saveButton = new Rectangle();
    private final Rectangle [] restartButton = new Rectangle[2];
    private boolean buttonPressed = false, saved;
    private float alpha = 1f;
    private boolean [] lvlWon = new boolean[12];
    private boolean notification;

    public LevelMenuState(Engine engine) {
        this.engine = engine;
        currentState = this;
        init();
    }

    @Override
    public void tick() {
        // LEVEL BUTTONS
        for (int i=0; i<6; i++) button[i].setBounds(new Rectangle(80+134*i, 64, levelTextureOption + optionsPosisionY, levelTextureOption));
        for (int i=0; i<6; i++) button[i+6].setBounds(new Rectangle(80+134*i, 64+130, levelTextureOption + optionsPosisionY, levelTextureOption));
        // "SAVE" BUTTON
        saveButton.setBounds(new Rectangle(730, 545, 150, 60));      
        // START LEVEL(1.. 18), IF LEVEL BUTTON(0.. 17) WAS PRESSED 
        for (int i=0; i<12; i++) 
            if (engine.getMouseManager().left == true && button[i].contains(engine.getMouseManager().getX(), engine.getMouseManager().getY())) {
                engine.getClickSound().play(false);
                if(!level[i].isWon()) {
                    State.setState(level[i]);
                }
                try {Thread.sleep(150); } catch (InterruptedException ex) { ex.getMessage(); }
            }
        // SAVE GAME, IF SAVE BUTTON WAS PRESSED
        if(engine.getMouseManager().left == true && saveButton.contains(engine.getMouseManager().getX(), engine.getMouseManager().getY())) {
            engine.getClickSound().play(false);
            Box [] box;
            BoxSite [] boxSite;
            PrintWriter printWriter = null;
            File file = null;
            try  {
                file = new File("save.txt");
                printWriter = new PrintWriter(file);
                
                for(int i=0; i<12; i++) {
                    box = level[i].box;
                    boxSite = level[i].boxSite;
                    printWriter.println(box.length);
                    for(int j=0; j<box.length; j++) printWriter.println(box[j].getX() + " " + box[j].getY());
                    for(int j=0; j<box.length; j++) printWriter.println(boxSite[j].getX() + " " + boxSite[j].getY());
                    printWriter.println(level[i].player.getX() + " " + level[i].player.getY());
                    printWriter.println(level[i].player.getMovesCount());
                }
            } 
            catch (IOException e) { System.err.println("Error: " + e.getMessage()); }
            finally { if(printWriter != null) { printWriter.close(); } }
            
            try { Thread.sleep(250); } catch (InterruptedException ex) { ex.getMessage(); }
            saved = true;
        }     
        // RETURN ANIMATION
        if (engine.getKeyManager().esc || buttonPressed == true) {
            buttonPressed = true;
            optionsPosisionY+=10;
            titlePosisionY+=10;
            buttonPosisionY+=10;
            if(optionsPosisionY > 1280) {
                titlePosisionY = -830;
                buttonPosisionY = -880;
                buttonPressed = false;
                optionsPosisionY = 0;
                State.setState(MainMenuState.getMainMenuState());
            }          
        }
        for(int i=0; i<12; i++) {
            if(level[i].isWon()) { lvlWon[i] = true; }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menu, 0, -GameLauncher.HEIGHT*2 + optionsPosisionY, null);
        g.drawImage(Assets.levelOptions, GameLauncher.WIDTH / 2 - Assets.levelOptions.getWidth() / 2, + optionsPosisionY, null);

        if(buttonPressed == true) {
            g.drawImage(Assets.title, 0, (int)titlePosisionY-Assets.title.getHeight(), null);
            g.drawImage(Assets.options, 395, buttonPosisionY, null);
        }
        
        for(int i=0; i<6; i++) {
            if(level[i].isWon()) g.drawImage(Assets.done, 123+i*134, 95, null);
        }
        for(int i=0; i<6; i++) {
            if(level[i+6].isWon()) g.drawImage(Assets.done, 123+i*134, 225, null);
        }
        
        if(notification) {
            g.drawImage(Assets.won, 333, 333, null);

        }
        
        // SAVE NOTIFICATION ANIMATION
        if(saved == true) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(ac);
         
            g2d.drawImage(Assets.gameSaved, 333, 333, null);
            alpha-=0.01;
            if(alpha < 0.0f) { alpha = 1f; saved = false; }
        }
        
    }

    @Override
    protected void init() {
        for(int i=0; i<12; i++) button[i] = new Rectangle();
        level[0] = new Level1(engine);
        level[1] = new Level2(engine);
        level[2] = new Level3(engine);
        level[3] = new Level4(engine);
        level[4] = new Level5(engine);
        level[5] = new Level6(engine);
        level[6] = new Level7(engine);
        level[7] = new Level8(engine);
        level[8] = new Level9(engine);
        level[9] = new Level10(engine);
        level[10] = new Level11(engine);
        level[11] = new Level12(engine);
    }
    
    public static LevelMenuState getLevelMenuState() {
        return currentState;
    }

    public static LevelState getLevel(int index) {
        return level[index];
    }
}

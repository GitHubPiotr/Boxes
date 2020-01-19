package boxes.states;

import boxes.collision.CollisionSiteBox;
import boxes.engine.Engine;
import boxes.engine.GameLauncher;
import boxes.entites.Box;
import boxes.entites.BoxSite;
import boxes.entites.Player;
import boxes.graphics.Assets;
import boxes.worlds.World;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LevelState extends State {

    protected int mapID;
    protected boolean[] score;
    protected int boxesCount;
    protected World world;
    protected Player player;
    protected final Engine engine;
    protected BoxSite [] boxSite;
    protected Box [] box;
    protected String path;
    protected boolean won;
    
    public LevelState(Engine engine, int mapID, String path) { 
        this.engine = engine;
        this.path = path;
        this.mapID = mapID;
        init();
    }
    
    @Override
    public void tick() {
        world.tick();
        player.tick();
        for (int i = 0; i < boxesCount; i++) score[i] = CollisionSiteBox.checkCollision(box, boxSite, i);
        // IF PLAYER WON A GAME
        if (areAllTrue(score)) {
            won = true;
            if(mapID == 12) State.setState(LevelMenuState.getLevelMenuState());
            else State.setState(LevelMenuState.getLevel(mapID));
        }
    }

    @Override
    public void render(Graphics g) {
        world.render(g, mapID);
        for (int i = 0; i < boxesCount; i++) boxSite[i].render(g);
        for (int i = 0; i < boxesCount; i++) box[i].render(g);
        for (int i = 0; i < boxesCount; i++) if(score[i] == true) boxSite[i].boxLocated(g);
        player.render(g);

        if(won) g.drawImage(Assets.won, GameLauncher.WIDTH / 2 - Assets.won.getWidth() / 2, GameLauncher.HEIGHT / 2 - Assets.won.getHeight() / 2, null);
    }

    // READ LEVEL FORM FILE
    @Override
    protected void init() {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String s;
            s = reader.readLine();
            boxesCount = Integer.parseInt(s);
        
            score = new boolean[boxesCount];
            world = new World(engine);   
            box = new Box[boxesCount];
            boxSite = new BoxSite[boxesCount];
        
            String [] coordsXY;
            String sX, sY; 
            int x, y;
            reader.readLine();
            for(int i = 0; i<boxesCount; i++) {
                s = reader.readLine();
                coordsXY = s.split(" ");
                sX = coordsXY[0];
                x = Integer.parseInt(sX);
                sY = coordsXY[1];
                y = Integer.parseInt(sY);
                box[i] = new Box(engine, x, y);
            }
            reader.readLine();
            for(int i = 0; i<boxesCount; i++) {
                s = reader.readLine();
                coordsXY = s.split(" ");
                sX = coordsXY[0];
                x = Integer.parseInt(sX);
                sY = coordsXY[1];
                y = Integer.parseInt(sY);
                boxSite[i] = new BoxSite(engine, x, y);
            }
        
            reader.readLine();
            s = reader.readLine();
            coordsXY = s.split(" ");
            sX = coordsXY[0];
            x = Integer.parseInt(sX);
            sY = coordsXY[1];
            y = Integer.parseInt(sY);
            player = new Player(engine, mapID, x, y, box);
        
            reader.close();
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }

    public boolean areAllTrue(boolean [] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }
    
    public boolean isWon() {
        return won;
    }
    
    public Player getPlayer() {
        return player;
    }
}

package boxes.entites;

import java.awt.Graphics;
import boxes.collision.CollisionPlayer;
import boxes.engine.Engine;
import boxes.engine.GameLauncher;
import boxes.graphics.Assets;
import boxes.states.LevelMenuState;
import boxes.states.State;
import boxes.worlds.World;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;

public class Player extends Entity {

    private enum PlayerMove { READY, STOPED, MOVING };
    private final PlayerMove move [] = new PlayerMove[4];
    private enum AnimationUndo { READY, STOPED, MOVING };
    private AnimationUndo undo;
    private int moveUndoCount, moveCount = 0;
    private int size;
    private boolean escPressed;
    private final int mapID;
    private int pixels = 1;
    private final Box [] boxes;
    private final ArrayList<ListOfMoves> arrayListOfMoves = new ArrayList<>();
    private boolean blockUndo, blockReset, blockMove = false;;
    private final Font font = new Font("Showcard Gothic", Font.PLAIN, 50);
    private AttributedString atString;

    public Player(Engine engine, int mapID, int x, int y, Box [] boxes) {
        super(engine);
        this.x = copyX = x;
        this.y = copyY = y;
        this.boxes = boxes;
        this.mapID = mapID;
        move[0] = PlayerMove.READY;
        move[1] = PlayerMove.READY;
        move[2] = PlayerMove.READY;
        move[3] = PlayerMove.READY;
        undo = AnimationUndo.READY;
    }

    // 0-UP		1-DOWN 		2-LEFT		3-RIGHT
    @Override
    public void tick() {
        if (!blockMove) movePlayer(0);
        if (!blockMove) movePlayer(1);
        if (!blockMove) movePlayer(2);
        if (!blockMove) movePlayer(3);
  
        if (engine.getKeyManager().r == true && blockReset == false) reset();
        if (engine.getKeyManager().t == true && blockUndo == false && undo == AnimationUndo.READY) { undo = AnimationUndo.MOVING; blockMove = true; } 
        if (undo == AnimationUndo.MOVING) undoMoves();
        if (engine.getKeyManager().esc || escPressed == true) {
            escPressed = true;
            if(size < 960) { size += 10; size += 10; }
            else {
                size = 0;
                escPressed = false;
                State.setState(LevelMenuState.getLevelMenuState());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, x, y, null);
        // COUNTER OF MOVES        
        atString = new AttributedString("Moves: " + moveCount);
        atString.addAttribute(TextAttribute.FONT, font);
        g.drawString(atString.getIterator(), 650, 600);
        // IF ESC BUTTON WAS PRESSED CALL ANIMATION
        if(escPressed == true) {
            g.drawImage(Assets.menu, 0, -1280, null);
            g.drawImage(Assets.levelOptions, (int)(GameLauncher.WIDTH/2 - (size*1)/2), (int)(GameLauncher.HEIGHT/2 - (size*2/3)/2), (int)(size*1), (int)(size*2/3), null);            
        }
    }

    private void movePlayer(int i) {
        // CHECK PLAYER COLLISON WITH WALL AND BOX
        checkCollision(i);
        // IF THE PLAYER CAN MOVE, MOVE HIM BY 32PIXELS
        movePlayerBy32px(i);
    }

    
    private void checkCollision(int i) {
        if (engine.getKeyManager().control[i] == true  && move[i] == PlayerMove.READY && 
        CollisionPlayer.checkCollisionWall(engine, this, mapID, i) && CollisionPlayer.checkCollisionBox(engine, this, mapID, i, boxes)) {
            move[0] = PlayerMove.STOPED; move[1] = PlayerMove.STOPED; move[2] = PlayerMove.STOPED; move[3] = PlayerMove.STOPED;
            move[i] = PlayerMove.MOVING; 
            arrayListOfMoves.add(new ListOfMoves(this, boxes));
        }
    }
    
    private void movePlayerBy32px(int i) {
        if (move[i] == PlayerMove.MOVING) {
            blockUndo = true;
            blockReset = true;
            if (pixels <= World.TEXTURE_SIZE) {
                if(CollisionPlayer.checkBoxCollision() == true) Box.moveBox(CollisionPlayer.getCurrentBox(), i);
                changePositionPlayer(i);
            } else {
                move[0] = PlayerMove.READY; move[1] = PlayerMove.READY; move[2] = PlayerMove.READY; move[3] = PlayerMove.READY;
                blockUndo = false;
                blockReset = false;
                moveCount++;
                pixels = 0;
            }
            pixels++; 
        }       
    }
    
    void changePositionPlayer(int i) {
        if (i == 0) y--;
        else if (i == 1) y++;
        else if (i == 2) x--;
        else if (i == 3) x++;
    }
 
    void undoMoves() {
        int index = arrayListOfMoves.size()-1;
        if(index < 0) {
            blockMove = false;
            return;
        }
        if(y > arrayListOfMoves.get(index).getPlayerY()) y--;
        if(y < arrayListOfMoves.get(index).getPlayerY()) y++;
        if(x > arrayListOfMoves.get(index).getPlayerX()) x--;
        if(x < arrayListOfMoves.get(index).getPlayerX()) x++;
   
        for(int i=0; i<boxes.length; i++) {
            if(boxes[i].getY() > arrayListOfMoves.get(index).getBoxY(i)) boxes[i].setY(boxes[i].getY()-1);
            if(boxes[i].getY() < arrayListOfMoves.get(index).getBoxY(i)) boxes[i].setY(boxes[i].getY()+1);
            if(boxes[i].getX() > arrayListOfMoves.get(index).getBoxX(i)) boxes[i].setX(boxes[i].getX()-1);
            if(boxes[i].getX() < arrayListOfMoves.get(index).getBoxX(i)) boxes[i].setX(boxes[i].getX()+1);           
        }
        moveUndoCount++;
        if(moveUndoCount == World.TEXTURE_SIZE) {
            blockMove = false;
            undo = AnimationUndo.READY;
            arrayListOfMoves.remove(index);
            moveUndoCount = 0;
            moveCount--;
        }
    }
    
    @Override
    public void reset() {
        arrayListOfMoves.clear();
        moveCount = 0;
        x = copyX;
        y = copyY;
        for(int i=0; i<boxes.length; i++) boxes[i].reset();
    }
    
    public int getMovesCount() {
        return moveCount;
    }
    
    public void setMovesCount(int moveCount) {
        this.moveCount = moveCount;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayer() {
        return this;
    }
}

package boxes.collision;

import boxes.engine.Engine;
import boxes.entites.Box;
import boxes.entites.Player;
import boxes.worlds.LoadWorld;
import boxes.worlds.World;

public class CollisionPlayer {

    private static int texturePlayerPosition, textureBoxPosition;
    private static boolean boxCollision;
    private static Box currentBox; 
    
    // CHECK PLAYER COLLISION WITH WALL
    public static boolean checkCollisionWall(Engine engine, Player player, int map, int direction) {
        if      (direction == 0) texturePlayerPosition = player.getX() / World.TEXTURE_SIZE + (player.getY() - World.TEXTURE_SIZE) / World.TEXTURE_SIZE * LoadWorld.getY();
        else if (direction == 1) texturePlayerPosition = player.getX() / World.TEXTURE_SIZE + (player.getY() + World.TEXTURE_SIZE) / World.TEXTURE_SIZE * LoadWorld.getY();
        else if (direction == 2) texturePlayerPosition = (player.getX() - World.TEXTURE_SIZE) / World.TEXTURE_SIZE + player.getY() / World.TEXTURE_SIZE * LoadWorld.getY();
        else                     texturePlayerPosition = (player.getX() + World.TEXTURE_SIZE) / World.TEXTURE_SIZE + player.getY() / World.TEXTURE_SIZE * LoadWorld.getY();
        
        return !(engine.getMap(map).getArrayList(map).get(texturePlayerPosition) == 1 || engine.getMap(map).getArrayList(map).get(texturePlayerPosition) == 0);
    }
    
    // CHECK PLAYER COLLISION WITH BOX AND BOX WITH WALL
    public static boolean checkCollisionBox(Engine engine, Player player, int map, int direction, Box [] box) {
        boxCollision = false;
        for (int i = 0; i < box.length; i++) {
            if (direction == 0 && player.getX() == box[i].getX() && (player.getY() - World.TEXTURE_SIZE) == box[i].getY()) {
                textureBoxPosition = box[i].getX() / World.TEXTURE_SIZE + (box[i].getY() - 1) / World.TEXTURE_SIZE * LoadWorld.getY();
                for (int j = 0; j < box.length; j++) {
                    if (box[i].getX() == box[j].getX() && (box[i].getY() - World.TEXTURE_SIZE) == box[j].getY() || engine.getMap(map).getArrayList(map).get(textureBoxPosition) == 1)
                        return false;
                    currentBox = box[i];
                    boxCollision = true;
                }
            } else if (direction == 1 && player.getX() == box[i].getX() && (player.getY() + World.TEXTURE_SIZE) == box[i].getY()) {
                textureBoxPosition = box[i].getX() / World.TEXTURE_SIZE + (box[i].getY() + World.TEXTURE_SIZE) / World.TEXTURE_SIZE * LoadWorld.getY();
                for (int j = 0; j < box.length; j++) {
                    if (box[i].getX() == box[j].getX() && (box[i].getY() + World.TEXTURE_SIZE) == box[j].getY() || engine.getMap(map).getArrayList(map).get(textureBoxPosition) == 1)
                        return false;
                    currentBox = box[i];
                    boxCollision = true;
                }
            } else if (direction == 2 && (player.getX() - World.TEXTURE_SIZE == box[i].getX()) && player.getY() == box[i].getY()) {
                textureBoxPosition = (box[i].getX() - 1) / World.TEXTURE_SIZE + box[i].getY() / World.TEXTURE_SIZE * LoadWorld.getY();
                for (int j = 0; j < box.length; j++) {
                    if ((box[i].getX() - World.TEXTURE_SIZE) == box[j].getX() && box[i].getY() == box[j].getY() || engine.getMap(map).getArrayList(map).get(textureBoxPosition) == 1)
                        return false;
                    currentBox = box[i];
                    boxCollision = true;
                }           
            } else if (direction == 3 && (player.getX() + World.TEXTURE_SIZE == box[i].getX()) && player.getY() == box[i].getY()) {
                textureBoxPosition = (box[i].getX() + World.TEXTURE_SIZE) / World.TEXTURE_SIZE + box[i].getY() / World.TEXTURE_SIZE * LoadWorld.getY();
                for (int j = 0; j < box.length; j++) {
                    if ((box[i].getX() + World.TEXTURE_SIZE) == box[j].getX() && box[i].getY() == box[j].getY() || engine.getMap(map).getArrayList(map).get(textureBoxPosition) == 1)
                        return false;
                    currentBox = box[i];
                    boxCollision = true;
                }
            }
        } 
        return true;
    }
    public static boolean checkBoxCollision() {
        return boxCollision;
    }
    
    public static Box getCurrentBox() {
        return currentBox;
    }
}

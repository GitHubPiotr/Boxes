package boxes.entites;

public class ListOfMoves {

    private int playerX, playerY;
    private int [] boxX, boxY;

    public ListOfMoves(Player player, Box [] box) {

        playerX = player.getX();
        playerY = player.getY();
        
        boxX = new int[box.length];
        boxY = new int[box.length];
        
        for (int i = 0; i < box.length; i++) {
            boxX[i] = box[i].getX();
            boxY[i] = box[i].getY();
        }
    }

    int getPlayerX() {
        return playerX;
    }

    int getPlayerY() {
        return playerY;
    }
    
    int getBoxX(int i) {
        return boxX[i];
    }
    int getBoxY(int i) {
        return boxY[i];
    }
}

package boxes.collision;

import boxes.entites.Box;
import boxes.entites.BoxSite;

public class CollisionSiteBox {

    //  CHECK BOX COLLISION WITH OTHER BOXES
    public static boolean checkCollision(Box[] box, BoxSite[] site, int siteIndex) {
        for (int j = 0; j < box.length; j++) {
            if (site[siteIndex].getX() == box[j].getX() && site[siteIndex].getY() == box[j].getY()) {
                return true;
            }
        }
        return false;
    }
}

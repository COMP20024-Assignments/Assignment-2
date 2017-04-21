package jebediah.generic;
/**
 * Created by Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 * Tile that can never be moved into
 */
public class BlockedTile extends Tile {

    public BlockedTile() {type = 'B';}

    public boolean isOccupied() {
        return true;
    }

    public void moveInto(char type) {
        System.out.println("cannot move here!!!");
        System.exit(1);
    }
    public void moveOut() {
        System.out.println("cannot move out of here!!!");
        System.exit(1);
    }
}

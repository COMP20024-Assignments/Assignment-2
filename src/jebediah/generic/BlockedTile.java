package jebediah.generic;
/**
 * Created by Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 * Tile that can never be moved into
 */
public class BlockedTile extends Tile {

    public BlockedTile() {}

    public boolean isOccupied() {
        return true;
    }
}

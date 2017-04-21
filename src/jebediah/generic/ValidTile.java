package jebediah.generic;
/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 * Tile that can be moved into if occupied == false
 */
public class ValidTile extends Tile {
    private boolean occupied;

    public ValidTile(boolean containsPiece, char type) {
        if (containsPiece) this.type = type;
        occupied = containsPiece;
    }
    @Override
    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public void moveInto(char type) {
        occupied = true;
        this.type = type;
    }
    @Override
    public void moveOut() {
        occupied = false;
        this.type = ' ';
    }
}

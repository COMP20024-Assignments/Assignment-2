package jebediah.generic;
/** COMP30024 Artificial Intelligence
 ValidTile class
 George Juliff - 624946
 Thomas Miles - 626263

 Represents standard game tiles, and keeps track of if it is occupied or not.
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

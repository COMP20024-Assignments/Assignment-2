package jebediah.generic;
/** COMP30024 Artificial Intelligence
 BlockedTile class
 George Juliff - 624946
 Thomas Miles - 626263

 represents a tile that can never be moved into
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

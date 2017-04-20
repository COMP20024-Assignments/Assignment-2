package jebediah.generic;
/**
 * Created by Tom Miles on 1/04/2017.
 *
 * Pieces belong to Agents, currently only have the ability to check if they can move in a given direction.
 */
public class Piece {
    private int xpos, ypos;

    public Piece(int x, int y) {
        xpos = x;
        ypos = y;
    }


    boolean canMove(String direction) {
        switch (direction) {
            case ("up"):
                if ((ypos+1 < Board.getSize()) && !Board.isOccupied(xpos, ypos+1)) {
                    return true;
                }
                break;
            case("down"):
                if ((ypos-1 >= 0) && !Board.isOccupied(xpos, ypos-1)) {
                    return true;
                }
                break;
            case("left"):
                if ((xpos-1 >= 0) && !Board.isOccupied(xpos-1, ypos)) {
                    return true;
                }
                break;
            case("right"):
                if ((xpos+1 < Board.getSize()) && !Board.isOccupied(xpos+1, ypos)) {
                    return true;
                }
                break;
            default:
                System.out.println("Invalid direction!");
                return false;
        }
        return false;
    }
}

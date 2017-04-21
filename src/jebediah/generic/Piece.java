package jebediah.generic;

import aiproj.slider.Move;

/**
 * Created by Tom Miles on 1/04/2017.
 *
 * Pieces belong to Agents, currently only have the ability to check if they can move in a given direction.
 */
public class Piece {
    private int xpos, ypos;
    private char type;

    Piece(int x, int y, char owner) {
        xpos = x;
        ypos = y;
        type = owner;
    }


    public boolean canMove(Move.Direction move, Board board) {
        switch (move) {
            case UP:
                return board.isOccupied(xpos, ypos + 1);

            case RIGHT:
                return board.isOccupied(xpos+1, ypos);

            case DOWN:
                return (this.type != 'V') && board.isOccupied(xpos, ypos - 1);

            case LEFT:
                return (this.type != 'H') && board.isOccupied(xpos-1, ypos);

            default:
                System.out.println("Invalid direction!");
                return false;
        }
    }
    boolean posMatch(int x,int y) {
        if (x==xpos && y==ypos) return true;
        return false;
    }
}

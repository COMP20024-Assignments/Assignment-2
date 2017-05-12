
package CyberdyneSystems.generic;

import aiproj.slider.Move;

import static CyberdyneSystems.generic.Board.O;

/** COMP30024 Artificial Intelligence
 Piece class
 George Juliff - 624946
 Thomas Miles - 626263

 Belong to a given agent and represent their game pieces on the board, recording their position
 */

public class Piece {
    private int xpos, ypos;
    private char type;

    Piece(int x, int y, char owner) {
        xpos = x;
        ypos = y;
        type = owner;
    }

    void move(Move.Direction d) {
        switch (d) {
            case DOWN: ypos--;
                break;

            case UP: ypos ++;
                break;

            case LEFT: xpos --;
                break;

            case RIGHT: xpos ++;
                break;

        }
    }

    boolean posMatch(int x,int y) {
        if (x==xpos && y==ypos) return true;
        return false;
    }
    boolean escape(int boardSize) {
        if (xpos >= boardSize || ypos >= boardSize) return true;
        return false;
    }


    Move generateMove(Move.Direction d) {
        // be careful to only do this if it's legal
        Move move = new Move (xpos, ypos, d);

        switch (d) {
            case UP:
                ypos++;
            case DOWN:
                ypos--;
            case RIGHT:
                xpos++;
            case LEFT:
                xpos--;
        }
        return move;
    }

    public int getXpos() {return xpos;}
    public int getYpos() {return ypos;}
}
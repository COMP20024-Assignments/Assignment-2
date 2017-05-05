package jebediah.generic;
import aiproj.slider.Move;

import java.util.ArrayList;

/** COMP30024 Artificial Intelligence
 Agent class
 George Juliff - 624946
 Thomas Miles - 626263

 the agent represents a player of the game and keeps track of the necessary pieces
 */
public class Agent {
    private ArrayList<Piece> pieces;
    public char player; // player type, either "horizontal" or "vertical"

    public Agent(char player) {
        this.player = player;
        pieces = new ArrayList<>();

    }

    void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public Piece getPiece(int index) {

        if (index >= pieces.size()) return null;

        return pieces.get(index);
    }

    public Move makeMove(Move.Direction direction, int index, int boardSize) {

        Move myMove = pieces.get(index).generateMove(direction);

        if (pieces.get(index).escape(boardSize)) {
            pieces.remove(index);
        } else {
            pieces.get(index).move(direction);
        }

        return myMove;
    }

    public int getPieceIndex(int x, int y) {
        for (int i=0; i < pieces.size(); i++) {
            if (pieces.get(i).posMatch(x,y)) return i;
        }
        return -1; // shouldnt get here if x and y are the coordinates for a piece I own.
    }
/*
    int calculateNumMoves() {
        int numValid=0;

        for (Piece piece : pieces) {

            // both players can move up or right, so always check these
            if (piece.canMove("up")) numValid++;
            if (piece.canMove("right")) numValid++;

            // check player specific moves
            if (player.equals("horizontal")) {
                if (piece.canMove("down")) numValid++;
            } else if (player.equals("vertical")) {
                if (piece.canMove("left")) numValid++;
            } else {
                System.out.println("Error, player name unrecognised, terminating");
                System.exit(1);
            }
        }
        return numValid;
    }
*/


}

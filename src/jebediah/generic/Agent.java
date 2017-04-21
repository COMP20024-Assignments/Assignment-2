package jebediah.generic;
import aiproj.slider.Move;

import java.util.ArrayList;

/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 *
 * Agent class represents a player of the game. Agents have pieces which are initialised when the board is created.
 *
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

    public Move makeMove(Move.Direction direction, int index) {

    }

    public int getPieceIndex(int x, int y) {
        for (int i=0; i < pieces.size(); i++) {
            if (pieces.get(i).)
        }
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


package CyberdyneSystems.generic;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

import java.util.ArrayList;

/** COMP30024 Artificial Intelligence
 Agent class
 George Juliff - 624946
 Thomas Miles - 626263

 the agent represents a player of the game and keeps track of the necessary pieces, move method is left abstract
 allowing different player types (AI/human/etc)
 */
public class Agent implements SliderPlayer {


    public final static int i = 0;
    public final static int j = 1;

    public Agent enemy;
    public Board board;
    ArrayList<byte[]> pieces;

    public byte player; // player type, either "horizontal" or "vertical"

    public Agent() {
        pieces = new ArrayList<>();
    }

    public void init(int dimension, String boardLayout, char player) {

        enemy = new Agent();
        if (player == 'H') {
            this.player = Board.H;
            enemy.player = Board.V;
        } else {
            this.player = Board.V;
            enemy.player = Board.H;
        }

        enemy = new Agent();
        board = new Board(dimension, boardLayout, this, enemy);

    }
    public void giveBoard(byte[][] state) {

        board = new Board(state);
    }

    public void givePieces() {
        for (int i=0; i<Board.getSize(); i++) {
            for (int j=0; j<Board.getSize(); j++) {
                if (board.layout[i][j] == player) {
                    addPiece((byte)i, (byte) j);
                }
            }
        }
    }


    public int numPieces() {
        return pieces.size();
    }

    private void addPiece(byte x, byte y) {

        byte p[] = new byte[2];
        p[i] = x;
        p[j] = y;
        pieces.add(p);
    }

    public byte[] getPiece(int index) {

        if (index >= pieces.size()) return null;
        return pieces.get(index);
    }

    public Move makeMove(Move.Direction direction, int index) {

        // be careful to only do this if it's legal
        Move myMove = new Move(pieces.get(index)[i], pieces.get(index)[j], direction);

        if (pieces.get(index)[i] == Board.getSize() || pieces.get(index)[j] == Board.getSize()) {
            pieces.remove(index);

        } else {
            switch (direction) {
                case UP:
                    pieces.get(index)[j]++;
                case DOWN:
                    pieces.get(index)[j]--;
                case RIGHT:
                    pieces.get(index)[i]++;
                case LEFT:
                    pieces.get(index)[i]--;
            }
        }
        board.update(myMove, player);
        return myMove;
    }

    protected int getPieceIndex(int x, int y) {
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i)[i] == x && pieces.get(i)[j] == y) return i;
        }
        return -1; // shouldnt get here if x and y are the coordinates for a piece I own.
    }


    public void update(Move move) {
        if (move != null) {
            board.update(move, enemy.player);
        }
    }
    /**
     * This method needs to be specified in an extended class
     * @return Move to be made by agent
     */
    public Move move(){
        return null;
    }
}
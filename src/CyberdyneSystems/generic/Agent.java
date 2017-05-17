
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

        if (index >= pieces.size()) {
            System.out.println(index+" im a dirty dog -dip '17 "+pieces.size());
            return null;
        }
        return pieces.get(index);
    }

    public Move makeMove(Move.Direction direction, int index) {

        // be careful to only do this if it's legal
        Move myMove = new Move(pieces.get(index)[i], pieces.get(index)[j], direction);
   //     board.printLayout();
  //  System.out.println("updating piece: "+pieces.get(index)[i]+" "+pieces.get(index)[j]+ " direction: "+direction);


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

//        System.out.println("updated");
//        board.printLayout();
//        System.out.println("\n\n");
        return myMove;
    }

    protected void movePiece(Move move) {

        for (int index = 0; index<numPieces(); index++) {

            if ((pieces.get(index)[i] == move.i) && (pieces.get(index)[j] == move.j)) {

                switch (move.d) {
                    case DOWN:
                        pieces.get(index)[j]--;
                        return;
                    case RIGHT:
                        if (player == Board.H && pieces.get(index)[i]+1 == Board.getSize()) {
                            pieces.remove(index);
                        } else {
                            pieces.get(index)[i]++;
                        }
                        return;
                    case UP:
                        if (player == Board.V && pieces.get(index)[j]+1 == Board.getSize()) {
                            pieces.remove(index);
                        } else {
                        pieces.get(index)[j]++;
                        }
                        return;
                    case LEFT:
                        pieces.get(index)[i]--;
                        return;
                }
            }
        }
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


public void printPieces(){
    for (byte[] piece : pieces) {
        System.out.print("("+piece[i]+", "+piece[j]+")");
    }
    System.out.println();
    }
}
package jebediah.generic;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 *
 * Will be modified to actually run the game, but for the purpose of assignment one, just read and input board from
 * stdin and calculate number of legal moves for each player
 */
public class TestMoves {
    public static void main(String[] args) {

        Agent vertical = new Agent("vertical");
        Agent horizontal = new Agent("horizontal");
        fillBoard(horizontal, vertical);
        System.out.println(horizontal.calculateNumMoves());
        System.out.println(vertical.calculateNumMoves());
        System.exit(0);

    }

    /**
     *  Reads board from stdin and passes through pieces to each player.
     * @param h horizontal player, passed through so H pieces can be created in its instance
     * @param v vertical player, passed through so V pieces can be created in its instance
     */
    private static void fillBoard(Agent h, Agent v) {

        Scanner stdin = new Scanner(new InputStreamReader(System.in));
        System.out.println("Awaiting Input...");
        new Board(stdin.nextInt());

        for (int j=Board.getSize()-1; j >= 0; j--) {
            for (int i=0; i < Board.getSize(); i++) {
                switch (stdin.next().charAt(0)) {
                    case ('B'):
                        Board.addTile(new BlockedTile(), i, j);
                        break;

                    case ('V'):
                        v.addPiece(new Piece(i,j));
                        Board.addTile(new ValidTile(true), i, j);
                        break;

                    case ('H'):
                        h.addPiece(new Piece(i,j));
                        Board.addTile(new ValidTile(true), i, j);
                        break;

                    case ('+'):
                        Board.addTile(new ValidTile(false), i, j);
                        break;

                    default:
                        System.out.println("Invalid tile in board creation, terminating");
                        System.exit(1);
                }
            }
        }
    }
}

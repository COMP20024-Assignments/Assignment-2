package jebediah.generic;
/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 * Board contains all the tiles in the game, set out in an array board[x][y]
 */
public class Board {
    private static Tile[][] board;
    private static int SIZE;
    private static final int MIN_SIZE = 3;

    public Board(int dimension) {
        if (dimension <= MIN_SIZE) {
            System.out.println("Invalid board size, terminating");
            System.exit(1);
        }
        SIZE = dimension;
        board = new Tile[SIZE][SIZE];
    }

    public static void addTile(Tile tile, int x, int y) {
        if (x >= SIZE || y >= SIZE) {
            System.out.println("Out of board array bound, terminating");
            System.exit(1);
        }

        board[x][y] = tile;
    }

    static boolean isOccupied(int x, int y) {
        return board[x][y].isOccupied();
    }

    static int getSize() {
        return SIZE;
    }

}

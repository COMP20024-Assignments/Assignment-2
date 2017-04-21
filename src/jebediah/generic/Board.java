package jebediah.generic;
/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 * Board contains all the tiles in the game, set out in an array board[x][y]
 */
public class Board {

    private Tile[][] board;
    private int SIZE;

    public Board(int dimension, String boardLayout, Agent player) {


        int MIN_SIZE = 3;

        if (dimension <= MIN_SIZE) {
            System.out.println("Invalid board size, terminating");
            System.exit(1);
        }
        SIZE = dimension;
        board = new Tile[dimension][dimension];

        Agent enemy;

    }

    private void addTile(Tile tile, int x, int y) {
        if (x >= SIZE || y >= SIZE) {
            System.out.println("Out of board array bound, terminating");
            System.exit(1);
        }

        board[x][y] = tile;
    }

    boolean isOccupied(int x, int y) {
        return board[x][y].isOccupied();
    }

    public int getSize() {
        return SIZE;
    }


    public void fillBoard(String boardLayout, Agent h, Agent v) {

        for (int j=0; j < SIZE; j++) {
            for (int i=0; i < SIZE; i++) {

                switch (boardLayout.charAt(i)) {
                    case ('B'):
                        this.addTile(new BlockedTile(), i, j);
                        break;

                    case ('V'):
                        v.addPiece(new Piece(i,j));
                        this.addTile(new ValidTile(true), i, j);
                        break;

                    case ('H'):
                        h.addPiece(new Piece(i,j));
                        this.addTile(new ValidTile(true), i, j);
                        break;

                    case ('+'):
                        this.addTile(new ValidTile(false), i, j);
                        break;

                    default:
                        System.out.println("Invalid tile in board creation, terminating");
                        System.exit(1);
                }
            }
        }
    }

    public char getTileType(int x, int y) {
        return this.board[x][y].type;
    }

}

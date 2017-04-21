package jebediah.generic;

import aiproj.slider.Move;

import java.io.StringReader;

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

    }

    private void addTile(Tile tile, int x, int y) {
        if (x >= SIZE || y >= SIZE) {
            System.out.println("Out of board array bound, terminating");
            System.exit(1);
        }

        board[x][y] = tile;
    }

    boolean isOccupied(int x, int y) {
        return (x >= SIZE || y >= SIZE) || board[x][y].isOccupied();

    }

    public int getSize() {
        return SIZE;
    }



    public void fillBoard(String boardLayout, Agent h, Agent v) {

        int k = 0;
        for (int j=SIZE-1; j >= 0; j--) {
            for (int i=0; i < SIZE; i++) {

                boolean registered=false;
                while (k < boardLayout.length() && !registered) {

                    switch (boardLayout.charAt(k)) {
                        case ('B'):
                            registered = true;
                            this.addTile(new BlockedTile(), i, j);
                            break;

                        case ('V'):
                            registered = true;
                            v.addPiece(new Piece(i, j, 'V'));
                            this.addTile(new ValidTile(true, 'V'), i, j);
                            break;

                        case ('H'):
                            registered = true;
                            h.addPiece(new Piece(i, j, 'H'));
                            this.addTile(new ValidTile(true, 'H'), i, j);
                            break;

                        case ('+'):
                            registered = true;
                            this.addTile(new ValidTile(false, '+'), i, j);
                            break;
                        default:
                            registered=false;
                    }
               //     System.out.println("char: " +  boardLayout.charAt(k) +"\nregistered: "+registered+"\ni: "+i+"\nj: "+j+"\n\n");
                    k++;
                }
            }
        }
    }

    public char getTileType(int x, int y) {
        if (x >= SIZE || y >= SIZE) return '*';
        return this.board[x][y].type;
    }

    public void update(Move move, char player) {
        switch (move.d) {
            case UP:
                board[move.i][move.j].moveOut();
                //piece didn't move off board
                if (!(move.j+1 >= SIZE)) {
                    board[move.i][move.j+1].moveInto(player);
                }
                break;

            case DOWN:
                board[move.i][move.j].moveOut();
                //piece didn't move off board
                if (!(move.j-1 >= SIZE)) {
                    board[move.i][move.j-1].moveInto(player);
                }
                break;
            case LEFT:
                board[move.i][move.j].moveOut();
                //piece didn't move off board
                if (!(move.i-1 >= SIZE)) {
                    board[move.i-1][move.j].moveInto(player);
                }
                break;
            case RIGHT:
                board[move.i][move.j].moveOut();
                //piece didn't move off board
                if (!(move.i+1 >= SIZE)) {
                    board[move.i+1][move.j].moveInto(player);
                }
                break;
        }
    }
}

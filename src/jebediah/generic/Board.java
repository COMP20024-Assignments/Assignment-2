package jebediah.generic;

import aiproj.slider.Move;

import java.io.StringReader;

/** COMP30024 Artificial Intelligence
 Board class
 George Juliff - 624946
 Thomas Miles - 626263

 Contains all the tiles of the game in a grid, and keeps track of if they are full, empty or blocked
 */
public class Board {

    private Tile[][] board;
    private int SIZE;

    public Board(int dimension) {


        int MIN_SIZE = 3;

        if (dimension < MIN_SIZE) {
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
        return (x >= SIZE || y >= SIZE) || x < 0 || y < 0 || board[x][y].isOccupied();

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

    public void fillBoard(char[][] boardLayout, Agent h, Agent v) {

        for (int j=0; j < SIZE; j++) {
            for (int i=0; i < SIZE; i++) {
                switch (boardLayout[i][j]) {
                        case ('B'):
                            this.addTile(new BlockedTile(), i, j);
                            break;

                        case ('V'):
                            v.addPiece(new Piece(i, j, 'V'));
                            this.addTile(new ValidTile(true, 'V'), i, j);
                            break;

                        case ('H'):
                            h.addPiece(new Piece(i, j, 'H'));
                            this.addTile(new ValidTile(true, 'H'), i, j);
                            break;

                        case (' '):
                            this.addTile(new ValidTile(false, '+'), i, j);
                            break;
                        default:
                            System.out.println("Error: invalid char in board array: '" + boardLayout[i][j] + "'");
                            System.exit(1);
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

    @Override
    public String toString() {
        String string = "";
        int x, y;
        for (y = 0; y < SIZE; y++) {
            for (x = 0; x < SIZE; x++) {
               if (x+1 < SIZE) {
                   string += board[x][y].type + " ";
               } else {
                   string += board[x][y].type + "/n";
               }
            }
        }
        return string;
    }

    public char[][] toArray() {
        char[][] out = new char[SIZE][SIZE];
        int i, j;
        for (j = 0; j < SIZE; j++) {
            for (i = 0; i < SIZE; i++) {
                out[i][j] = board[i][j].type;
            }
        }
        return out;
    }
}

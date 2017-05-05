package jebediah.agents;

import java.util.Scanner;
import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import jebediah.generic.*;
/** COMP30024 Artificial Intelligence
 human class
 George Juliff - 624946
 Thomas Miles - 626263

 A class that can be used to allow a human to play the slider game in order to assist in training the AI
 */
public class human implements SliderPlayer  {

    private Agent me;
    private Agent enemy;
    private Board board;

    public void init(int dimension, String boardLayout, char player) {
        me = new Agent(player);
        board = new Board(dimension);

        if (player == 'H') {
            enemy = new Agent('V');
            board.fillBoard(boardLayout, me, enemy);
        }
        else {
            enemy = new Agent('H');
            board.fillBoard(boardLayout, enemy, me);
        }
    }

    /**
     *  Updates the board after opponent has moved and prints it so human can make a decision
    **/
    public void update(Move move) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (move == null) {
            printBoard();
            System.out.println("Opponent Passed");
            return;
        }
        System.out.println("Your turn: "+me.player);
        board.update(move, enemy.player);
        printBoard();

    }

    /**
     * handles keyboard input so the player can interact with the game
     * @return move made so referee can handle simulation
     */
    public Move move() {

        int pieceIndex;
        Move.Direction direction = null;
        Piece piece;
        int counter = 0;

        Scanner s = new Scanner(System.in);


        System.out.println("Please select piece to move:  ");


        while (true) {
            try {
                pieceIndex = s.nextInt();

                if ((piece = me.getPiece(pieceIndex)) == null) {
                    System.out.println("invalid index, try again!");
// check if selected piece is stuck
                } else if (piece.canMove(Move.Direction.UP, this.board) || piece.canMove(Move.Direction.DOWN, this.board) ||
                        piece.canMove(Move.Direction.LEFT, this.board) || piece.canMove(Move.Direction.RIGHT, this.board)) {
                    break;
// if all are stuck pass turn
                } else if (counter + 1 < board.getSize()) {
                    counter++;
                    System.out.println("Piece is stuck, pick another, " + counter + " pieces stuck.");
                } else {
                    System.out.println("Passing");
                    return null;
                }
            } catch (Exception e){
                System.out.println("Piece index expects int, try again");
                s = new Scanner(System.in);
            }
        }
        System.out.println("Please select direction ('w' // 'a' // 's' // 'd'):  ");

        boolean valid = false;
        while (!valid) {

            switch (s.next().charAt(0)) {
                // if the move requested is valid, break the loop, make the move on our record of board and return the
                // move made
                case 'w':
                    direction = Move.Direction.UP;
                    valid = piece.canMove(direction, this.board);
                    break;

                case 'a':
                    direction = Move.Direction.LEFT;
                    valid = piece.canMove(direction, this.board);
                    break;

                case 's':
                    direction = Move.Direction.DOWN;
                    valid = piece.canMove(direction, this.board);
                    break;

                case 'd':
                    direction = Move.Direction.RIGHT;
                    valid = piece.canMove(direction, this.board);
                    break;
                default:
                    System.out.println("Invalid key press, controls are wasd");
                    valid = false;
            }
        }
        Move move = me.makeMove(direction, pieceIndex, board.getSize());
        board.update(move, me.player);

        return move;

    }

    /**
     * prints the board with players tiles as index values for ease of control
     */
    private void printBoard() {

        for (int j=board.getSize()-1; j >= 0; j--) {
            for (int i=0; i < board.getSize(); i++) {
                // make sure indexes get printed for my pieces
                if (board.getTileType(i,j) == me.player) {
                    System.out.print(" "+ me.getPieceIndex(i,j)+ " ");
                } else {
                    System.out.print(" "+ board.getTileType(i,j)+ " ");
                }
            }
            System.out.print("\n");

        }
    }

}

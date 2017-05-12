
package CyberdyneSystems.agents;

import java.util.Scanner;
import aiproj.slider.Move;
import CyberdyneSystems.generic.*;

import static CyberdyneSystems.generic.Board.O;

/** COMP30024 Artificial Intelligence
 Human class
 George Juliff - 624946
 Thomas Miles - 626263

 A class that can be used to allow a Human to play the slider game in order to assist in testing and training the AI
 */
public class Human extends Agent  {


    /**
     *  Updates the board after opponent has moved and prints it so Human can make a decision
     **/
    @Override
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

        byte[] piece;
        int pieceIndex;
        Move.Direction direction = null;
        int counter = 0;

        Scanner s = new Scanner(System.in);


        System.out.println("Please select piece to move:  ");


        while (true) {
            try {
                pieceIndex = s.nextInt();

                if ((piece = me.getPiece(pieceIndex)) == null) {
                    System.out.println("invalid index, try again!");
// check if selected piece is stuck
                } else if (
                        (((me.player == Board.V) && (piece[1]+1 == board.getSize()))) || (board.board[piece[0]][piece[1]+1] == O ) || // UP
                                ((me.player == Board.H) && (piece[1]-1 >=0) && (board.board[piece[0]][piece[1]-1] == O)) || // DOWN
                                ((me.player == Board.V) && (piece[0]-1 >=0) && (board.board[piece[0]-1][piece[1]] == O)) || // LEFT
                                (((me.player == Board.H) && (piece[0]+1 == board.getSize()))) || (board.board[piece[0]][piece[1]+1] == O )) // RIGHT
                {
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
                    valid = (((me.player == Board.V) && (piece[1]+1 == board.getSize()))) || (board.board[piece[0]][piece[1]+1] == O );
                    break;

                case 'a':
                    direction = Move.Direction.LEFT;
                    valid = ((me.player == Board.V) && (piece[0]-1 >=0) && (board.board[piece[0]-1][piece[1]] == O));
                    break;

                case 's':
                    direction = Move.Direction.DOWN;
                    valid = ((me.player == Board.H) && (piece[1]-1 >=0) && (board.board[piece[0]][piece[1]-1] == O));
                    break;

                case 'd':
                    direction = Move.Direction.RIGHT;
                    valid = (((me.player == Board.H) && (piece[0]+1 == board.getSize()))) || (board.board[piece[0]][piece[1]+1] == O );
                    break;
                default:
                    System.out.println("Invalid key press, controls are wasd");
                    valid = false;
            }
        }
        Move move = me.makeMove(direction, pieceIndex);
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
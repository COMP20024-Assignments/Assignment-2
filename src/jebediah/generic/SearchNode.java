package jebediah.generic;

import aiproj.slider.Move;

import java.util.ArrayList;

/** COMP30024 Artificial Intelligence
 SearchNode class
 George Juliff - 624946
 Thomas Miles - 626263

 Makes up the tree that the adversarial search will use, is responsible for both holding a board and generating its
 children
 IMPORTANT NOTE: boards states are stored in a character array rather than as an object as size is more of a limiting
 factor than time, so while this adds extra operations it greatly reduces the space needed to perform a search.
 */
public class SearchNode {
    public char[][] current;
    public Move lastMove;
    public int depth;
    private int size;
    private ArrayList<SearchNode> children;

    /**
     * Generate a SearchNode, should only be used externally to generate the root node
     * @param board The current board state
     * @param move The move taken to get to this state
     * @param depth The current depth in the tree
     * @param size The edge length of the board
     */
    public SearchNode(char[][] board, Move move, int depth, int size) {
        current = board;
        lastMove = move;
        children = null;
        this.depth = depth;
        this.size = size;
    }

    /**
     * Given a player return as children the board states after all possible moves
     * @param player either 'V' or 'H'
     * @return an array of the children
     */
    public ArrayList<SearchNode> getChildren(char player) {
        // skip logic if children already found
        if (children != null) {return children;}
        int i = 0;
        // Create and populate board of current state for testing
        Board testBoard = new Board(size);
        Agent v = new Agent('V'), h = new Agent('H');
        testBoard.fillBoard(current, h, v);

        //For each piece of the active player test each move direction and generate a new child with that move applied
        Piece piece;
        Agent active = v;
        if (player == 'H') {active = h;}
        piece = active.getPiece(0);
        children = new ArrayList<SearchNode>();
        do {
            if (piece.canMove(Move.Direction.UP, testBoard)) {
                children.add(newNode(new Move(piece.getXpos(), piece.getYpos(), Move.Direction.UP)));
            }
            if (piece.canMove(Move.Direction.DOWN, testBoard)) {
                children.add(newNode(new Move(piece.getXpos(), piece.getYpos(), Move.Direction.DOWN)));
            }
            if (piece.canMove(Move.Direction.LEFT, testBoard)) {
                children.add(newNode(new Move(piece.getXpos(), piece.getYpos(), Move.Direction.LEFT)));
            }
            if (piece.canMove(Move.Direction.RIGHT, testBoard)) {
                children.add(newNode(new Move(piece.getXpos(), piece.getYpos(), Move.Direction.RIGHT)));
            }
            piece = active.getPiece(++i);
        } while (piece != null);

        //If no children were created return null, otherwise return array
        if (children.size() == 0) {
            children = null;
            return null;
        } else {return children;}
    }

    /**
     * Method called in the special case where the player cannot move, but the game is not yet over, sets the boards
     * only child as a duplicate of itself at a lower depth.
     */
    public void noMove() {
        children = new ArrayList<SearchNode>();
        children.add(new SearchNode(current, null, depth+1, size));
    }

    /**
     * Creates new nodes from the current node given a move, used to generate all child nodes given a move
     * @param move the move to be applied
     * @return a node of the new state to be added to children
     */
    private SearchNode newNode(Move move) {
        char[][] newBoard = new char[size][size];
        int i;
        // Copy the board
        for (i = 0; i < size; i++) {
            System.arraycopy(current[i],0,newBoard[i],0,size);
        }
        // Move the piece if it is on the board
        switch (move.d) {

            case UP:
                if (move.j+1 < size) {
                    newBoard[move.i][move.j+1] = newBoard[move.i][move.j];
                }
                break;

            case DOWN:
                if (move.j-1 >= 0) {
                    newBoard[move.i][move.j-1] = newBoard[move.i][move.j];
                }
                break;

            case LEFT:
                if (move.i-1 >= 0) {
                    newBoard[move.i-1][move.j+1] = newBoard[move.i][move.j];
                }
                break;

            case RIGHT:
                if (move.i+1 < size) {
                    newBoard[move.i+1][move.j] = newBoard[move.i][move.j];
                }
                break;
        }
        // Empty the old tile
        newBoard[move.i][move.j] = ' ';
        return new SearchNode(newBoard, move, depth+1, size);
    }
}

package jebediah.agents;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import jebediah.generic.Agent;
import jebediah.generic.Board;
import jebediah.generic.SearchNode;

/** COMP30024 Artificial Intelligence
 skynet class
 George Juliff - 624946
 Thomas Miles - 626263

 The overarching AI class responsible for playing the game by use of adversarial search, improved by machine learning
 */
public class skynet implements SliderPlayer {
    public static final int SEARCH_DEPTH = 5; // The depth the adversarial search will explore to
    private Board board;
    private int size;
    private Agent player, opponent;


    /**
     * Implementation of interface method creates local board and saves variables
     * @param dimension The width and height of the board in cells
     * @param board A string representation of the initial state of the board
     * @param player 'H' or 'V'
     */
    public void init(int dimension, String board, char player) {
        this.board = new Board(dimension);
        this.player = new Agent(player);
        if (player == 'H') {
            opponent = new Agent('V');
            this.board.fillBoard(board,this.player,opponent);
        }
        else {
            opponent = new Agent('H');
            this.board.fillBoard(board,opponent,this.player);
        }
        size = dimension;
    }

    /**
     * Implementation of interface method Updates the board with the opponents moves
     * @param move A Move object representing the previous move made by the
     * opponent, which may be null (indicating a pass).
     */
    public void update(Move move) {
        if (move != null) {
            board.update(move,opponent.player);
        }
    }

    /**
     * Implementation of interface method resposible for finding the move for the AI to take using a adversarial search
     * @return A move object for the move determined to maximise chance of victory
     */
    public Move move() {
        // Create the root at the current board state
        SearchNode root = new SearchNode(board.toArray(),null, 0, size);
        // Return null if no move possible
        if (root.getChildren(player.player) == null) {
            return null;
        }
        /* Split of top level of search so that move can be extracted and use adversarial search on each branch to
        * find the best option*/
        float temp, bestScore = Float.MIN_VALUE;
        Move bestMove = null;
        for (SearchNode node: root.getChildren(player.player)) {
            temp = recursiveFind(node, SEARCH_DEPTH, opponent.player, true, Float.MIN_VALUE, Float.MAX_VALUE);
            if (temp > bestScore) {
                bestScore = temp;
                bestMove = node.lastMove;
            }
        }
        if (bestMove != null) {
            board.update(bestMove,player.player);
        } else {
            System.out.println("Error: no move selected");
            System.exit(1);
        }
        return bestMove;
    }

    /**
     * A recursive a-b search fucntion that is used with the SkyNetHeuristic to find the optimal outcome
     * @param node The node of the current board state
     * @param trgtDepth The deepest the search will go
     * @param player The player who is currently moving (not always the AI)
     * @param max if the current player is trying to maximise the score
     * @param a alpha
     * @param b beta
     * @return the value of the current node
     */
    private float recursiveFind(SearchNode node, int trgtDepth, char player, boolean max, float a, float b) {
        // check if at terminal depth
        if (node.depth >= trgtDepth) {return SkyNetHeuristic.applyHeuristic(node.current, size, player);}
        // check if the player can make a move
        if (node.getChildren(player) == null) {
            //then check if this is a draw
            if (player == 'V') {node.getChildren('H');}
            else {node.getChildren('V');}
            if (node.getChildren(player) == null) {
                //if so return value
                return SkyNetHeuristic.applyHeuristic(node.current, size, player);
            } else {
                //if not set child of node as the same as node
                node.noMove();
            }
        }
        // Set opponent to take next step
        char oppn = 'V';
        if (player == 'V') {oppn = 'H';}

        if (max) {
            // if attempting to maximise the value recursively call the function then compare to alpha
            float i, tempVal = Float.MIN_VALUE;
            for(SearchNode child: node.getChildren(player)){
                if (tempVal < (i = recursiveFind(child, trgtDepth, oppn, false, a,b))) {
                    tempVal = i;
                    if (a < tempVal) {
                        a = tempVal;
                        if (a >= b) {
                            break;
                        }
                    }
                }
            }
            // return the highest score of the given branch, this will eventually be the score of the root node.
            return tempVal;
        } else {
            // else if trying to minimise call the recursive function and compare to beta
            float i, tempVal = Float.MAX_VALUE;
            for(SearchNode child: node.getChildren(player)){
                if (tempVal > (i = recursiveFind(child, trgtDepth, oppn, true, a,b))) {
                    tempVal = i;
                    if (b > tempVal) {
                        b = tempVal;
                        if (b <= a){
                            break;
                        }
                    }
                }
            }
            // return the lowest value
            return tempVal;
        }
    }
}


package CyberdyneSystems.agents;

import CyberdyneSystems.generic.Agent;
import aiproj.slider.Move;

/** COMP30024 Artificial Intelligence

 George Juliff - 624946
 Thomas Miles - 626263

 AI agent for slider game. Uses iterative deepening minimax with alpha-beta pruning. (may be sent back in time to kill
 Sarah Connor before she gives birth to the leader of the human resistance)
 */
public class T800 extends Agent {

    private int depth=3; // initial value for max depth, move uses iterative deepening to maximise this
    private int depthBound=100; // when to stop iterative deepening if no OutOfMemoryError occurs
    private Move bestMove = null;
    private float bestScore = -Float.MAX_VALUE;

    @Override
    public void update(Move move) {

        /*
        *   Here is where we might want to add some active learning behavior to try and beat opponents making unexpected
        *   moves.  e.g. % moves that are unexpected might change a multiplier a value used to compare our evaluation
        *   functions, and breaking ties in the broarder comparison using an average of all children or similar
        */

        if (move != null) {
            board.update(move, enemy.player);
        }
        bestScore = -Float.MAX_VALUE;   // reset best score
    }

    /**
     * Main operation of the AI is here, needs to be prompted by the referee
     * @return Move object for the ref to deal with
     */
    public Move move() {

        Node root = new Node(me);

        while (depth <= depthBound) {
            try {
                alphaBeta(root, depth, -Float.MAX_VALUE, Float.MAX_VALUE, true, null);
                depth ++;
            } catch (OutOfMemoryError e) {
                // update the depthBound so future errors wont occur
                depthBound = depth-1;

/*
*   debug printout, machine learning will be used to find a better bound if this is inconsistent
*/
                System.out.printf("depthBound has changed to: "+depthBound);
            }
        }
        if (bestMove == null) { System.out.println("Warning: null move returned"); }
        return bestMove;
    }







    /**
     * Alpha beta pruning minimax algorithm
     * @param node Current node being visited
     * @param depthRemaining Terminal depth of search
     * @param a alpha input
     * @param b beta input
     * @param maximise are we at a max depth
     * @param firstMove keep track of the firstMove to get here so it can be recovered when we find the best move after
     *                  recursion
     * @return minimax evaluation value so recursion can happen, updates bestMove when it returns to the root
     */
    private float alphaBeta(Node node, int depthRemaining, float a, float b, boolean maximise, Move firstMove) {

        Node child;
        float alpha = a, beta = b, value;

        // maximum depth or terminal node reached
        if (depthRemaining == 0 || node.calculateNumMoves() == 0) {
            // since the evaluation fns require enemy and AI agents to evaluate the board, and these swap in nodes,
            // make evaluate is called correctly.
            /*
            *   May like to revisit the way this is done so nodes just know me and enemy agents and just have a boolean
            *   max.. kind of a screw up...
            */

            if (maximise) {
                return Skynet.getSkynet().evaluate(firstMove, node.playerState, node.otherAgent);
            } else {
                return Skynet.getSkynet().evaluate(firstMove, node.otherAgent, node.playerState);
            }
        }
        if (maximise) {
            value = -Float.MAX_VALUE;
            while ((child = node.nextChild()) != null) {
                // this is the first move, store that and pass down recursion so we can recover the best move
                if (firstMove == null) {
                    firstMove = child.moveToArrive;
                }
                value = Math.max(value, alphaBeta(child, depthRemaining-1, a, b, false, firstMove));
                alpha = Math.max(alpha, value);

                if (beta <= alpha) break;
            }

            // if this node is the child of the root and evaluation value is better than the bestScore found so far,
            // update bestMove to the move taken to get to this node, once complete this will mean bestMove is the
            // minimaxed option.


            if (value > bestScore && depthRemaining == depthBound - 1) { /* this logic is sketchy maybe idk..... */
                bestMove = firstMove;
            }
            return value;

        } else {
            value = Float.MAX_VALUE;
            while ((child = node.nextChild()) != null) {
                value = Math.min(value, alphaBeta(child, depthRemaining-1, a, b, true,firstMove));
                beta = Math.min(beta, value);

                if (beta <= alpha) break;
            }
            return value;
        }

    }
}

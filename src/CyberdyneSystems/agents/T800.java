
package CyberdyneSystems.agents;

import CyberdyneSystems.AI.Config;
import CyberdyneSystems.AI.Node;
import CyberdyneSystems.AI.Skynet;
import CyberdyneSystems.generic.Agent;
import CyberdyneSystems.generic.Board;
import aiproj.slider.Move;

/** COMP30024 Artificial Intelligence

 George Juliff - 624946
 Thomas Miles - 626263

 AI agent for aiproj.slider game. Uses iterative deepening minimax with alpha-beta pruning. (may be sent back in time to kill
 Sarah Connor before she gives birth to the leader of the human resistance)
 */
public class T800 extends Agent {

    public static int expanded=0;
    public static int prunes=0;

    private Move bestMove = null;
    private float bestScore = -Float.MAX_VALUE;

    /**
     * initialize T-800 in the same way as the generic agent class, also load the weighting values from the config file
     */
    @Override
    public void init(int dimension, String boardLayout, char player) {
        super.init (dimension, boardLayout, player);
        new Config();
    }

    @Override
    public void update(Move move) {

        /*
        *   Here is where we might want to add some active learning behavior to try and beat opponents making unexpected
        *   moves.  e.g. % moves that are unexpected might change a multiplier a value used to compare our evaluation
        *   functions, and breaking ties in the broarder comparison using an average of all children or similar
        */

        if (move != null) {
            if (player == Board.H) {
                board.update(move, Board.V);
            } else {
                board.update(move, Board.H);
            }
        }
        bestScore = -Float.MAX_VALUE;   // reset best score
        bestMove = null;


    }

    /**
     * Main operation of the AI is here, needs to be prompted by the referee
     * @return Move object for the ref to deal with
     */
    public Move move() {

        int depth = Config.getDepthInit();

        while (depth <= Config.getDepthCutoff()) {
            try {
                Node root = new Node(this, null);
                alphaBeta(root, depth, -Float.MAX_VALUE, Float.MAX_VALUE, true, null);
                depth++;

            } catch (OutOfMemoryError e) {
                return bestMove;
            }
        }
        if (bestMove != null) {
            board.update(bestMove, player);
            movePiece(bestMove);
        }
        expanded=0;
        prunes=0;

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
        float alpha = a;
        float beta = b;
        float value;

        // maximum depth or terminal node reached
        if (depthRemaining == 0 || node.calculateNumMoves() == 0) {

            // since the evaluation fns require enemy and AI agents to evaluate the layout, and these swap in nodes,
            // make evaluate is called correctly.
            if (maximise) {
                return Skynet.evaluate(firstMove, node.playerState, node.otherAgent);
            } else {
                return Skynet.evaluate(firstMove, node.otherAgent, node.playerState);
            }
        }
        if (maximise) {
            value = -Float.MAX_VALUE;
            while (node.moreChilds()) {

                child = node.nextChild(node.firstMove);
                // this is the first move, store that and pass down recursion so we can recover the best move

                value = Math.max(value, alphaBeta(child, depthRemaining-1, alpha, beta, false, firstMove));
                alpha = Math.max(alpha, value);

                if (beta <= alpha) {
                    prunes++;
                    break;
                }

            }
            return value;

        } else {
            value = Float.MAX_VALUE;

            while (node.moreChilds()) {

                child = node.nextChild(node.firstMove);





                value = Math.min(value, alphaBeta(child, depthRemaining-1, alpha, beta, true, firstMove));
                beta = Math.min(beta, value);

                if (beta <= alpha) {
                    prunes ++;
                    break;
                }
            }
            // once recursion has ended, if we are at a child of the root node, check its evaluation value against the
            // best yet found, and update the bestScore and bestMove if appropriate
            if (depthRemaining == Config.getDepthCutoff() -1) {

                if (value > bestScore) {

                    bestMove = node.firstMove;
                    bestScore = value;
                }
            }
            return value;
        }
    }
}

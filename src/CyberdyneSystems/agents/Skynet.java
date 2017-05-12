
package CyberdyneSystems.agents;

import aiproj.slider.Move;
import CyberdyneSystems.generic.Agent;
import CyberdyneSystems.generic.Board;
import CyberdyneSystems.generic.Piece;

/** COMP30024 Artificial Intelligence

 George Juliff - 624946
 Thomas Miles - 626263

 A class solely responsible for calculating the evaluation value if a given board state for a given agent.
 It contains several weighting values for various heuristic components that can be changed by machine learning code.


 *******        WARNING: likely to become self aware and cause the nuclear apocalypse - judgement day       *******
 */
public class Skynet {

    private static final Skynet INSTANCE = new Skynet(2,1,1.5f,0.75f, 2,10,10,1,0.5f);

    private static float W1, W1_e, W2, W2_e, W2x, W3, W3_e, W4f,W4n, W4f_e,W4n_e;

    /**
     * Initialises all the weightings for the evaluation function, need to get rid of singleton pattern during machine
     * learning
     * @param w1 weighting on AI's 'forwardness'
     * @param w1_e weighting on enemies 'forwardness'
     * @param w2 weighting on AI's 'blockedness'
     * @param w2_e weighting on enemies 'blockedness'
     * @param w3 weighting on AI's number of pieces
     * @param w3_e weighting on enemies number of pieces
     * @param w4f weighting on AI's 1st move being forward
     * @param w4n weighting on AI's 1st move not being forward

     */
    private Skynet (float w1, float w1_e, float w2, float w2_e, float w2x, float w3, float w3_e, float w4f, float w4n) {

        W1 = w1;
        W1_e = w1_e;
        W2 = w2;
        W2_e = w2_e;
        W2x = w2x;
        W3 = w3;
        W3_e = w3_e;
        W4f = w4f;
        W4n = w4n;
    }

    public static Skynet getSkynet() {
        return INSTANCE;
    }

    /**
     *
     * @param firstMove
     * @param me
     * @param enemy
     * @return
     */
    public float evaluate(Move firstMove, Agent me, Agent enemy) {

        float e1, e1e, e2, e2e, e3, e3e, e4;

        e1  = E1(me);
        e1e = E1(enemy);
        e2  = E2(me);
        e2e = E2(enemy);
        e3  = E3(me);
        e3e = E3(enemy);
        e4 = E4(firstMove, me);

        return W1*e1 - W1_e*e1e - W2*e2 + W2_e*e2e  + W3*e3 - W3_e*e3e + e4;
    }

    /**
     * Sums how far forward all the pieces are. High is good.
     * @return the total number of moves the pieces will need if the move in a straight line to the end of the board
     */
    private static float E1(Agent agent) {
        float E=0;
        int i;

        for (i=0; i < agent.numPieces(); i++) {
            if (agent.player == Board.H) {
                E += agent.getPiece(i)[Agent.i];
            } else {
                E += agent.getPiece(i)[Agent.j];
            }
        }
        return E;
    }

    /**
     * Sum of number of directions in which all an agents pieces are blocked. A weight can be set to blocking in the
     * forward direction since it is more harmful. Low is good for the AI agent's pieces, high is good for enemy pieces
     */
    private static float E2(Agent agent) {
        float E=0;
        int i;

        for (i=0;i<agent.numPieces(); i++) {
            // both players can move right and up
            // check the tile to the right of each piece, if it isn't open add to E
            if (agent.board.board [agent.getPiece(i)[Agent.i+1]] [agent.getPiece(i)[Agent.j]] != Board.O) {
                // if this is a forward block
                if (agent.player == Board.H) {
                    E += W2x;
                } else {
                    E++;
                }
            }
            // same deal up
            if (agent.board.board [agent.getPiece(i)[Agent.i]] [agent.getPiece(i)[Agent.j+1]] != Board.O) {
                if (agent.player == Board.V) {
                    E += W2x;
                } else {
                    E++;
                }
            }
            // only H can move down
            if (agent.player == Board.H) {
                if (agent.board.board [agent.getPiece(i)[Agent.i]] [agent.getPiece(i)[Agent.j]-1] != Board.O) {
                    E++;
                }
            }
            // and only V can move left
            if (agent.player == Board.V) {
                if (agent.board.board [agent.getPiece(i)[Agent.i]-1] [agent.getPiece(i)[Agent.j]] != Board.O) {
                    E++;
                }
            }
        }
        return E;
    }

    /**
     * Counts the number of pieces a player has, low is good
     */
    private static float E3(Agent agent){
        return (float) agent.numPieces();
    }

    /**
     * Returns the adjusted value of the first move
     * @param firstMove the first move taken from the real current board state
     * @param player the agent object being inspected
     */
    private static float E4(Move firstMove, Agent player) {
        if((player.player == 'H' && firstMove.d == Move.Direction.RIGHT) ||
                (player.player == 'V' && firstMove.d == Move.Direction.UP)) {

            return W4f;

        } else {
            return W4n;
        }
    }
}
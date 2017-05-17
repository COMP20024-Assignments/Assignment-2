
package CyberdyneSystems.AI;

import aiproj.slider.Move;
import CyberdyneSystems.generic.Agent;
import CyberdyneSystems.generic.Board;

/** COMP30024 Artificial Intelligence

 George Juliff - 624946
 Thomas Miles - 626263

 A class solely responsible for calculating the evaluation value if a given layout state for a given agent.
 It contains several weighting values for various heuristic components that can be changed by machine learning code.


 *******        WARNING: likely to become self aware and cause the nuclear apocalypse - judgement day       *******
 */
public class Skynet {

    /**
     * @param firstMove
     * @param me
     * @param enemy
     * @return
     */
    public static float evaluate(Move firstMove, Agent me, Agent enemy) {
        float e1, e1e, e2, e2e, e3, e3e, e4, E=0; // evaluation values

        e1  = E1(me);
        e1e = E1(enemy);
        e2  = E2(me);
        e2e = E2(enemy);
        e3  = E3(me);
        e3e = E3(enemy);
        e4 = E4(firstMove, me);

        E += Config.getW1()*e1 - Config.getW1e()*e1e; // AI forwardness - opponent  (weighted)
 //System.out.println("1 " +e1+", 1e "+e1e+", 2 "+e2+", 2e "+e2e+", 3 "+e3+", 3e "+e3e+", 4 "+e4);

        E += Config.getW2e() - e2e/Config.getW2()*e2; // Opponent blockedness - AI's(weighted)
        E += Config.getW3e() - e3e/Config.getW3()*e3; // AI's first move modifier   (internally weighted)
        E += e4;

        return E;
    }

    /**
     * Sums how far forward all the pieces are. High is good.
     * @return the total number of moves the pieces will need if the move in a straight line to the end of the layout
     */
    private static float E1(Agent agent) {
        float E=0;
        int i;

        for (i=0; i < agent.numPieces(); i++) {
            if (agent.player == Board.H) {
                E += agent.getPiece(i)[Agent.i] - Board.getSize();
            } else {
                E += agent.getPiece(i)[Agent.j] - Board.getSize();
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
            if (agent.board.canMoveTo(agent.getPiece(i)[Agent.i] + 1, agent.getPiece(i)[Agent.j], agent.player)) {
                // if this is a forward block
                if (agent.player == Board.H) {
                    E += Config.getW2x();
                } else {
                    E++;
                }
            }
            // same deal up
            if (agent.board.canMoveTo(agent.getPiece(i)[Agent.i], agent.getPiece(i)[Agent.j] + 1, agent.player)) {
                if (agent.player == Board.V) {
                    E += Config.getW2x();
                } else {
                    E++;
                }
            }
            // only H can move down
            if (agent.player == Board.H) {
                if ((agent.board.canMoveTo(agent.getPiece(i)[Agent.i], agent.getPiece(i)[Agent.j - 1], agent.player))) {
                    E++;
                }
            }
            // and only V can move left
            if (agent.player == Board.V) {
                if (agent.board.canMoveTo(agent.getPiece(i)[Agent.i] - 1, agent.getPiece(i)[Agent.j], agent.player)) {
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
     * @param firstMove the first move taken from the real current layout state
     * @param player the agent object being inspected
     */
    private static float E4(Move firstMove, Agent player) {
        if((player.player == 'H' && firstMove.d == Move.Direction.RIGHT) ||
                (player.player == 'V' && firstMove.d == Move.Direction.UP)) {

            return Config.getW4f();

        } else {
            return Config.getW4n();
        }
    }
}
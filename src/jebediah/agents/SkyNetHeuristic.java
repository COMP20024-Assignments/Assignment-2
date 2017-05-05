package jebediah.agents;

import jebediah.generic.Agent;
import jebediah.generic.Board;
import jebediah.generic.Piece;

/** COMP30024 Artificial Intelligence
 SktNetHeuristic class
 George Juliff - 624946
 Thomas Miles - 626263

 A class solely responsible for assessing the desirability of a given board state, separated in order to improve
 readability and make training the AI easier
 */
public class SkyNetHeuristic {
    public static final float H1 = 1; //Divided by the minimum total foward distance remaining
    public static final float H2 = 1; //Multiplies combined remaining distance of pieces
    public static final float H3 = 1; //Divided by the 'blockedness' of of good pieces
    public static final float H4 = 1; //Multiplies the 'blockedness' of of bad pieces

    public static float applyHeuristic(char[][] state, int size, char player) {
        //////////////////////////////PLACEHOLDER LOGIC
        float F1;
        Agent good, bad;
        Board board = new Board(state.length);
        good = new Agent(player);

        if (player == 'H') {
            bad = new Agent('V');
            board.fillBoard(state,good,bad);
        }
        else {
            bad = new Agent('H');
            board.fillBoard(state,bad,good);
        }

        Piece piece = good.getPiece(0);
        int i = 0;
        F1 = 0;
        do {
            F1 += state.length - piece.getXpos();
            piece = good.getPiece(++i);
        } while (piece != null);

        return H1/F1;
    }
}

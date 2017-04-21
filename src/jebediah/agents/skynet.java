package jebediah.agents;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

/**
 * Created by Tom Miles (626263) & George Juliff (624946) on 21/04/2017.
 *
 *
 * AI agent for playing (and winning) the slider game... not implimented
 *
 */
public class skynet implements SliderPlayer {



    public void init(int dimension, String board, char player) {

    }


    public void update(Move move) {

    }


    public Move move() {

        Move move=new Move (1,1, Move.Direction.DOWN);


        return move;
    }
}

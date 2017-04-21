package jebediah.generic;

/**
 * Created by Tom Miles (tmiles, 626263) and George Juliff (juliffg, 624946) on 1/04/2017.
 *
 * Abstract class for tile objects on the board
 */
public abstract class Tile {
    public char type='-';
    public abstract boolean isOccupied();
}

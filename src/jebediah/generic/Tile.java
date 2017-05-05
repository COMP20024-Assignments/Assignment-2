package jebediah.generic;

/** COMP30024 Artificial Intelligence
 Tile class
 George Juliff - 624946
 Thomas Miles - 626263

 abstract class extended by all types of tile
 */
public abstract class Tile {
    public char type=' ';
    public abstract boolean isOccupied();
    public abstract void moveInto(char type);
    public abstract void moveOut();
}

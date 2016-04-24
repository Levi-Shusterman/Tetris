package Tetronimoes;

import java.util.Vector;

/**
 * Abstract representation of a tetronimo
 * 
 * 
 * @author Levi Shusterman
 *
 */
public abstract class Tetronimo {
	
	// Different configurations
	Vector<Position> Normal;
	Vector<Position> Right;
	Vector<Position> Under;
	Vector<Position> Left;
	
	// Current position and information
	Vector<Position> Current;
	int maxLower;
	int maxRight;
	
	Tetronimo() {
	}
	
	// gets the current configuration
	public abstract Vector<Position> getCurrentPos();
	
	// get the maximum extents of the piece
	public abstract Position getMaxExtent();
	
	// rotates and changes the configuration to 
	// the ones listed below
	public abstract void rotate();
	
	abstract void normal();
	abstract void right();
	abstract void under();
	abstract void left();
	
	
}

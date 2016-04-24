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
	
	// Maximum extent of the block in its cubic position
	int maxLower;
	int maxRight;
	
	// Beginning points of the block
	int startRow;
	int startCol;
	
	Tetronimo() {
	}
	
	// gets the current configuration
	public abstract Vector<Position> getCurrentPos();
	
	// get the maximum extents of the piece
	public abstract Position getMaxExtent();
	
	// get the positions of where the shape starts out
	public abstract Position getStartPos();
	
	// rotates and changes the configuration to 
	// the ones listed below
	public abstract void rotate();
	
	abstract void normal();
	abstract void right();
	abstract void under();
	abstract void left();
	
	
}

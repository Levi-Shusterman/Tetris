package Tetronimoes;

import java.util.Vector;

public class LBlock extends Tetronimo {

	LBlock(){
		/*
		 * Set up configurations
		 */
		
		/*
		 * How a piece is positioned
		 * 
		 * 0,0  0,1  0,2
		 * 1,0  1,1  1,2
		 * 2,0  2,1  2,2
		 * 
		 * Note, some later tetronimos will need
		 * a 4 by 4 representation
		 */
		Normal= new Vector<Position>();
		Normal.add(new Position(0,1));
		Normal.add(new Position(1,1));
		Normal.add(new Position(2,1));
		Normal.add(new Position(2,2));
		
		Right= new Vector<Position>();
		Right.add(new Position(2,0));
		Right.add(new Position(1,0));
		Right.add(new Position(1,1));
		Right.add(new Position(1,2));
		
		Under = new Vector<Position>();
		Under.add(new Position(0,1));
		Under.add(new Position(0,2));
		Under.add(new Position(1,2));
		Under.add(new Position(2,2));
		
		Left= new Vector<Position>();
		Left.add(new Position(2,0));
		Left.add(new Position(2,1));
		Left.add(new Position(2,2));
		Left.add(new Position(1,2));
		
		/*
		 * Tetronimo starts out in normal configuration
		 */
		normal();
		
	}
	
	@Override
	public Vector<Position> getCurrentPos() {
		// TODO Auto-generated method stub
		return Current;
	}
	
	@Override
	public Position getMaxExtent() {
		// TODO Auto-generated method stub
		return new Position(maxLower, maxRight);
	}
	

	@Override
	public void rotate() {
		if( Current == Normal)
			right();
		else if( Current == Right)
			under();
		else if( Current == Under)
			left();
		else if( Current == Left)
			normal();
	}

	@Override
	void normal() {
		Current = Normal;
		maxLower = 2;
		maxRight = 2;
		
		startRow = 0;
		startCol = 1;
		
	}

	
	@Override
	void right() {
		Current = Right;
		maxLower = 2;
		maxRight = 2;
		
		startRow = 1;
		startCol = 0;
	}

	@Override
	void under() {
		// TODO Auto-generated method stub
		Current = Under;
		maxLower = 2;
		maxRight = 2;
		
		startRow = 0;
		startCol = 1;
	}

	@Override
	void left() {
		// TODO Auto-generated method stub
		Current = Left;
		maxLower = 2;
		maxRight = 2;
		
		startRow = 1;
		startCol = 0;
		
	}

	@Override
	public Position getStartPos() {
		// TODO Auto-generated method stub
		return new Position(startRow, startCol);
	}

}

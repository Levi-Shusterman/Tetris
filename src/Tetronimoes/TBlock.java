package Tetronimoes;

import java.util.Vector;

public class TBlock extends Tetronimo{
	
	TBlock(){
		Normal = new Vector<Position>();
		Normal.add(new Position(1,0));
		Normal.add(new Position(1,1));
		Normal.add(new Position(0,1));
		Normal.add(new Position(1,2));
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void normal() {
		Current = Normal;
		maxLower = 1;
		maxRight = 2;
		
	}

	
	@Override
	void right() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void under() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void left() {
		// TODO Auto-generated method stub
		
	}

	
	
}


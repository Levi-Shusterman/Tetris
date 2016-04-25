package Tetronimoes;

import java.util.Random;

public class TetronimoFactory {
	Random rand;
	
	public TetronimoFactory(){
		rand = new Random(System.currentTimeMillis());
	}
	
	public Tetronimo getNewTetronimo() throws Exception{
		
		return new TBlock();
//		switch (rand.nextInt(3)){
//		case 0:
//			return new TBlock();
//		case 1:
//			return new SBlock();
//		case 2:
//			return new LBlock();
//		}
		//throw new Exception("Case statement exited without case");
		
	}
}

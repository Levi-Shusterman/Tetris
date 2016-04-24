package Tetronimoes;

public class TetronimoFactory {
	
	public TetronimoFactory(){
		
	}
	
	public Tetronimo getNewTetronimo(){
		return new TBlock();
		
	}
}

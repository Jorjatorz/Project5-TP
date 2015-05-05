package tp.pr5.control;

import java.util.Random;

import tp.pr5.logic.Board;
import tp.pr5.logic.ComplicaMove;
import tp.pr5.logic.Counter;
import tp.pr5.logic.Move;

public class RandomComplicaPlayer implements Player{
	
	public Move getMove(Board board, Counter colour)
	{
		//Easy function, just choose a random column (logic will make space if the column is full)
		Random rnd = new Random();
		int col = rnd.nextInt(board.getWidth()) + 1; //Generate a number between 1 and width
		
		return new ComplicaMove(col, colour);

	}

}

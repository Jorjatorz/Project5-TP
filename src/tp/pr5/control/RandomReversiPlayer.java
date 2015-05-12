package tp.pr5.control;

import java.util.Random;

import tp.pr5.logic.Board;
import tp.pr5.logic.Counter;
import tp.pr5.logic.GravityMove;
import tp.pr5.logic.Move;
import tp.pr5.logic.ReversiMove;

public class RandomReversiPlayer implements Player {

	@Override
	public Move getMove(Board board, Counter colour) {
		
		Random rnd = new Random();
		int col = rnd.nextInt(board.getWidth()) + 1; //Generate a number between 1 and width
		int row = rnd.nextInt(board.getHeight()) + 1; //Generate a number between 1 and height
		
		//Generate a new random position until it finds an empty position
		while(!ReversiMove.moveValid(board, colour, col, row))
		{
			col = rnd.nextInt(board.getWidth()) + 1;
			row = rnd.nextInt(board.getHeight()) + 1;
		}
		
		return new ReversiMove(col, row, colour);
	}

}

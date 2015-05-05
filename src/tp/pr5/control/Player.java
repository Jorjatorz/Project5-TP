package tp.pr5.control;

import tp.pr5.logic.Board;
import tp.pr5.logic.Counter;
import tp.pr5.logic.Move;

public interface Player {
	
	public Move getMove(Board board, Counter colour);

}

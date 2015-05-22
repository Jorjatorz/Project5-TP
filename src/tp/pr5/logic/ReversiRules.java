package tp.pr5.logic;

import tp.pr5.logic.Board;
import tp.pr5.logic.Counter;

public class ReversiRules implements GameRules {

	private final Counter mStartPlayer;
	private final int mBoardHeight, mBoardWidth;
	
	public ReversiRules()
	{
		mStartPlayer = Counter.BLACK;
		mBoardWidth = 8;
		mBoardHeight = 8;
	}
	
	@Override
	public Counter initialPlayer() {
		return mStartPlayer;
	}

	@Override
	public boolean isDraw(Counter lastPlayer, Board board) {
		//Loop that counts the number of counters of each color
		int blacks = 0, whites = 0;
		for(int i = 1; i < board.getHeight(); i++)
		{
			for(int j = 1; j < board.getWidth(); j++)
			{
				Counter color = board.getPosition(j, i);
				
				if(color == Counter.WHITE)
				{
					whites++;
				}
				else if(color == Counter.BLACK)
				{
					blacks++;
				}
			}
		}
		
		//Check if the number of whites is equal to the number of blacks and if there is not available moves for the current counter
		if(whites == blacks && !ReversiMove.moveAvailable(board, Counter.swap(lastPlayer)))
		{
			return true;
		}
		
		
		return false;
	}

	@Override
	public Board newBoard() {
		Board board = new Board(mBoardWidth, mBoardHeight);
		
		//Set the initial counters
		board.setPosition(4, 4, Counter.WHITE);
		board.setPosition(5, 5, Counter.WHITE);
		board.setPosition(5, 4, Counter.BLACK);
		board.setPosition(4, 5, Counter.BLACK);
		
		return board;
	}

	@Override
	public Counter nextTurn(Counter lastPlayer, Board board) {
		return Counter.swap(lastPlayer);
	}

	@Override
	public Counter winningMove(Move lastMove, Board board) {
		
		int blacks = 0, whites = 0;
		for(int i = 1; i <= board.getHeight(); i++)
		{
			for(int j = 1; j <= board.getWidth(); j++)
			{
				Counter color = board.getPosition(j, i);
				
				if(color == Counter.WHITE)
				{
					whites++;
				}
				else if(color == Counter.BLACK)
				{
					blacks++;
				}
			}
		}
		
		if(!ReversiMove.moveAvailable(board, Counter.swap(lastMove.getPlayer())) && board.isFull())
		{
			if(blacks > whites)
			{
				return Counter.BLACK;
			}
			else if(whites > blacks)
			{
				return Counter.WHITE;
			}
		}
		
		
		return Counter.EMPTY;
	}
	}
	

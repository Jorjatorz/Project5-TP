package tp.pr4.logic;

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
		
		if(whites == blacks)
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Counter winningMove(Move lastMove, Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}

package tp.pr4.logic;

public class GravityRules implements GameRules{
	
	private final Counter mStartPlayer;
	private final int mBoardHeight, mBoardWidth;
	
	
	public GravityRules()
	{
		mStartPlayer = Counter.WHITE;
		mBoardWidth = 10;
		mBoardHeight = 10;
	}
	
	public GravityRules(int width, int height)
	{
		mStartPlayer = Counter.WHITE;
		mBoardWidth = width;
		mBoardHeight = height;
	}
	
	public Counter initialPlayer()
	{
		return mStartPlayer;
	}
	public boolean isDraw(Counter lastPlayer, Board board)
	{
		return board.isFull();
	}
	public Board newBoard()
	{
		return new Board(mBoardWidth, mBoardHeight);
	}
	public Counter nextTurn(Counter lastPlayer, Board board)
	{
		return Counter.swap(lastPlayer); 
	}
	public Counter winningMove(Move lastMove, Board board)
	{
		Counter winner = Counter.EMPTY;
		//If we have a winner save it and return it
		if(board.checkForWinner())
		{
			winner = lastMove.getPlayer();
		}
		
		return winner;
	}

}

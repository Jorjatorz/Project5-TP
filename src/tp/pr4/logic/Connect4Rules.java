package tp.pr4.logic;

public class Connect4Rules implements GameRules {
	
	private final Counter mStartPlayer;
	private final int mBoardHeight, mBoardWidth;
	
	public Connect4Rules()
	{
		mStartPlayer = Counter.WHITE;
		mBoardWidth = 7;
		mBoardHeight = 6;
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
		if(board.checkForWinner())
		{
			winner = lastMove.getPlayer();
		}
		
		return winner;
	}
	
	
}

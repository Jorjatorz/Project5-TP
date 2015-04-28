package tp.pr4.logic;

public class ComplicaRules implements GameRules {

	private final Counter mStartPlayer;
	private final int mBoardHeight, mBoardWidth;
	
	public ComplicaRules()
	{
		mStartPlayer = Counter.WHITE;
		mBoardWidth = 4;
		mBoardHeight = 7;
	}
	
	public Counter initialPlayer()
	{
		return mStartPlayer;
	}
	public boolean isDraw(Counter lastPlayer, Board board)
	{
		return false; //Can't be a draw
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
		if(board.checkForWinner()) //Check if we have a winner
		{
			winner = lastMove.getPlayer(); //Save the winner
		}
		
		return winner;
	}
	
}

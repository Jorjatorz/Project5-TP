package tp.pr5.logic;

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
		if(checkForWinner(board))
		{
			winner = lastMove.getPlayer();
		}
		
		return winner;
	}
	
	//Function that checks if there is for in a row (Horizontally, vertically and diagonally)
		private boolean checkForWinner(Board mBoard) {
			boolean winner = false;
			
			//Horizontal
			for(int i = mBoard.getHeight(); i > 0; --i)
			{
				for(int j = mBoard.getWidth(); j > 0; --j)
				{
					if(mBoard.getPosition(j, i) != Counter.EMPTY)
					{
						if((i - 3 > 0) && (mBoard.getPosition(j, i) == mBoard.getPosition(j, i - 1))) //i - 3 >= 0 means that we check if from that position we can have 4 in a row (without going outside the board)(Can be done in the for loop for performance boost, but the for loop will get ugly)
						{
							if(mBoard.getPosition(j, i - 1) == mBoard.getPosition(j, i - 2))
							{
								if(mBoard.getPosition(j, i - 2) == mBoard.getPosition(j, i - 3))
								{
									winner = true;
								}
							}
						}
					}
				}
			}
			
			//Vertical
			for(int i = mBoard.getHeight(); i > 0; --i)
			{
				for(int j = mBoard.getWidth(); j > 0; --j)
				{
					if(mBoard.getPosition(j, i) != Counter.EMPTY)
					{
						if((j - 3 > 0) && (mBoard.getPosition(j, i) == mBoard.getPosition(j - 1, i))) //i - 3 >= 0 means that we check if from that position we can have 4 in a row (without going outside the board) (Can be done in the for loop for performance boost, but the for loop will get ugly)
						{
							if(mBoard.getPosition(j - 1, i) == mBoard.getPosition(j - 2, i))
							{
								if(mBoard.getPosition(j - 2, i) == mBoard.getPosition(j - 3, i))
								{
									winner = true;
								}
							}
						}
					}
				}
			}
			
			//Diagonal bottom-left to top-right
			for(int i = mBoard.getHeight(); i > 0; --i)
			{
				for(int j = 1; j <= mBoard.getWidth(); j++)
				{
					if(mBoard.getPosition(j, i) != Counter.EMPTY)
					{
						if ((j + 3 <= mBoard.getWidth()) && (i - 3 > 0) && (mBoard.getPosition(j, i) ==mBoard.getPosition(j + 1, i - 1))){ //We check if we can have 3 more in y axis and 3 more in X axis (Can be done in the for loop for performance boost, but the for loop will get ugly) and if true we start comparing in a diagonal
							if (mBoard.getPosition(j + 1, i - 1) == mBoard.getPosition(j + 2, i - 2)){
								if (mBoard.getPosition(j + 2, i - 2) == mBoard.getPosition(j + 3, i + 3)){
										winner = true;
									}
								}
							}
						}
					}
				}
					
					
					
			//Diagonal bottom-right to top-left
			for(int i = mBoard.getHeight(); i > 0; --i)
			{
				for(int j = mBoard.getWidth(); j > 0; --j)
				{
					if(mBoard.getPosition(j, i) != Counter.EMPTY)
					{
						if ((j-3 > 0) && (i-3 > 0) && (mBoard.getPosition(j, i)==mBoard.getPosition(j - 1, i - 1))){
							if (mBoard.getPosition(j - 1, i-1)==mBoard.getPosition(j - 2, i - 2)){
								if (mBoard.getPosition(j - 2, i - 2)==mBoard.getPosition(j - 3, i - 3)){
										winner = true;
									}
								}
							
							}
						}
					}
				}
					
					
					
			return winner;
		}

}

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
		return Counter.swap(lastPlayer);
	}

	@Override
	public Counter winningMove(Move lastMove, Board board) {
		return Counter.EMPTY;
	}
	
	//Function that checks if there is for in a row (Horizontally, vertically and diagonally)
		private boolean checkForWinner(Board mBoard) {
			boolean winner = false;
			
			//It can be up to 2 winners, if we have 2 winners at the same time there is no winning move
			Counter winner1;
			winner1 = Counter.EMPTY;
			
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
									
									//If we have the first winner save it
									if(winner1 == Counter.EMPTY)
									{
										winner1 = mBoard.getPosition(j, i);
									}
									else
									{
										//Else we can have two winners and there is no winner
										if(winner1 != mBoard.getPosition(j, i))
										{
											return false;
										}
									}
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
									
									//If we have the first winner save it
									if(winner1 == Counter.EMPTY)
									{
										winner1 = mBoard.getPosition(j, i);
									}
									else
									{
										//Else we can have two winners and there is no winner
										if(winner1 != mBoard.getPosition(j, i))
										{
											return false;
										}
									}
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
										
										//If we have the first winner save it
										if(winner1 == Counter.EMPTY)
										{
											winner1 = mBoard.getPosition(j, i);
										}
										else
										{
											//We can have two winners and that means there is no winner
											if(winner1 != mBoard.getPosition(j, i))
											{
												return false;
											}
										}
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
										
										//If we have the first winner save it
										if(winner1 == Counter.EMPTY)
										{
											winner1 = mBoard.getPosition(j, i);
										}
										else
										{
											//Else we can have two winners and there is no winner
											if(winner1 != mBoard.getPosition(j, i))
											{
												return false;
											}
										}
									}
								}
							
							}
						}
					}
				}
					
					
					
			return winner;
		}

}

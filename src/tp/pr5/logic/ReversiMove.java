package tp.pr5.logic;

public class ReversiMove extends Move {
	
	private int mRow;
	
	public ReversiMove(int col, int row, Counter moveCounter)
	{
		mColumn = col;
		mRow = row;
		mColour = moveCounter;
	}

	@Override
	public void executeMove(Board board) throws InvalidMove {
		System.out.println("Execute move");
		applyMoveTransformations(board);
		System.out.println("Execute move ended");
	}

	@Override
	public void undo(Board board) {
		// TODO Auto-generated method stub

	}
	
	public static boolean moveAvailable(Board board, Counter currentPlayer)
	{
		
		System.out.println("Checking for move available");
		boolean toReturn = false;
		
		for(int row = 1; row <= board.getHeight(); row++)
		{
			for(int col = 1; col <= board.getWidth(); col++)
			{
				if(board.getPosition(col, row) == Counter.EMPTY)
				{
					//Look upwards
					Counter toLook = Counter.EMPTY;
					int i = row - 1;
					int j = col;
					if( i > 0 && board.getPosition(j, i) != currentPlayer)
					{
						while(i > 0 && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i--;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look downwards
					toLook = Counter.EMPTY;
					i = row + 1;
					j = col;
					if( i <= board.getHeight() && board.getPosition(j, i) != currentPlayer)
					{
						while(i <= board.getHeight() && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i++;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look right
					toLook = Counter.EMPTY;
					i = row;
					j = col + 1;
					if( j <= board.getWidth() && board.getPosition(j, i) != currentPlayer)
					{
						while(j <= board.getWidth() && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							j++;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look left
					toLook = Counter.EMPTY;
					i = row;
					j = col - 1;
					if( j > 0 && board.getPosition(j, i) != currentPlayer)
					{
						while(j > 0 && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							j--;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look up right diagonal
					toLook = Counter.EMPTY;
					i = row - 1;
					j = col + 1;
					if( i > 0 && j <= board.getWidth() && board.getPosition(j, i) != currentPlayer)
					{
						while(i > 0 && j <= board.getWidth() && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i--;
							j++;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look up left diagonal
					toLook = Counter.EMPTY;
					i = row - 1;
					j = col - 1;
					if( i > 0 && j > 0 && board.getPosition(j, i) != currentPlayer)
					{
						while(i > 0 && j > 0 && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i--;
							j--;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look down right diagonal
					toLook = Counter.EMPTY;
					i = row + 1;
					j = col + 1;
					if( i <= board.getHeight() && j <= board.getWidth() && board.getPosition(j, i) != currentPlayer)
					{
						while(i <= board.getHeight() && j <= board.getWidth() && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i++;
							j++;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
					//Look down left diagonal
					toLook = Counter.EMPTY;
					i = row + 1;
					j = col - 1;
					if( i <= board.getHeight() && j > 0 && board.getPosition(j, i) != currentPlayer)
					{
						while(i <= board.getHeight() && j > 0 && toLook != currentPlayer)
						{
							toLook = board.getPosition(j,  i);
							i++;
							j--;
							
							if(toLook == currentPlayer){
								toReturn = true;
							}
						}
					}
				}
			}
		}
		
		System.out.println("Checking for move available Ended");
		
		return toReturn;
	}

	private boolean applyMoveTransformations(Board board) throws InvalidMove
	{
		boolean toReturn = false;
		
		if (mColumn < 1 || mColumn > board.getWidth()) {
			throw new InvalidMove("Invalid move: column number " + mColumn + " is not on the board.");
		} else {
			boolean swapsAvailable = false;
			//Look upwards			
			Counter toLook = Counter.EMPTY;
			int i = mRow - 1;
			int j = mColumn;
			if( i > 0 && board.getPosition(j, i) != mColour)
			{
				while(i > 0 && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i--;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow - 1;
					j = mColumn;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i--;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look downwards
			toLook = Counter.EMPTY;
			i = mRow + 1;
			j = mColumn;
			if( i <= board.getHeight() && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(i <= board.getHeight() && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i++;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);

					i = mRow + 1;
					j = mColumn;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i++;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look right
			toLook = Counter.EMPTY;
			i = mRow;
			j = mColumn + 1;
			if( j <= board.getWidth() && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(j <= board.getWidth() && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					j++;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow;
					j = mColumn + 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						j++;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look left
			toLook = Counter.EMPTY;
			i = mRow;
			j = mColumn - 1;
			if( j > 0 && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(j > 0 && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					j--;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow;
					j = mColumn - 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						j--;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look up right diagonal
			toLook = Counter.EMPTY;
			i = mRow - 1;
			j = mColumn + 1;
			if( i > 0 && j <= board.getWidth() && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(i > 0 && j <= board.getWidth() && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i--;
					j++;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow - 1;
					j = mColumn + 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i--;
						j++;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look up left diagonal
			toLook = Counter.EMPTY;
			i = mRow - 1;
			j = mColumn - 1;
			if( i > 0 && j > 0 && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(i > 0 && j > 0 && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i--;
					j--;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow - 1;
					j = mColumn - 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i--;
						j--;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look down right diagonal
			toLook = Counter.EMPTY;
			i = mRow + 1;
			j = mColumn + 1;
			if( i <= board.getHeight() && j <= board.getWidth() && board.getPosition(j, i) != mColour)
			{
				swapsAvailable = false;
				while(i <= board.getHeight() && j <= board.getWidth() && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i++;
					j++;
					
					if(toLook == mColour){
						toReturn = true;
						swapsAvailable = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow + 1;
					j = mColumn + 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i++;
						j++;
						toLook = board.getPosition(j,  i);
					}
				}
			}
			//Look down left diagonal
			//Look down left diagonal
			toLook = Counter.EMPTY;
			i = mRow + 1;
			j = mColumn - 1;
			if( i <= board.getHeight() && j > 0 && board.getPosition(j, i) != mColour)
			{
				while(i <= board.getHeight() && j > 0 && toLook != mColour)
				{
					toLook = board.getPosition(j,  i);
					i++;
					j--;
					
					if(toLook == mColour){
						toReturn = true;
					}
				}
				
				if(swapsAvailable)
				{
					board.setPosition(mColumn, mRow, mColour);
					
					i = mRow + 1;
					j = mColumn - 1;
					toLook = board.getPosition(j,i);
					while(toLook != mColour)
					{
						board.setPosition(j, i, Counter.swap(toLook));
						i++;
						j--;
						toLook = board.getPosition(j,  i);
					}
				}
			}
		}
		
		return toReturn;
	}
}

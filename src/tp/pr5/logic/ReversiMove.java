package tp.pr5.logic;

import java.util.Stack;

public class ReversiMove extends Move {
	
	private int mRow;
	private Stack<UndoCounter> modifiedCountersStack;
	
	public ReversiMove(int col, int row, Counter moveCounter)
	{
		mColumn = col;
		mRow = row;
		mColour = moveCounter;
		
		modifiedCountersStack = new Stack<UndoCounter>();
	}

	@Override
	public void executeMove(Board board) throws InvalidMove {
		//If the move is invalid throw exception
		if(!applyMoveTransformations(board))
		{
			throw new InvalidMove("Counter needs to be in the same line as other of the same type and flip, at least, one of the other color");
		};
	}

	@Override
	public void undo(Board board)
	{
		while(!modifiedCountersStack.empty())
		{
			UndoCounter uc = modifiedCountersStack.pop();			
			board.setPosition(uc.getColumn(), uc.getRow(), uc.getColour());
		}
		
		board.setPosition(mColumn, mRow, Counter.EMPTY);
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

	public static boolean moveValid(Board board, Counter currentPlayer, int col, int row)
	{
		if(board.getPosition(col, row) != Counter.EMPTY)
		{
			return false;
		}
		
		boolean up = upMoveAvailability(board, col, row, currentPlayer);
		boolean down = downMoveAvailability(board, col, row, currentPlayer);
		boolean right = rightMoveAvailability(board, col, row, currentPlayer);
		boolean left = leftMoveAvailability(board, col, row, currentPlayer);
		boolean upRD = upRightDiagonalMoveAvailability(board, col, row, currentPlayer);
		boolean upLD = upLeftDiagonalMoveAvailability(board, col, row, currentPlayer);
		boolean downRD = downRightDiagonalMoveAvailability(board, col, row, currentPlayer);
		boolean downLD = downLeftDiagonalMoveAvailability(board, col, row, currentPlayer);		
		
		if(up || down || right || left || upRD || upLD || downRD || downLD)
		{
			return true;
		}
		
		return false;
	}
	
	private boolean applyMoveTransformations(Board board) throws InvalidMove
	{		
		if (mColumn < 1 || mColumn > board.getWidth()) {
			throw new InvalidMove("Invalid move: column number " + mColumn + " is not on the board.");
		} else {
			
			System.out.println("up move");
			boolean up = upMoveTransformations(board);
			System.out.println("down move");
			boolean down = downMoveTransformations(board);
			System.out.println("right move");
			boolean right = rightMoveTransformations(board);
			System.out.println("left move");
			boolean left = leftMoveTransformations(board);
			System.out.println("upRD move");
			boolean upRD = upRightDiagonalMoveTransformations(board);
			System.out.println("upLD move");
			boolean upLD = upLeftDiagonalMoveTransformations(board);
			System.out.println("downRD move");
			boolean downRD = downRightDiagonalMoveTransformations(board);
			System.out.println("downLD move");
			boolean downLD = downLeftDiagonalMoveTransformations(board);		
			
			if(up || down || right || left || upRD || upLD || downRD || downLD)
			{
				return true;
			}
		}
		
		return false;
	}

	private boolean upMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		//Look upwards			
		int i = mRow - 1;
		int j = mColumn;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && toLook != mColour)
		{
			while(i > 0 && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i--;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean downMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow + 1;
		int j = mColumn;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && toLook != mColour)
		{
			swapsAvailable = false;
			while(i <= board.getHeight() && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i++;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean rightMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
		if( j <= board.getWidth() && toLook != mColour)
		{
			swapsAvailable = false;
			while(j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					j++;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean leftMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);
		if( j > 0 && toLook != mColour)
		{
			swapsAvailable = false;
			while(j > 0 && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					j--;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean upRightDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow - 1;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && j <= board.getWidth() && toLook != mColour)
		{
			swapsAvailable = false;
			while(i > 0 && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i--;
					j++;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean upLeftDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow - 1;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && j > 0 && toLook != mColour)
		{
			swapsAvailable = false;
			while(i > 0 && j > 0 && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i--;
					j--;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean downRightDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		int i = mRow + 1;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && j <= board.getWidth() && toLook != mColour)
		{
			swapsAvailable = false;
			while(i <= board.getHeight() && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i++;
					j++;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean downLeftDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow + 1;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && j > 0 && toLook != mColour)
		{
			while(i <= board.getHeight() && j > 0 && (toLook != Counter.EMPTY) && (toLook != mColour))
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
					modifiedCountersStack.add(new UndoCounter(toLook, i, j));
					i++;
					j--;
					toLook = board.getPosition(j,  i);
				}
			}
		}
		
		return toReturn;
	}

	private static boolean upMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;
		//Look upwards			
		int i = row - 1;
		int j = col;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && toLook != player)
		{
			while(i > 0 && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i--;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean downMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row + 1;
		int j = col;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && toLook != player)
		{
			while(i <= board.getHeight() && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i++;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean rightMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row;
		int j = col + 1;
		Counter toLook = board.getPosition(j, i);
		if( j <= board.getWidth() && toLook != player)
		{
			while(j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				j++;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean leftMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row;
		int j = col - 1;
		Counter toLook = board.getPosition(j, i);
		if( j > 0 && toLook != player)
		{
			while(j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				j--;
				
				if(toLook == player){
					toReturn = true;
				}
			}
			
		}
		
		return toReturn;
	}
	
	private static boolean upRightDiagonalMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row - 1;
		int j = col + 1;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && j <= board.getWidth() && toLook != player)
		{
			while(i > 0 && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i--;
				j++;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean upLeftDiagonalMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row - 1;
		int j = col - 1;
		Counter toLook = board.getPosition(j, i);
		if( i > 0 && j > 0 && toLook != player)
		{
			while(i > 0 && j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i--;
				j--;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean downRightDiagonalMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row + 1;
		int j = col + 1;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && j <= board.getWidth() && toLook != player)
		{
			while(i <= board.getHeight() && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i++;
				j++;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private static boolean downLeftDiagonalMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;
		
		int i = row + 1;
		int j = col - 1;
		Counter toLook = board.getPosition(j, i);
		if( i <= board.getHeight() && j > 0 && toLook != player)
		{
			while(i <= board.getHeight() && j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
			{
				toLook = board.getPosition(j,  i);
				i++;
				j--;
				
				if(toLook == player){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	private class UndoCounter
	{
		private int row, col;
		private Counter colour;
		
		public UndoCounter(Counter colour, int row, int col)
		{
			this.colour = colour;
			this.row = row;
			this.col = col;
		}
		
		public Counter getColour()
		{
			return colour;
		}
		
		public int getRow()
		{
			return row;
		}
		
		public int getColumn()
		{
			return col;
		}
	}

	
}

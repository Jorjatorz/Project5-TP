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
		
		//Stack to keep track of the counters that were modified in this move
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
		//swap all the counters in the stack to return to the previous point before the move
		while(!modifiedCountersStack.empty())
		{
			UndoCounter uc = modifiedCountersStack.pop();			
			board.setPosition(uc.getColumn(), uc.getRow(), uc.getColour());
		}
		//Delete the addded counter
		board.setPosition(mColumn, mRow, Counter.EMPTY);
	}
	
	//Checks if there is a possible move to execute given a counter
	public static boolean moveAvailable(Board board, Counter currentPlayer)
	{
		boolean toReturn = false;
		
		//Check if there is at least one valid move in all the empty board positions for current player
		for(int row = 1; row <= board.getHeight(); row++)
		{
			for(int col = 1; col <= board.getWidth(); col++)
			{
				if(board.getPosition(col, row) == Counter.EMPTY)
				{
					if(moveValid(board, currentPlayer, col, row))
					{
						return true;
					}
				}
			}
		}
		
		return toReturn;
	}

	//Checks if the move to execute is valid (at least one swap)
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
	
	//Checks if the move to execute is valid (at least one swap) and if it is the counters are swap
	private boolean applyMoveTransformations(Board board) throws InvalidMove
	{		
		if (mColumn < 1 || mColumn > board.getWidth()) {
			throw new InvalidMove("Invalid move: column number " + mColumn + " is not on the board.");
		} else {
			boolean up = upMoveTransformations(board);
			boolean down = downMoveTransformations(board);
			boolean right = rightMoveTransformations(board);
			boolean left = leftMoveTransformations(board);
			boolean upRD = upRightDiagonalMoveTransformations(board);
			boolean upLD = upLeftDiagonalMoveTransformations(board);
			boolean downRD = downRightDiagonalMoveTransformations(board);
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
		
		int i = mRow - 1;
		int j = mColumn;
		Counter toLook = board.getPosition(j, i);

		//Look for correct move
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
			//Add the new counter
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
		
		return toReturn;
	}
	
	private boolean downMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow + 1;
		int j = mColumn;
		Counter toLook = board.getPosition(j, i);

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
		
		return toReturn;
	}
	
	private boolean rightMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
		
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
		
		return toReturn;
	}
	
	private boolean leftMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);
		
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
		
		return toReturn;
	}
	
	private boolean upRightDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow - 1;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
		
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
		
		return toReturn;
	}
	
	private boolean upLeftDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow - 1;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);

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
		
		return toReturn;
	}
	
	private boolean downRightDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow + 1;
		int j = mColumn + 1;
		Counter toLook = board.getPosition(j, i);
	
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
		
		
		return toReturn;
	}
	
	private boolean downLeftDiagonalMoveTransformations(Board board)
	{
		boolean toReturn = false;
		boolean swapsAvailable = false;
		
		int i = mRow + 1;
		int j = mColumn - 1;
		Counter toLook = board.getPosition(j, i);

		while(i <= board.getHeight() && j > 0 && (toLook != Counter.EMPTY) && (toLook != mColour))
		{
			toLook = board.getPosition(j,  i);
			i++;
			j--;
			
			if(toLook == mColour){
				toReturn = true;
				swapsAvailable = true;
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
		
		
		return toReturn;
	}

	private static boolean upMoveAvailability(Board board, int col, int row, Counter player)
	{
		boolean toReturn = false;

		int i = row - 1;
		int j = col;
		Counter toLook = board.getPosition(j, i);
		
		while(i > 0 && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i--;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(i <= board.getHeight() && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i++;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			j++;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			j--;
			
			if(toLook == player){
				toReturn = true;
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

		while(i > 0 && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i--;
			j++;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(i > 0 && j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i--;
			j--;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(i <= board.getHeight() && j <= board.getWidth() && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i++;
			j++;
			
			if(toLook == player){
				toReturn = true;
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
		
		while(i <= board.getHeight() && j > 0 && (toLook != Counter.EMPTY) && (toLook != player))
		{
			toLook = board.getPosition(j,  i);
			i++;
			j--;
			
			if(toLook == player){
				toReturn = true;
			}
		}
		
		
		return toReturn;
	}
	
	
	//Private class containing information about a counter which was swapped
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

package tp.pr5.logic;

public class Board implements ReadOnlyBoard {

	//Private variables
	private Counter[][] mBoard; //Game board, it will be mWidth x mHeight
	private int mWidth;
	private int mHeight;

	public Board(int width, int height) {
		mWidth = width;
		mHeight = height;
		
		//Invalid width - height. Make a default 1 x 1 board
		if((mWidth < 1) || (mHeight < 1))
		{
			mWidth = 1;
			mHeight = 1;
		}
		mBoard = new Counter[mHeight][mWidth];
		//Make the board empty
		resetBoard();
	}

	//Function to reset the board
	public void resetBoard() {
		//Traverse the whole board and make it empty
		for (int i = 1; i <= mHeight; ++i) {
			for (int j = 1; j <= mWidth; ++j) {
				setPosition(j, i, Counter.EMPTY);
			}
		}
	}

	//Function to print he board - Called by System.out.print...
	public String toString() {
		String sBoard = "";
		
		//Board body
		for (int i = 0; i < mHeight; ++i) {
			sBoard += "|";
			for (int j = 0; j < mWidth; ++j) {
				switch (mBoard[i][j]) {
				case EMPTY:
					sBoard += " ";
					break;
				case WHITE:
					sBoard += "O";
					break;
				case BLACK:
					sBoard += "X";
					break;
				}
			}
			sBoard += "|";
			sBoard += "\n";
		}

		//Board bottom border
		sBoard += "+";
		for (int i = 0; i < mWidth; ++i) {
			sBoard += "-";
		}
		sBoard += "+\n ";

		//Board column numbers
		for (int i = 1; i < mWidth + 1; ++i) {
			sBoard += i % 10; //Apply modulus to make number of just one digit
		}

		return sBoard;
	}
	

	//Sets the new counter in the desired position x is column, y is row (Range from 1 to N) - Where N is the width or height depending on the axis
	public void setPosition(int x, int y, Counter col) {
		if (x < 1 || x > mWidth || y < 1 || y > mHeight) {
				return; //Invalid coordinates, don't do anything
		}
		else
		{
			mBoard[y - 1][x - 1] = col;
		}
	}

	//Get the current pointer in a given position - x is column, y is row (Range from 1 to N), if the range is invalid it returns empty - Where N is the width or height depending on the axis
	public Counter getPosition(int col, int row) {
		if((col < 1) || (row < 1) || (col > mWidth) || (row > mHeight))
		{
			return Counter.EMPTY;
		}
		
		return mBoard[row - 1][col - 1];
	}

	//Return the curren width
	public int getWidth() {
		return mWidth;
	}

	//Return the current height
	public int getHeight() {
		return mHeight;
	}

	//Return the first empty position in that column, return 0 if the column is full or the column is invalid (This function should not be called if the column is invalid) (Range from 1 to N) - Where N is the width or height depending on the axis
	public int getLastRowFilledInColumn(int column) {
		
		if((column > 0) && (column < mWidth + 1))
		{
			//If the first element of the column is empty, return it
			if (mBoard[mHeight - 1][column - 1] == Counter.EMPTY) {
				return mHeight;
			} else {
				for (int i = 0; i < mHeight; ++i) {
					//Return the position of the empty row, as we are in a range from 1 - N returning i is lastRowWithCounter - 1
					if (mBoard[i][column - 1] != Counter.EMPTY) {
						return i; //From 0 to N
					}
				}
			}
		}

		return 0;
	}
	
	//Traverse the whole board and if it has no empty cells then its full and return true (You said yo dont care about performance so i use two for loops, easy)
	public boolean isFull()
	{
		boolean full = true;
		
		for (int i = 1; i <= mHeight; ++i) {//row
			for (int j = 1; j <= mWidth; ++j) {//Column
				if(getPosition(j, i) == Counter.EMPTY)
				{
					full = false;
					//break outsideFirstLoop; I'm not sure if you allow us to do this so i will just comment it. Even if full is false it will traverse the whole board. (Could use whiles and insert as condition boolean full but for-loops are more clean to traverse matrices)
				}
			}
		}
		
		return full;
	}
	
	//Traverse the column and return true if its full. (You said you dont care about performace so i use one for loop, easy solution)
	public boolean isColumnFull(int col)
	{
		boolean full = true;
		
		for (int i = 1; i <= mHeight; ++i) {//row
			if(getPosition(col, i) == Counter.EMPTY)
			{
				full = false;
			}
		}
		
		return full;
	}	
}

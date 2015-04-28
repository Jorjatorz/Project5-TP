package tp.pr4.logic;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void undo(Board board) {
		// TODO Auto-generated method stub

	}

}

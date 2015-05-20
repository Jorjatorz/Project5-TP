package tp.pr5.logic;

public enum Counter
{
	EMPTY("Empty"), WHITE("White"), BLACK("Black");
	
	private String name;
	private PlayerType mPlayerType = PlayerType.HUMAN;
	
	Counter(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	static public Counter swap(Counter  col)
	{
		if(col == WHITE)
		{
			col = BLACK;
		}
		else if(col == BLACK)
		{
			col = WHITE;
		}
		
		return col;
	}
	
	public PlayerType getPlayerType()
	{
		return mPlayerType;
	}
	
	public void setPlayerType(PlayerType newPlayerType)
	{
		mPlayerType = newPlayerType;
	}

}

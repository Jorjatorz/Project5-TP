package tp.pr5.control;

import java.util.Scanner;









import tp.pr5.logic.*;


public abstract class Controller {
	
	protected Controller()
	{
		mWhitePlayer = Counter.WHITE;
		mBlackPlayer = Counter.BLACK;
	}
	
	public abstract void run();
	
	protected Game mGame;
	//Game rules for the different games, also the rules that are currently being used
	protected GameTypeFactory mGameFactory;
	//TYpe of player in each color
	protected Counter mWhitePlayer, mBlackPlayer;
	
	protected  abstract void makeMove(int col, int row);

}

package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logic.Game;
import tp.pr5.views.window.MainWindow;

public class WindowController extends Controller {

	private volatile boolean isRunning = true;
	
	public WindowController(GameTypeFactory factory, Game g) {
		mGame = g;
		mGameFactory = factory;
	}

	@Override
	public void run() {
		//Create the view
		MainWindow view = new MainWindow(mGame, this);		
		
		//Empty loop
		while(isRunning)
		{
		}
	}
	
	//Called by the view.
	public void GUImakeMove(int col, int row)
	{
		mWhitePlayer = mGameFactory.createHumanPlayerAtGUI(col, row);
		mBlackPlayer = mGameFactory.createHumanPlayerAtGUI(col, row);
		
		makeMove();
	}
	//Called by the view.
	public void GUImakeUndo()
	{
		mGame.undo();
	}
	//Called by the view.
	public void GUImakeReset()
	{
		mGame.reset(mGameFactory.createRules());
	}
	//Called by the view.
	public void GUImakeRandomMove()
	{
		mWhitePlayer = mGameFactory.createRandomPlayer();
		mBlackPlayer = mGameFactory.createRandomPlayer();
		
		makeMove();
	}
	//Called by the view.
	public void GUImakeChangeGame(int game, int cols, int rows)
	{
		switch(game)
		{
			case 0: //c4
				mGameFactory = new Connect4Factory();
				break;
				
			case 1: //co
				mGameFactory = new ComplicaFactory();
				break;
				
			case 2: //Reversi
				mGameFactory = new ReversiFactory();
				break;
				
			case 3: //gr
				mGameFactory = new GravityFactory(cols, rows);
				break;
			default:
				mGameFactory = new Connect4Factory();
		}
		
		mGame.reset(mGameFactory.createRules());
		
	}
	//Called by the view.
	public void GUImakeExit()
	{
		isRunning = false;
	}

}

package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logic.Counter;
import tp.pr5.logic.Game;
import tp.pr5.logic.InvalidMove;
import tp.pr5.logic.Move;
import tp.pr5.logic.PlayerType;
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
		makeMove(col, row);
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
		/*mWhitePlayer = mGameFactory.createRandomPlayer().;
		mBlackPlayer = mGameFactory.createRandomPlayer();
		
		makeMove();*/
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

	public void GUIsetPlayerMode(Counter player, PlayerType selectedPlayerType) {
		// TODO Auto-generated method stub
		
	}
	
	private void stopAutoPlayer()
	{
		
	}
	
	private void automaticMove()
	{
		
	}

	@Override
	//TODO Make a makeRandomMove y automaticMove
	protected void makeMove(int col, int row) {
		Move newMove;
		
		if(mGame.getTurn() == Counter.WHITE)
		{
			if(mWhitePlayer.getPlayerType() == PlayerType.HUMAN)
				newMove = mGameFactory.createHumanPlayerAtGUI(col, row).getMove(mGame.getBoard(), mWhitePlayer);
			else
				newMove = mGameFactory.createRandomPlayer().getMove(mGame.getBoard(), mWhitePlayer);
		}
		else
		{
			if(mBlackPlayer.getPlayerType() == PlayerType.HUMAN)
				newMove = mGameFactory.createHumanPlayerAtGUI(col, row).getMove(mGame.getBoard(), mBlackPlayer);
			else
				newMove = mGameFactory.createRandomPlayer().getMove(mGame.getBoard(), mBlackPlayer);
		}
		
		try
		{
			mGame.executeMove(newMove);
			
		}catch(InvalidMove e)
		{
			mGame.moveErrorTriggered(e.getMessage());
		}

		
	}

}

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
	
	private Thread autoThread;
	
	public WindowController(GameTypeFactory factory, Game g) {
		mGame = g;
		mGameFactory = factory;
	}

	@Override
	public void run() {
		//Create the view
		MainWindow view = new MainWindow(mGame, this);		
		
		//Reset the game
		GUImakeReset();
	}
	
	//Called by the view.
	public void GUImakeMove(int col, int row)
	{		
		makeMove(col, row);
		automaticMove();
	}
	//Called by the view.
	public void GUImakeUndo()
	{
		mGame.undo();
		automaticMove();
	}
	//Called by the view.
	public void GUImakeReset()
	{
		stopAutoPlayer();
		mGame.reset(mGameFactory.createRules());
		automaticMove();
	}
	//Called by the view.
	public void GUImakeRandomMove()
	{
		makeRandomMove();
		automaticMove();
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
		
		GUImakeReset();
		
	}
	//Called by the view.
	public void GUImakeExit()
	{
		stopAutoPlayer();
		
		System.exit(0);
	}

	public void GUIsetPlayerMode(Counter player, PlayerType selectedPlayerType) {
		player.setPlayerType(selectedPlayerType);
		mGame.setPlayerType(player);
		
		automaticMove();
	}
	
	private void stopAutoPlayer()
	{
		if(autoThread != null)
		{
			autoThread.interrupt();
			
			try {
				autoThread.join();
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void automaticMove()
	{
		if(mGame.getTurn().getPlayerType() == PlayerType.HUMAN)
			return;
		
		if(autoThread != null)
		{
			stopAutoPlayer();
		}
		
		autoThread = new Thread()
				{
					@Override
					public void run()
					{
						while(!(mGame.getTurn().getPlayerType() == PlayerType.HUMAN) && !mGame.isFinished() && !Thread.interrupted())
						{
							try {
								this.sleep(500);								
								makeRandomMove();
							} catch (InterruptedException e) 
							{
								//Exit the loop
								break;
							}
						}
					}
				};
				
		autoThread.start();
	}

	@Override
	//TODO Make a makeRandomMove y automaticMove
	protected void makeMove(int col, int row) {
		Move newMove = null;
		
		if(mGame.getTurn().getPlayerType() == PlayerType.HUMAN)
			newMove = mGameFactory.createHumanPlayerAtGUI(col, row).getMove(mGame.getBoard(), mGame.getTurn());
		else
			newMove = mGameFactory.createHumanPlayerAtGUI(col, row).getMove(mGame.getBoard(), mGame.getTurn());
	
	
		if(newMove != null)
			mGame.executeMove(newMove);


		
	}
	
	private void makeRandomMove()
	{
		Move newMove;
		
		if(mGame.getTurn() == Counter.WHITE)
		{
			newMove = mGameFactory.createRandomPlayer().getMove(mGame.getBoard(), mWhitePlayer);
		}
		else
		{
			newMove = mGameFactory.createRandomPlayer().getMove(mGame.getBoard(), mBlackPlayer);
		}
		
		mGame.executeMove(newMove);
	}

}

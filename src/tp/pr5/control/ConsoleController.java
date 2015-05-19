package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logic.Counter;
import tp.pr5.logic.Game;
import tp.pr5.logic.InvalidMove;
import tp.pr5.logic.Move;
import tp.pr5.logic.PlayerType;
import tp.pr5.views.console.ConsoleView;

public class ConsoleController extends Controller {

	//Internal pointers to the game and scanner objects passed in the constructor

	private Scanner in;

	
	public ConsoleController(GameTypeFactory factory, Game g)
	{
		mGame = g;
		mGameFactory = factory;
		
		in = new Scanner(System.in);
		
		mWhitePlayer = Counter.WHITE;
		mBlackPlayer = Counter.BLACK; 
	}
	
	private void handleCommand(String command)
	{
		String originalCommand = command; //Save the original command for printing it if its invalid (see default switch part)
		
		//Make all the command with lower case.
		command = command.toLowerCase();
		//Make a string with only 1 space
		command = command.trim();
		
		//Get all the words separated by spaces
		String[] words = command.split(" ");
		
		switch(words[0])
		{
		case "player":
			if(!handlePlayerEvent(words))
			{
				System.err.println(originalCommand + ": command not understood, please try again");
			}
			break;
		case "play":
			if(!handlePlayEvent(words))
			{
				System.err.println(originalCommand + ": command not understood, please try again");
			}
			break;
		case "make": //Make a move in the current game
			if(words.length == 3)
			{
				if(words[1].equals("a") && words[2].equals("move"))
				{
					makeMove(-1, -1);
				}
				else
				{
					System.err.println(originalCommand + ": command not understood, please try again");
				}
			}
			break;
		case "undo":
			mGame.undo();
			break;
		case "restart":
			mGame.reset(mGameFactory.createRules()); //Reset the game according to the current rules
			break;
		case "help":
			displayCommandsHelp();
			break;
		case "exit":
			mGame.setFinished(true);
			System.out.println("Exit requested.");
			break;
		default: //Invalid command
			System.err.println(originalCommand + ": command not understood, please try again");
			break;
		}
	}
	
	//Handles the input from the user when he introduce the command player
	private boolean handlePlayerEvent(String[] words)
	{
		boolean valid = false;
		
		//If the command is valid (can't have less/more than 3 words)
		if(words.length == 3)
		{
			//Handle cases
			switch(words[1])
			{
			case "white":
				//Handle second cases
				switch(words[2])
				{
				case "human":
					mWhitePlayer.setPlayerType(PlayerType.HUMAN);
					valid = true;
					break;
				case "random":
					mWhitePlayer.setPlayerType(PlayerType.AUTO);
					valid = true;
					break;						
				}
				
			break;
			case "black":
				//Handle second cases
				switch(words[2])
				{
				case "human":
					mBlackPlayer.setPlayerType(PlayerType.HUMAN);
					valid = true;
					break;
				case "random":
					mBlackPlayer.setPlayerType(PlayerType.AUTO);
					valid = true;
					break;						
				}
				
			break;	
			}
		}
		
		return valid;
	}

	//Handles the input from the user when he introduce the command play
	private boolean handlePlayEvent(String[] words)
	{
		boolean valid = false;
		
		if(words.length > 1)
		{
			switch(words[1])
			{
				case "c4":
					mGameFactory = new Connect4Factory();
					mWhitePlayer = Counter.WHITE;
					mBlackPlayer = Counter.BLACK;
					mGame.reset(mGameFactory.createRules());
					valid = true;
					break;
					
				case "co":
					mGameFactory = new ComplicaFactory();
					mWhitePlayer = Counter.WHITE;
					mBlackPlayer = Counter.BLACK;
					mGame.reset(mGameFactory.createRules());
					valid = true;
					break;
					
				case "rv":
					mGameFactory = new ReversiFactory();
					mWhitePlayer = Counter.WHITE;
					mBlackPlayer = Counter.BLACK;
					mGame.reset(mGameFactory.createRules());
					valid = true;
					break;
					
				case "gr":
					if(words.length == 4)
					{
						int width = Integer.parseInt(words[2]);
						int height = Integer.parseInt(words[3]);
						
						//If the w/h is correct create it normally
						if(height > 0 && width > 0)
						{
							mGameFactory = new GravityFactory(width, height);
							mWhitePlayer = Counter.WHITE;
							mBlackPlayer = Counter.BLACK;
							mGame.reset(mGameFactory.createRules());
							valid = true;
						}
						else
						{
							//If not generate a  1 x 1 board
							mGameFactory = new GravityFactory(1, 1);
							mWhitePlayer = Counter.WHITE;
							mBlackPlayer = Counter.BLACK;
							mGame.reset(mGameFactory.createRules());
							valid = true;
						}
					}
					break;
			}
		}
		
		return valid;
	}
	
	private void displayCommandsHelp()
	{
		System.out.println("The available commands are:");
		System.out.println();
		System.out.println("MAKE A MOVE: place a counter on the board.");
		System.out.println("UNDO: undo the last move of the game.");
		System.out.println("RESTART: restart the game.");
		System.out.println("PLAY [c4|co|gr] [dimX dimY]: change the type of game.");
		System.out.println("PLAYER [white|black] [human|random]: change the type of player.");
		System.out.println("EXIT: exit the application.");
		System.out.println("HELP: show this help.");
		System.out.println();
	}
	
	public void showMenu(Counter turn)
	{
		System.out.print("Please enter a command: ");
	}
	
	public void run()
	{
		ConsoleView mView = new ConsoleView(mGame, this);
		
		//Run until the game is finished
		while(!mGame.isFinished())
		{			
			//Show the menu
			showMenu(mGame.getTurn());
			
			 //Handle the user input
			String command = in.nextLine();
			handleCommand(command);
		}
	}

	@Override
	protected void makeMove(int col, int row)
	{
		Move newMove;
		
		if(mGame.getTurn() == Counter.WHITE)
		{
			if(mWhitePlayer.getPlayerType() == PlayerType.HUMAN)
				newMove = mGameFactory.createHumanPlayerAtConsole(in).getMove(mGame.getBoard(), mWhitePlayer);
			else
				newMove = mGameFactory.createRandomPlayer().getMove(mGame.getBoard(), mWhitePlayer);
		}
		else
		{
			if(mBlackPlayer.getPlayerType() == PlayerType.HUMAN)
				newMove = mGameFactory.createHumanPlayerAtConsole(in).getMove(mGame.getBoard(), mBlackPlayer);
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

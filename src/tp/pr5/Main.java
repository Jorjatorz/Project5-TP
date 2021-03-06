package tp.pr5;

import tp.pr5.control.*;
import tp.pr5.logic.*;

public class Main {
	
	//Set to true if user asked for help by argument
	static private boolean helpDisplayed;
	
	public static void main(String[] args) {
		Controller control;
		helpDisplayed = false;
		
		Game g = null;
		
		if(args.length != 0)
		{
			//Create the controller
			control = handleCommandsParameters(args);
		}
		else
		{
			//Default
			GameTypeFactory c4Fact = new Connect4Factory();
			g = new Game(c4Fact.createRules());
			control = new WindowController(c4Fact, g);
		}
		
		//Run the game until the user exit or the game finish
		if(control != null)
		{
			control.run();
	
		}
		else
		{
			//If we have displayed the help it is not an error (but control is null) so handle it
			if(helpDisplayed)
			{
				System.exit(0);
			}
			else
			{
				//We have an exception so return 1
				System.exit(1);
			}
		}
	}
	
	static private Controller handleCommandsParameters(String[] args)
	{
		//Default modes
		int gameToPlay = 0; //0 - c4, 1 - c0 2 - gr
		int cols = 10, rows = 10;
		boolean console = true;
		Controller toReturnController = null;
		Counter whitePlayer = Counter.WHITE, blackPlayer = Counter.BLACK;
		
		//Handle all the commands
		boolean correct = true;
		int i = 0;
		while(i < args.length && correct)
		{
			//Two basic commands
			switch(args[i])
			{
			case "-g":
			case "--game":
				if(args.length > i + 1)
				{
					gameToPlay = getGameFromParameter(args, i + 1);

					//If an error occured
					if(gameToPlay == -1)
					{
						System.err.println("Invalid command: " + args[i + 1] + " is not a valid game!!");
						correct = false;
					}
					
					i = i + 2;
				}
				else
				{
					System.err.println("Incomplete command: Need a game type!!");
					correct = false;
				}
				break;
			case "-x":
			case "--dimX":
				if(args.length > i + 1)
				{
					try
					{
						cols = Integer.parseInt(args[i + 1]);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Invalid command: Need a column number!!");
						correct = false;
					}
					
					i = i + 2;
				}
				else
				{
					System.err.println("Incomplete command: Need a column number!!");
					correct = false;
				}
				break;
			case "-y":
			case "--dimY":
				if(args.length > i + 1)
				{
					try
					{
						rows = Integer.parseInt(args[i + 1]);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Invalid command: Need a row number!!");
						correct = false;
					}
				}
				else
				{
					System.err.println("Incomplete command: Need a row number!!");
					correct = false;
				}
				break;
			case "--black":
				if(args.length > i + 1)
				{
					if(args[i + 1].equals("auto"))
					{
						blackPlayer.setPlayerType(PlayerType.AUTO);
						i = i + 2;
					}
					else if(args[i + 1].equals("human"))
					{
						blackPlayer.setPlayerType(PlayerType.HUMAN);
						i = i + 2;
					}
					else
					{
						System.err.println("Incomplete command: Need a valid player type!!");
						correct = false;
					}
				}
				else
				{
					System.err.println("Incomplete command: Need a player type!!");
					correct = false;
				}
				break;
			case "--white":
				if(args.length > i + 1)
				{
					if(args[i + 1].equals("auto"))
					{
						whitePlayer.setPlayerType(PlayerType.AUTO);
						i = i + 2;
					}
					else if(args[i + 1].equals("human"))
					{
						whitePlayer.setPlayerType(PlayerType.HUMAN);
						i = i + 2;
					}
					else
					{
						System.err.println("Incomplete command: Need a valid player type!!");
						correct = false;
					}
				}
				else
				{
					System.err.println("Incomplete command: Need a player type!!");
					correct = false;
				}
				break;
			case "-u":
			case "--ui":
				if(args.length > i + 1)
				{
					if(args[i + 1].equals("console"))
					{
						console = true;
					}
					else if(args[i + 1].equals("window"))
					{
						console = false;
					}
					else
					{
						displayErrorInCommand("Incorrect use: Unrecognized option: ", args, i);
						correct = false;
					}
					i = i + 2;
				}
				else
				{
					System.err.println("Incomplete command: Need a type!!");
					correct = false;
				}
				break;
			case "-h":
			case "--help":
				//Display the help and exit the application
				displayCommandHelp();
				helpDisplayed = true;
				correct = false;
				i++;
				break;
			default:
				displayErrorInCommand("Incorrect use: Unrecognized option: ", args, i);
				correct = false;
			}
		}
		
		//Create the specific controller if all correct
		if(correct)
		{
			GameTypeFactory gameFactory;
			Game g = null;
			
			switch(gameToPlay)
			{
			case 0:
				if(console)
				{
					gameFactory = new Connect4Factory();
					g = new Game(gameFactory.createRules());
					toReturnController = new ConsoleController(gameFactory, g);
				}
				else
				{
					gameFactory = new Connect4Factory();
					g = new Game(gameFactory.createRules());
					toReturnController = new WindowController(gameFactory, g);
				}
				break;
			case 1:
				if(console)
				{
					gameFactory = new ComplicaFactory();
					g = new Game(gameFactory.createRules());
					toReturnController = new ConsoleController(gameFactory, g);
				}
				else
				{
					gameFactory = new ComplicaFactory();
					g = new Game(gameFactory.createRules());
					toReturnController = new WindowController(gameFactory, g);
				}
				break;
			case 2:
				if(console)
				{
					gameFactory = new ReversiFactory();
					g = new Game(gameFactory.createRules());
					toReturnController = new ConsoleController(gameFactory, g);
				}
				else
				{
					gameFactory = new ReversiFactory();
					g = new Game(gameFactory.createRules());
					toReturnController = new WindowController(gameFactory, g);
				}
				break;
			case 3:
				if(console)
				{
					gameFactory = new GravityFactory(cols, rows);
					g = new Game(gameFactory.createRules());
					toReturnController = new ConsoleController(gameFactory, g);
				}
				else
				{
					gameFactory = new GravityFactory(cols, rows);
					g = new Game(gameFactory.createRules());
					toReturnController = new WindowController(gameFactory, g);
				}
				break;
			}
			
			g.setPlayerType(blackPlayer);
			g.setPlayerType(whitePlayer);
		}
				
		return toReturnController;
	}
	
	static private int getGameFromParameter(String[] args, int index)
	{
		switch(args[index])
		{
		case "c4":
			return 0;
		case "co":
			return 1;
		case "rv":
			return 2;
		case "gr":
			return 3;
		default:
			return -1;
		}
	}
	
	static private void displayErrorInCommand(String msg, String[] args, int index)
	{
		String invArgs = "";
		
		for(int i = index; i < args.length; i++)
		{
			invArgs += args[i] + " ";
		}
		
		System.err.println(msg + invArgs);
		System.err.println("For more details, use -h|--help.");
	}
	
	static private void displayCommandHelp()
	{
		System.out.println("usage: tp.pr3.Main [-g <game>] [-h] [-u <tipo>] [-x <columnNumber>] [-y <rowNumber>]");
		System.out.println(" -g,--game <game>           Type of game (c4, co, gr). By default, c4.");
		System.out.println(" -h,--help                  Displays this help.");
		System.out.println(" -u,--ui <tipo>             Type of interface (console, window).");
		System.out.println("                            By default, console.");
		System.out.println(" -x,--dimX <columnNumber>   Number of columns on the board (Gravity only).");
		System.out.println("                            By default, 10.");
		System.out.println(" -y,--dimY <rowNumber>      Number of rows on the board (Gravity only). By");
		System.out.println("                            default, 10.");
	}

}

package tp.pr5.logic;

import java.util.ArrayList;
import java.util.Stack;

public class Game implements Observable<GameObserver>{
	
	//Private variables
	private Board mBoard;
	private Counter mTurn;
	private Counter mWhitePlayer, mBlackPlayer;
	private boolean mFinished;
	private Counter mWinner;
	private GameRules mRules;
	
	//Undo stack
	private Stack<Move> mUndoStack;
	
	//Oberservers list
	private ArrayList<GameObserver> mObserversList;
	
	public Game(GameRules rules)
	{
		mBoard = rules.newBoard();
		mFinished = false;
		
		mTurn = rules.initialPlayer();
		mWhitePlayer = Counter.WHITE;
		mBlackPlayer = Counter.BLACK;
		
		mUndoStack = new Stack<Move>();
		
		mObserversList = new ArrayList<GameObserver>();
		
		mRules = rules;
	}

	
	//Return the board class
	public Board getBoard()
	{
		return mBoard;
	}
	
	//Return the current turn
	public Counter getTurn()
	{
		return mTurn;
	}
	
	//If the game is finished it returns the winner
	public Counter getWinner()
	{
		if(isFinished())
		{
			return mWinner;
		}
		else
		{
			return Counter.EMPTY;
		}
	}
	
	//Return if the game is finished or not
	public boolean isFinished()
	{
		return mFinished;
	}
	
	//Sets of the game is finished or not
	public void setFinished(boolean fin)
	{
		mFinished = fin;
	}
	
	//Reset the whole board, the turn and the counter list
	public void reset(GameRules rules)
	{
		mRules = rules;
		
		mBoard = rules.newBoard();
		
		mTurn = rules.initialPlayer();
		
		mUndoStack.removeAllElements();
		
		//Notify the reset
		for(GameObserver o: mObserversList)
		{
			o.reset(mBoard, mTurn, false);
		}
		
		mFinished = false;
	}

	//Goes to the next turn
	public Counter nextTurn()
	{		
		Counter toRet = mRules.nextTurn(mTurn, mBoard);
		
		if(toRet == Counter.WHITE)
		{
			toRet.setPlayerType(mWhitePlayer.getPlayerType());
		}
		else
		{
			toRet.setPlayerType(mBlackPlayer.getPlayerType());
		}
		
		return toRet;
	}
	
	//Change the type of the player
	public void setPlayerType(Counter player)
	{
		if(player == Counter.WHITE)
		{
			mWhitePlayer.setPlayerType(player.getPlayerType());
		}
		else if(player == Counter.BLACK)
		{
			mBlackPlayer.setPlayerType(player.getPlayerType());
		}
		
		if(mTurn == Counter.WHITE)
		{
			mTurn = mWhitePlayer;
		}
		else if(mTurn == Counter.BLACK)
		{
			mTurn = mBlackPlayer;
		}
	}
	
	//Execute a new move if the game is not finished, its made by the correct player and the column is valid.
	public void executeMove(Move move)
	{
		if(isFinished())
		{
			moveErrorTriggered("Game has finished");
		}
		else if(move.getPlayer() != mTurn)
		{
			moveErrorTriggered("Not your turn!");
		}
		else
		{			
			//Notify the start of a move
			for(GameObserver o: mObserversList)
			{
				o.moveExecStart(mTurn);
			}
			
		 	//Do the move (if valid)
			try {
				move.executeMove(mBoard);
			} catch (InvalidMove e) {
				moveErrorTriggered(e.getMessage());
				return;
			}
			
			//Add the move to the stack
			mUndoStack.push(move);
			
			//Notify the end of a move
			for(GameObserver o: mObserversList)
			{
				o.moveExecFinished(mBoard, mTurn,  mRules.nextTurn(mTurn, mBoard));
			}
			
			//Check for enf of game
			Counter newWinner = mRules.winningMove(move, mBoard);
			if(newWinner != Counter.EMPTY) //If we have a winner exit
			{
				mWinner = newWinner;
				setFinished(true);
				
				for(GameObserver o: mObserversList)
				{
					o.onGameOver(mBoard, mWinner);
				}
			}
			else if(mRules.isDraw(nextTurn(), mBoard)) //If the board full is a draw, exit
			{
				mWinner = Counter.EMPTY; //No winner
				setFinished(true);
				
				for(GameObserver o: mObserversList)
				{
					o.onGameOver(mBoard, mWinner);
				}
			}
			else
			{
				//If we haven't finish yet go to the next turn.
				mTurn = nextTurn();
			}
		}
	}
	
	//Undo the last movement, false if there is no movement to undo
	public boolean undo()
	{
		boolean correctUndo;
		
		//If the stack is empty return false
		if(mUndoStack.empty())
		{
			correctUndo = false;
			
			//Notify undo fail
			for(GameObserver o: mObserversList)
			{
				o.onUndoNotPossible();
			}
		}
		else
		{
			mUndoStack.pop().undo(mBoard);
			
			mTurn = nextTurn(); //After undo the turn should change
			
			//Notify correct undo. Send the status of the queue (if its empty we pass false to undo possible)
			for(GameObserver o: mObserversList)
			{
				o.onUndo(mBoard, getTurn(), !mUndoStack.isEmpty());
			}
			
			correctUndo = true;
		}
		
		return correctUndo;
	}


	@Override
	public void addObserver(GameObserver o) {
		mObserversList.add(o);
		
		o.reset((ReadOnlyBoard)mBoard, mTurn, false);  // TODO: change it
	}


	@Override
	public void removeObserver(GameObserver o) {
		mObserversList.remove(o);		
	}
	
	//Notify all the observers about the error
	public void moveErrorTriggered(String msg)
	{
		for(GameObserver o: mObserversList)
		{
			o.onMoveError(msg);
		}
	}
	
	

}

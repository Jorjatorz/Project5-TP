package tp.pr5.views.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.Border;

import tp.pr5.logic.*;
import tp.pr5.control.WindowController;
import tp.pr5.Main;

public class MainWindow extends javax.swing.JFrame implements GameObserver {
	
	private WindowController mWindowController;
	private Observable<GameObserver> mGame;
	
	public MainWindow(Observable<GameObserver> g, WindowController c)
	{
		super("Jorge's Game");
		
		mGame = g;
		mWindowController = c;
		
		initGUI();
		
		g.addObserver(this);
	}
	
	//GUI COMPONENTS--------------------------------------------------------------------------------------------------------------
	//Panels of the GUI
	private JPanel mainPanel, bottomPanel, rightPanel, gamePanel, changeGamePanel,centerPanel, turnPanel, playerTypePanel;
	//Buttons of the GUI
	private JButton randomMoveButton, exitButton, undoButton, resetButton, changeButton;
	//Graphic board
	GraphicBoardComponent graphicBoard;
	//Labels
	JLabel turnLabel, colsLabel, rowsLabel;
	//Text fields
	JTextField colsTextArea, rowsTextArea;
	//------------------------------------------------------------------------------------------------------------------------------
	
	private void initGUI()
	{
		//JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1080, 720);
		this.setMinimumSize(new Dimension(640, 360));
		
		//Create the mainPanel
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.LIGHT_GRAY);		
		this.setContentPane(mainPanel);
		

		initGUIBottomComponents();
		initGUIRightComponents();
		initGUICenterComponents();
		
		this.setVisible(true);
	}
	
	private void initGUIRightComponents()
	{
		//Right Panel
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setSize(new Dimension(this.getWidth() / 2, this.getHeight() - bottomPanel.getHeight()));
		rightPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() - bottomPanel.getHeight()));
		rightPanel.setBackground(Color.LIGHT_GRAY);		
		mainPanel.add(rightPanel, BorderLayout.LINE_END);
		
		//Game Controls Panel------------------------------------------------------------------------------
		Border rightPanelsBorder = BorderFactory.createLineBorder(Color.black, 1);
		gamePanel = new JPanel();
		gamePanel.setSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		gamePanel.setPreferredSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		gamePanel.setBackground(Color.LIGHT_GRAY);
		gamePanel.setBorder(BorderFactory.createTitledBorder(rightPanelsBorder, "Game"));
		rightPanel.add(gamePanel, BorderLayout.PAGE_START);
		//Undo Button
		undoButton = new JButton("Undo");
		undoButton.setPreferredSize(new Dimension(180, 55));
		undoButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				mWindowController.GUImakeUndo();				
			}
			
		});
		undoButton.setIcon(new ImageIcon(Main.class.getResource("Icons/undo.png")));
		gamePanel.add(undoButton);
		//Reset Button
		resetButton = new JButton("Reset");
		resetButton.setPreferredSize(new Dimension(180, 55));
		resetButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				mWindowController.GUImakeReset();				
			}
			
		});
		resetButton.setIcon(new ImageIcon(Main.class.getResource("Icons/reset.png")));
		gamePanel.add(resetButton);	
		
		//PlayerType Panel------------------------------------------------------------------------------------------
		playerTypePanel = new JPanel(new GridLayout(2, 1));
		playerTypePanel.setSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		playerTypePanel.setPreferredSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		playerTypePanel.setBackground(Color.LIGHT_GRAY);
		playerTypePanel.setBorder(BorderFactory.createTitledBorder(rightPanelsBorder, "Player Type"));
		rightPanel.add(playerTypePanel, BorderLayout.CENTER);
		
		JPanel whitePlayerPanel = new JPanel();
		whitePlayerPanel.setBackground(Color.LIGHT_GRAY);
		JLabel whitePlayerLabel = new JLabel("White Player");
		whitePlayerPanel.add(whitePlayerLabel);
		
		//Combo box with different games list
		final JComboBox<PlayerType> whitePlayerTypeList;
		whitePlayerTypeList = new JComboBox<PlayerType>(new PlayersModel(Counter.WHITE, mWindowController));
		whitePlayerTypeList.setSelectedIndex(0);
		whitePlayerPanel.add(whitePlayerTypeList);
		
		JPanel blackPlayerPanel = new JPanel();
		blackPlayerPanel.setBackground(Color.LIGHT_GRAY);
		JLabel blackPlayerLabel = new JLabel("Black Player");
		blackPlayerPanel.add(blackPlayerLabel);
		
		final JComboBox<PlayerType> blackPlayerTypeList;
		blackPlayerTypeList = new JComboBox<PlayerType>(new PlayersModel(Counter.BLACK, mWindowController));
		blackPlayerTypeList.setSelectedIndex(0);
		blackPlayerPanel.add(blackPlayerTypeList);
		
		playerTypePanel.add(whitePlayerPanel);
		playerTypePanel.add(blackPlayerPanel);
		
		//ChangeGame Panel-------------------------------------------------------------------------------------------
		changeGamePanel = new JPanel();
		changeGamePanel.setLayout(new BoxLayout(changeGamePanel, BoxLayout.Y_AXIS));

		changeGamePanel.setSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		changeGamePanel.setPreferredSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight() / 3));
		changeGamePanel.setBackground(Color.LIGHT_GRAY);
		changeGamePanel.setBorder(BorderFactory.createTitledBorder(rightPanelsBorder, "Change game"));
		rightPanel.add(changeGamePanel, BorderLayout.PAGE_END);
		//Combo box with different games list
		final JComboBox<String> gamesList;
		String gamesNames[] = {"Connect-4", "Complica", "Reversi", "Gravity"};
		gamesList = new JComboBox<String>(gamesNames);
		gamesList.setSelectedIndex(0);
		gamesList.setSize(changeGamePanel.getWidth(), 10);
		gamesList.setPreferredSize(new Dimension(changeGamePanel.getWidth() - 25, 25));
		gamesList.setAlignmentX(Component.CENTER_ALIGNMENT);
		gamesList.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				//Detect if the selected index is the one of the gravity game
				//Set visible or not the labels and textareas for getting the widht and height for the gravity game
				if(gamesList.getSelectedIndex() == 3)
				{
					colsLabel.setVisible(true);
					colsTextArea.setVisible(true);	
					rowsLabel.setVisible(true);
					rowsTextArea.setVisible(true);
				}
				else
				{
					colsLabel.setVisible(false);
					colsTextArea.setVisible(false);	
					rowsLabel.setVisible(false);
					rowsTextArea.setVisible(false);
				}
			}
			
		});
		//Change game button
		changeButton = new JButton("Change");
		changeButton.setPreferredSize(new Dimension(180, 45));
		changeButton.setLocation(changeGamePanel.getWidth() - 25, changeGamePanel.getHeight() - 25);
		changeButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				//Get the text inside the text areas
				String colsS = colsTextArea.getText();
				String rowsS = rowsTextArea.getText();
				int cols = 0;
				int rows = 0;
				//If the user wrote something modify the cols and rows
				if((!colsS.equals("")) && (!rowsS.equals("")))
				{
					cols = Integer.parseInt(colsS);
					rows = Integer.parseInt(rowsS);
				}
				//Tell the controller
				mWindowController.GUImakeChangeGame(gamesList.getSelectedIndex(), cols, rows);
				
				colsTextArea.setText("");
				rowsTextArea.setText("");
			}
			
		});
		changeButton.setIcon(new ImageIcon(Main.class.getResource("Icons/check.png")));
		
		//Panels inside the right panel containing the previous init components (buttons, combobox, etc)
		JPanel listButtonPanel = new JPanel();
		listButtonPanel.setBackground(Color.LIGHT_GRAY);

		listButtonPanel.add(gamesList);
		listButtonPanel.add(changeButton);
		
		listButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeGamePanel.add(listButtonPanel);
		
		JPanel crNumberPanel = new JPanel();
		crNumberPanel.setBackground(Color.LIGHT_GRAY);
		colsLabel = new JLabel("Width");
		colsLabel.setVisible(false);
		
		colsTextArea = new JTextField(5);
		colsTextArea.setVisible(false);
		colsTextArea.setSize(100,  100);
		
		rowsLabel = new JLabel("Height");
		rowsLabel.setVisible(false);
		
		rowsTextArea = new JTextField(5);
		rowsTextArea.setVisible(false);
		
		crNumberPanel.add(colsLabel);
		crNumberPanel.add(colsTextArea);		
		crNumberPanel.add(rowsLabel);
		crNumberPanel.add(rowsTextArea);
		
		crNumberPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeGamePanel.add(crNumberPanel);
	}
	
	private void initGUIBottomComponents()
	{
		//Bottom Panel
		bottomPanel = new JPanel();
		bottomPanel.setSize(new Dimension(this.getWidth(), 65));
		bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 65));
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		
		randomMoveButton = new JButton("Random Move");
		randomMoveButton.setPreferredSize(new Dimension(180, 55));
		randomMoveButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				mWindowController.GUImakeRandomMove();				
			}
			
		});
		randomMoveButton.setIcon(new ImageIcon(Main.class.getResource("Icons/random.png")));
		bottomPanel.add(randomMoveButton);
		
		exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(180, 55));
		exitButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				mWindowController.GUImakeExit();
			}
			
		});
		exitButton.setIcon(new ImageIcon(Main.class.getResource("Icons/exit.png")));
		bottomPanel.add(exitButton);		
		
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	private void initGUICenterComponents()
	{
		//Center Panel
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setSize(new Dimension(this.getWidth() / 2, this.getHeight() - bottomPanel.getHeight()));
		centerPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight() - bottomPanel.getHeight()));
		centerPanel.setBackground(Color.LIGHT_GRAY);		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		//Graphical board for representing the games board
		graphicBoard = new GraphicBoardComponent(10, 10, mWindowController, mGame);
		graphicBoard.setPreferredSize(new Dimension(centerPanel.getWidth(), centerPanel.getHeight() - centerPanel.getHeight() / 12));
		centerPanel.add(graphicBoard, BorderLayout.CENTER);
		
		//Border
		Border bottomPanelsBorder = BorderFactory.createRaisedBevelBorder();
		//turn panel
		turnPanel = new JPanel();
		turnPanel.setSize(new Dimension(centerPanel.getWidth(), centerPanel.getHeight() / 12));
		turnPanel.setPreferredSize(new Dimension(centerPanel.getWidth(), centerPanel.getHeight() / 12));
		turnPanel.setBackground(Color.LIGHT_GRAY);
		turnPanel.setBorder(bottomPanelsBorder);
		centerPanel.add(turnPanel, BorderLayout.PAGE_END);
		//Turn label
		turnLabel = new JLabel("");
		turnPanel.add(turnLabel);
	}
	

	@Override
	public void moveExecFinished(ReadOnlyBoard board, Counter player, Counter nextPlayer) {
		//Update board, turn and undo button
		turnLabel.setText(nextPlayer.toString());
		undoButton.setEnabled(true);
	}

	@Override
	public void moveExecStart(Counter player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameOver(ReadOnlyBoard board, Counter winner) {
		//Disable the buttons
		undoButton.setEnabled(false);
		randomMoveButton.setEnabled(false);
		
		//Pop a meesage to the user. 
		int n;
		if(winner != Counter.EMPTY)
		{
			n = JOptionPane.showOptionDialog(this, winner.toString() + " player has won. Would you like to quit the game?", winner + " won!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		}
		else
		{
			n = JOptionPane.showOptionDialog(this, "It's a draw. Would you like to quit?", "It's a draw!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		}
		
		//If the suer wants to exit jsut exit
		if(n == 0)
		{
			//Tell the controller to exit
			mWindowController.GUImakeExit();
		}
		else
		{
		}

	}

	@Override
	public void onMoveError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onUndo(ReadOnlyBoard board, Counter nextPlayer,
			boolean undoPossible) {
		
		//Update the turn
		turnLabel.setText(nextPlayer.toString());
		//Update buttons
		if(undoPossible)
		{
			undoButton.setEnabled(true);
		}
		else
		{
			undoButton.setEnabled(false);
		}
	}

	@Override
	public void onUndoNotPossible() {
		JOptionPane.showMessageDialog(this, "Nothing to undo!", "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void reset(ReadOnlyBoard board, Counter player, Boolean undoPossible) {
		//Reset the player turn
		turnLabel.setText(player.toString());
		
		//Activate buttons
		if(!undoPossible)
		{
			undoButton.setEnabled(false);
		}
		else
		{
			undoButton.setEnabled(true);
		}
		
		randomMoveButton.setEnabled(true);

	}

}

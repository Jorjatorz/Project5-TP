package tp.pr5.views.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import tp.pr5.control.WindowController;
import tp.pr5.logic.Counter;
import tp.pr5.logic.ReadOnlyBoard;

/**
 * Code of this class is a modified version of BoardComponent example given and created by Samir Genaim
 * @author Jorge
 *
 */

public class GraphicBoardComponent extends JComponent {
	private int mCellHeight;
	private int mCellWidth;

	private int rows;
	private int cols;
	private Counter[][] mBoard;
	
	private WindowController mController;

	public GraphicBoardComponent(int rows, int cols, WindowController c) {
		mCellHeight = 50;
		mCellWidth = 50;
		mController = c;
		
		initBoard(rows, cols);
		initGUI();
	}

	//Init board to empty
	private void initBoard(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		mBoard = new Counter[rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				mBoard[i][j] = Counter.EMPTY;
			}
		}
	}

	private void initGUI() {

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				mController.GUImakeMove((e.getX() / mCellWidth) + 1, (e.getY() / mCellHeight) + 1);
			}
		});
		
		final GraphicBoardComponent me = this;
		addMouseMotionListener( new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				me.setToolTipText("Column:" + ((arg0.getX() / mCellWidth) + 1) + " Row: " + (arg0.getY() / mCellHeight + 1));
				
			}
			
		});
		
		this.setSize(new Dimension(rows * mCellHeight, cols * mCellWidth));
		this.setToolTipText(null);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		mCellWidth = this.getWidth() / cols;
		mCellHeight = this.getHeight() / rows;

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				drawCell(i, j, g);
			}
		}
	}

	private void drawCell(int row, int col, Graphics g) {
		int x = col * mCellWidth;
		int y = row * mCellHeight;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, mCellWidth - 4, mCellHeight - 4);

		g.setColor(counterToColor(mBoard[row][col]));
		g.fillOval(x + 4, y + 4, mCellWidth - 8, mCellHeight - 8);

		g.setColor(Color.black);
		g.drawOval(x + 4, y + 4, mCellWidth - 8, mCellHeight - 8);

	}

	public void setBoardSize(int rows, int cols) {
		initBoard(rows, cols);
		repaint();
	}
	
	public void setBoardToRender(ReadOnlyBoard board)
	{
		for(int i = 1; i < board.getHeight() + 1; i++)
		{
			for(int j = 1; j < board.getWidth() + 1; j++)
			{
				mBoard[i - 1][j - 1] = board.getPosition(j,  i);
			}
		}
		
		this.repaint();
	}
	
	private Color counterToColor(Counter c)
	{
		switch(c)
		{
		case BLACK:
			return Color.black;
		case WHITE:
			return Color.white;
		default:
			return Color.lightGray;
		}
	}
}

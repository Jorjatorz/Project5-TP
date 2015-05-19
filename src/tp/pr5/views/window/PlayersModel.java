package tp.pr5.views.window;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import tp.pr5.control.WindowController;
import tp.pr5.logic.Counter;
import tp.pr5.logic.PlayerType;

public class PlayersModel implements ComboBoxModel<PlayerType> {

	private Counter player;
	private PlayerType selectedPlayerType;
	WindowController mController;
	
	public PlayersModel(Counter player, WindowController ctrl)
	{
		this.player = player;
		this.selectedPlayerType = player.getPlayerType();
		
		mController = ctrl;
	}
	
	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public PlayerType getElementAt(int index) {
		if(index == 0)
		{
			return PlayerType.HUMAN;
		}
		else
		{
			return PlayerType.AUTO;
		}
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(Object anItem) {
		this.selectedPlayerType = (PlayerType)anItem;
		mController.GUIsetPlayerMode(player, this.selectedPlayerType);	
	}

	@Override
	public Object getSelectedItem() {
		return selectedPlayerType;
	}

}

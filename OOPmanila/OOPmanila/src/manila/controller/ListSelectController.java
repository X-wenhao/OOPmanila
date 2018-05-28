package manila.controller;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import manila.model.Game;
import manila.model.Player;

public class ListSelectController implements ListSelectionListener {
	private JLabel show_info;
	private JList<String> player_list;
	private JList<String> share_list;
	private Game game;
	
	public ListSelectController(JLabel show_info,JList<String> player_list,JList<String> share_list,Game game) {
		// TODO Auto-generated constructor stub
		this.show_info=show_info;
		this.player_list=player_list;
		this.share_list=share_list;
		this.game=game;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		String share_info = share_list.getSelectedValue();
		if(share_info.equals("清空")) {
			show_info.setText("不可见");
			player_list.clearSelection();
			//share_list.clearSelection();
			return;
		};
		
		if(player_list.isSelectionEmpty()) {
			return;
		}
		
		int player_id=player_list.getSelectedIndex();
		
		Player player=game.getPlayerByID(player_id);
		int[] info=player.findShareInfoByName(share_info);
		
		show_info.setText("拥有:"+info[0]+" 可抵押:"+info[1]);
		
	}
	

}

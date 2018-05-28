package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import manila.model.Game;
import manila.model.Player;
import manila.model.Share;
import manila.view.GameView;

/**
 * 控制抵押赎回的监听类
 * 
 * @author tk-ice
 *
 */
public class PledgeController implements ActionListener {
	/**游戏主页面*/
	private GameView gv;
	/**抵押或者赎回的button*/
	private JButton current_button;
	/**玩家列表*/
	private JList<String> player_list;
	/**股票列表*/
	private JList<String> share_list;
	/**显示玩家股票信息的label*/
	private JLabel share_info;

	public PledgeController(GameView gv, JButton current_button, JList<String> player_list, JList<String> share_list,
			JLabel share_info) {
		this.gv = gv;
		this.current_button = current_button;
		this.player_list = player_list;
		this.share_list = share_list;
		this.share_info = share_info;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (player_list.isSelectionEmpty() || share_list.isSelectionEmpty()) {
			return;
		}

		Game game = gv.getGame();
		Player player = game.getPlayerByID(player_list.getSelectedIndex());
		String share_name = share_list.getSelectedValue();
		if (current_button.getName().equals("抵押")) {
			player.pledgeShare(share_name);
		} else {
			player.getShareBack(share_name);
		}
		
		gv.updatePlayersView(player.getPid(), gv.getPlayerviews().get(player.getName()).isActive());
		int[] info = player.findShareInfoByName(share_name);
		share_info.setText("拥有:" + info[0] + " 可抵押:" + info[1]);
	}
}

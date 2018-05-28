package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import manila.model.Player;
import manila.view.GameView;

/**
 * 第三轮结束开始新的一轮或者结束游戏
 * @author 彭淮越
 *
 */
public class NextController implements ActionListener {
	/**游戏主页面*/
	private GameView gv;

	public NextController(GameView gv) {
		// TODO Auto-generated constructor stub
		this.gv = gv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.gv.getGame().getCurrent_round() == 3) {
			this.gv.getGame().refreshGame();
			this.gv.refreshGameView();
			this.gv.getChoosingBossView().setVisible(true);
			this.gv.getChoosingBossView().setAlwaysOnTop(true);
			this.gv.setEnabled(false);
			gv.addGameInfo("开始新的回合....\n");
		}
		
		if(gv.getGame().isIs_game_over()) {
			gv.getGame().accountPlayerMoney();
			Player p=gv.getGame().showWinner();
			gv.showWinner(p);
		}
	}

}

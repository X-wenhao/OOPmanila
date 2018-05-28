package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import manila.model.Boat;
import manila.model.Game;
import manila.view.GameView;

/**
 * 控制摇骰子的按钮的事件监听类
 */
public class DiceController implements ActionListener {

	private Game game;
	private GameView gv;

	public DiceController(GameView gv) {
		this.game = gv.getGame();
		this.gv = gv;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// roll the dice to move the boats
		if (!this.game.isIs_game_over() && !this.game.isIs_choosing()) {
			game.diceMoveBoats();
			gv.updateBoatViews();
		}


		// prepare the next round
		this.game.setIs_choosing(true);

		if (this.game.getCurrent_round() == 3) {
			// game is over
			this.game.calculateProfits();
		}
	}

}

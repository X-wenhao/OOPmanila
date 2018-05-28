package manila.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import manila.*;
import manila.model.Game;
import manila.view.BoatView;

public class GameController implements MouseListener {

	private Game game;

	public GameController(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	private void clickOnBoat(BoatView bv) {
		if (bv.getBoat().getAvailPosIndex() == -1)
			return;
		if (bv.getBoat().playerAboard(game.getCurrentPlayer())) {
			bv.getOnBoat();
		} else {
			// TODO 没钱上船的显示
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().getClass().getSimpleName().equals("BoatView")) {
			clickOnBoat((BoatView)e.getSource());
			return;
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

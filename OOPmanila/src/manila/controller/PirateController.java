package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import manila.model.Boat;
import manila.model.Player;
import manila.view.BoatView;
import manila.view.GameView;
import manila.view.PrivateInteractor;

/**
 * 海盗抢劫的控制类
 * @author tk-ice
 *
 */
public class PirateController implements ActionListener {
	/**游戏主页面*/
	private GameView gv;
	/**抢劫的player*/
	private Player player;
	/**抢劫的船的视图*/
	private BoatView bv;
	/**海盗id*/
	private int pirate_id;
	/**海盗交互页面（弹出式）*/
	private PrivateInteractor pi;

	public PirateController(GameView gv, Player player, BoatView bv, int pirate_id, PrivateInteractor pi) {
		super();
		this.gv = gv;
		this.player = player;
		this.bv = bv;
		this.pirate_id = pirate_id;
		this.pi = pi;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (gv.getGame().getCurrent_round() == 2) {
			if (arg0.getActionCommand().equals("yes")) {
				gv.getGame().getPirateBoat().robBoat(pirate_id, bv.getBoat(), gv.getGame().getCurrent_round(), false);
				gv.updatePirteBoat();
				gv.updateBoatViews(bv);
				gv.addGameInfo(player.getName() + " 在第 " + gv.getGame().getCurrent_round() + "轮抢劫了船 "
						+ bv.getBoat().getCargo_name()+"\n");
				pi.dispose();
			} else {
				pi.dispose();
			}
		} else if (gv.getGame().getCurrent_round() == 3) {
			if (arg0.getActionCommand().equals("yes")) {
				gv.getGame().getPirateBoat().robBoat(pirate_id, bv.getBoat(), 3, true);
				gv.getGame().getPort().boatArrive(bv.getBoat());
			} else if (arg0.getActionCommand().equals("no")) {
				gv.getGame().getPirateBoat().robBoat(pirate_id, bv.getBoat(), 3, false);
				gv.getGame().getFix_port().boatArrive(bv.getBoat());
			}
			
			gv.updatePirteBoat();
			gv.updateBoatViews(bv);
			gv.addGameInfo(player.getName() + " 在第 " + gv.getGame().getCurrent_round() + "轮抢劫了船 "
					+ bv.getBoat().getCargo_name()+"\n");
			pi.dispose();
		}

	}
}

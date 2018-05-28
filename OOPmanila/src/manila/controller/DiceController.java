package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import manila.model.*;
import manila.model.Game;
import manila.view.BoatView;
import manila.view.GameView;
import manila.view.PlayerView;
import manila.view.PrivateInteractor;
import sun.misc.GC;

/**
 * 控制摇骰子的按钮的事件监听类
 * @author tk-ice
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
		// 船只前进
		if (!this.game.isIs_game_over() && !this.game.isIs_choosing() && this.game.getBoss().getPlayer() != null) {

			game.diceMoveBoats();
			gv.addGameInfo("船只前进了....\n");
			gv.updateBoatViews(null);
			
			//如果是第一轮则进入下一轮的放置流程
			if (this.game.getCurrent_round() == 1) {
				this.game.setIs_choosing(true);
			}
			
			//第二轮尝试进行海盗抢劫
			if (this.game.getCurrent_round() == 2) {
				pirateAtSecondArround();
				this.game.setIs_choosing(true);
			}
			//第三轮的操作
			if (this.game.getCurrent_round() == 3) {
				//第三轮尝试进行海盗抢劫
				pirateAtThirdArround();
				
				//计算本回合收益
				this.game.calculateProfits();
				this.gv.addGameInfo("收益计算完成..\n");
				for (String p_name : gv.getPlayerviews().keySet()) {
					Player player = gv.getPlayerviews().get(p_name).getPlayer();
					this.gv.updatePlayersView(player.getPid(), gv.getPlayerviews().get(p_name).isActive());
				}
				gv.updateShareInfo();
				
				//将船停在港口或者修船厂
				placeBoatView();
				gv.getNext_pass().setEnabled(true);
			}

		}
		gv.getGo_button().setEnabled(false);
	}

	/**
	 * 第二轮海盗抢劫
	 */
	private void pirateAtSecondArround() {
		for (String boat_name : gv.getOut_boat_names()) {
			BoatView bv = gv.getBoatviews().get(boat_name);
			Boat boat = bv.getBoat();
			if (boat.getPos_in_sea() == 13) {
				int i = 0;
				for (Position p : game.getPirateBoat().getPositions()) {
					if (p.getPid() != -1 && boat.getAvailPosIndex() != -1) {
						Player player = game.getPlayerByID(p.getPid());
						String info = player.getName() + " 是否抢劫  " + boat_name + "?";
						PrivateInteractor view = new PrivateInteractor(gv, i, bv, info);
						gv.addGameInfo(player.getName() + "在第2轮抢劫了" + boat_name + "\n");
					}
					i++;
				}
			}
		}
		// PrivateInteractor view=new PrivateInteractor();
	}

	/**
	 * 第三轮海盗抢劫
	 */
	private void pirateAtThirdArround() {
		for (String boat_name : gv.getOut_boat_names()) {
			BoatView bv = gv.getBoatviews().get(boat_name);
			Boat boat = bv.getBoat();
			if (boat.getPos_in_sea() == 13) {
				for (Position p : game.getPirateBoat().getPositions()) {
					if (p.getPid() != -1) {
						Player player = game.getPlayerByID(p.getPid());
						String info = player.getName() + " 是否将船  " + boat_name + "开到港口?";
						PrivateInteractor view = new PrivateInteractor(gv, 0, bv, info);

						gv.addGameInfo(player.getName() + "在第3轮率队抢劫了" + boat_name + "\n");
						gv.updatePirteBoat();
						gv.updateBoatViews(bv);
						break;
					}

				}
			}
		}

		// PrivateInteractor view=new PrivateInteractor();
	}

	/**
	 * 第三轮移动完之后将bv移动到修船厂或者港口
	 */
	public void placeBoatView() {
		String info = "成功抵达的船有：";
		/*
		 * for (String bv_name : gv.getOut_boat_names()) { BoatView bv =
		 * gv.getBoatviews().get(bv_name); if (bv.getBoat().isArrive()) { int i =
		 * this.game.getPort().boatArrive(bv.getBoat()); bv.setBounds(230 + 55 * i + 5,
		 * 55, 40, 160);
		 * 
		 * info += " " + bv.getBoat().getCargo_name(); } else { int i =
		 * this.game.getFix_port().boatArrive(bv.getBoat()); bv.setBounds(35 + 55 * i +
		 * 5, 55, 40, 160); } bv.setVisible(true);
		 * 
		 * System.out.println(bv.getBoat().getCargo_name() + ":" + bv.getX() + "  " +
		 * bv.getY()); }
		 */
		for (int i = 0; i < game.getPort().getBoats().size(); i++) {
			Boat boat = game.getPort().getBoats().get(i);
			BoatView bv = gv.getBoatviews().get(boat.getCargo_name());
			bv.setBounds(230 + 55 * i + 5, 55, 40, 160);
			info += " " + bv.getBoat().getCargo_name();
		}

		for (int i = 0; i < game.getFix_port().getBoats().size(); i++) {
			Boat boat = game.getFix_port().getBoats().get(i);
			BoatView bv = gv.getBoatviews().get(boat.getCargo_name());
			bv.setBounds(35 + 55 * i + 5, 55, 40, 160);

		}

		gv.addGameInfo(info + "\n");
	}
}

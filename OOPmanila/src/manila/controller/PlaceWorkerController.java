package manila.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.model.Game;
import manila.model.Player;
import manila.view.BoatView;
import manila.view.GameView;

/**
 * 放置工人的监听类，分为监听label与监听panel两种
 * @author tk-ice
 *
 */
public class PlaceWorkerController implements MouseListener {
	/**待放置的label、*/
	private JLabel pos_label;
	/**对应的value_label*/
	private JLabel value_label;
	
	/**待放置的panel，为船或者海盗船*/
	private JPanel panel;
	
	/**监听的事件类型*/
	private String type;
	private GameView gv;

	/**
	 * 需要选择位置时的监听
	 * 
	 * @param pos_label
	 *            位置
	 * @param type
	 *            类型
	 * @param gv
	 */
	public PlaceWorkerController(JLabel pos_label, String type, GameView gv) {
		// TODO Auto-generated constructor stub
		this.pos_label = pos_label;
		this.type = type;
		this.gv = gv;
	}

	/**
	 * 不需要选择位置时的监听
	 * 
	 * @param panel
	 *            海盗、船
	 * @param type类型
	 * @param gv
	 */
	public PlaceWorkerController(JPanel panel, String type, GameView gv) {
		// TODO Auto-generated constructor stub
		this.panel = panel;
		this.type = type;
		this.gv = gv;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("pressed");
		if (!gv.getGame().isIs_choosing() && gv.getGame().getCurrent_round() == 2 && type.equals("船")) {
			//领航员控制船前进
			BoatView bView = (BoatView) panel;
			int i = 1;
			if (arg0.getY() > 80)
				i = -1;
			System.out.println("领航员。。。。。");
			gv.getGame().getNavigator().moveBoat(bView.getBoat(), i);
			int[] num=gv.getGame().getNavigator().getNum();
			gv.updateBoatViews(null);
			
			if (num[1] == 0 && !gv.getGame().getNavigator().ifPosAvail()[0] && num[0] != 0) {
				gv.addGameInfo("请小领航员行使权力\n");
			} else if (num[0] == 0 || gv.getGame().getNavigator().ifPosAvail()[0]) {
				gv.addGameInfo("领航员行使权力结束\n");
				gv.getGo_button().setEnabled(true);
			}
			return;
		} else if (gv.getGame().isIs_choosing()) {
			if (!gv.getGame().getCurrentPlayer().isCanAboard()) {
				//如果不能上船，没钱，则切换
				gv.updatePlayersView(gv.getGame().getCurrent_pid(), false);
				gv.getGame().switchPlayer();
				gv.updatePlayersView(gv.getGame().getCurrent_pid(), true);
				gv.updateButton();
				return;
			}
			if (type.equals("海盗")) {
				getOnPirateBoat();
			} else if (type.equals("船")) {
				getOnBoat();
			} else {
				placeWorkerOnLabel();
			}
				
			if (!gv.getGame().isIs_choosing()) {
				//最后一个玩家放置完之后如果是第二轮提示领航员行使权利
				if (gv.getGame().getCurrent_round() == 2) {
					if (!gv.getGame().getNavigator().ifPosAvail()[1]) {
						gv.addGameInfo("请大领航员行使权力\n");
					} else if (!gv.getGame().getNavigator().ifPosAvail()[0]) {
						gv.addGameInfo("请小领航员行使权力\n");
					}else {
						gv.getGo_button().setEnabled(true);
					}
				}else if(gv.getGame().getCurrent_round()!=3){
					gv.getGo_button().setEnabled(true);
				}else {
					gv.getNext_pass().setEnabled(true);
				}
			}
		}

	}

	/**
	 * 放置工人在海盗船上
	 */
	private void getOnPirateBoat() {
		Game game = gv.getGame();
		Player player = game.getCurrentPlayer();
		if (game.getPirateBoat().playerOnPirateBoat(player)) {
			gv.updatePirteBoat();
			gv.updatePlayersView(player.getPid(), false);
			game.switchPlayer();
			gv.updatePlayersView(game.getCurrent_pid(), true);
			gv.addGameInfo(player.getName() + " place a worker on pirateBoat\n");
		}
		;

	}

	/**
	 * 放置工人在船上
	 */
	private void getOnBoat() {
		Game game = gv.getGame();
		Player player = game.getCurrentPlayer();
		BoatView bv = (BoatView) panel;
		System.out.println(bv.getBoat().getCargo_name());
		if (bv.getBoat().playerAboard(player)) {
			gv.updateBoatViews(bv);
			gv.updatePlayersView(player.getPid(), false);
			game.switchPlayer();
			gv.updatePlayersView(game.getCurrent_pid(), true);
			gv.addGameInfo(player.getName() + " place a worker on Boat " + bv.getBoat().getCargo_name()+"\n");
		}
		;
	}

	/**
	 * 放置工人在label上
	 */
	private void placeWorkerOnLabel() {
		Game game = gv.getGame();
		Player player = game.getCurrentPlayer();
		if (!pos_label.getBackground().equals(gv.getPos_color()))
			return;
		if (type.equals("保险")) {
			if (!game.getInsurance().playerOnInsurance(player))
				return;
			gv.addGameInfo(player.getName() + "place a worker on Insurance..\n");
		} else if (type.equals("领航")) {
			if (!game.getNavigator().playerOnNavigator(player, Integer.parseInt(this.pos_label.getName())))
				return;
			gv.addGameInfo(player.getName() + "place a worker on Navigator " + this.pos_label.getName()+"\n");
		} else if (type.equals("港口")) {
			if (!game.getPort().playerOnPort(player, Integer.parseInt(this.pos_label.getName())))
				return;
			gv.addGameInfo(player.getName() + "place a worker on Port " + this.pos_label.getName()+"\n");
		} else if (type.equals("修船厂")) {
			if (!game.getFix_port().playerOnPort(player, Integer.parseInt(this.pos_label.getName())))
				return;
			gv.addGameInfo(player.getName() + "place a worker on FixPort " + this.pos_label.getName()+"\n");
		}

		this.pos_label.setBackground(player.getColor());
		pos_label.setBorder(BorderFactory.createLineBorder(player.getColor(),2));
		gv.updatePlayersView(player.getPid(), false);
		game.switchPlayer();
		gv.updatePlayersView(game.getCurrent_pid(), true);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

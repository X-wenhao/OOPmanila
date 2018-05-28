package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;

import manila.model.Boat;
import manila.model.Share;
import manila.view.BoatView;
import manila.view.BossInteractor;
import manila.view.ChoosingBossView;
import manila.view.GameView;

/**
 * 船老大行使权利的事件监听类
 * @author 彭淮越
 *
 */
public class BossController implements ActionListener {
	/**船老大行使权利的页面*/
	private BossInteractor bi;
	/**所有股票*/
	private ArrayList<Share> shares;
	/**游戏主页面*/
	private GameView gv;

	public BossController(BossInteractor bi) {
		this.bi = bi;
		shares = bi.getGame().getShares();
		gv = bi.getGv();
	}

	/**
	 * 重置BossInteractor
	 */
	public void resetBossInteractor() {
		bi.getjRadioButton1().setSelected(false);
		bi.getjRadioButton2().setSelected(false);
		bi.getjRadioButton3().setSelected(false);
		bi.getjRadioButton4().setSelected(false);
		bi.getjRadioButton5().setSelected(false);
		bi.getjRadioButton6().setSelected(false);
		bi.getjRadioButton7().setSelected(false);
		bi.getjRadioButton8().setSelected(false);
		bi.getBoat1Message().setText("");
		bi.getBoat2Message().setText("");
		bi.getBoat3Message().setText("");
		bi.getBoat4Message().setText("");
		bi.setVisible(false);
	}

	/**
	 * 判断内容是否为数字或空
	 * 
	 * @return
	 */
	private boolean inputIsOk(String s) {
		if (s.equals("")) {
			return false;
		}
		int amount;
		try {
			amount = Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 海港管理员决定船只起始位置
	 * 
	 * @return
	 */
	public boolean banAndSet() {
		if (!bi.getjRadioButton5().isSelected() && !bi.getjRadioButton6().isSelected()
				&& !bi.getjRadioButton7().isSelected() && !bi.getjRadioButton8().isSelected()) {
			return false;
		}
		if (bi.getjRadioButton5().isSelected() && (!inputIsOk(bi.getBoat2Message().getText())
				|| !inputIsOk(bi.getBoat3Message().getText()) || !inputIsOk(bi.getBoat4Message().getText())))
			return false;
		if (bi.getjRadioButton6().isSelected() && (!inputIsOk(bi.getBoat1Message().getText())
				|| !inputIsOk(bi.getBoat3Message().getText()) || !inputIsOk(bi.getBoat4Message().getText())))
			return false;
		if (bi.getjRadioButton7().isSelected() && (!inputIsOk(bi.getBoat2Message().getText())
				|| !inputIsOk(bi.getBoat1Message().getText()) || !inputIsOk(bi.getBoat4Message().getText())))
			return false;
		if (bi.getjRadioButton8().isSelected() && (!inputIsOk(bi.getBoat2Message().getText())
				|| !inputIsOk(bi.getBoat3Message().getText()) || !inputIsOk(bi.getBoat1Message().getText())))
			return false;

		HashMap<String, Integer> boatline = new HashMap<String, Integer>();
		if (bi.getjRadioButton5().isSelected()) {
			boatline.put("可可", Integer.parseInt(this.bi.getBoat2Message().getText()));
			boatline.put("人参", Integer.parseInt(this.bi.getBoat3Message().getText()));
			boatline.put("丝绸", Integer.parseInt(this.bi.getBoat4Message().getText()));
			if (bi.getGame().getBoss().bossSetBoats(this.bi.getGame(), boatline)) {
				this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(), "玉石");
				//System.out.println(this.bi.getGame().getSetout_boats().get("可可").getPos_in_sea());
				//System.out.println(boatline.get("可可"));
				return true;
			}
		}
		if (bi.getjRadioButton6().isSelected()) {
			boatline.put("玉石", Integer.parseInt(this.bi.getBoat1Message().getText()));
			boatline.put("人参", Integer.parseInt(this.bi.getBoat3Message().getText()));
			boatline.put("丝绸", Integer.parseInt(this.bi.getBoat4Message().getText()));
			if (bi.getGame().getBoss().bossSetBoats(this.bi.getGame(), boatline)) {
				this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(), "可可");
				return true;
			}
		}
		if (bi.getjRadioButton7().isSelected()) {
			boatline.put("玉石", Integer.parseInt(this.bi.getBoat1Message().getText()));
			boatline.put("可可", Integer.parseInt(this.bi.getBoat2Message().getText()));
			boatline.put("丝绸", Integer.parseInt(this.bi.getBoat4Message().getText()));
			if (bi.getGame().getBoss().bossSetBoats(this.bi.getGame(), boatline)) {
				this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(), "人参");
				return true;
			}
		}
		if (bi.getjRadioButton8().isSelected()) {
			boatline.put("玉石", Integer.parseInt(this.bi.getBoat1Message().getText()));
			boatline.put("可可", Integer.parseInt(this.bi.getBoat2Message().getText()));
			boatline.put("人参", Integer.parseInt(this.bi.getBoat3Message().getText()));
			if (bi.getGame().getBoss().bossSetBoats(this.bi.getGame(), boatline)) {
				this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(), "丝绸");
				return true;
			}
		}
		return false;
	}


	/**
	 * 船老大购买股票
	 */
	public void buy() {
		if (bi.getjRadioButton1().isSelected() && !bi.getjRadioButton2().isSelected()
				&& !bi.getjRadioButton3().isSelected() && !bi.getjRadioButton4().isSelected()) {

			for (int i = 0; i < shares.size(); i++) {
				if (this.shares.get(i).getName().equals("玉石") && this.shares.get(i).getPid() == -1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					gv.addGameInfo(bi.getGame().getBoss().getPlayer().getName()+" 购买了股票  "+this.shares.get(i).getName()+"\n");
					break;
				}
			}
		}
		else if (!bi.getjRadioButton1().isSelected() && bi.getjRadioButton2().isSelected()
				&& !bi.getjRadioButton3().isSelected() && !bi.getjRadioButton4().isSelected()) {
			for (int i = 0; i < shares.size(); i++) {
				if (this.shares.get(i).getName().equals("可可") && this.shares.get(i).getPid() == -1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					gv.addGameInfo(bi.getGame().getBoss().getPlayer().getName()+" 购买了股票  "+this.shares.get(i).getName()+"\n");
					break;
				}
			}
		}
		else if (!bi.getjRadioButton1().isSelected() && !bi.getjRadioButton2().isSelected()
				&& bi.getjRadioButton3().isSelected() && !bi.getjRadioButton4().isSelected()) {
			for (int i = 0; i < shares.size(); i++) {
				if (this.shares.get(i).getName().equals("人参") && this.shares.get(i).getPid() == -1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					gv.addGameInfo(bi.getGame().getBoss().getPlayer().getName()+" 购买了股票  "+this.shares.get(i).getName()+"\n");
					break;
				}
			}
		}
		else if (!bi.getjRadioButton1().isSelected() && !bi.getjRadioButton2().isSelected()
				&& !bi.getjRadioButton3().isSelected() && bi.getjRadioButton4().isSelected()) {
			for (int i = 0; i < shares.size(); i++) {
				if (this.shares.get(i).getName().equals("丝绸") && this.shares.get(i).getPid() == -1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					gv.addGameInfo(bi.getGame().getBoss().getPlayer().getName()+" 购买了股票  "+this.shares.get(i).getName()+"\n");
					break;
				}
			}
		} else {
			gv.addGameInfo("输入有误，请重新输入\n");
		}
		gv.updatePlayersView(this.gv.getGame().getBoss().getPlayer().getPid(), true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getActionCommand().equals("buy")) {
			this.buy();
		} else if (arg0.getActionCommand().equals("set")) {
			if (this.banAndSet()) {
				gv.selectBoatViews();
				for (String bv_name : gv.getBoatviews().keySet()) {
					gv.getBoatviews().get(bv_name).moveBv();
				}
				gv.getGame().setIs_choosing(true);
	

				gv.addGameInfo("\n"+gv.getGame().getBoss().getPlayer().getName()+"行使完权利...\n开始放置伙伴......\n");
				this.resetBossInteractor();
			}
		}

	}
}

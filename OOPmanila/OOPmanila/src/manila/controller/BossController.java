package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.org.glassfish.external.statistics.BoundaryStatistic;

import manila.model.Boat;
import manila.model.Share;
import manila.view.BossInteractor;
import manila.view.ChoosingBossView;

public class BossController implements ActionListener  {
	private BossInteractor bi;
	private ArrayList<Share> shares = bi.getGame().getShares();
	public BossController(BossInteractor bi){
		this.bi = bi;
		
	}
	public void ban() {
		
		if(bi.getjRadioButton5().isSelected()) {
			this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(),"玉石");
		}
		if(bi.getjRadioButton6().isSelected()) {
			this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(),"可可");
		}
		if(bi.getjRadioButton7().isSelected()) {
			this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(),"人参");
		}
		if(bi.getjRadioButton8().isSelected()) {
			this.bi.getGame().getBoss().bossBanBoat(this.bi.getGame(),"丝绸");
		}
	}
	public void set() {
		HashMap<String,Integer> boatline = new HashMap<String,Integer>();
		boatline.put("玉石", Integer.parseInt(this.bi.getBoat1Message().getText()));
		boatline.put("可可", Integer.parseInt(this.bi.getBoat2Message().getText()));
		boatline.put("人参", Integer.parseInt(this.bi.getBoat3Message().getText()));
		boatline.put("丝绸", Integer.parseInt(this.bi.getBoat4Message().getText()));
		this.bi.getGame().getBoss().bossSetBoats(this.bi.getGame(), boatline);
	}
	public void buy() {
		if(bi.getjRadioButton1().isSelected()&&!bi.getjRadioButton2().isSelected()&&!bi.getjRadioButton3().isSelected()&&!bi.getjRadioButton4().isSelected()) {
			
			for(int i=0;i<shares.size();i++) {
				if(this.shares.get(i).getName().equals("玉石")&&this.shares.get(i).getPid()==-1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					break;
				}
			}
		}
        if(!bi.getjRadioButton1().isSelected()&&bi.getjRadioButton2().isSelected()&&!bi.getjRadioButton3().isSelected()&&!bi.getjRadioButton4().isSelected()) {
        	for(int i=0;i<shares.size();i++) {
				if(this.shares.get(i).getName().equals("可可")&&this.shares.get(i).getPid()==-1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					break;
				}
			}
		} 
        if(!bi.getjRadioButton1().isSelected()&&!bi.getjRadioButton2().isSelected()&&bi.getjRadioButton3().isSelected()&&!bi.getjRadioButton4().isSelected()) {
        	for(int i=0;i<shares.size();i++) {
				if(this.shares.get(i).getName().equals("人参")&&this.shares.get(i).getPid()==-1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					break;
				}
			}
        }
        if(!bi.getjRadioButton1().isSelected()&&!bi.getjRadioButton2().isSelected()&&!bi.getjRadioButton3().isSelected()&&bi.getjRadioButton4().isSelected()) {
        	for(int i=0;i<shares.size();i++) {
				if(this.shares.get(i).getName().equals("丝绸")&&this.shares.get(i).getPid()==-1) {
					bi.getGame().getBoss().bossBuyShare(this.shares.get(i));
					break;
				}
			}
        }
		else {
			System.out.println("请重新输入");
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("buy")){
			this.buy();
		}
		else if(arg0.getActionCommand().equals("set")){
			this.ban();
			this.set();
			
		}
		
	}
}

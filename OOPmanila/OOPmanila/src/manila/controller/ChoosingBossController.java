package manila.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import manila.model.Player;
import manila.view.ChoosingBossView;

/**
 * Manila 游戏选举船老大的窗口控制器。
 */
public class ChoosingBossController implements ActionListener {

	private ChoosingBossView cbv;
	private int bid_amount;
	private int boss_pid;
	private ArrayList<Integer> pass_pid;
	
	public ChoosingBossController(ChoosingBossView cbv){
		this.cbv = cbv;
		this.bid_amount = 0;
		pass_pid = new ArrayList<Integer>();
	}
	
	/**
	 * 当前玩家参与竞价，如果输入价格小于当前标价，也认为竞标有效，自动将标价+1
	 * 如果输入为空，则不作相应（默认输入为数字，不处理其他复杂情况）。
	 * 读取完输入后将文本框内容清空，并将当前玩家设为船老大。
	 * 将下一名玩家设为当前玩家，并更新竞选面板的显示内容。
	 */
	public void bid(){
		// 读取玩家输入的金额
		String amountText = this.cbv.getAmountT().getText();
		if(!amountText.equals("")){
			int amount;
			try {
				amount = Integer.parseInt(amountText);
			} catch (Exception e) {
				this.cbv.getAmountT().setText("");
				return;
			}
			if(amount > this.bid_amount){
				if(amount > this.cbv.getGame().getCurrentPlayer().getAccount_balance()) {
					this.cbv.getAmountT().setText("");
					return;
				}
				this.bid_amount = amount;
			}
			else{
				if((this.bid_amount+1) > this.cbv.getGame().getCurrentPlayer().getAccount_balance()) {
					this.cbv.getAmountT().setText("");
					return;
				}
				this.bid_amount++;
			}
			
			this.boss_pid = this.cbv.getGame().getCurrent_pid();
			this.cbv.getBossLabel().setText(this.cbv.getGame().getCurrentPlayer().getName()
					+ " " + this.bid_amount+"$");
			this.cbv.getAmountT().setText("");
			this.cbv.updateBidView(this.boss_pid, false);
			
			boolean has_pass;
			int tem_pid = this.cbv.getGame().getCurrent_pid();
			do {
				has_pass = false;
				this.cbv.getGame().switchPlayer();
				if(this.cbv.getGame().getCurrent_pid() == tem_pid) {
					confirm();
				}
				for (Integer i : pass_pid) {
					if(i == this.cbv.getGame().getCurrent_pid()) {
						has_pass = true;
						break;
					}
				}
			} while (has_pass);
			this.cbv.updateBidView(this.cbv.getGame().getCurrent_pid(), true);
		}
		else
			System.out.println("请输入金额");
		
	}
	
	/**
	 * 当前玩家跳过竞价环节，不加价。
	 * 将下一名玩家设为当前玩家，并更新竞选面板的显示内容。
	 */
	public void pass(){
		this.cbv.updateBidView(this.cbv.getGame().getCurrent_pid(), false);
		pass_pid.add(this.cbv.getGame().getCurrent_pid());
		boolean has_pass;
		int tem_pid = this.cbv.getGame().getCurrent_pid();
		do {
			has_pass = false;
			this.cbv.getGame().switchPlayer();
			if(this.cbv.getGame().getCurrent_pid() == tem_pid) {
				confirm();
			}
			for (Integer i : pass_pid) {
				if(i == this.cbv.getGame().getCurrent_pid()) {
					has_pass = true;
					break;
				}
			}
		} while (has_pass);
		this.cbv.updateBidView(this.cbv.getGame().getCurrent_pid(), true);
	}
	
	/**
	 * 竞价结束，产生船老大(扣除费用)，
	 * 将船老大设置为游戏的当前玩家，
	 * 关闭竞选窗口，更新主游戏画面（扣除费用，并为船老大对应玩家加框）。
	 */
	public void confirm(){
		Player p = this.cbv.getGame().getPlayerByID(boss_pid);
		p.payMoney(this.bid_amount);
		this.cbv.getGame().getBoss().setPlayer(p);
		
		// 设置当前玩家
		this.cbv.getGame().setCurrent_pid(boss_pid);

		this.cbv.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("bid")){
			this.bid();
		}
		else if(arg0.getActionCommand().equals("pass")){
			this.pass();
		}
		/*else if(arg0.getActionCommand().equals("confirm")){
			//this.confirm();
		}*/
	}

}

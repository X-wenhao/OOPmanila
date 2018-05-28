package manila.model;
import java.awt.Color;
import java.util.ArrayList;

/**
 * 玩家
 * @author 
 *
 */
public class Player {

	/**用户名*/
	private String name;	
	/**用户ID*/
	private int pid;			
	/**余额*/
	private int account_balance;
	/**工人数*/
	private int worker_num;		
	/**颜色*/
	private Color color;	
	/**股票*/
	private ArrayList<Share> shares;
	
	public Player(String name,int pid,int account_balance,int worker_num,Color color) {
		this.name = name;
		this.pid = pid;
		this.account_balance = 30;     //每人初始30
		this.worker_num = 3;       //每位玩家工人数工人数3
		this.color = color;
		this.shares = new ArrayList<Share>();
	}
	
	/**
	 * 工人上船，减少工人数
	 */
	public void deleteWorker() {
		this.worker_num--;
	}
	
	/**
	 * 玩家付钱  如果钱不够则不购买
	 * @param money 扣的钱
	 * @return 付钱成功则返回true  支付失败返回false
	 */
	public boolean payMoney(int money) {		
		if(this.getAccount_balance()<money)
			return false;      //图形界面可根据返回值提示玩家钱不够       
		else 
		{
			this.account_balance = this.account_balance-money;
			return true;
		}
	}
	
	
	/**
	 * 玩家抵押股票
	 */
	public void pledgeShare(String share_name) {
		for (Share i : shares) {
			if (i.getName().equals(share_name)) {
				i.playerPledgeShare(this);
				return;
			} 
		}
	}
	/**
	 * 玩家抵押股票来支付修船费
	 */
	public void pledgeAShare(int fixcount) {
		for (Share i : shares) {
			if (this.account_balance < fixcount) {
				if (!i.isIs_pledge()) {
					i.playerPledgeShare(this);
				}
			}else {
				return;
			}
		}
	}
	/**
	 * 玩家赎回股票
	 * @param share_name
	 */
	public void getShareBack(String share_name) {
		for (Share i : shares) {
			if (i.getName().equals(share_name)) {
				i.playerRansomShare(this);
				return;
			} 
		}
	}
	/**
	 * 玩家获得收益
	 * @param money
	 */
	public void getProfit(int money) {		
		this.account_balance += money;
	}
	
	/**
	 * 玩家可抵押的股票
	 * @return 可抵押的股票名
	 */
	public ArrayList<String> findShareCanPledge(){
		ArrayList<String> sharesCanPledgedName = new ArrayList<String>();
		for(Share i : shares) {
			sharesCanPledgedName.add(i.getName());
		}
		return sharesCanPledgedName;
	}
	/**
	 * 通过股票名查找玩家股票信息
	 * @param share_name
	 * @return 数组num[0]该类股票总数，num[1]该类股票可抵押数
	 */
	public int[] findShareInfoByName(String share_name) {
		int[] num = {0,0};
		for(Share share:shares) {
			if(share.getName().equals(share_name)) {
				num[0] += 1;
				if(!share.isIs_pledge()) {
					num[1] += 1;
				}
			}
		}
		return num;
	}
	/**
	 * 根据编号获得股票实例
	 * @param share_id 股票在shares的编号
	 * @return 返回查找的股票实例
	 */
	public Share findShareFromPlayer(int share_id) {
		return shares.get(share_id);
	}
	//getter setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(int account_balance) {
		this.account_balance = account_balance;
	}
	public int getWorker_num() {
		return worker_num;
	}
	public void setWorker_num(int worker_num) {
		this.worker_num = worker_num;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public ArrayList<Share> getShares() {
		return shares;
	}
	public void setShares(ArrayList<Share> shares) {
		this.shares = shares;
	}

}

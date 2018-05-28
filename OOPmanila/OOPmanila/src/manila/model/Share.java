package manila.model;

/**
 * 股票
 * @author 
 *
 */
public class Share {

	/**股票名*/
	private String name;	
	/**股票价格*/
	private int value = 0;	
	/**是否抵押*/
	private boolean is_pledge = false;
	/**玩家ID未购买设为-1*/
	private int pid = -1;			

	public Share(String name,int value) {			//构造函数
		//To do
		this.name=name;
		this.value=value;
	}
	
	public void playerGetShare(Player player) {		//玩家获得股票 player:获得股票的玩家
		//To do
		this.pid=player.getPid();
		player.getShares().add(this);
	}
	/**
	 * 玩家购买股票
	 * @param player 购买的玩家
	 * @return
	 */
	public boolean playerBuyShare(Player player) {			
		//To do
		if(this.value==0) {
			if(player.payMoney(5)) {
				this.pid=player.getPid();
				player.getShares().add(this);
				return true;
			}
		}
		else if(player.payMoney(this.value)) {
			this.pid=player.getPid();
			player.getShares().add(this);
			return true;
		}
		return false;
	}
	
	public void playerPledgeShare(Player player) {		//玩家抵押股票
		//To do
		player.getProfit(12);
		this.is_pledge=true;
	}
	
	public void playerRansomShare(Player player) {		//玩家赎回股票
		//To do
		if(player.payMoney(15)) {
			this.is_pledge=false;
		}
	}
	
	public void AccountShareValue(Player player) {		//游戏结算，股票价格转为收益，未赎回的股票赎回
		//To do
		if(this.is_pledge) {
		if(player.payMoney(15)) {
			this.is_pledge=false;
		}
		else {
			this.is_pledge=false;
			player.setAccount_balance(player.getAccount_balance()-15);
		}
		}
		player.getProfit(this.value);
		this.setPid(-1);
		
	}
	
	public void shareRise(String goods_name) { 			//股票升值 goods_name:货物名
		//To do
		if(this.name==goods_name) {
			if(this.getValue()==0) {
				this.setValue(5);
			}
			if(this.getValue()==5) {
				this.setValue(10);
			}
			if(this.getValue()==10) {
				this.setValue(20);
			}
			if(this.getValue()==20) {
				this.setValue(30);
			}
		}
	}
	
	
	
	//getter setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean isIs_pledge() {
		return is_pledge;
	}
	public void setIs_pledge(boolean is_pledge) {
		this.is_pledge = is_pledge;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	
}

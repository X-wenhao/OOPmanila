package manila.model;


/**
 * 股票
 * 
 * @author 彭淮越
 *
 */
public class Share {
	/** 股票名 */
	private String name;
	/** 股票价格 */
	private int value = 0;
	/** 是否抵押 */
	private boolean is_pledge = false;
	/** 玩家ID未购买设为-1 */
	private int pid = -1;

	public Share(String name, int value) { // 构造函数
		// To do
		this.name = name;
		this.value = value;
	}
	
	/**
	 *  玩家获得股票 (设置股票的所有者
	 * @param player 获得股票的玩家
	 */
	public void playerGetShare(Player player) {
		// To do
		this.pid = player.getPid();
		player.getShares().add(this);
	}

	/**
	 * 玩家购买股票
	 * 
	 * @param player
	 *            购买的玩家
	 * @return
	 */
	public boolean playerBuyShare(Player player) {
		// To do
		if (this.value == 0) {
			if (player.payMoney(5)) {
				this.pid = player.getPid();
				player.getShares().add(this);
				return true;
			}
		} else if (player.payMoney(this.value)) {
			this.pid = player.getPid();
			player.getShares().add(this);
			return true;
		}
		return false;
	}
	
	/**
	 * 玩家抵押股票
	 * @param player
	 */
	public void playerPledgeShare(Player player) {
		// To do
		player.getProfit(12);
		this.is_pledge = true;
	}
	
	/**
	 * 玩家赎回股票
	 * @param player
	 */
	public void playerRansomShare(Player player) { 
		// To do
		if (player.payMoney(15)) {
			this.is_pledge = false;
		}
	}

	/**
	 * 游戏结算，股票价格转为收益，未赎回的股票赎回
	 * @param player
	 */
	public void accountShareValue(Player player) { 
		// To do
		if (this.is_pledge) {
			if (player.payMoney(15)) {
				this.is_pledge = false;
			} else {
				this.is_pledge = false;
				player.setAccount_balance(player.getAccount_balance() - 15);
			}
		}
		player.getProfit(this.value);
		this.setPid(-1);

	}
	
	/**
	 * 股票升值
	 * @param goods_name
	 */
	public void shareRise(String goods_name) { 
		if (this.name.equals(goods_name)) {
			System.out.println(goods_name+"升值");
			switch (value) {
			case 0:
				value += 5;
				break;
			case 5:
				value += 5;
				break;
			case 10:
				value += 10;
				break;
			case 20:
				value += 10;
				break;
			default:
				break;
			}
		}
	}

	// getter setter
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

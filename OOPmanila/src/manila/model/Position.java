package manila.model;

/**
 * 抽象的位置，代表可以放置工人的地方
 * @author 尧
 *
 */
public class Position {
	/**方位所消耗的金额，无则设为-1*/
	private int price;
	/**放置在位置可获得的收益*/
	private int profit;
	/**用户id无则设为-1*/
	private int pid = -1;

	public Position(int price,int profit) {
		this.price = price;
		this.profit = profit;
	}
	
	// getter and setter
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}

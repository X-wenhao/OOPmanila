package manila.model;

/**
 * 保险公司类
 * 
 * @author 尧
 *
 */
public class Insurance {
	/**游戏类*/
	private Game game;
	/** 可放置工人的位置 */
	private Position position;
	/** 修船厂的实例 */
	private Abport fix_port;

	public Insurance(Game game, Abport fix_port, Position position) { // 构造函数
		this.game = game;
		this.fix_port = fix_port;
		this.position = position;
	}

	/**
	 * 位置是否为空格
	 * 
	 * @return 位置为空则返回true
	 */
	public boolean ifPosAvail() {
		if (position.getPid() != -1) {
			return false;
		}
		return true;
	}

	/**
	 * 玩家放同伙到保险公司，玩家获得10块钱
	 * 
	 * @param player
	 */
	public boolean playerOnInsurance(Player player) {
		if (player.getAccount_balance() == 0 && player.findShareCanPledge().size() == 0)
			return false;
		player.getProfit(10);
		position.setPid(player.getPid());
		player.deleteWorker();
		return true;
	}

	/**
	 * 游戏结算时，如果修船厂有船则扣钱,如果玩家钱不够又还有股票,返回false,
	 */
	public boolean payProfit() {
		if(position.getPid()==-1) {
			return false;
		}
		int boat_num = fix_port.getBoats().size();
		int player_money = game.getPlayers()[position.getPid()].getAccount_balance();
		int player_shares_num = game.getPlayers()[position.getPid()].findShareCanPledge().size();
		int fix_money = 0;
		if (boat_num >= 1 && fix_port.getPositions()[0].getPid() != -1) {
			fix_money += 6;
		}
		if (boat_num >= 2 && fix_port.getPositions()[1].getPid() != -1) {
			fix_money += 8;
		}
		if (boat_num >= 3 && fix_port.getPositions()[2].getPid() != -1) {
			fix_money += 15;
		}
		if (player_money < fix_money) {
			// if(player_shares_num != 0)return false;
			game.getPlayers()[position.getPid()].pledgeAShare(fix_money);
			if (player_money < fix_money) {
				game.getPlayers()[position.getPid()].setAccount_balance(0);
			} else {
				game.getPlayers()[position.getPid()].payMoney(fix_money);
			}
		} else {
			game.getPlayers()[position.getPid()].payMoney(fix_money);
		}
		return true;
	}

	/**
	 * 回合结束，重置保险公司的状态
	 */
	public void resetInsurance() {
		position.setPid(-1);
	}

	// getter setter
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Abport getFix_port() {
		return fix_port;
	}

	public void setFix_port(Abport fix_port) {
		this.fix_port = fix_port;
	}
}

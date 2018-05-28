package manila.model;

/**
 * 搭载货物的船的类
 * @author 肖康
 *
 */
public class Boat {
	/**游戏类*/
	private Game game;
	/** 船上搭载的货物名称 */
	private String cargo_name;
	/** 货物的价值 */
	private int cargo_value;
	/** 船上可以放置工人的位置 */
	private Position[] positions;
	/** 船在海中的位置 */
	private int pos_in_sea = 0;
	/** 船是否到达港口 */
	private boolean is_arrive = false; // 感觉没有必要。。。。//。防止逻辑判断时出麻烦
	/** 船是否被海盗劫持 */
	private boolean is_kidnap = false;

	public Boat(String cargo_name, int cargo_value, Position[] positions, Game game) {
		this.cargo_name = cargo_name;
		this.cargo_value = cargo_value;
		this.positions = positions;
		this.game = game; // fix by tk-ice

	}

	/**
	 * 获得该船当前空着的位置的编号（登船时自动从较低的编号开始）
	 * 
	 * @return 当前编号最小的空位所对应的编号值,无则返回-1
	 */
	public int getAvailPosIndex() {
		for (int i = 0; i < this.positions.length; i++) {
			if (this.positions[i].getPid() == -1)
				return i; // 当前编号最小的空位
		}
		// no position left
		return -1;
	}

	/**
	 * 返回当前船上已有多少个坐了人的位置数
	 * 
	 * @return 已有人的位置数
	 */
	public int getFilledPosNum() {
		int pos_ind = getAvailPosIndex();
		if (pos_ind == -1)
			return this.positions.length;
		else
			return pos_ind;
	}

	/**
	 * 返回当前编号最小的空位对应的登船费用
	 * 
	 * @return 当前编号最小的空位对应的登船费用
	 */
	public int getAvailPosPrice() {
		for (int i = 0; i < this.positions.length; i++) {
			if (this.positions[i].getPid() == -1)
				return this.positions[i].getPrice();
		}
		return -1;
	}

	/**
	 * 玩家上船，付钱
	 * 
	 * @param player
	 */
	public boolean playerAboard(Player player) {
		int availPosPrice = getAvailPosPrice();
		if (player.payMoney(availPosPrice)) {
			if (getAvailPosIndex() == -1)
				return false;
			positions[getAvailPosIndex()].setPid(player.getPid());
			player.setWorker_num(player.getWorker_num() - 1);
			return true;
		}
		return false;
	}

	/**
	 * 海盗上船，海盗不付钱
	 * 
	 * @param player
	 */
	public void playerPirateAboard(Player player, int arround) {
		// TODO
		/*
		 * if (this.getAvailPosIndex()!=-1) { player.payMoney(0); }
		 */
		if (arround == 3) {
			for (int i = 0; i < positions.length; i++) {
				positions[i].setPid(-1);
			}
		}
		positions[getAvailPosIndex()].setPid(player.getPid());
	}

	/**
	 * 移动船，
	 * 
	 * @param move_pos
	 */
	public void moveOn(int move_pos) {
		pos_in_sea += move_pos;
	}

	/**
	 * 船是否到达
	 * 
	 * @return
	 */
	public boolean isArrive() {
		if (this.pos_in_sea < 14) {
			is_arrive = false;
			return false;
		} else {
			is_arrive = true;
			return true;
		}
	}

	/**
	 * 船到达时，如果没被海盗抢夺，支付船员报酬
	 */
	public void payProfit() {
		// for boats that get to the harbor
		// share the money by selling the cargo
		int money_to_share;
		if (this.getPos_in_sea() > 13&&this.getFilledPosNum()!=0) {
			System.out.println("The boat " + this.getCargo_name() + " has arrived");
			money_to_share = this.getCargo_value() / this.getFilledPosNum();
			System.out.println("money_to_share: " + money_to_share);
			for (Position pos : this.getPositions()) {
				if (pos.getPid() != -1)
					// this.players[pos.getSailorID()].receiveProfit(money_to_share);
					game.getPlayerByID(pos.getPid()).getProfit(money_to_share);
			}
		} else
			System.out.println("The boat " + this.getCargo_name() + " has sank!");
	}

	/**
	 * 回合结束，重置船的状态
	 */
	public void resetBoat() {
		this.is_arrive = false;
		this.pos_in_sea = 0;
		this.is_kidnap = false;
		for (Position position : positions) {
			position.setPid(-1);
		}
	}

	public String getCargo_name() {
		return cargo_name;
	}

	public void setCargo_name(String cargo_name) {
		this.cargo_name = cargo_name;
	}

	public int getCargo_value() {
		return cargo_value;
	}

	public void setCargo_value(int cargo_value) {
		this.cargo_value = cargo_value;
	}

	public Position[] getPositions() {
		return positions;
	}

	public void setPositions(Position[] positions) {
		this.positions = positions;
	}

	public int getPos_in_sea() {
		return pos_in_sea;
	}

	public void setPos_in_sea(int pos_in_sea) {
		this.pos_in_sea = pos_in_sea;
	}

	public boolean isIs_kidnap() {
		return is_kidnap;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isIs_arrive() {
		return is_arrive;
	}

	public void setIs_arrive(boolean is_arrive) {
		this.is_arrive = is_arrive;
	}

	public void setIs_kidnap(boolean is_kidnap) {
		this.is_kidnap = is_kidnap;
	}
}

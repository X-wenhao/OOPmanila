package manila.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import java.awt.Color;

/**
 * 游戏的主要逻辑
 * @author 尧
 *
 */
public class Game {
	/** 玩家数组 */
	private Player[] players;
	/** 可以出航的船只 */
	private HashMap<String, Boat> setout_boats;
	/** 所有船只 */
	private HashMap<String, Boat> all_boats;
	/** 随机数产生器 */
	private Random dice_generator;
	/** 游戏是否结束 */
	private boolean is_game_over = false;
	/** 当前游戏处于第几轮 */
	private int current_round = 0;
	/** 是否正在选位置 */
	private boolean is_choosing = false;
	/** 当前正在选位置的玩家ID */
	private int current_pid = 0;
	/** 港口管理员 */
	private Boss boss;
	/** 港口 */
	private Abport port;
	/** 修船厂 */
	private Abport fix_port;
	/** 保险员 */
	private Insurance insurance;
	/** 领航员 */
	private Navigator navigator;
	/** 海盗船 */
	private PirateBoat pirateBoat;
	/** 股票 */
	private ArrayList<Share> shares;

	public Game() { // 构造函数
		players = new Player[] { new Player("文浩", 0, 30, 3, Color.red), new Player("尧尧", 1, 30, 3, Color.yellow),
				new Player("康总", 2, 30, 3, Color.blue), new Player("怀玉", 3, 30, 3, Color.green), };
		all_boats = new HashMap<String, Boat>();
		all_boats.put("玉石", new Boat("玉石", 36,
				new Position[] { new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(5, 0) },
				this));
		all_boats.put("可可", new Boat("可可", 18,
				new Position[] { new Position(1, 0), new Position(2, 0), new Position(3, 0) }, this));
		all_boats.put("人参", new Boat("人参", 24,
				new Position[] { new Position(2, 0), new Position(3, 0), new Position(4, 0) }, this));
		all_boats.put("丝绸", new Boat("丝绸", 30,
				new Position[] { new Position(3, 0), new Position(4, 0), new Position(5, 0) }, this));
		// setout_boats = (HashMap<String,Boat>)all_boats.clone();
		setout_boats = new HashMap<String, Boat>();
		Iterator iterator = all_boats.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			setout_boats.put((String) key, all_boats.get(key));
		}

		dice_generator = new Random();
		boss = new Boss(players[0]);
		port = new Abport(this, new Position[] { new Position(4, 6), new Position(3, 8), new Position(2, 15) });
		fix_port = new Abport(this, new Position[] { new Position(4, 6), new Position(3, 8), new Position(2, 15) });
		insurance = new Insurance(this, fix_port, new Position(0, 10));
		navigator = new Navigator(this, new Position[] { new Position(5, 0), new Position(2, 0) });
		pirateBoat = new PirateBoat(this, new Position[] { new Position(5, 0), new Position(5, 0) });
		shares = new ArrayList<Share>();
		shares.add(new Share("玉石", 0));
		shares.add(new Share("玉石", 0));
		shares.add(new Share("玉石", 0));
		shares.add(new Share("玉石", 0));
		shares.add(new Share("玉石", 0));
		shares.add(new Share("可可", 0));
		shares.add(new Share("可可", 0));
		shares.add(new Share("可可", 0));
		shares.add(new Share("可可", 0));
		shares.add(new Share("可可", 0));
		shares.add(new Share("人参", 0));
		shares.add(new Share("人参", 0));
		shares.add(new Share("人参", 0));
		shares.add(new Share("人参", 0));
		shares.add(new Share("人参", 0));
		shares.add(new Share("丝绸", 0));
		shares.add(new Share("丝绸", 0));
		shares.add(new Share("丝绸", 0));
		shares.add(new Share("丝绸", 0));
		shares.add(new Share("丝绸", 0));

		// 每个玩家获得两张股票
		for (int i = 0; i < 4; i++) {
			int x = 0;
			while (x < 2) {
				Random ran = new Random();
				int r = ran.nextInt(20);
				if (shares.get(r).getPid() == -1) {
					shares.get(r).playerGetShare(players[i]);
					x++;
				}
			}
		}
	}

	/**
	 * 产生一个1-6之间的随机整数（模拟骰子的功能）。
	 * 
	 * @return 1-6之间的随机整数
	 */
	private int rollDice() {
		return dice_generator.nextInt(5) + 1;
	}

	/**
	 * 每回合移动三条船
	 */
	public void diceMoveBoats() {
		for (String key : setout_boats.keySet()) {
			Boat tempB = setout_boats.get(key);
			tempB.moveOn(rollDice());
		}
		current_round++;
	}

	/**
	 * 查找可购买的股票
	 * 
	 * @return 返回股票名称数组
	 */
	public ArrayList<String> findAvilShare() {
		ArrayList<String> tempS = new ArrayList<String>();
		for (Share s : shares) {
			if (s.getPid() == -1) {
				tempS.add(s.getName());
			}
		}
		return tempS;
	}

	/**
	 * 根据玩家的ID返回玩家对象。
	 * 
	 * @param id
	 *            要寻找的玩家ID
	 * @return 玩家对象
	 */
	public Player getPlayerByID(int id) {
		return this.players[id];
	}

	/**
	 * 返回停在13格的船的货物名
	 * 
	 * @return
	 */
	public ArrayList<String> hasBoatOnThirteen() {
		ArrayList<String> boats_name = new ArrayList<String>();
		Iterator iterator = setout_boats.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			Boat temp_boat = setout_boats.get(key);
			if (temp_boat.getPos_in_sea() == 13) {
				boats_name.add(temp_boat.getCargo_name());
			}
		}
		return boats_name;
	}

	/**
	 * 返回当前正在进行操作的玩家对象。
	 * 
	 * @return 当前玩家对象
	 */
	public Player getCurrentPlayer() {
		return this.players[this.current_pid];
	}

	/**
	 * 下个玩家放人,所有玩家切换完一边后,is_choosing设为false
	 */
	public void switchPlayer() {
		this.current_pid = (this.current_pid + 1) % this.players.length;
		if (this.current_pid == this.boss.getPlayer().getPid()) {
			this.is_choosing = false;
		}
	}

	/**
	 * 在所有轮结束后，根据船是否到港以及船上海员的归属，为每位玩家分配收益。（没写保险的结算！）
	 */
	public void calculateProfits() {
		// 货船部分
		for (String key : setout_boats.keySet()) {
			Boat temp_boat = setout_boats.get(key);
			if (temp_boat.isIs_kidnap()) {
				pirateBoat.getProfit(temp_boat.getCargo_value());
				if(temp_boat.isArrive()) {
					for (Share s : shares) {
						s.shareRise(temp_boat.getCargo_name());
					}
				}
			} else {
				if (temp_boat.getPos_in_sea() > 13) {
					temp_boat.setIs_arrive(true);
					temp_boat.payProfit();
					port.boatArrive(temp_boat);
					for (Share s : shares) {
						s.shareRise(temp_boat.getCargo_name());
					}
				} else {
					temp_boat.setIs_arrive(false);
					fix_port.boatArrive(temp_boat);
				}
			}
		}
		port.payProfit();
		fix_port.payProfit();
		// 保险公司有可能会结算失败，所以没写在这
		insurance.payProfit();
	}

	/**
	 * 新的回合
	 */
	public void refreshGame() {
		setout_boats.clear();// (HashMap<String,Boat>)all_boats.clone();

		Iterator iterator = all_boats.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			setout_boats.put((String) key, all_boats.get(key));
		}

		for (String key : this.all_boats.keySet()) {
			this.all_boats.get(key).resetBoat();
		}
		current_round = 0;
		is_choosing = false;
		port.resetPort();
		fix_port.resetPort();
		insurance.resetInsurance();
		navigator.resetNavigator();
		pirateBoat.resetPirateBoat();
		boss.resetBoss();
		for (Player p : players) {
			p.setWorker_num(3);
			p.setCanAboard(true);
		}
		isGameover();
	}

	/**
	 * 游戏是否结束（股价达到30）
	 * 
	 * @return 游戏是否结束
	 */
	public boolean isGameover() {
		for (Share s : shares) {
			if (s.getValue() >= 30) {
				is_game_over = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * 游戏结束时结算各玩家最终金钱
	 */
	public void accountPlayerMoney() {
		for (Player player : players) {
			ArrayList<Share> player_shares = player.getShares();
			for (Share share : player_shares) {
				share.accountShareValue(player);
			}
			if (player.getAccount_balance() < 0)
				player.setAccount_balance(0);
		}
	}

	/**
	 * 游戏结束时，显示获胜玩家
	 * 
	 * @return Player玩家
	 */
	public Player showWinner() {
		Player temp_player = players[0];
		for (Player player : players) {
			if (player.getAccount_balance() > temp_player.getAccount_balance()) {
				temp_player = player;
			}
		}
		return temp_player;
	}
	// getter setter

	public Abport getPort() {
		return port;
	}

	public void setPort(Abport port) {
		this.port = port;
	}

	public Abport getFix_port() {
		return fix_port;
	}

	public void setFix_port(Abport fix_port) {
		this.fix_port = fix_port;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public PirateBoat getPirateBoat() {
		return pirateBoat;
	}

	public void setPirateBoat(PirateBoat pirateBoat) {
		this.pirateBoat = pirateBoat;
	}

	public ArrayList<Share> getShares() {
		return shares;
	}

	public void setShares(ArrayList<Share> shares) {
		this.shares = shares;
	}

	public HashMap<String, Boat> getSetout_boats() {
		return setout_boats;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public HashMap<String, Boat> getAll_boats() {
		return all_boats;
	}

	public void setAll_boats(HashMap<String, Boat> all_boats) {
		this.all_boats = all_boats;
	}

	public Random getDice_generator() {
		return dice_generator;
	}

	public void setDice_generator(Random dice_generator) {
		this.dice_generator = dice_generator;
	}

	public boolean isIs_game_over() {
		return is_game_over;
	}

	public void setIs_game_over(boolean is_game_over) {
		this.is_game_over = is_game_over;
	}

	public int getCurrent_round() {
		return current_round;
	}

	public void setCurrent_round(int current_round) {
		this.current_round = current_round;
	}

	public boolean isIs_choosing() {
		return is_choosing;
	}

	public void setIs_choosing(boolean is_choosing) {
		this.is_choosing = is_choosing;
	}

	public int getCurrent_pid() {
		return current_pid;
	}

	public void setCurrent_pid(int current_pid) {
		this.current_pid = current_pid;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	public void setSetout_boats(HashMap<String, Boat> setout_boats) {
		this.setout_boats = setout_boats;
	}

}

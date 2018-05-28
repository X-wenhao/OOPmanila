package manila.model;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 船老大类
 * @author 彭淮越
 *
 */
public class Boss {
	/**玩家*/
	Player player;		
	/**玩家是否买过股票*/
	private boolean hasBuyShare = false;
	
	public Boss(Player player) {
		this.player = player;
	}
	
	/**
	 * 当上海港负责人的玩家付钱
	 * @return
	 */
	public boolean payBoss() {
		return true;
	}
	
	/**
	 *  海港负责人购买股票 ,只能购买一次
	 * @param share
	 * @return 是否购买成功
	 */
	public boolean bossBuyShare(Share share) { 
		// To do
		if (!hasBuyShare) {
			if (share.playerBuyShare(this.player)) {
				hasBuyShare = true;
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 船老大ban掉一艘船
	 * @param game
	 * @param boat_name ban掉的船的名字
	 */
	public void bossBanBoat(Game game,String boat_name) {
		HashMap setout_boats=game.getSetout_boats();
		setout_boats.remove(boat_name);
		//game.setSetout_boats(setout_boats);
		
	}
	
	/**
	 * 海港管理员决定船只，起始位置
	 * @param game
	 * @param boats_pos String是船名，Integer是船起始位置
	 * @return 如果传入的参数不符合游戏规则，返回false
	 */
	public boolean bossSetBoats(Game game, HashMap<String, Integer> boats_pos) { 
		Iterator iterator = boats_pos.keySet().iterator();
		int a = 0;
		while (iterator.hasNext()) {
			Object key = iterator.next();
			if (boats_pos.get(key) > 5 || boats_pos.get(key) < 0) {
				return false;
			}
			a += boats_pos.get(key);
		}
		if (a != 9) {
			return false;
		}
		Iterator iterator2 = boats_pos.keySet().iterator();
		// Iterator iterator3 = game.getSetout_boats().keySet().iterator();
		
		while (iterator2.hasNext()) {
			Object key2 = iterator2.next();
			// Object key3 = iterator3.next();
			game.getSetout_boats().get(key2).setPos_in_sea(boats_pos.get(key2));
		}

		return true;
	}
	
	/**
	 * 重置boss
	 */
	public void resetBoss() {
		hasBuyShare = false;
	}
	//getter setter
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isHasBuyShare() {
		return hasBuyShare;
	}

	public void setHasBuyShare(boolean hasBuyShare) {
		this.hasBuyShare = hasBuyShare;
	}
}

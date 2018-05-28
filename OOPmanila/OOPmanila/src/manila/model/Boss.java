package manila.model;

import java.util.HashMap;
import java.util.Iterator;

public class Boss {

	/**玩家*/
	Player player;		

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
	
	public boolean bossBuyShare(Share share) {		//海港负责人购买股票 share:购买的股票
		//To do
		if(share.playerBuyShare(this.player)) {
			return true;
		}
		return false;
	}
	
	public void bossBanBoat(Game game,String boat_name) {//boats:储存4条船的数组  boat_id:不能出航的船在数组setout_boats的位置
		//To do
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
	public boolean bossSetBoats(Game game,HashMap<String,Integer> boats_pos) {		//海港负责人决定三条船初始位置,如果boats_pos中的三个数不符合游戏规则，return false。设定setout_boats船在海中的位置
		//To do
		//boats_pos.get(game.getAll_boats());
		Iterator iterator = boats_pos.keySet().iterator();
		int a=0;
        while (iterator.hasNext()) {    
         Object key = iterator.next();
         if(boats_pos.get(key)>5||boats_pos.get(key)<0) {
        	 return false;
         }
         a+=boats_pos.get(key);
        }
        if(a!=9) {
        	return false;
        }
        Iterator iterator2 = boats_pos.keySet().iterator();
        Iterator iterator3 = game.getSetout_boats().keySet().iterator();
        while (iterator2.hasNext()) {    
         Object key2 = iterator2.next();
         Object key3 = iterator3.next();
         game.getSetout_boats().get(key3).setPos_in_sea(boats_pos.get(key2));
        }
        
		return true;
	}
	
	//getter setter
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}

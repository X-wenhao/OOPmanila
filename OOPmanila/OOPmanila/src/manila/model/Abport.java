package manila.model;

import java.util.ArrayList;

/**
 * 抽象的港口类  由此派生出港口和修船厂|突然发现港口和修船厂都用这个类就行了，修船厂扣保险员钱的逻辑在保险结算部分实现
 * @author 
 *
 */

public class Abport {
	private Game game;

	/**港口可放置工人的位置*/
	private Position[] positions;
	/**港口停靠的船*/
	private ArrayList<Boat> boats;

	public Abport(Game game,Position[] positions) {
		this.game = game;
		this.positions = positions;
	}
	
	/**
	 * 获得该港口当前各个放人的位置的状态（是否空的）
	 * @return 按照positions的顺序，位置空的是true，否则false
	 */
	public boolean[] ifPosAvail() {	    //修船厂三个位置,港口三个位置
		ArrayList<Boolean> isEmpty = new ArrayList<Boolean>();
		for(Position p : positions) {
			if(p.getPid()==-1)   
				isEmpty.add(true);    
			else
				isEmpty.add(false);
		}
		
		return new boolean[]{isEmpty.get(0),isEmpty.get(1),isEmpty.get(2)};
	}
	
	/**
	 * 玩家放同伙到港口，扣钱              (这里的港口指的修船厂)
	 * @param player
	 * @param pos_num 船位的编号0，1，2
	 */
	public boolean playerOnPort(Player player, int pos_num) {
		if (player.getAccount_balance() > positions[pos_num].getPrice()
				|| player.getAccount_balance() == positions[pos_num].getPrice()) {
			positions[pos_num].setPid(player.getPid());
			player.payMoney(positions[pos_num].getPrice());
			player.deleteWorker(); 
			return true;
		} else
			return false; // 钱不够返回false
	}
	
	/**
	 * 船只进入海港
	 * @param boat
	 * @return 船进入的船位的编号0，1，2
	 */
	public int boatArrive(Boat boat) {
		this.boats.add(boat);
		return this.boats.size()-1 ;
	}

	/**
	 * 游戏结算时，船到达，支付玩家同伙报酬
	 */
	public void payProfit() {	
		for(Position p :positions) {
			if(p.getPid()!=-1)
				game.getPlayerByID(p.getPid()).getProfit(p.getProfit());
		}
	}
	
	/**
	 * 回合结束，重置港口的状态
	 */
	public void resetPort() {	
		for(int i=0;i<this.positions.length;i++) {
			this.positions[i].setPid(-1);   //将所有港口位置设为空
		}
		for(int i=0;i<this.boats.size();i++) {
			this.boats.get(i).resetBoat();  //重置每条船
		}
	}
	
	//getter setter
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public Position[] getPositions() {
		return positions;
	}

	public void setPositions(Position[] positions) {
		this.positions = positions;
	}

	public ArrayList<Boat> getBoats() {
		return boats;
	}

	public void setBoats(ArrayList<Boat> boats) {
		this.boats = boats;
	}
}

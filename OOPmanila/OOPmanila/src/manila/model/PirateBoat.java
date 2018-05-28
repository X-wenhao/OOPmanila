package manila.model;

/**
 * 海盗船
 * @author 
 *
 */
public class PirateBoat {

	private Game game;
	/**海盗船上可以放置工人的2个位置*/
	private Position[] positions;

	public PirateBoat(Game game,Position[] positions) {
		this.game=game;
		this.positions=positions;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获得海盗船当前各个放人的位置的状态（是否空的）
	 * @return 按照positions的顺序，位置空的是true，否则false
	 */
	public boolean[] ifPosAvail() {	
		boolean a= true ;
		boolean b= true ;
		if(this.getPositions()[0].getPid()!=-1) {
			a=false;
		}
		if(this.getPositions()[1].getPid()!=-1) {
			b=false;
		}
		//TODO
		return new boolean[]{a,b};
	}

	/**
	 * 玩家放同伙到海盗船，先放的是船长，扣钱
	 * @param player
	 */
	public boolean playerOnPirateBoat(Player player) {
		//TODO
		if(this.getPositions()[0].getPid()==-1) {
			if(player.payMoney(positions[0].getPrice())) {
				this.getPositions()[0].setPid(player.getPid());
				player.deleteWorker();
				return true;
			}
			
		}
		else if(this.getPositions()[1].getPid()==-1) {
			if(player.payMoney(positions[1].getPrice())) {
				this.getPositions()[1].setPid(player.getPid());
				player.deleteWorker();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 海盗行动（第三轮海盗劫船后，让船进海港就把船向前移动，否则船沉没）
	 * @param pirate_id 海盗id 海盗船长id为0，
	 * @param boat 船只
	 * @param game_round 游戏是第一轮，第二轮，还是第三轮
	 * @param let_boat_to_port 第三轮海盗劫船后,是否让船进海港
	 */
	public void robBoat(int pirate_id,Boat boat,int game_round,boolean let_boat_to_port) {
		//TODO
		if(game_round==2) {
			if(boat.getAvailPosIndex()!=-1) {
				boat.playerPirateAboard(game.getPlayers()[positions[pirate_id].getPid()]);
				positions[pirate_id].setPid(-1);
			}
		}
		if(game_round==3&&pirate_id==0) {
			boat.setIs_kidnap(true);
			if(let_boat_to_port) {
				boat.setPos_in_sea(14);
				boat.isArrive();
			}
		}
		
	}
	
	/**
	 * 第二轮海盗行动完后，如果船长上了一条货船，另一个海盗成为船长
	 */
	public void refreshPirateBoat() {
		//TODO
		if(positions[0].getPid()==-1&&positions[1].getPid()!=-1) {
			positions[0].setPid(positions[1].getPid());
			positions[1].setPid(-1);
		}
	}
	
	/**
	 * 第三轮海盗劫持了船，平均分钱
	 * @param money  所劫持船的钱
	 */
	public void getProfit(int money) {
		//TODO
		if(positions[0].getPid()!=-1) {
			if(positions[1].getPid()!=-1) {
				game.getPlayers()[positions[0].getPid()].getProfit(money/2);
				game.getPlayers()[positions[1].getPid()].getProfit(money/2);
			}
			else {
				game.getPlayers()[positions[0].getPid()].getProfit(money);
			}
		}
	}
	/**
	 * 回合结束，重置海盗船的状态
	 */
	public void resetPirateBoat() {	
		//TODO
		positions[0].setPid(-1);
		positions[1].setPid(-1);
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
}

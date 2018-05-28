package manila.model;

import java.util.ArrayList;

/**
 * 领航员类
 * @author 尧
 *
 */
public class Navigator {
	/**游戏类*/
	private Game game;
	/**领航岛上可以放置工人的2个位置*/
	private Position[] positions;
	/**领航员剩余操作数*/
	private int[] num= {1,2};
	
	public Navigator(Game game,Position[] positions) {
		this.game = game;
		this.positions = new Position[]{new Position(2,0) , new Position(5,0)};
	}

	/**
	 * 获得领航岛当前各个放人的位置的状态（是否空的）
	 * @return 按照positions的顺序，位置空的是true，否则false
	 */
	public boolean[] ifPosAvail() {	
		boolean[] pos_avail = new boolean[]{true,true};
		for(int i=0;i<positions.length;i++) {
			if(positions[i].getPid() != -1) {
				pos_avail[i] = false;
			}
		}
		return pos_avail;
	}

	/**
	 * 玩家放同伙到领航岛，扣钱
	 * @param player
	 * @param pos_num 领航岛的编号0，1 领航长id为0
	 */
	public boolean playerOnNavigator(Player player,int pos_num) {
		if(player.payMoney(positions[pos_num].getPrice())) {
			positions[pos_num].setPid(player.getPid());
			player.deleteWorker();
			return true;
		}
		return false;
	}
	
	/**
	 * 领航员移动船只
	 * @param navigator_id 领航员id 领航长id为0，
	 * @param boats 移动的船的数组，1或2条船
	 * @param move_dir 移动距离
	 * @return 根据游戏规则，领航长可移动1船2格，或移动2船各1格。领航员只可移动1船1格。可以不移动。如果传入数据不符合规则返回false
	 */
	public int[] moveBoat(Boat boat,int i) {
		if(!ifPosAvail()[1]&&num[1]!=0) {
			boat.setPos_in_sea(boat.getPos_in_sea()+i);
			num[1]--;
		}else if(!ifPosAvail()[0]&&num[0]!=0) {
			boat.setPos_in_sea(boat.getPos_in_sea()+i);
			num[0]--;
		}
		
		
		return num;
	}
	
	/**
	 * 回合结束，重置领航岛的状态
	 */
	public void resetNavigator() {	
		for(Position p:positions) {
			p.setPid(-1);
		}
		num[0]=1;
		num[1]=2;
	}
	//getter setter
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int[] getNum() {
		return num;
	}	

	
	public Position[] getPositions() {
		return positions;
	}
	

	public void setPositions(Position[] positions) {
		this.positions = positions;
	}
}

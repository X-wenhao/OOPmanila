package manila.model;

import java.util.ArrayList;

/**
 * 领航员
 * @author 
 *
 */
public class Navigator {

	private Game game;
	/**领航岛上可以放置工人的2个位置*/
	private Position[] positions;
	public Navigator(Game game,Position[] positions) {
		this.game = game;
		positions = new Position[]{new Position(5,0) , new Position(2,0)};
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
	public boolean moveBoat(int navigator_id, ArrayList<Boat> boats, ArrayList<Integer> move_dir) {
		if (boats.size() != move_dir.size())
			return false;
		if (navigator_id == 0) {
			if (boats.size() == 1) {
				if (move_dir.get(0).intValue() > 2 || move_dir.get(0).intValue() < -2) {
					return false;
				}
			}else {
				for(Integer integer:move_dir) {
					if (integer.intValue() > 1 || integer.intValue() < -1) {
						return false;
					}
				}
			}
		} else if (navigator_id == 1) {
			if (boats.size() == 2)
				return false;
			if (move_dir.get(0).intValue() > 1 || move_dir.get(0).intValue() < -1) {
				return false;
			}
		}

		for(int num=0;num<boats.size();num++) {
			boats.get(num).moveOn(move_dir.get(num).intValue());
		}
		return true;
	}
	
	/**
	 * 回合结束，重置领航岛的状态
	 */
	public void resetNavigator() {	
		for(Position p:positions) {
			p.setPid(-1);
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
}

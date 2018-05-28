package manila.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.model.*;

/**
 * 单个小船的视图
 * @author tk-ice
 *
 */
public class BoatView extends JPanel {
	/** 小船宽度 */
	private static int INFO_W = 40;
	/** 小船高度 */
	private static int INFO_H = 160;
	/** 小船向上移动高度 */
	private static int MOVE_H = 30;
	/** 船体背景色 */
	private static Color boat_color = new Color(220, 220, 220);
	/** 位置背景色 */
	private static Color pos_color = new Color(245,245,245);

	/** x坐标 */
	private int x = 200;
	/** y坐标 */
	private int y = 300;

	private JLabel value_label;
	private ArrayList<JLabel> pos_labels;

	private Boat boat;
	private Game game;

	public BoatView(Boat boat, Game game) {
		// TODO Auto-generated constructor stub
		this.boat = boat;
		this.game = game;

		this.setBackground(boat_color);
		this.setSize(this.INFO_W, this.INFO_H);
		this.setLayout(null);

		value_label = new JLabel("" + boat.getCargo_value(),JLabel.CENTER);
		value_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		value_label.setBackground(boat_color);
		value_label.setBounds(5, 10, INFO_W - 10, 20);
		value_label.setOpaque(true);
		this.add(value_label);
		
		pos_labels = new ArrayList<>();
		Position[] positions = boat.getPositions();

		for (int i = 0; i < positions.length; i++) {
			JLabel temp_label = new JLabel("" + positions[i].getPrice(),JLabel.CENTER);
			temp_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
			temp_label.setBackground(pos_color);
			temp_label.setOpaque(true);
			temp_label.setBounds(5, 40 + 30 * i, INFO_W - 10, 20);
			pos_labels.add(temp_label);
			this.add(temp_label);
		}


	}
	
	/**
	 * 玩家上船的视图显示
	 */
	public void getOnBoat() {
		pos_labels.get(boat.getAvailPosIndex()).setForeground(game.getCurrentPlayer().getColor());
	}

	/**
	 * 回合结束后重置boatview
	 */
	public void reset() {

	}
	
	/**
	 * 设置船位置
	 * @param x 左上角x
	 * @param y 左上角y
	 */
	public void setPos(int x,int y) {
		this.x=x;
		this.y=y;
		this.setBounds(x, y, this.INFO_W,this.INFO_H);
	}
	
	/**
	 * boatview移动
	 */
	public void moveBv() {
		
		
		this.setBounds(this.x,this.y-(boat.getPos_in_sea()%14)*this.MOVE_H, this.INFO_W, this.INFO_H);
	}
	
	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat boat) {
		this.boat = boat;
	}


}

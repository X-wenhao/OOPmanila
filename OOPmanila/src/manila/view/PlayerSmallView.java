package manila.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.model.Player;

/**
 * 选择船老大时玩家的信息展示界面
 * @author 尧
 */
public class PlayerSmallView extends JPanel {
	/** 玩家名称标签 */
	private JLabel nameV;
	/** 该界面对应的玩家对象引用 */
	private Player player;
	/** 当前面板是否显示边框 */
	private boolean active;

	/**
	 * 玩家视图构造函数
	 * 
	 * @param p
	 *            玩家对象的引用
	 */
	public PlayerSmallView(Player p) {
		this.active = false;

		this.setOpaque(false);
		this.player = p;
		this.nameV = new JLabel("",JLabel.CENTER);
		this.nameV.setOpaque(false);
		nameV.setIcon(new ImageIcon(
				new ImageIcon("src/玩家头像/player"+player.getPid()+".png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
		this.nameV.setFont(new Font("SansSerif", Font.PLAIN, 32));

		//this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

		this.add(this.nameV);

		this.setBackground(Color.WHITE);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		if (active) {
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		} else {
			this.setBorder(null);
		}
	}

}

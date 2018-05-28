package manila.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import manila.controller.ChoosingBossController;
import manila.model.Game;
import manila.model.Player;
import sun.font.GlyphLayout.GVData;

/**
 * Manila 游戏选举船老大的窗口。
 * 
 * @author 尧
 */
public class ChoosingBossView extends JFrame {
	/** 游戏类 */
	private Game game;
	/** 游戏主页面 */
	private GameView gameView;
	/**控制器*/
	private ChoosingBossController cbc;

	/** 主面板 */
	private JPanel content;
	/** 玩家名称面板（用来装简略版PlayerView） */
	private JPanel playerView;
	/** 当前船老大姓名和对应金额面板 */
	private JPanel bossView;
	/** 选择船老大的面板 */
	private JPanel chooseView;
	/** 用于显示当前船老大姓名和对应金额 */
	private JLabel bossLabel;

	/** 竞选金额输入框 */
	private JTextField amountT;
	/** 竞选按钮 */
	private JButton bidB;
	/** 跳过本轮竞选按钮 */
	private JButton passB;
	/** 结束竞选按钮 */
	private JButton confirmB;

	/** 所有PlayerView的数组 */
	private PlayerSmallView[] pvList;

	public ChoosingBossView(Game g, GameView gV) {
		this.game = g;
		this.gameView = gV;
		this.cbc = new ChoosingBossController(this);
		
		this.setResizable(false);

		this.content = new JPanel() {
			@Override
			public void paint(Graphics g) {
				// super.paint(g);
				g.drawImage(new ImageIcon("src/竞选页面/竞选背景.png").getImage(), 0, 0, 600, 300, this);
				super.paintChildren(g);
				// super.paintBorder(g);

			}
		};

		this.playerView = new JPanel();
		playerView.setBounds(0, 0, 600, 150);
		playerView.setLayout(new GridLayout(1, 4));
		playerView.setOpaque(false);
		this.bossView = new JPanel();
		bossView.setBounds(0, 150, 600, 75);
		bossView.setOpaque(false);
		this.chooseView = new JPanel();
		chooseView.setBounds(0, 225, 600, 75);
		chooseView.setLayout(null);
		chooseView.setOpaque(false);

		this.setContentPane(this.content);
		this.content.setPreferredSize(new Dimension(600, 300));

		this.content.setLayout(null);

		this.pvList = new PlayerSmallView[this.game.getPlayers().length];
		for (int i = 0; i < this.game.getPlayers().length; i++) {
			this.pvList[i] = new PlayerSmallView(this.game.getPlayers()[i]);
			this.playerView.add(this.pvList[i]);
			if (i == 0) {
				this.pvList[i].setActive(true);
			}
		}
		this.content.add(this.playerView);

		JLabel label = new JLabel("最高价:");
		label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		this.bossView.add(label);
		this.bossLabel = new JLabel("xxxx");
		this.bossLabel.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
		this.bossView.add(bossLabel);
		this.content.add(this.bossView, BorderLayout.CENTER);

		this.amountT = new JTextField();
		amountT.setBounds(150, 15, 150, 50);
		amountT.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		amountT.setHorizontalAlignment(JTextField.CENTER);
		amountT.setOpaque(false);
		this.bidB = new JButton();
		bidB.setBounds(300, 0, 75, 75);
		bidB.setBorder(null);
		bidB.setOpaque(false);
		bidB.setBackground(Color.GRAY);
		bidB.setIcon(new ImageIcon(
				new ImageIcon("src/竞选页面/竞选.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
		this.passB = new JButton();
		passB.setBounds(375, 0, 75, 75);
		passB.setBorder(null);
		passB.setOpaque(false);
		passB.setBackground(Color.GRAY);
		passB.setIcon(new ImageIcon(
				new ImageIcon("src/竞选页面/pass.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		// this.confirmB = new JButton("确认");

		this.bidB.addActionListener(this.cbc);
		this.bidB.setActionCommand("bid");
		this.passB.addActionListener(this.cbc);
		this.passB.setActionCommand("pass");
		// this.confirmB.addActionListener(this.cbc);
		// this.confirmB.setActionCommand("confirm");

		this.chooseView.add(this.amountT);
		this.chooseView.add(this.bidB);
		this.chooseView.add(this.passB);
		// this.chooseView.add(this.confirmB);

		this.content.add(this.chooseView);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("开始竞选吧");
		this.pack();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
	
	/**
	 * 更新玩家方块
	 * @param pid
	 * @param active
	 */
	public void updateBidView(int pid, boolean active) {
		for (PlayerSmallView pv : this.pvList) {
			Player p = pv.getPlayer();
			if (p.getPid() == pid) {
				pv.setActive(active);
			}
		}
	}
	
	/**
	 * 选完船老大刷新页面
	 */
	public void refresh() {
		this.bossLabel.setText("");
		this.amountT.setText("");
		for (int i = 0; i < game.getPlayers().length; i++) {
			updateBidView(game.getPlayers()[i].getPid(), game.getPlayers()[i].equals(game.getBoss().getPlayer()));
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		content.repaint();
	}

	
	public GameView getGameView() {
		return gameView;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public JTextField getAmountT() {
		return amountT;
	}

	public void setAmountT(JTextField amountT) {
		this.amountT = amountT;
	}

	public JLabel getBossLabel() {
		return bossLabel;
	}

	public void setBossLabel(JLabel bossLabel) {
		this.bossLabel = bossLabel;
	}

}

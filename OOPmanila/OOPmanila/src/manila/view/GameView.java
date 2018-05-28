package manila.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import manila.controller.*;
import manila.model.Boat;
import manila.model.Game;
import manila.model.Player;
import manila.model.Share;

/**
 * 游戏主界面，包含main函数
 */
public class GameView extends JPanel {
	/** 游戏类 */
	private Game game;
	/** 所有船的视图 */
	private HashMap<String, BoatView> boatviews;
	/** 出航的船的名称列表 */
	private ArrayList<String> out_boat_names;
	/** 玩家信息视图 */
	private HashMap<String, PlayerView> playerviews;
	/** 展示玩家信息的窗口 */
	private JPanel playersView;
	/** 展示玩家股票信息的窗口 */
	private JPanel share_view;
	/** 海盗船视图 */
	private JPanel pirate_boat_view;
	/** 领航员视图 */
	private JPanel navigator_view;
	/** 保险视图 */
	private JPanel insurance_view;
	/** 股票信息视图 */
	private JPanel share_info_view;
	/** 前进按钮 */
	private JButton go_button;
	/** 修船厂和港口视图 */
	private FixAndPortView fix_port_view;
	/** 海盗船、保险、领航员panel背景色 */
	private static Color back_color = new Color(220, 220, 220);
	/** 位置背景色 */
	private static Color pos_color = new Color(245, 245, 245);

	public GameView() {
		this.game = new Game();
		this.setLayout(null);
		this.setSize(700, 800);

		this.initBoatViews();
		this.initPlayerViews();
		this.initSellView();
		this.initPirateBoat();
		this.initNavigatorView();
		this.initInsuranceView();
		this.initShareInfoView();

		go_button = new JButton("前进");
		go_button.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		go_button.setBounds(400, 750, 300, 50);
		go_button.addActionListener(new DiceController(this));
		this.add(go_button);

		fix_port_view = new FixAndPortView();
		fix_port_view.setBounds(0, 0, 400, 220);
		this.add(fix_port_view);
	}

	/**
	 * 对玩家的显示在界面上的相关信息进行更新
	 * 
	 * @param pid
	 *            对应玩家的ID
	 * @param active
	 *            是否标记此玩家为当前玩家
	 */
	public void updatePlayersView(int pid, boolean active) {
		Player p = game.getPlayerByID(pid);
		PlayerView pv = this.playerviews.get(p.getName());
		pv.getScoreV().setText(p.getAccount_balance() + "$");
		pv.getWorker_nbV().setText(p.getWorker_num() + "");
		
		pv.setActive(active);
	}
	
	public void updateBoatViews() {
		for(String boat_name :this.out_boat_names) {
			Boat boat=game.getAll_boats().get(boat_name);
			BoatView bv=boatviews.get(boat_name);
			bv.moveBv();
			
			System.out.println(bv.getX()+"   "+bv.getY());
		}
		
	}

	public Game getGame() {
		return game;
	}

	public HashMap<String, BoatView> getBoatviews() {
		return boatviews;
	}

	public ArrayList<String> getOut_boat_names() {
		return out_boat_names;
	}

	public HashMap<String, PlayerView> getPlayerviews() {
		return playerviews;
	}

	public JPanel getPlayersView() {
		return playersView;
	}

	public JPanel getShare_view() {
		return share_view;
	}

	public JPanel getPirate_boat_view() {
		return pirate_boat_view;
	}

	public JPanel getNavigator_view() {
		return navigator_view;
	}

	public JPanel getInsurance_view() {
		return insurance_view;
	}

	public JPanel getShare_info_view() {
		return share_info_view;
	}

	public JButton getGo_button() {
		return go_button;
	}

	public FixAndPortView getFix_port_view() {
		return fix_port_view;
	}

	/**
	 * 初始化boatviews
	 */
	private void initBoatViews() {
		boatviews = new HashMap<>();
		out_boat_names = new ArrayList<>();
		out_boat_names.add("人参");
		out_boat_names.add("玉石");
		out_boat_names.add("可可");
		for (String key : game.getAll_boats().keySet()) {
			BoatView temp = new BoatView(game.getAll_boats().get(key), game);
			boatviews.put(key, temp);
		}

		this.selectBoatViews();

	}

	/**
	 * 添加出航的船的视图
	 */
	private void selectBoatViews() {

		for (String key : boatviews.keySet()) {
			this.remove(boatviews.get(key));
		}

		int x = 105;
		int y = 630;
		for (String key : out_boat_names) {
			boatviews.get(key).setPos(x, y);
			this.add(boatviews.get(key));
			x += 80;
		}
	}

	/**
	 * 初始化玩家视图
	 */
	private void initPlayerViews() {
		this.playersView = new JPanel();
		this.playersView.setLayout(new GridLayout(5, 1));
		this.playersView.setBounds(400, 0, 300, 250);
		;

		JLabel text = new JLabel("玩家信息", JLabel.CENTER);
		text.setHorizontalTextPosition(SwingConstants.LEFT);
		text.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
		this.playersView.add(text);

		Player[] players = this.game.getPlayers();
		this.playerviews = new HashMap<>();
		for (int i = 0; i < 4; i++) {
			PlayerView pv = new PlayerView(players[i], true);
			if (this.game.getCurrent_pid() == players[i].getPid()) {
				pv.setActive(true);
			}
			this.playerviews.put(players[i].getName(), pv);
			this.playersView.add(pv);
		}

		this.add(playersView);

	}

	/**
	 * 初始化抵押视图
	 */
	private void initSellView() {

		this.share_view = new JPanel();
		this.share_view.setLayout(null);
		this.share_view.setBounds(400, 250, 300, 300);
		this.add(share_view);

		JLabel info_label = new JLabel("玩家股票信息", JLabel.CENTER);
		info_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
		info_label.setBounds(0, 0, 300, 50);
		this.share_view.add(info_label);

		JPanel list_panel = new JPanel();
		list_panel.setBounds(0, 50, 300, 200);
		list_panel.setLayout(new GridLayout(1, 2));
		this.share_view.add(list_panel);

		JList<String> player_list = new JList<>();
		player_list.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		Player[] players = game.getPlayers();
		DefaultListModel<String> player_model = new DefaultListModel<>();
		player_list.setFixedCellHeight(50);
		player_list.setModel(player_model);
		for (int i = 0; i < players.length; i++) {
			player_model.addElement(players[i].getName());
		}
		list_panel.add(player_list);

		JList<String> share_list = new JList<>();
		share_list.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		String[] data = { "玉石", "人参", "可可", "丝绸", "清空" };
		share_list.setListData(data);
		share_list.setFixedCellHeight(40);
		share_list.setPreferredSize(new Dimension(150, 200));
		list_panel.add(share_list);

		JPanel show_panel = new JPanel();
		show_panel.setLayout(null);
		show_panel.setBounds(0, 250, 300, 50);
		this.share_view.add(show_panel);

		JLabel share_info_label = new JLabel("null", JLabel.CENTER);
		share_info_label.setName("share_info_label");
		share_info_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		share_info_label.setBounds(0, 0, 150, 50);
		show_panel.add(share_info_label);

		JButton sale_button = new JButton("抵押");
		sale_button.setName("抵押");
		sale_button.setBounds(150, 0, 75, 50);
		show_panel.add(sale_button);
		JButton get_button = new JButton("赎回");
		get_button.setName("赎回");
		get_button.setBounds(225, 0, 75, 50);
		show_panel.add(get_button);

		// player_list.addListSelectionListener(new
		// ListSelectController(share_info_label,player_list,share_list,game));
		share_list.addListSelectionListener(new ListSelectController(share_info_label, player_list, share_list, game));

		sale_button.addActionListener(new PledgeController(this, sale_button, player_list, share_list, share_info_label));

		get_button.addActionListener(new PledgeController(this, get_button, player_list, share_list, share_info_label));
	}

	/**
	 * 初始化海盗船视图
	 */
	private void initPirateBoat() {
		pirate_boat_view = new JPanel();
		pirate_boat_view.setLayout(null);
		pirate_boat_view.setBounds(15, 240, 40, 110);
		pirate_boat_view.setBackground(back_color);
		pirate_boat_view.setOpaque(true);
		this.add(pirate_boat_view);

		JLabel name = new JLabel("海盗", JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		pirate_boat_view.add(name);

		JLabel pos1 = new JLabel("5", JLabel.CENTER);
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		pos1.setOpaque(true);
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		pirate_boat_view.add(pos1);

		JLabel pos2 = new JLabel("5", JLabel.CENTER);
		pos2.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		pos2.setOpaque(true);
		pos2.setBounds(5, 75, 30, 30);
		pos2.setBackground(pos_color);
		pirate_boat_view.add(pos2);
	}

	/**
	 * 初始化领航员视图
	 */
	private void initNavigatorView() {
		navigator_view = new JPanel();
		navigator_view.setLayout(null);
		navigator_view.setBounds(15, 360, 40, 110);
		navigator_view.setBackground(back_color);
		navigator_view.setOpaque(true);
		this.add(navigator_view);

		JLabel name = new JLabel("领航", JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		navigator_view.add(name);

		JLabel pos1 = new JLabel("2", JLabel.CENTER);
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		pos1.setOpaque(true);
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		navigator_view.add(pos1);

		JLabel pos2 = new JLabel("5", JLabel.CENTER);
		pos2.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		pos2.setOpaque(true);
		pos2.setBounds(5, 75, 30, 30);
		pos2.setBackground(pos_color);
		navigator_view.add(pos2);
	}

	/**
	 * 初始化保险视图
	 */
	private void initInsuranceView() {
		insurance_view = new JPanel();
		insurance_view.setLayout(null);
		insurance_view.setBounds(15, 480, 40, 75);
		insurance_view.setBackground(back_color);
		insurance_view.setOpaque(true);
		this.add(insurance_view);

		JLabel name = new JLabel("保险", JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		insurance_view.add(name);

		JLabel pos1 = new JLabel("10", JLabel.CENTER);
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		pos1.setOpaque(true);
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		insurance_view.add(pos1);

	}

	/**
	 * 初始化股票信息视图
	 */
	private void initShareInfoView() {
		this.share_info_view = new JPanel();
		share_info_view.setLayout(new GridLayout(4, 1));
		share_info_view.setBounds(400, 550, 300, 200);
		this.add(share_info_view);

		ArrayList<Share> shares = game.getShares();

		for (int i = 0; i < 20; i += 5) {
			JLabel temp_label = new JLabel("null", JLabel.CENTER);
			temp_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
			share_info_view.add(temp_label);
			String info = "";
			info += shares.get(i).getName() + " : ";
			info += "$" + shares.get(i).getValue();
			temp_label.setText(info);
		}
	}

	/**
	 * 画出大海（一组线段）
	 * 
	 * @param g2
	 *            图形类
	 */
	private void drawSea(Graphics2D g2) {
		for (int i = 0; i <= 13; i++) {
			g2.draw(new Line2D.Double(75, 240 + i * 30, 75 + 270, 240 + i * 30));
			g2.drawString(13 - i + "", 75 + 270, 240 + i * 30);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		this.drawSea(g2);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame mw = new JFrame();
		mw.setSize(700, 840);
		mw.setTitle("Manila");
		GameView gv = new GameView();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setContentPane(gv);
		// mw.pack();
		mw.setVisible(true);

	}

}

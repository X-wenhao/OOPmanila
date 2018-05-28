package manila.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;
import com.sun.xml.internal.ws.assembler.jaxws.AddressingTubeFactory;

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
	/** 新的回合按钮 */
	private JButton next_pass;
	/** 修船厂和港口视图 */
	private FixAndPortView fix_port_view;
	/** 选择船老大的Frame */
	private ChoosingBossView choosing_boss_view;
	/** 船老大行使权力的页面 */
	private BossInteractor boss_interactor;
	/** 游戏流程信息 */
	private JTextArea gameStatus;
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
		this.initStatusText();

		go_button = new JButton();
		go_button.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		go_button.setBounds(400, 700, 150, 100);
		go_button.addActionListener(new DiceController(this));
		go_button.setEnabled(false);
		go_button.setOpaque(false);
		go_button.setBackground(Color.GRAY);
		go_button.setBorder(null);
		go_button.setIcon(new ImageIcon(new ImageIcon("src/骰子.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
		this.add(go_button);

		next_pass = new JButton();
		next_pass.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		next_pass.setBounds(550, 700, 150, 100);
		next_pass.addActionListener(new NextController(this));
		next_pass.setEnabled(false);
		next_pass.setOpaque(false);
		next_pass.setBackground(Color.GRAY);
		next_pass.setIcon(new ImageIcon(new ImageIcon("src/nextPass.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
		next_pass.setBorder(null);
		this.add(next_pass);

		fix_port_view = new FixAndPortView(this);
		fix_port_view.setBounds(0, 0, 400, 220);
		this.add(fix_port_view);

		this.choosing_boss_view = new ChoosingBossView(game, this);
		this.boss_interactor = new BossInteractor(game, this);

		// choosingBoss();
	}

	/**
	 * 新的回合重置gv
	 */
	public void refreshGameView() {
		for (String bv : this.boatviews.keySet()) {
			this.boatviews.get(bv).refreshBv();
		}

		JPanel[] panels = { this.pirate_boat_view, this.navigator_view, this.insurance_view };
		for (JPanel to_change : panels) {
			for (int i = 0; i < to_change.getComponentCount(); i++) {
				JLabel label = (JLabel) to_change.getComponent(i);
				label.setBackground(this.pos_color);
				label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
		}

		for (String name : playerviews.keySet()) {
			updatePlayersView(playerviews.get(name).getPlayer().getPid(), playerviews.get(name).isActive());
		}

		updateShareInfo();
		fix_port_view.refreshFixAndPort();
		go_button.setEnabled(false);
		next_pass.setEnabled(false);
		
		String info="\n~~~~~~~~~~~~~~~\n";
		for(String boat:game.getAll_boats().keySet()) {
			info+=boat+":"+game.getAll_boats().get(boat).getCargo_value()+";";
		}
		info+="\n";
		addGameInfo(info);
	}
	
	/**
	 * 更新前进button与新回合button的状态
	 */
	public void updateButton() {
		if(game.isIs_choosing()) {
			go_button.setEnabled(false);
		}else {
			go_button.setEnabled(true);
		}
		
		if(game.getCurrent_round()==3) {
			next_pass.setEnabled(true);
		}else {
			next_pass.setEnabled(false);
		}
	}
	
	/**
	 * 更新股票的黑市信息
	 */
	public void updateShareInfo() {
		ArrayList<Share> shares = game.getShares();
		for (int i = 0; i < 20; i += 5) {
			JLabel temp_label = (JLabel) share_info_view.getComponent(i / 5);
			String info = "<html>";
			info += shares.get(i).getName() + "<br>";
			info += "&nbsp; " + shares.get(i).getValue() + "</html>";
			temp_label.setText(info);
		}
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

	/**
	 * 刷新船视图
	 * 
	 * @param bv_tochange
	 *            待刷新的船
	 */
	public void updateBoatViews(BoatView bv_tochange) {
		if (bv_tochange != null) {
			for (int i = 0; i < bv_tochange.getBoat().getPositions().length; i++) {

				int pid = bv_tochange.getBoat().getPositions()[i].getPid();
				if (pid != -1) {
					bv_tochange.getComponent(i + 1).setBackground(game.getPlayerByID(pid).getColor());
					((JLabel)bv_tochange.getComponent(i+1)).setBorder(BorderFactory.createLineBorder(game.getPlayerByID(pid).getColor(),2));
				} else {
					bv_tochange.getComponent(i + 1).setBackground(pos_color);
					((JLabel)bv_tochange.getComponent(i+1)).setBorder(BorderFactory.createLineBorder(Color.GRAY));
				}
			}
		}

		for (String boat_name : this.out_boat_names) {
			Boat boat = game.getAll_boats().get(boat_name);
			BoatView bv = boatviews.get(boat_name);
			bv.moveBv();
		}

	}

	/**
	 * 刷新海盗船视图
	 */
	public void updatePirteBoat() {
		boolean[] if_avail = game.getPirateBoat().ifPosAvail();
		for (int i = 0; i < 2; i++) {
			JLabel to_change = (JLabel) this.pirate_boat_view.getComponent(i + 1);
			if (!if_avail[i]) {
				int pid = game.getPirateBoat().getPositions()[i].getPid();
				to_change.setBackground(game.getPlayerByID(pid).getColor());
				to_change.setBorder(BorderFactory.createLineBorder(game.getPlayerByID(pid).getColor(),2));
			} else {
				to_change.setBackground(pos_color);
				to_change.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}

		}
	}
	
	/**
	 * 添加出航的船的视图
	 */
	public void selectBoatViews() {

		for (String key : boatviews.keySet()) {
			this.remove(boatviews.get(key));
		}
		this.out_boat_names.clear();
		String info = "本次出航的船是 ";
		for (String name : game.getSetout_boats().keySet()) {
			info += name + " ";
			out_boat_names.add(name);
		}
		try {
			this.addGameInfo(info + "\n");
		} catch (Exception e) {
			// TODO: handle exception
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
	 *在显示栏中添加信息
	 * @param info 要添加的信息（注意换行
	 */
	public void addGameInfo(String info) {
		this.gameStatus.setText(this.gameStatus.getText() + info);
	}
	
	/**
	 *游戏结束  展示胜利者 
	 * @param p胜利的玩家
	 */
	public void showWinner(Player p) {
		for(Player player:game.getPlayers()) {
			updatePlayersView(player.getPid(), this.getPlayerviews().get(player.getName()).isActive());
		}
		
		addGameInfo("\n~~~~~~~~~~~~~\n");
		addGameInfo("恭喜 " +p.getName()+" 击败其余玩家...\n" );
		addGameInfo(p.getName()+":恕我之言，在座各位，都是渣渣......\n");
		
		this.go_button.setEnabled(false);
		this.next_pass.setEnabled(false);
		this.choosing_boss_view.dispose();
		this.boss_interactor.dispose();
	}
	
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		g.drawImage(new ImageIcon("src/海面.jpg").getImage(), 0, 0, 700, 800, this);
		super.paintChildren(g);
		//super.paintBorder(g);
		
		for (String name : out_boat_names) {
			this.boatviews.get(name).repaint();
		}
		Graphics2D g2 = (Graphics2D) g;

		this.drawSea(g2);

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

	public JButton getNext_pass() {
		return next_pass;
	}

	public FixAndPortView getFix_port_view() {
		return fix_port_view;
	}

	public ChoosingBossView getChoosingBossView() {
		return this.choosing_boss_view;
	}

	public BossInteractor getBossInteractor() {
		return boss_interactor;
	}

	public static Color getPos_color() {
		return pos_color;
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
			temp.addMouseListener(new PlaceWorkerController(temp, "船", this));
		}

		this.selectBoatViews();

	}

	/**
	 * 初始化玩家视图
	 */
	private void initPlayerViews() {
		this.playersView = new JPanel();
		this.playersView.setLayout(new GridLayout(5, 1));
		this.playersView.setBounds(400, 0, 300, 200);
		this.playersView.setOpaque(false);

		JLabel text = new JLabel("玩家信息", JLabel.CENTER);
		text.setHorizontalTextPosition(SwingConstants.LEFT);
		text.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
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
		this.share_view.setBounds(400, 200, 300, 200);
		this.share_view.setOpaque(false);
		this.add(share_view);

		JLabel info_label = new JLabel("玩家股票信息", JLabel.CENTER);
		info_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		info_label.setBounds(0, 0, 300, 30);
		info_label.setOpaque(false);
		this.share_view.add(info_label);

		JPanel list_panel = new JPanel();
		list_panel.setBounds(0, 30, 300, 140);
		list_panel.setLayout(new GridLayout(1, 2));
		list_panel.setOpaque(false);
		this.share_view.add(list_panel);

		JList<String> player_list = new JList<>();
		player_list.setOpaque(false);
		((JLabel)player_list.getCellRenderer()).setOpaque(false);
		player_list.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		Player[] players = game.getPlayers();
		DefaultListModel<String> player_model = new DefaultListModel<>();
		player_list.setFixedCellHeight(35);
		player_list.setModel(player_model);
		for (int i = 0; i < players.length; i++) {
			player_model.addElement(players[i].getName());
		}
		list_panel.add(player_list);

		JList<String> share_list = new JList();
		share_list.setOpaque(false);
		((JLabel)share_list.getCellRenderer()).setOpaque(false);
		share_list.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		String[] data = { "玉石", "可可", "人参", "丝绸", "清空" };
		share_list.setListData(data);
		share_list.setFixedCellHeight(28);
		share_list.setPreferredSize(new Dimension(150, 140));
		list_panel.add(share_list);

		JPanel show_panel = new JPanel();
		show_panel.setOpaque(false);
		show_panel.setLayout(null);
		show_panel.setBounds(0, 170, 300, 30);
		this.share_view.add(show_panel);

		JLabel share_info_label = new JLabel("", JLabel.CENTER);
		share_info_label.setOpaque(false);
		share_info_label.setName("share_info_label");
		share_info_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		share_info_label.setBounds(0, 0, 150, 30);
		show_panel.add(share_info_label);

		JButton sale_button = new JButton();
		sale_button.setOpaque(false);
		sale_button.setBackground(Color.GRAY);
		sale_button.setName("抵押");
		sale_button.setBounds(150, 0, 75, 30);
		sale_button.setBorder(null);
		sale_button.setIcon(new ImageIcon(new ImageIcon("src/抵押.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		show_panel.add(sale_button);
		JButton get_button = new JButton();
		get_button.setOpaque(false);
		get_button.setBackground(Color.GRAY);
		get_button.setName("赎回");
		get_button.setBounds(225, 0, 75, 30);
		get_button.setBorder(null);
		get_button.setIcon(new ImageIcon(new ImageIcon("src/赎回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
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
		//pirate_boat_view.setOpaque(true);
		pirate_boat_view.setOpaque(false);
		//pirate_boat_view.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pirate_boat_view.addMouseListener(new PlaceWorkerController(pirate_boat_view, "海盗", this));
		this.add(pirate_boat_view);

		JLabel name = new JLabel("", JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		name.setIcon(new ImageIcon(new ImageIcon("src/海盗.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		pirate_boat_view.add(name);

		JLabel pos1 = new JLabel("5", JLabel.CENTER);
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		//pos1.setOpaque(true);
		pos1.setOpaque(false);
		pos1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		pirate_boat_view.add(pos1);

		JLabel pos2 = new JLabel("5", JLabel.CENTER);
		pos2.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		pos2.setOpaque(true);
		pos2.setOpaque(false);
		pos2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		//navigator_view.setOpaque(true);
		navigator_view.setOpaque(false);
		navigator_view.setOpaque(false);
		//navigator_view.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		this.add(navigator_view);

		JLabel name = new JLabel("", JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		name.setIcon(new ImageIcon(new ImageIcon("src/领航.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		navigator_view.add(name);

		JLabel pos1 = new JLabel("2", JLabel.CENTER);
		pos1.setName("0");
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		//pos1.setOpaque(true);
		pos1.setOpaque(false);
		pos1.setBorder(BorderFactory.createLineBorder(Color.GRAY));;
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		pos1.addMouseListener(new PlaceWorkerController(pos1, "领航", this));
		navigator_view.add(pos1);

		JLabel pos2 = new JLabel("5", JLabel.CENTER);
		pos2.setName("1");
		pos2.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		//pos2.setOpaque(true);
		pos2.setOpaque(false);
		pos2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pos2.setBounds(5, 75, 30, 30);
		pos2.setBackground(pos_color);
		pos2.addMouseListener(new PlaceWorkerController(pos2, "领航", this));
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
		//insurance_view.setOpaque(true);
		insurance_view.setOpaque(false);
		//insurance_view.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.add(insurance_view);

		JLabel name = new JLabel("",JLabel.CENTER);
		name.setBounds(5, 5, 30, 30);
		name.setOpaque(false);
		name.setIcon(new ImageIcon(new ImageIcon("src/保险.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		insurance_view.add(name);

		JLabel pos1 = new JLabel("10", JLabel.CENTER);
		pos1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
		//pos1.setOpaque(true);
		pos1.setOpaque(false);
		pos1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pos1.setBounds(5, 40, 30, 30);
		pos1.setBackground(pos_color);
		pos1.addMouseListener(new PlaceWorkerController(pos1, "保险", this));
		insurance_view.add(pos1);

	}

	/**
	 * 初始化股票信息视图
	 */
	private void initShareInfoView() {
		this.share_info_view = new JPanel();
		share_info_view.setOpaque(false);
		share_info_view.setLayout(new GridLayout(1, 4));
		share_info_view.setBounds(400, 400, 300, 100);
		this.add(share_info_view);

		ArrayList<Share> shares = game.getShares();

		for (int i = 0; i < 20; i += 5) {
			JLabel temp_label = new JLabel("null", JLabel.CENTER);
			temp_label.setOpaque(false);
			temp_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
			share_info_view.add(temp_label);
			String info = "<html>";
			info += shares.get(i).getName() + "<br>";
			info += "&nbsp; " + shares.get(i).getValue() + "</html>";
			temp_label.setText(info);
		}
	}
	
	/**
	 * 初始化游戏信息视图
	 */
	private void initStatusText() {
		gameStatus = new JTextArea("欢迎使用manila\n");
		addGameInfo("本游戏有以下四种货物：\n");
		String info="";
		for(String boat:game.getAll_boats().keySet()) {
			info+=boat+":"+game.getAll_boats().get(boat).getCargo_value()+";";
		}
		info+="\n";
		addGameInfo(info);
		gameStatus.setOpaque(false);
		gameStatus.setEditable(false);
		gameStatus.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 13));
		JScrollPane bar = new JScrollPane(gameStatus);
		bar.setOpaque(false);
		bar.getViewport().setOpaque(false);
		bar.setBounds(400, 500, 300, 200);
		this.add(bar);
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame mw = new JFrame();
		mw.setSize(700, 840);
		mw.setTitle("Manila");
		mw.setResizable(false);
		GameView gv = new GameView();
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setContentPane(gv);
		// mw.pack();
		mw.setVisible(true);

	}

}

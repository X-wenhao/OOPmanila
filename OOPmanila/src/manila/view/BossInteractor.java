package manila.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import manila.controller.BossController;
import manila.model.Game;

/**
 * 船老大行使权利的页面
 * @author 肖康
 *
 */
public class BossInteractor extends JFrame {
	/**船的初始位置*/
	ArrayList<Integer> boatsLines = new ArrayList<Integer>(); 
	// 写了部分点击事件
	private Game game;
	private GameView gv;
	JRadioButton jRadioButton1;
	JRadioButton jRadioButton2;
	JRadioButton jRadioButton3;
	JRadioButton jRadioButton4;
	ButtonGroup bg1;
	JButton confirmButton1; // 船老大单选购买一种股票，
	JRadioButton jRadioButton5;
	JRadioButton jRadioButton6;
	JRadioButton jRadioButton7;
	JRadioButton jRadioButton8;
	ButtonGroup bg2;
	JButton confirmButton2; // 船老大决定三条船出发，ban掉一条船
	JButton enter; // 进入游戏

	JPanel panel1; // 选择股票的面板

	JPanel panel2; // 选择船只的面板
	JPanel panel2Boat1; // 四只船及船老大决定的信息
	JPanel panel2Boat2;
	JPanel panel2Boat3;
	JPanel panel2Boat4;
	JTextField boat1Message;
	JTextField boat2Message;
	JTextField boat3Message;
	JTextField boat4Message;

	// JPanel panel3; //组件放置在panel上
	public BossInteractor(Game g, GameView gv) {
		super("海港管理员交互界面");
		this.game = g;
		this.gv = gv;
		jRadioButton1 = new JRadioButton("玉石", false);
		jRadioButton2 = new JRadioButton("可可", false);
		jRadioButton3 = new JRadioButton("人参", false);
		jRadioButton4 = new JRadioButton("丝绸", false);
		bg1 = new ButtonGroup();
		bg1.add(jRadioButton1);
		bg1.add(jRadioButton2);
		bg1.add(jRadioButton3);
		bg1.add(jRadioButton4);
		confirmButton1 = new JButton(); // 选择购置股票组件
		confirmButton1.setOpaque(false);
		confirmButton1.setBorder(null);
		confirmButton1.setBackground(Color.GRAY);
		confirmButton1.setIcon(new ImageIcon(new ImageIcon("src/船长页面/购买.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
		jRadioButton5 = new JRadioButton("玉石", false);
		jRadioButton6 = new JRadioButton("可可", false);
		jRadioButton7 = new JRadioButton("人参", false);
		jRadioButton8 = new JRadioButton("丝绸", false);
		bg2 = new ButtonGroup();
		bg2.add(jRadioButton5);
		bg2.add(jRadioButton6);
		bg2.add(jRadioButton7);
		bg2.add(jRadioButton8);
		boat1Message = new JTextField();
		boat2Message = new JTextField();
		boat3Message = new JTextField();
		boat4Message = new JTextField();
		
		boat1Message.setHorizontalAlignment(JTextField.CENTER);
		boat1Message.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		boat2Message.setHorizontalAlignment(JTextField.CENTER);
		boat2Message.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		boat3Message.setHorizontalAlignment(JTextField.CENTER);
		boat3Message.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		boat4Message.setHorizontalAlignment(JTextField.CENTER);
		boat4Message.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
		boat1Message.setOpaque(false);
		boat2Message.setOpaque(false);
		boat3Message.setOpaque(false);
		boat4Message.setOpaque(false);
		
		confirmButton2 = new JButton(); // ban掉一条船组件,船及其信息组件
		confirmButton2.setOpaque(false);
		confirmButton2.setBorder(null);
		confirmButton2.setBackground(Color.GRAY);
		confirmButton2.setIcon(new ImageIcon(new ImageIcon("src/船长页面/开始.png").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
		// enter = new JButton("进入游戏界面");
		panel1 = new JPanel(new GridLayout(1, 5));
		panel1.setOpaque(false);
		jRadioButton1.setOpaque(false);
		jRadioButton2.setOpaque(false);
		jRadioButton3.setOpaque(false);
		jRadioButton4.setOpaque(false);
		jRadioButton5.setOpaque(false);
		jRadioButton6.setOpaque(false);
		jRadioButton7.setOpaque(false);
		jRadioButton8.setOpaque(false);
		panel1.add(jRadioButton1);
		panel1.add(jRadioButton2);
		panel1.add(jRadioButton3);
		panel1.add(jRadioButton4);
		panel1.add(confirmButton1);

		// 船及其信息
		panel2Boat1 = new JPanel(new GridLayout(2, 1));
		panel2Boat2 = new JPanel(new GridLayout(2, 1));
		panel2Boat3 = new JPanel(new GridLayout(2, 1));
		panel2Boat4 = new JPanel(new GridLayout(2, 1));
		panel2Boat1.add(jRadioButton5);
		panel2Boat1.add(boat1Message);
		panel2Boat2.add(jRadioButton6);
		panel2Boat2.add(boat2Message);
		panel2Boat3.add(jRadioButton7);
		panel2Boat3.add(boat3Message);
		panel2Boat4.add(jRadioButton8);
		panel2Boat4.add(boat4Message);
		panel2 = new JPanel(new GridLayout(1, 5));
		panel2.setOpaque(false);
		panel2Boat1.setOpaque(false);
		panel2Boat2.setOpaque(false);
		panel2Boat3.setOpaque(false);
		panel2Boat4.setOpaque(false);
		panel2.add(panel2Boat1);
		panel2.add(panel2Boat2);
		panel2.add(panel2Boat3);
		panel2.add(panel2Boat4);
		panel2.add(confirmButton2);

		// panel3 = new JPanel(new GridLayout(1,1));
		// panel3.add(enter);

		// Container container = this.getContentPane();
		JPanel backgroung_panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				// super.paint(g);
				g.drawImage(new ImageIcon("src/竞选页面/竞选背景.png").getImage(), 0, 0, 600, 200, this);
				super.paintChildren(g);
				// super.paintBorder(g);

			}
		};
		backgroung_panel.setLayout(new GridLayout(2, 1));
		this.add(backgroung_panel);
		backgroung_panel.add(panel1);
		backgroung_panel.add(panel2);
		// container.add(panel3);
		// 关闭窗口程序结束
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 设置窗口大小
		this.setSize(600, 200);
		// 设置窗口可见
		// this.setVisible(true);

		// addMouseListener(this); //鼠标点击事件，以后处理
		confirmButton1.setActionCommand("buy");
		confirmButton1.addActionListener(new BossController(this));
		confirmButton2.setActionCommand("set");
		confirmButton2.addActionListener(new BossController(this)); // 按下confirmButton判断jRadioButton是否已选择
		// enter.addActionListener(new BossController(this));
		this.setResizable(false);
	}

	@Override
	public void paint(Graphics g) {

		g.drawImage(new ImageIcon("src/竞选页面/竞选背景.png").getImage(), 0, 0, 600, 200, this);
		super.paint(g);
	}

	public ArrayList<Integer> getBoatsLines() {
		return boatsLines;
	}

	public GameView getGv() {
		return this.gv;
	}

	public void setBoatsLines(ArrayList<Integer> boatsLines) {
		this.boatsLines = boatsLines;
	}

	public JRadioButton getjRadioButton1() {
		return jRadioButton1;
	}

	public void setjRadioButton1(JRadioButton jRadioButton1) {
		this.jRadioButton1 = jRadioButton1;
	}

	public JRadioButton getjRadioButton2() {
		return jRadioButton2;
	}

	public void setjRadioButton2(JRadioButton jRadioButton2) {
		this.jRadioButton2 = jRadioButton2;
	}

	public JRadioButton getjRadioButton3() {
		return jRadioButton3;
	}

	public void setjRadioButton3(JRadioButton jRadioButton3) {
		this.jRadioButton3 = jRadioButton3;
	}

	public JRadioButton getjRadioButton4() {
		return jRadioButton4;
	}

	public void setjRadioButton4(JRadioButton jRadioButton4) {
		this.jRadioButton4 = jRadioButton4;
	}

	public ButtonGroup getBg1() {
		return bg1;
	}

	public void setBg1(ButtonGroup bg1) {
		this.bg1 = bg1;
	}

	public JButton getConfirmButton1() {
		return confirmButton1;
	}

	public void setConfirmButton1(JButton confirmButton1) {
		this.confirmButton1 = confirmButton1;
	}

	public JRadioButton getjRadioButton5() {
		return jRadioButton5;
	}

	public void setjRadioButton5(JRadioButton jRadioButton5) {
		this.jRadioButton5 = jRadioButton5;
	}

	public JRadioButton getjRadioButton6() {
		return jRadioButton6;
	}

	public void setjRadioButton6(JRadioButton jRadioButton6) {
		this.jRadioButton6 = jRadioButton6;
	}

	public JRadioButton getjRadioButton7() {
		return jRadioButton7;
	}

	public void setjRadioButton7(JRadioButton jRadioButton7) {
		this.jRadioButton7 = jRadioButton7;
	}

	public JRadioButton getjRadioButton8() {
		return jRadioButton8;
	}

	public void setjRadioButton8(JRadioButton jRadioButton8) {
		this.jRadioButton8 = jRadioButton8;
	}

	public ButtonGroup getBg2() {
		return bg2;
	}

	public void setBg2(ButtonGroup bg2) {
		this.bg2 = bg2;
	}

	public JButton getConfirmButton2() {
		return confirmButton2;
	}

	public void setConfirmButton2(JButton confirmButton2) {
		this.confirmButton2 = confirmButton2;
	}

	public JButton getEnter() {
		return enter;
	}

	public void setEnter(JButton enter) {
		this.enter = enter;
	}

	public JPanel getPanel1() {
		return panel1;
	}

	public void setPanel1(JPanel panel1) {
		this.panel1 = panel1;
	}

	public JPanel getPanel2() {
		return panel2;
	}

	public void setPanel2(JPanel panel2) {
		this.panel2 = panel2;
	}

	public JPanel getPanel2Boat1() {
		return panel2Boat1;
	}

	public void setPanel2Boat1(JPanel panel2Boat1) {
		this.panel2Boat1 = panel2Boat1;
	}

	public JPanel getPanel2Boat2() {
		return panel2Boat2;
	}

	public void setPanel2Boat2(JPanel panel2Boat2) {
		this.panel2Boat2 = panel2Boat2;
	}

	public JPanel getPanel2Boat3() {
		return panel2Boat3;
	}

	public void setPanel2Boat3(JPanel panel2Boat3) {
		this.panel2Boat3 = panel2Boat3;
	}

	public JPanel getPanel2Boat4() {
		return panel2Boat4;
	}

	public void setPanel2Boat4(JPanel panel2Boat4) {
		this.panel2Boat4 = panel2Boat4;
	}

	public JTextField getBoat1Message() {
		return boat1Message;
	}

	public void setBoat1Message(JTextField boat1Message) {
		this.boat1Message = boat1Message;
	}

	public JTextField getBoat2Message() {
		return boat2Message;
	}

	public void setBoat2Message(JTextField boat2Message) {
		this.boat2Message = boat2Message;
	}

	public JTextField getBoat3Message() {
		return boat3Message;
	}

	public void setBoat3Message(JTextField boat3Message) {
		this.boat3Message = boat3Message;
	}

	public JTextField getBoat4Message() {
		return boat4Message;
	}

	public void setBoat4Message(JTextField boat4Message) {
		this.boat4Message = boat4Message;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}

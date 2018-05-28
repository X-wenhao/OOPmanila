package manila.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import manila.model.Game;



public class BossInteractor extends JFrame implements MouseListener,ActionListener{

	
	ArrayList<Integer> boatsLines = new ArrayList<Integer>();  //点击confirm后用来存储输入文本框中的信息
	                          //写了部分点击事件
	private Game game;
	JRadioButton jRadioButton1;
	JRadioButton jRadioButton2;
	JRadioButton jRadioButton3;
	JRadioButton jRadioButton4;
	ButtonGroup bg1;
	JButton confirmButton1;       //船老大单选购买一种股票，
	JRadioButton jRadioButton5;
	JRadioButton jRadioButton6;
	JRadioButton jRadioButton7;
	JRadioButton jRadioButton8;
	ButtonGroup bg2;
	JButton confirmButton2;      //船老大决定三条船出发，ban掉一条船
	JButton enter;         //进入游戏
	
	JPanel panel1;        //选择股票的面板
	
	JPanel panel2;       //选择船只的面板
	JPanel panel2Boat1;    //四只船及船老大决定的信息
	JPanel panel2Boat2;
	JPanel panel2Boat3;
	JPanel panel2Boat4;
	JTextField boat1Message;
	JTextField boat2Message;
	JTextField boat3Message;
	JTextField boat4Message;
	
	JPanel panel3;      //组件放置在panel上
	public BossInteractor(Game g) {
		super("海港管理员交互界面");
		this.game =g;
		jRadioButton1 = new JRadioButton("玉石",false);
		jRadioButton2 = new JRadioButton("可可",false);
		jRadioButton3 = new JRadioButton("人参",false);
		jRadioButton4 = new JRadioButton("丝绸",false);
		bg1 = new ButtonGroup();
		bg1.add(jRadioButton1);
		bg1.add(jRadioButton2);
		bg1.add(jRadioButton3);
		bg1.add(jRadioButton4);
		confirmButton1 = new JButton("购买股票");     //选择购置股票组件
		jRadioButton5 = new JRadioButton("船一",false);
		jRadioButton6 = new JRadioButton("船二",false);
		jRadioButton7 = new JRadioButton("船三",false);
		jRadioButton8 = new JRadioButton("船四",false);
		bg2 = new ButtonGroup();
		bg2.add(jRadioButton5);
		bg2.add(jRadioButton6);
		bg2.add(jRadioButton7);
		bg2.add(jRadioButton8);
		boat1Message = new JTextField();
		boat2Message = new JTextField();
		boat3Message = new JTextField();
		boat4Message = new JTextField();
		
		confirmButton2 = new JButton("confirm");    //ban掉一条船组件,船及其信息组件
		enter = new JButton("进入游戏界面");
		panel1 = new JPanel(new GridLayout(1,5));
		panel1.add(jRadioButton1);
		panel1.add(jRadioButton2);
		panel1.add(jRadioButton3);
		panel1.add(jRadioButton4);
		panel1.add(confirmButton1);
		
	//   船及其信息
			panel2Boat1 = new JPanel(new GridLayout(2,1));
			panel2Boat2 = new JPanel(new GridLayout(2,1));
			panel2Boat3 = new JPanel(new GridLayout(2,1));
			panel2Boat4 = new JPanel(new GridLayout(2,1));
			panel2Boat1.add(jRadioButton5);
			panel2Boat1.add(boat1Message);
			panel2Boat2.add(jRadioButton6);
			panel2Boat2.add(boat2Message);
			panel2Boat3.add(jRadioButton7);
			panel2Boat3.add(boat3Message);
			panel2Boat4.add(jRadioButton8);
			panel2Boat4.add(boat4Message);
		panel2 = new JPanel(new GridLayout(1,5));
		panel2.add(panel2Boat1);
		panel2.add(panel2Boat2);
		panel2.add(panel2Boat3);
		panel2.add(panel2Boat4);
		panel2.add(confirmButton2);
		
		
		panel3 = new JPanel(new GridLayout(1,1));
		panel3.add(enter);
		
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(3,0));
		container.add(panel1);
		container.add(panel2);
		container.add(panel3);
		//关闭窗口程序结束
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(600,200);
		//设置窗口可见
		this.setVisible(true);
		addMouseListener(this);    //鼠标点击事件，以后处理
		confirmButton1.addActionListener(this);
		confirmButton2.addActionListener(this);   //按下confirmButton判断jRadioButton是否已选择
		enter.addActionListener(this);
		
		
	}     //end of constructor
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==confirmButton2) {
			boatsLines.add(Integer.parseInt(boat1Message.getText()));
			boatsLines.add(Integer.parseInt(boat2Message.getText()));
			boatsLines.add(Integer.parseInt(boat3Message.getText()));
			boatsLines.add(Integer.parseInt(boat4Message.getText()));
			int sum = 0;
			for(int i : boatsLines) {
				sum = sum + i;
			}
			if(sum!=9) {
				//TODO图形界面强制提示重新输入初始各条船的初始位置
				System.out.println("请重新输入");
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Integer> getBoatsLines() {
		return boatsLines;
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



	public JPanel getPanel3() {
		return panel3;
	}



	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}
	public Game getGame() {
		return game;
	}



	public void setGame(Game game) {
		this.game = game;
	}



}

package manila.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.model.Game;

public class NavigatorInteractor extends JFrame implements ActionListener{
	private JLabel label;
	private JLabel boatLabel1;
	private JLabel boatLabel2;
	private JLabel boatLabel3;
	private JTextField move1;
	private JTextField move2;
	private JTextField move3;
	private JButton confirmButton;
	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	
	public NavigatorInteractor() {
		super("领航员交互界面");
		label = new JLabel("navigator 0 or 1 : player");      //Todo  确定玩家
		boatLabel1 = new JLabel("船一");        //todo  确定出发的三条船名称填入jlabel
		boatLabel2 = new JLabel("船二");
		boatLabel3 = new JLabel("船三");
		move1 = new JTextField();
		move2 = new JTextField(); 
		move3 = new JTextField();     //后面还需要有判断领航员输入是否合理的操作
		confirmButton = new JButton("confirm");
		confirmButton.addActionListener(this);
		panel1 = new JPanel();
		panel2 = new JPanel(new GridLayout(1,3));
		panel3 = new JPanel(new GridLayout(1,3));
		panel4 = new JPanel();
		panel1.add(label);
		panel2.add(boatLabel1);
		panel2.add(boatLabel2);
		panel2.add(boatLabel3);
		panel3.add(move1);
		panel3.add(move2);
		panel3.add(move3);
		panel4.add(confirmButton);
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(4,1));
		c.add(panel1);
		c.add(panel2);
		c.add(panel3);
		c.add(panel4);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(400, 160);
		this.setVisible(true);
		
	}
	public static void main(String args[]) {
		NavigatorInteractor p = new NavigatorInteractor();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

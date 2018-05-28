package manila.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.model.Game;

public class PrivateInteractor extends JFrame implements ActionListener {
	//private Game game;
	private JLabel label;
	private JButton confirmButton1;
	private JButton confirmButton2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	public PrivateInteractor() {
		//this.game = g;
		super("海盗交互界面");
		label = new JLabel("玩家player是否上船boat");  //todo 确定玩家和上的船
		confirmButton1 = new JButton("yes");
		confirmButton2 = new JButton("no");
		confirmButton1.addActionListener(this);
		confirmButton2.addActionListener(this);
		jPanel1 = new JPanel();
		jPanel2 = new JPanel(new GridLayout(1,2));
		jPanel1.add(label);
		jPanel2.add(confirmButton1);
		jPanel2.add(confirmButton2);
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(2,1));
		container.add(jPanel1);
		container.add(jPanel2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(300, 100);
		this.setVisible(true);
		
	}    //end of constructor
	
	public static void main(String args[]) {
		PrivateInteractor p = new PrivateInteractor();
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {   //todo  处理点击事件
		// TODO Auto-generated method stub
		
	}

}

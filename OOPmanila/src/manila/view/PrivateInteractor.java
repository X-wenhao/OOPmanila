package manila.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import manila.controller.PirateController;
import manila.model.Boat;
import manila.model.Game;
import manila.model.Player;

/**
 * 弹出式海盗交互页面
 * @author 肖康
 *
 */
public class PrivateInteractor extends JDialog {
	//private Game game;
	private JLabel label;
	private JButton confirmButton1;
	private JButton confirmButton2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private GameView gv;
	private BoatView bv;
	public PrivateInteractor(GameView gv,int pirate_index,BoatView bv,String info) {
		//this.game = g;type name = new type();
		super(JOptionPane.getFrameForComponent(gv),"海盗交互页面",true);

		this.gv=gv;
		this.bv=bv;
		int pid=gv.getGame().getPirateBoat().getPositions()[pirate_index].getPid();
		Player player=gv.getGame().getPlayerByID(pid);
		label = new JLabel(info);  //todo 确定玩家和上的船
		confirmButton1 = new JButton("yes");
		confirmButton1.setActionCommand("yes");
		confirmButton2 = new JButton("no");
		confirmButton2.setActionCommand("no");
		confirmButton1.addActionListener(new PirateController(gv, player, bv, pirate_index, this));
		confirmButton2.addActionListener(new PirateController(gv, player, bv, pirate_index, this));
		jPanel1 = new JPanel();
		jPanel2 = new JPanel(new GridLayout(1,2));
		jPanel1.add(label);
		jPanel2.add(confirmButton1);
		jPanel2.add(confirmButton2);
		Container container = this.getContentPane();
		container.setLayout(new GridLayout(2,1));
		container.add(jPanel1);
		container.add(jPanel2);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(300, 100);
		this.setVisible(true);
		
		
	}    //end of constructor
	
}

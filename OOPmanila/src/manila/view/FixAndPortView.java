package manila.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manila.controller.PlaceWorkerController;
import manila.model.Game;

/**
 * 港口以及修船厂视图
 * @author tk-ice
 *
 */
public class FixAndPortView extends JPanel{
	/**修船厂视图*/
	private JPanel fix_view;
	/**港口视图*/
	private JPanel port_view;
	/** 船体背景色 */
	private static Color boat_color = new Color(220, 220, 220);
	/** 位置背景色 */
	private static Color pos_color = new Color(245,245,245);
	/**修船厂panels*/
	private ArrayList<JPanel> fix_panels;
	/**港口panels*/
	private ArrayList<JPanel> port_panels;
	
	/**游戏视图，用于点击事件控制器的初始化*/
	private GameView gv;
	
	public FixAndPortView(GameView gv) {
		this.setBounds(0, 0, 400,220);
		this.setLayout(new GridLayout(1, 2));
		this.gv=gv;
		
		this.fix_panels=new ArrayList<>();
		this.port_panels=new ArrayList<>();
		this.setOpaque(false);
		
		this.initFixView();
		this.initPortView();
	}
	
	/**
	 * 新的回合重置港口可修船厂
	 */
	public void refreshFixAndPort() {
		for(JPanel panel:fix_panels) {
			for(int i=0;i<panel.getComponentCount();i++) {
				panel.getComponent(i).setBackground(this.pos_color);
				JLabel temp_label=(JLabel)panel.getComponent(i);
				temp_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
		}
		for(JPanel panel:port_panels) {
			for(int i=0;i<panel.getComponentCount();i++) {
				panel.getComponent(i).setBackground(this.pos_color);
				JLabel temp_label=(JLabel)panel.getComponent(i);
				temp_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
		}
	}
	
	public void setGv(GameView gv) {
		this.gv=gv;
	}
	
	/**初始化修船厂*/
	private void initFixView() {
		fix_view=new JPanel();
		fix_view.setLayout(null);
		fix_view.setOpaque(false);
		this.add(fix_view);
		
		JLabel name=new JLabel("",JLabel.CENTER);
		name.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		name.setBounds(0, 0, 40,200);
		name.setOpaque(false);
		name.setIcon(new ImageIcon(new ImageIcon("src/修船厂.png").getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)));
		fix_view.add(name);
		
		String[] i1= {"6","8","15"};
		String[] i2= {"4","3","2"};
		
		for(int i=0;i<3;i++) {
			JPanel temp_panel=new JPanel();
			temp_panel.setOpaque(false);
			this.fix_panels.add(temp_panel);
			temp_panel.setLayout(null);
			temp_panel.setBounds(35+55*i, 0, 50, 220);
			temp_panel.setBackground(boat_color);
			//temp_panel.setOpaque(true);
			fix_view.add(temp_panel);
			JLabel value_label=new JLabel(i1[i],JLabel.CENTER);
			value_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			//value_label.setOpaque(true);
			value_label.setBackground(pos_color);
			value_label.setOpaque(false);
			//value_label.setIcon(new ImageIcon(new ImageIcon("src/xuena.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT)));
			value_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			value_label.setBounds(10,5,30,20);
			temp_panel.add(value_label);
			JLabel pos_label=new JLabel(i2[i],JLabel.CENTER);
			pos_label.setName(String.valueOf(i));
			pos_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			//pos_label.setOpaque(true);
			pos_label.setBackground(pos_color);
			pos_label.setOpaque(false);
			pos_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
			pos_label.setBounds(10,30,30,20);
			pos_label.addMouseListener(new PlaceWorkerController(pos_label, "修船厂", gv));
			temp_panel.add(pos_label);
			
			JLabel boat_label=new JLabel();
			boat_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			boat_label.setOpaque(false);
			boat_label.setBackground(pos_color);
			boat_label.setOpaque(false);
			boat_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
			boat_label.setBounds(5,55,40,160);
			temp_panel.add(boat_label);
			
			}
		}
		
		/**初始化港口*/
		private void initPortView() {
			port_view=new JPanel();
			port_view.setLayout(null);
			port_view.setOpaque(false);
			this.add(port_view);
			
			JLabel name=new JLabel("<html>" + "港" + "<br><br>" + "口" + "</html>",JLabel.CENTER);
			name.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
			name.setBounds(0, 0, 30,200);
			name.setOpaque(false);
			name.setIcon(new ImageIcon(new ImageIcon("src/港口.png").getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)));
			port_view.add(name);
			
			String[] i1= {"6","8","15"};
			String[] i2= {"4","3","2"};
			
			for(int i=0;i<3;i++) {
				JPanel temp_panel=new JPanel();
				this.port_panels.add(temp_panel);
				temp_panel.setLayout(null);
				temp_panel.setBounds(30+55*i, 0, 50, 220);
				//temp_panel.setOpaque(true);
				temp_panel.setBackground(boat_color);
				temp_panel.setOpaque(false);
				port_view.add(temp_panel);
				JLabel value_label=new JLabel(i1[i],JLabel.CENTER);
				value_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				//value_label.setOpaque(true);
				value_label.setBackground(pos_color);
				value_label.setOpaque(false);
				value_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				value_label.setBounds(10,5,30,20);
				temp_panel.add(value_label);
				JLabel pos_label=new JLabel(i2[i],JLabel.CENTER);
				pos_label.setName(String.valueOf(i));
				pos_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				//pos_label.setOpaque(true);
				pos_label.setBackground(pos_color);
				pos_label.setOpaque(false);
				pos_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				pos_label.setBounds(10,30,30,20);

				pos_label.addMouseListener(new PlaceWorkerController(pos_label, "港口", gv));
				temp_panel.add(pos_label);
				
				JLabel boat_label=new JLabel();
				boat_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				//boat_label.setOpaque(true);
				boat_label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				boat_label.setBackground(pos_color);
				boat_label.setBounds(5,55,40,160);
				temp_panel.add(boat_label);
			}
		
	}
}

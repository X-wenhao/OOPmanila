package manila.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	public FixAndPortView() {
		this.setBounds(0, 0, 400,220);
		this.setLayout(new GridLayout(1, 2));
		
		this.initFixView();
		this.initPortView();
	}
	
	/**初始化修船厂*/
	private void initFixView() {
		fix_view=new JPanel();
		fix_view.setLayout(null);
		this.add(fix_view);
		
		JLabel name=new JLabel("<html>" + "修" + "<br>" + "船"+"<br>"+"厂" + "</html>",JLabel.CENTER);
		name.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
		name.setBounds(0, 0, 40,200);
		fix_view.add(name);
		
		String[] i1= {"6","8","15"};
		String[] i2= {"4","3","2"};
		
		for(int i=0;i<3;i++) {
			JPanel temp_panel=new JPanel();
			temp_panel.setLayout(null);
			temp_panel.setBounds(35+55*i, 0, 50, 220);
			temp_panel.setBackground(boat_color);
			temp_panel.setOpaque(true);
			fix_view.add(temp_panel);
			JLabel value_label=new JLabel(i1[i],JLabel.CENTER);
			value_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			value_label.setOpaque(true);
			value_label.setBackground(pos_color);
			value_label.setBounds(10,5,30,20);
			temp_panel.add(value_label);
			JLabel pos_label=new JLabel(i2[i],JLabel.CENTER);
			pos_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			pos_label.setOpaque(true);
			pos_label.setBackground(pos_color);
			pos_label.setBounds(10,30,30,20);
			temp_panel.add(pos_label);
			
			JLabel boat_label=new JLabel();
			boat_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
			boat_label.setOpaque(true);
			boat_label.setBackground(pos_color);
			boat_label.setBounds(5,55,40,160);
			temp_panel.add(boat_label);
			
			}
		}
		
		/**初始化港口*/
		private void initPortView() {
			port_view=new JPanel();
			port_view.setLayout(null);
			this.add(port_view);
			
			JLabel name=new JLabel("<html>" + "港" + "<br><br>" + "口" + "</html>",JLabel.CENTER);
			name.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 18));
			name.setBounds(0, 0, 30,200);
			port_view.add(name);
			
			String[] i1= {"6","8","15"};
			String[] i2= {"4","3","2"};
			
			for(int i=0;i<3;i++) {
				JPanel temp_panel=new JPanel();
				temp_panel.setLayout(null);
				temp_panel.setBounds(30+55*i, 0, 50, 220);
				temp_panel.setOpaque(true);
				temp_panel.setBackground(boat_color);
				port_view.add(temp_panel);
				JLabel value_label=new JLabel(i1[i],JLabel.CENTER);
				value_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				value_label.setOpaque(true);
				value_label.setBackground(pos_color);
				value_label.setBounds(10,5,30,20);
				temp_panel.add(value_label);
				JLabel pos_label=new JLabel(i2[i],JLabel.CENTER);
				pos_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				pos_label.setOpaque(true);
				pos_label.setBackground(pos_color);
				pos_label.setBounds(10,30,30,20);
				temp_panel.add(pos_label);
				
				JLabel boat_label=new JLabel();
				boat_label.setFont(new Font("SansSerif", Font.CENTER_BASELINE,13));
				boat_label.setOpaque(true);
				boat_label.setBackground(pos_color);
				boat_label.setBounds(5,55,40,160);
				temp_panel.add(boat_label);
			}
		
	}
}

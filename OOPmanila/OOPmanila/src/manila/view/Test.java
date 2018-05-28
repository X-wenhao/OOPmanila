package manila.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.istack.internal.Nullable;

import manila.model.*;
import manila.view.*;

public class Test {
	public static void main(String[] args) {
	 Game game=new Game();
	 System.out.println(game.getAll_boats().get("玉石").getCargo_name());
	 
	 BoatView bView=new BoatView(game.getAll_boats().get("玉石"), game);
	 
	 JFrame mw=new JFrame("test");
	 mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 mw.setSize(800, 900);
	 
	 JPanel mainpa=new JPanel();
	 mainpa.setLayout(null);
	 mainpa.setSize(800,900);
	 mainpa.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getSource().getClass().getSimpleName());
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
	 
	 mainpa.add(bView);
	 mw.getContentPane().add(mainpa);
	 
	 mw.setVisible(true);
	 
	 
	 System.out.println(game.getClass().getSimpleName());
	 
	 }
}

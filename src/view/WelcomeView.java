package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WelcomeView extends JFrame {
	JFrame WelcomeFrame;
	JPanel panel2;
	JLabel l;
	
	
	//Dimension screenSize;
	public WelcomeView(){
		WelcomeFrame =new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WelcomeFrame.setSize(screenSize.width, screenSize.height);
		WelcomeFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    
		panel2 = new JPanel();
		
		l=new JLabel();
		l.setIcon(new ImageIcon("src/images/HS.png"));
		
	    panel2.add(l);
	    
	    WelcomeFrame.add(panel2);
	    panel2.setVisible(true);
		WelcomeFrame.setVisible(true);
		this.repaint();
		this.revalidate();
		
	}
	public JFrame getWelcomeFrame() {
		return WelcomeFrame;
	}  
	
	public JPanel getPanel2() {
		return panel2;
	}
	
	public JLabel getL() {
		return l;
	}

	
//	public static void main(String [] args){
//		new WelcomeView();
//	}

}

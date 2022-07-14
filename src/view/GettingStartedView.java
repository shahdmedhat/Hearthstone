package view;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class GettingStartedView extends JFrame {
	
	private JFrame choosePlayer;
	private JPanel panel;

	
	public GettingStartedView() {
		choosePlayer = new JFrame("Selecting Players..");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		choosePlayer.setSize(screenSize.width, screenSize.height);
		//choosePlayer.setBounds(640, 480, 800, 600);
		choosePlayer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	    panel = new JPanel();
		panel.setLayout(new GridLayout(2, 6));
		//panel.setPreferredSize(new Dimension(choosePlayer.getWidth(), 500));
		choosePlayer.add(panel);
		panel.setVisible(true);
		choosePlayer.setVisible(true);
	//	transfer();
	}


	public JPanel getPanel() {
		return panel;
	}


	public JFrame getChoosePlayer() {
		return choosePlayer;
	}

//	private void transfer()
//    {
//        new GettingStartedController(this);
//    }
	
	
	
}

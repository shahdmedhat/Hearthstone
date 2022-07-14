package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import view.FinishView;
//import engine.Game;

public class FinishController implements ActionListener {
	// private Game model;
	private FinishView view;

	private JLabel l2;
	private JLabel l3;

	public FinishController(String s) { // Hero winner
		view = new FinishView();
		//this.v=v;
		//System.out.println(view.getWinner().getHeight());
		//System.out.println(view.getWinner().getWidth());
	
		l2 = new JLabel();
		//l2.setBounds(0, 0, 1898, 512);
		l2.repaint();
		l2.revalidate();
		
		ImageIcon i=new ImageIcon("src/images/HSLogo.jbg");
		l2.setIcon(i);
		view.getHSlogo().add(l2);

		JLabel l1 = new JLabel(" THE WINNER IS....");
		l1.setFont(new Font("Helvatica", Font.BOLD, 33));
		view.getWinner().add(l1, BorderLayout.CENTER);
        l1.repaint();
        l1.revalidate();
        
		l3 = new JLabel(s);
		l3.setFont(new Font("Helvatica", Font.BOLD, 33));
		view.getWinner().add(l3, BorderLayout.SOUTH);
        l3.repaint();
        l3.revalidate();
        
		JButton exit = new JButton("EXIT");
		exit.setFont(new Font("Helvetica", Font.BOLD, 18));
		exit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		exit.setBackground(Color.YELLOW);
		exit.setPreferredSize(new Dimension(200, 100));
		view.getP().add(exit);
		exit.addActionListener(this);
		
		JButton play = new JButton("PLAY AGAIN!");
		play.setFont(new Font("Helvetica", Font.BOLD, 18));
		play.setBackground(Color.yellow);
		play.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		play.setPreferredSize(new Dimension(200, 100));
		view.getP().add(play);
		play.addActionListener(this);
		

		view.getHSlogo().revalidate();
		view.getWinner().repaint();
	}

	public JLabel getL3() {
		return l3;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton b = (JButton) ae.getSource();

		if (b.getActionCommand().equals("EXIT")) {
			view.dispose();
			System.exit(0);
		}
		if (b.getActionCommand().equals("PLAY AGAIN!")){
			view.getFrame().dispose();
			new GettingStartedController();
		}
		// TODO Auto-generated method stub

	}

//	public static void main(String[] args) {
//		new FinishController();
//
//	}

}
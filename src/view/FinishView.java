package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FinishView extends JFrame {

	JPanel winner;
	JPanel HSlogo;
	JPanel p;
	JFrame Frame;
	JLabel l2;

	public FinishView() {
		EndGame();
		this.revalidate();
		this.repaint();

	}

	public void EndGame() {
		Frame = new JFrame("Announcing The Winner!");
		Frame.setLayout(new GridLayout(3, 0));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Frame.setSize(screenSize.width, screenSize.height);
		Frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Frame.setBackground(Color.gray);

		HSlogo = new JPanel();
		HSlogo.setBorder(BorderFactory.createLineBorder(Color.black));
		HSlogo.setBackground(Color.WHITE);
		Frame.add(HSlogo);

		winner = new JPanel();
		winner.setLayout(new FlowLayout());
		Frame.add(winner);
		//winner.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		winner.setBackground(Color.gray);

		p = new JPanel();
		p.setLayout(new FlowLayout());
		Frame.add(p);
		//p.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		p.setBackground(Color.gray);

		p.setVisible(true);
		HSlogo.setVisible(true);
		winner.setVisible(true);
		Frame.setVisible(true);
		this.repaint();
		this.revalidate();
	}

	
	public JPanel getP() {
		return p;
	}

	public JPanel getHSlogo() {
		return HSlogo;
	}

	public JPanel getWinner() {
		return winner;
	}

	public JFrame getFrame() {
		return Frame;
	}

}
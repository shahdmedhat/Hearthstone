package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class GameView extends JFrame {
	
	private JFrame playMode;

	private JPanel opponentView;
	private JPanel opponentField;
	private JPanel currentView;
	private JPanel currentField;

	
	private JTextArea opponentInfo;
	private JTextArea currentInfo;
	
	private JPanel opponentCards;
	private JPanel currentCards;
	
	
	public GameView() {
		mainGame();
		this.revalidate();
		this.repaint();
	}
	

	public void mainGame() {
		playMode=new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		playMode.setSize(screenSize.width, screenSize.height);
		//playMode.setSize(800, 800);
		playMode.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		playMode.setLayout(new GridLayout(4,0));
//		playMode.setState(Frame.NORMAL);
		
		opponentView=new JPanel();
		opponentView.setLayout(new FlowLayout());
		playMode.add(opponentView);
		
		opponentField=new JPanel();
		//opponentField.setName("OPPONENT'S FIELD");
		opponentField.setBackground(Color.DARK_GRAY);
		opponentField.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder title1=BorderFactory.createTitledBorder("OPPONENT'S FIELD");
		title1.setTitleColor(Color.white);
		opponentField.setBorder(title1);
		opponentField.setLayout(new FlowLayout());
		//opponentField.setLayout(new GridLayout(0,11));
		playMode.add(opponentField);
		
		currentField=new JPanel();
		currentField.setBackground(Color.DARK_GRAY);
		
		//currentField.setPreferredSize(getMaximumSize());
		//-------------CHECK
//		try {
//			TextAreaBackground img = new TextAreaBackground("src/images/field4.jpg",screenSize.width , screenSize.height);
//			currentField.add(img);
//			System.out.println(screenSize.width);
//			System.out.println(screenSize.height);
//			System.out.println(currentField.getHeight());
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		currentField.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder title2=BorderFactory.createTitledBorder("YOUR FIELD");
		title2.setTitleColor(Color.white);
		currentField.setBorder(title2);
		currentField.setLayout(new FlowLayout());
		//currentField.setLayout(new GridLayout(0,11));
		playMode.add(currentField);
		
		currentView=new JPanel();
		currentView.setLayout(new FlowLayout());
		playMode.add(currentView);
		
		opponentInfo=new JTextArea();
		opponentInfo.setLayout(new GridLayout(5,0));
		opponentInfo.setBackground(Color.YELLOW);
		opponentInfo.setBorder(BorderFactory.createLineBorder(Color.black));
		opponentInfo.setEditable(false);
		opponentView.add(opponentInfo);

		
		currentInfo=new JTextArea();
		currentInfo.setLayout(new GridLayout(5,0));
		currentInfo.setBackground(Color.YELLOW);
		currentInfo.setBorder(BorderFactory.createLineBorder(Color.black));
		currentInfo.setEditable(false);
		currentView.add(currentInfo);

		opponentCards=new JPanel();
		opponentCards.setName("OPPONENT'S HAND");
		opponentCards.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder title3=BorderFactory.createTitledBorder("OPPONENT'S HAND");
		opponentCards.setBorder(title3);
		opponentCards.setLayout(new FlowLayout());
		opponentView.add(opponentCards);

		currentCards=new JPanel();
		currentCards.setName("YOUR HAND");
		currentCards.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder title4=BorderFactory.createTitledBorder("YOUR HAND");
		currentCards.setBorder(title4);
		currentCards.setLayout(new FlowLayout());
		currentView.add(currentCards);
		
		playMode.setVisible(true);
		playMode.revalidate();
		playMode.repaint();


	}

	public JFrame getPlayMode() {
		return playMode;
	}


	public JPanel getOpponentView() {
		return opponentView;
	}


	public JPanel getOpponentField() {
		return opponentField;
	}


	public JPanel getCurrentView() {
		return currentView;
	}


	public JPanel getCurrentField() {
		return currentField;
	}


	public JTextArea getOpponentInfo() {
		return opponentInfo;
	}


	public JTextArea getCurrentInfo() {
		return currentInfo;
	}


	public JPanel getOpponentCards() {
		return opponentCards;
	}


	public JPanel getCurrentCards() {
		return currentCards;
	}

	
	public void setOpponentView(JPanel opponentView) {
		this.opponentView = opponentView;
	}


	public void setOpponentField(JPanel opponentField) {
		this.opponentField = opponentField;
	}


	public void setCurrentView(JPanel currentView) {
		this.currentView = currentView;
	}


	public void setCurrentField(JPanel currentField) {
		this.currentField = currentField;
	}


	public void setOpponentInfo(JTextArea opponentInfo) {
		this.opponentInfo = opponentInfo;
	}


	public void setCurrentInfo(JTextArea currentInfo) {
		this.currentInfo = currentInfo;
	}


	public void setOpponentCards(JPanel opponentCards) {
		this.opponentCards = opponentCards;
	}


	public void setCurrentCards(JPanel currentCards) {
		this.currentCards = currentCards;
	}

	


}

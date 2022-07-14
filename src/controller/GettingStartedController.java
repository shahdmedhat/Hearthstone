package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.util.*;

import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
//import engine.Game;
//import engine.GameListener;
import exceptions.FullHandException;
import sounds.Sound;
import view.GettingStartedView;

public class GettingStartedController implements ActionListener {
	private GettingStartedView view;
	//private Game model;
	private Hero firstHero;
	private Hero secondHero;
	private JButton done;
	private ArrayList<JButton> hero1Button=new ArrayList<JButton>();
	private ArrayList<JButton> hero2Button=new ArrayList<JButton>();
    private Sound s1;

	@SuppressWarnings("static-access")
	public GettingStartedController(){
		
		view=new GettingStartedView();
		view.getPanel().setVisible(true);
		view.getChoosePlayer().setVisible(true);
		try {
			s1=new Sound("src/sounds/s1.wav");
			s1.getClip().loop(s1.getClip().LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JLabel l1 = new JLabel("SELECT PLAYER ONE:", SwingConstants.CENTER);
		//l1.setEditable(false);
		l1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		l1.setFont(new Font("Helvetica", Font.BOLD, 18));
		view.getPanel().add(l1, BorderLayout.PAGE_START);

		
		JButton hunter1 = new JButton();
		hero1Button.add(hunter1);
		hunter1.setFont(new Font("Helvetica", Font.BOLD, 18));
		hunter1.setPreferredSize(new Dimension(200, 200));
		hunter1.setBackground(Color.LIGHT_GRAY);
		hunter1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		hunter1.setIcon(new ImageIcon("src/images/Rexxar.jbg"));
		view.getPanel().add(hunter1);

		JButton mage1 = new JButton();
		hero1Button.add(mage1);
		mage1.setIcon(new ImageIcon("src/images/Jaina_Proudmoore.jbg"));
		mage1.setFont(new Font("Helvetica", Font.BOLD, 18));
		mage1.setBackground(Color.LIGHT_GRAY);
		mage1.setPreferredSize(new Dimension(200, 200));
		mage1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(mage1);

		JButton paladin1 = new JButton();
		hero1Button.add(paladin1);
		paladin1.setIcon(new ImageIcon("src/images/Uther_Lightbringer.jbg"));
		paladin1.setFont(new Font("Helvetica", Font.BOLD, 18));
		paladin1.setBackground(Color.LIGHT_GRAY);
		paladin1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		paladin1.setPreferredSize(new Dimension(200, 200));
		view.getPanel().add(paladin1);

		JButton priest1 = new JButton();
		hero1Button.add(priest1);
		priest1.setIcon(new ImageIcon("src/images/Anduin_Wrynn.jbg"));
		priest1.setFont(new Font("Helvetica", Font.BOLD, 18));
		priest1.setBackground(Color.LIGHT_GRAY);
		priest1.setPreferredSize(new Dimension(200, 200));
		priest1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(priest1);

		JButton warlock1 = new JButton();
		hero1Button.add(warlock1);
		warlock1.setIcon(new ImageIcon("src/images/Gul'dan.jbg"));
		warlock1.setFont(new Font("Helvetica", Font.BOLD, 18));
		warlock1.setBackground(Color.LIGHT_GRAY);
		warlock1.setPreferredSize(new Dimension(200, 200));
		warlock1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(warlock1);

		for (int i = 0; i < hero1Button.size(); i++) {
			hero1Button.get(i).addActionListener(this);
		}

		JLabel l2 = new JLabel("SELECT PLAYER TWO:", SwingConstants.CENTER);
		l2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		l2.setFont(new Font("Helvetica", Font.BOLD, 18));
		view.getPanel().add(l2, BorderLayout.CENTER);

		JButton hunter2 = new JButton();
		hero2Button.add(hunter2);
		hunter2.setIcon(new ImageIcon("src/images/Rexxar.jbg"));
		hunter2.setFont(new Font("Helvetica", Font.BOLD, 18));
		hunter2.setBackground(Color.LIGHT_GRAY);
		hunter2.setPreferredSize(new Dimension(200, 200));
		hunter2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(hunter2);

		JButton mage2 = new JButton();
		hero2Button.add(mage2);
		mage2.setIcon(new ImageIcon("src/images/Jaina_Proudmoore.jbg"));
		mage2.setFont(new Font("Helvetica", Font.BOLD, 18));
		mage2.setBackground(Color.LIGHT_GRAY);
		mage2.setPreferredSize(new Dimension(200, 200));
		mage2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(mage2);

		JButton paladin2 = new JButton();
		hero2Button.add(paladin2);
		paladin2.setIcon(new ImageIcon("src/images/Uther_Lightbringer.jbg"));
		paladin2.setFont(new Font("Helvetica", Font.BOLD, 18));
		paladin2.setBackground(Color.LIGHT_GRAY);
		paladin2.setPreferredSize(new Dimension(200, 200));
		paladin2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(paladin2);

		JButton priest2 = new JButton();
		hero2Button.add(priest2);
		priest2.setIcon(new ImageIcon("src/images/Anduin_Wrynn.jbg"));
		priest2.setFont(new Font("Helvetica", Font.BOLD, 18));
		priest2.setBackground(Color.LIGHT_GRAY);
		priest2.setPreferredSize(new Dimension(200, 200));
		priest2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(priest2);

		JButton warlock2 = new JButton();
		hero2Button.add(warlock2);
		warlock2.setIcon(new ImageIcon("src/images/Gul'dan.jbg"));
		warlock2.setFont(new Font("Helvetica", Font.BOLD, 18));
		warlock2.setBackground(Color.LIGHT_GRAY);
		warlock2.setPreferredSize(new Dimension(200, 200));
		warlock2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getPanel().add(warlock2);
		
		for (int i = 0; i < hero2Button.size(); i++) {
 		    hero2Button.get(i).addActionListener(this);
		}
		
			
		done = new JButton("Finish");
		done.setFont(new Font("Helvetica", Font.BOLD, 18));
		done.setPreferredSize(new Dimension(50, 30));
		done.setBackground(Color.LIGHT_GRAY);
		done.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		view.getChoosePlayer().add(done, BorderLayout.SOUTH);
		
		done.addActionListener(this);
	
		view.revalidate();
		view.repaint();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		 try {
				new Sound("src/sounds/Click On.wav");
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
//		System.out.print("hi");
		
		if (hero1Button.contains(b)) {
			for(int i=0;i<hero1Button.size();i++) {
				hero1Button.get(i).setBackground(Color.LIGHT_GRAY);
				
			}
			b.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			b.setBackground(Color.RED);
		    int i = hero1Button.indexOf(e.getSource());

		
			//settingPlayer1(b);
			
			try {
				switch (i) {
			case 0:firstHero = new Hunter();break;
			case 1:firstHero = new Mage();break;
			case 2:firstHero = new Paladin();break;
			case 3:firstHero = new Priest();break;
			case 4:firstHero = new Warlock();break;
			}
				} catch (IOException e1){
					JOptionPane.showMessageDialog (null, e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				} catch( CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog (null, e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			

			}

		if (hero2Button.contains(b)) {
			for(int i=0;i<hero2Button.size();i++) {
				hero2Button.get(i).setBackground(Color.LIGHT_GRAY);
				
			}
			b.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			b.setBackground(Color.BLUE);
		    int i = hero2Button.indexOf(e.getSource());

			try {
				switch (i) {
				case 0:secondHero = new Hunter();break;
				case 1:secondHero = new Mage();break;
				case 2:secondHero = new Paladin();break;
				case 3: secondHero = new Priest();break;
				case 4: secondHero = new Warlock();break;
				}
				} catch (IOException | CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog (null, e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
		
		
		if (b.getActionCommand().equals("Finish") & firstHero!=null & secondHero!=null) {
			try {
				s1.stop();
				view.getChoosePlayer().dispose(); //CHECK
				//view.getChoosePlayer().setVisible(false);
				
				new GameController(firstHero,secondHero);
				
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog (null, e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog (null, e1.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	

	
	public Hero getFirstHero() {
		return firstHero;
	}
	public Hero getSecondHero() {
		return secondHero;
	}

//	public static void main(String[] args) {
//		new GettingStartedController();
//	}

}
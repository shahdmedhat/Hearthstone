package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

import sounds.Sound;
import view.WelcomeView;

public class WelcomeController implements ActionListener{
	private WelcomeView view;
	private Sound s;
	
	
	@SuppressWarnings("static-access")
	public WelcomeController(){
		
		view = new WelcomeView();
		 JButton start= new JButton("Start Battle !");
		 start.setFont(new Font("Helvetica", Font.BOLD, 18));
		 start.addActionListener(this);
		 start.setBounds(800, 750, 200, 100);
		 //start.setPreferredSize(new Dimension (100,90));
		 start.setBackground(Color.YELLOW);
		 
		 view.getL().add(start);
		 view.repaint();
		 view.revalidate();
		 
		 try {
			s=new Sound("src/sounds/s1.wav");
			s.getClip().loop(s.getClip().LOOP_CONTINUOUSLY);
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton b= (JButton) ae.getSource();
		
		if (b.getActionCommand().equals("Start Battle !")){
			
			 try {
				    s.stop();
					s=new Sound("src/sounds/Click On.wav");
					
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			finally {
				view.getWelcomeFrame().dispose();
				new GettingStartedController();
			}
			
		
		}
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String [] args){
		new WelcomeController();
		
	}

}

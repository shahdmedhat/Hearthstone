package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import images.TextAreaBackground;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import sounds.Sound;
//import model.heroes.Hunter;
//import model.heroes.Mage;
//import model.heroes.Paladin;
//import model.heroes.Priest;
//import model.heroes.Warlock;
import view.GameView;

public class GameController implements GameListener, ActionListener {

	private Game model;
	private GameView view;
	// private ArrayList<JButton> heroes;
	private ArrayList<JTextArea> heroCards;
	private ArrayList<JTextArea> opponentCards;

	private ArrayList<JTextArea> currentFieldCards;
	private ArrayList<JTextArea> opponentFieldCards;

	private ArrayList<JButton> playHandCardButtons;
	private ArrayList<JButton> playFieldCardButtons;

	private ArrayList<JButton> attackButtons;

	private JButton endTurn;
	private JButton powers;
	private JButton powersOpp;
	private JButton attackHero;
	private JButton attackOpponent;

	private int spellIndex;
	private int clickCount = 0;
	private Minion attacker;
	private int j;

	private Spell spellAttack;
	private Hero winner;

	private Hero firstHero;
	 

	public GameController(Hero h1, Hero h2) throws FullHandException,CloneNotSupportedException {

		firstHero=h1;
		
		heroCards = new ArrayList<JTextArea>();
		opponentCards = new ArrayList<JTextArea>();
		playHandCardButtons = new ArrayList<JButton>();
		playFieldCardButtons = new ArrayList<JButton>();
		attackButtons = new ArrayList<JButton>();
		currentFieldCards = new ArrayList<JTextArea>();
		opponentFieldCards = new ArrayList<JTextArea>();

		view = new GameView();

		endTurn = new JButton("END TURN");
		powers = new JButton("HERO POWER");
		powersOpp= new JButton("HERO POWER");
		attackOpponent = new JButton("ATTACK OPPONENT");
		attackHero = new JButton("ATTACK HERO");

		endTurn.addActionListener(this);
		powers.addActionListener(this);
		powersOpp.addActionListener(this);
		attackHero.addActionListener(this);
		attackOpponent.addActionListener(this);

		
		view.getCurrentView().add(powers);
		view.getCurrentView().add(attackHero);
		view.getCurrentView().add(endTurn);
		view.getOpponentView().add(powersOpp);
		view.getOpponentView().add(attackOpponent);

		model = new Game(h1, h2);
		model.setListener(this);
		view.getCurrentInfo().setText(model.getCurrentHero().toString());
		view.getOpponentInfo().setText(model.getOpponent().toString());

		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
			cardInfo.setEditable(false);
			cardInfo.setLayout(new BorderLayout());
			heroCards.add(cardInfo);
			cardInfo.setText(model.getCurrentHero().getHand().get(i).toString());
			JButton playCard = new JButton("PLAY CARD");
			playCard.addActionListener(this);
			playHandCardButtons.add(playCard);
			cardInfo.add(playCard, BorderLayout.PAGE_END);
			view.getCurrentCards().add(cardInfo);

		}

		for (int i = 0; i < model.getOpponent().getHand().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.black));
			cardInfo.setEditable(false);
			try {
				TextAreaBackground img = new TextAreaBackground("src/images/Cardback.png", 120, 200);
				img.setBounds(0, 0, 120, 200);
				img.setOpaque(true);
				cardInfo.add(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// cardInfo.setLayout(new GridLayout(8,0));
			opponentCards.add(cardInfo);
			// cardInfo.setText(model.getOpponent().getHand().get(i).toString());
			// //SHOULD NOT BE DISPLAYED
			view.getOpponentCards().add(cardInfo);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b.getActionCommand().equals("HERO POWER")) {
			if (e.getSource()==powers) {
				heroPowerListener();
			}
			else {
				JOptionPane.showMessageDialog(null,"You can not do any action in your opponent's turn" , "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
//			attacker=null;
//			clickCount=0;
			
		}
		

		if (b.getActionCommand().equals("END TURN")) {
			endingTurn();
		}


		// or b.getActionCommand().equals("ATTACK HERO")
		if ((b.getActionCommand().equals("ATTACK OPPONENT") || b
				.getActionCommand().equals("ATTACK HERO"))
				&& clickCount == 1
				&& model.getCurrentHero() instanceof Mage
				&& spellAttack == null && attacker == null) {
			try {
				if (b.getActionCommand().equals("ATTACK HERO")) {
					((Mage) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
					try {
						new Sound("src/sounds/Small Fireball.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// if (model.getCurrentHero().getCurrentHP() <= 0) {
					// onGameOver();
					// }
					view.getCurrentInfo().setText(model.getCurrentHero().toString());

				}
				if (b.getActionCommand().equals("ATTACK OPPONENT")) {
					((Mage) model.getCurrentHero()).useHeroPower(model.getOpponent());
					try {
						new Sound("src/sounds/Small Fireball.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					view.getOpponentInfo().setText(model.getOpponent().toString());

				}

			}

			catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeroPowerAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, e1.getBurned(),
						"Burnt Card", JOptionPane.PLAIN_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				clickCount = 0;
			}
		}

		

		if ((b.getActionCommand().equals("ATTACK HERO") || b.getActionCommand()
				.equals("ATTACK OPPONENT"))
				&& clickCount == 1
				&& model.getCurrentHero() instanceof Priest
				&& spellAttack == null && attacker == null) {
			try {
				if (b.getActionCommand().equals("ATTACK HERO")) {
					((Priest) model.getCurrentHero()).useHeroPower(model.getCurrentHero());
					try {
						new Sound("src/sounds/Power-Up.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					view.getCurrentInfo().repaint();
					view.getCurrentInfo().revalidate();
				}
				if (b.getActionCommand().equals("ATTACK OPPONENT")) {
					((Priest) model.getCurrentHero()).useHeroPower(model.getOpponent());
					try {
						new Sound("src/sounds/Power-Up.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					view.getOpponentInfo().setText(model.getOpponent().toString());
					view.getOpponentInfo().repaint();
					view.getOpponentInfo().revalidate();
				}

			}

			catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeroPowerAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, e1.getBurned(),
						"Burnt Card", JOptionPane.PLAIN_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				clickCount = 0;

			}
		}

		if (b.getActionCommand().equals("PLAY CARD")
				&& playHandCardButtons.contains(b)) {
			spellIndex = playHandCardButtons.indexOf(e.getSource());

			if (model.getCurrentHero().getHand().get(spellIndex) instanceof Spell) {
				spellAttack = (Spell) model.getCurrentHero().getHand().get(spellIndex);
				if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Level Up!") {
					// model.getCurrentHero().getHand().get(spellIndex).getClass().isInstance(FieldSpell);
					// CHECKKKK
					try {
						int temp1 = model.getCurrentHero().getField().size();
						model.getCurrentHero().castSpell((FieldSpell) spellAttack);
						try {
							new Sound("src/sounds/Power-Up.wav");

						} catch (UnsupportedAudioFileException | IOException
								| LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for (int j = 0; j < temp1; j++) {
							currentFieldCards.get(j).setText(model.getCurrentHero().getField().get(j).toString());
							view.repaint();
							view.revalidate();
						}

						view.getCurrentCards().remove(heroCards.get(spellIndex));
						heroCards.remove(spellIndex);
						playHandCardButtons.remove(spellIndex);
						view.getCurrentInfo().setText(model.getCurrentHero().toString());

					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Error!", JOptionPane.ERROR_MESSAGE);
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Error!", JOptionPane.ERROR_MESSAGE);
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					spellAttack = null;
				}
				
				if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Divine Spirit"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Kill Command"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Polymorph"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Pyroblast"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Seal of Champions"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Shadow Word: Death") {

					if (model.getCurrentHero().getHand().get(spellIndex)
							.getName() == "Kill Command"
							|| model.getCurrentHero().getHand().get(spellIndex)
									.getName() == "Pyroblast") {
						JOptionPane.showMessageDialog(null,
										"Please Select A Target Minion Or A Target Hero",
										"Notification",JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"Please Select A Target Minion",
								"Notification", JOptionPane.PLAIN_MESSAGE);
					}
					clickCount = 1;

				}
				if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Siphon Soul") {
					clickCount = 1;
					JOptionPane.showMessageDialog(null,
							"Please Select A Target Minion", "Notification",JOptionPane.PLAIN_MESSAGE);
				}

				if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Curse of Weakness"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Flamestrike"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Holy Nova"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Multi-Shot"
						|| model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Twisting Nether") {
					try {
						if (model.getCurrentHero().getHand().get(spellIndex)
								.getName() == "Twisting Nether") {
							model.getCurrentHero().castSpell((AOESpell) spellAttack,model.getOpponent().getField());
							int temp1 = model.getCurrentHero().getField().size();
							int temp2 = model.getOpponent().getField().size();
							for (int i = 0; i < temp1; i++) {
								view.getCurrentField().remove(currentFieldCards.get(0));
								view.getCurrentField().update(view.getCurrentField().getGraphics());
								currentFieldCards.remove(0);
								playFieldCardButtons.remove(0);
							}
							view.getCurrentField().repaint();
							view.getCurrentField().revalidate();

							for (int i = 0; i < temp2; i++) {
								view.getOpponentField().remove(opponentFieldCards.get(0));
								view.getOpponentField().update(view.getOpponentField().getGraphics());
								opponentFieldCards.remove(0);
								attackButtons.remove(0);

							}
							view.getOpponentField().repaint();
							view.getOpponentField().revalidate();
							try {
								new Sound("src/sounds/Blast.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

						else if (model.getCurrentHero().getHand()
								.get(spellIndex).getName() == "Holy Nova") {
							model.getCurrentHero().castSpell((AOESpell) spellAttack,model.getOpponent().getField());
							try {
								new Sound("src/sounds/media.io_divine.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							view.getOpponentField().removeAll();
							view.getOpponentField().revalidate();
							view.getOpponentField().repaint();
							opponentFieldCards.clear();
							attackButtons.clear();
							view.getOpponentField().repaint();
							view.getOpponentField().revalidate();
							view.repaint();
							view.revalidate();
							for (int i = 0; i < model.getOpponent().getField().size(); i++) {
								JTextArea cardInfo = new JTextArea();
								cardInfo.setPreferredSize(new Dimension(120,200));
								cardInfo.setBackground(Color.LIGHT_GRAY);
								cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
								cardInfo.setEditable(false);
								cardInfo.setLayout(new BorderLayout());
								cardInfo.setText(model.getOpponent().getField().get(i).toString());
								opponentFieldCards.add(cardInfo);
								JButton attack = new JButton("ATTACK");
								attack.addActionListener(this);
								attackButtons.add(attack);
								cardInfo.add(attack, BorderLayout.PAGE_END);
								view.getOpponentField().add(cardInfo);
								view.getOpponentField().repaint();
								view.getOpponentField().revalidate();
							}
							view.getOpponentField().repaint();
							view.getOpponentField().revalidate();
							view.repaint();
							view.revalidate();

							for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
								currentFieldCards.get(i).setText(model.getCurrentHero().getField().get(i).toString());
								view.getCurrentField().update(view.getCurrentField().getGraphics());
								view.getCurrentField().updateUI();
								view.getCurrentField().revalidate();
								view.getCurrentField().repaint();
							}

						}

						else if (model.getCurrentHero().getHand()
								.get(spellIndex).getName() == "Curse of Weakness") {
							model.getCurrentHero().castSpell((AOESpell) spellAttack,model.getOpponent().getField());
							try {
								 new Sound("src/sounds/gunshots.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							for (int i = 0; i < model.getOpponent().getField().size(); i++) {
								opponentFieldCards.get(i).setText(model.getOpponent().getField().get(i).toString());
								view.getOpponentField().update(view.getOpponentField().getGraphics());
								// view.getOpponentField().updateUI();
								view.getOpponentField().revalidate();
								view.getOpponentField().repaint();
							}
						}

						else {
							model.getCurrentHero().castSpell((AOESpell) spellAttack,model.getOpponent().getField());
							try {
								new Sound("src/sounds/gunshots.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							view.getOpponentField().removeAll();
							view.getOpponentField().revalidate();
							view.getOpponentField().repaint();
							opponentFieldCards.clear();
							attackButtons.clear();

							for (int i = 0; i < model.getOpponent().getField().size(); i++) {
								JTextArea cardInfo = new JTextArea();
								cardInfo.setPreferredSize(new Dimension(120,200));
								cardInfo.setBackground(Color.LIGHT_GRAY);
								cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
								cardInfo.setEditable(false);
								cardInfo.setLayout(new BorderLayout());
								cardInfo.setText(model.getOpponent().getField().get(i).toString());
								opponentFieldCards.add(cardInfo);
								JButton attack = new JButton("ATTACK");
								attack.addActionListener(this);
								attackButtons.add(attack);
								cardInfo.add(attack, BorderLayout.PAGE_END);
								view.getOpponentField().add(cardInfo);
								view.getOpponentField().repaint();
								view.getOpponentField().revalidate();
							}

							view.repaint();
							view.revalidate();

						}

						view.getCurrentCards().remove(heroCards.get(spellIndex));
						heroCards.remove(spellIndex);
						playHandCardButtons.remove(spellIndex);

						view.getCurrentInfo().setText(model.getCurrentHero().toString());

						// clickCount=0;
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Error!", JOptionPane.ERROR_MESSAGE);
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					finally {
						spellAttack = null;
					}
				}

			}

		}


		if ((b.getActionCommand().equals("ATTACK OPPONENT") || b
				.getActionCommand().equals("ATTACK HERO"))
				&& clickCount == 1
				&& spellAttack != null) {

			try {
				if (b.getActionCommand().equals("ATTACK OPPONENT")) {
					model.getCurrentHero().castSpell((HeroTargetSpell) spellAttack, model.getOpponent());
					try {
						new Sound("src/sounds/Blast.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getOpponentInfo().setText(model.getOpponent().toString());
				}
				else {
					model.getCurrentHero().castSpell((HeroTargetSpell) spellAttack, model.getCurrentHero());
				}

				view.getCurrentInfo().setText(model.getCurrentHero().toString());
				view.getCurrentCards().remove(heroCards.get(spellIndex));
				heroCards.remove(spellIndex);
				playHandCardButtons.remove(spellIndex);
				view.revalidate();
				view.repaint();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				clickCount = 0;
				spellAttack = null;
			}
			

		}

		
		
		
		if ( (b.getActionCommand().equals("ATTACK") || b.getActionCommand().equals("PLAY CARD")) && clickCount == 1
				&& attacker != null) {
			
			//System.out.println(target);
			try {
				Minion target;
				int k;
				if(b.getActionCommand().equals("ATTACK")) {
					 k = attackButtons.indexOf(e.getSource());
					 target = model.getOpponent().getField().get(k);
				}
				else {
					 k = playFieldCardButtons.indexOf(e.getSource());
					 target = model.getCurrentHero().getField().get(k);
				}
				
				model.getCurrentHero().attackWithMinion(attacker, target);
				try {
					new Sound("src/sounds/punch (2).wav");

				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (attacker.getCurrentHP() <= 0) {
					view.getCurrentField().remove(currentFieldCards.get(j));
					view.getCurrentField().update(view.getCurrentField().getGraphics());
					currentFieldCards.remove(j);
					playFieldCardButtons.remove(j);
				} else {
					currentFieldCards.get(j).setText(attacker.toString());
					view.getCurrentField().update(view.getCurrentField().getGraphics());
					view.getCurrentField().updateUI();
					view.getCurrentField().revalidate();
					view.getCurrentField().repaint();
				}

				if (target.getCurrentHP() <= 0) {
					view.getOpponentField().remove(opponentFieldCards.get(k));
					view.getOpponentField().update(view.getOpponentField().getGraphics());
					opponentFieldCards.remove(k);
					attackButtons.remove(k);
				} else {
					opponentFieldCards.get(k).setText(target.toString());
					view.getOpponentField().update(view.getOpponentField().getGraphics());
					// view.getOpponentField().updateUI();
					view.getOpponentField().revalidate();
					view.getOpponentField().repaint();
				}

			} catch (CannotAttackException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TauntBypassException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Warning!", JOptionPane.WARNING_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotSummonedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			finally {
				clickCount = 0;
				attacker = null;
				
			}
				
			
		}
		
		else if (b.getActionCommand().equals("PLAY CARD")
				&& playFieldCardButtons.contains(b) && clickCount == 0) {
			int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure you want to attack with this minion?","Confirmation",JOptionPane.YES_NO_OPTION);
			if (dialogButton==JOptionPane.YES_OPTION){
				clickCount = 1;
				j = playFieldCardButtons.indexOf(e.getSource());
				attacker = model.getCurrentHero().getField().get(j);
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select another action.");
			}
			

			// System.out.println(model.getCurrentHero().getField().get(j));
		}
		
		if ((b.getActionCommand().equals("ATTACK") || (b.getActionCommand()
				.equals("PLAY CARD") && playFieldCardButtons.contains(b)))
				&& clickCount == 1
				&& model.getCurrentHero() instanceof Mage
				&& spellAttack == null && attacker == null) {

			try {
				if (b.getActionCommand().equals("PLAY CARD")
						&& playFieldCardButtons.contains(b)) {
					int k = playFieldCardButtons.indexOf(e.getSource());
					Minion target = model.getCurrentHero().getField().get(k);
					((Mage) model.getCurrentHero()).useHeroPower(target);
					try {
						new Sound("src/sounds/Small Fireball.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					currentFieldCards.get(k).setText(target.toString());
					view.getCurrentField().update(view.getCurrentField().getGraphics());
					if (target.getCurrentHP() <= 0) {
						view.getCurrentField().remove(currentFieldCards.get(k));
						view.getCurrentField().update(view.getCurrentField().getGraphics());
						currentFieldCards.remove(k);
						playFieldCardButtons.remove(k);
					}

				} else {
					int k = attackButtons.indexOf(e.getSource());
					Minion target = model.getOpponent().getField().get(k);
					((Mage) model.getCurrentHero()).useHeroPower(target);
					try {
						new Sound("src/sounds/Small Fireball.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					opponentFieldCards.get(k).setText(target.toString());
					view.getOpponentField().update(view.getOpponentField().getGraphics());
					view.repaint();
					view.revalidate();
					if (target.getCurrentHP() <= 0) {
						view.getOpponentField().remove(opponentFieldCards.get(k));
						view.getOpponentField().update(view.getOpponentField().getGraphics());
						opponentFieldCards.remove(k);
						attackButtons.remove(k);
					}
				}

			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeroPowerAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, e1.getBurned(),
						"Burnt Card", JOptionPane.PLAIN_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				clickCount = 0;
			}
		}
		
		if ((b.getActionCommand().equals("ATTACK") || (b.getActionCommand()
				.equals("PLAY CARD") && playFieldCardButtons.contains(b)))
				&& clickCount == 1
				&& model.getCurrentHero() instanceof Priest
				&& spellAttack == null && attacker == null) {

			try {
				if (b.getActionCommand().equals("PLAY CARD")
						&& playFieldCardButtons.contains(b)) {
					int k = playFieldCardButtons.indexOf(e.getSource());
					Minion target = model.getCurrentHero().getField().get(k);
					((Priest) model.getCurrentHero()).useHeroPower(target);
					try {
						new Sound("src/sounds/Power-Up.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					currentFieldCards.get(k).setText(target.toString());
					view.getCurrentField().update(view.getCurrentField().getGraphics());

				} else {
					int k = attackButtons.indexOf(e.getSource());
					Minion target = model.getOpponent().getField().get(k);
					((Priest) model.getCurrentHero()).useHeroPower(target);
					try {
						 new Sound("src/sounds/Power-Up.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					opponentFieldCards.get(k).setText(target.toString());
					view.getOpponentField().update(view.getOpponentField().getGraphics());
					// view.getOpponentField().updateUI();

				}
				view.getOpponentField().revalidate();
				view.getOpponentField().repaint();
				view.getCurrentField().revalidate();
				view.getCurrentField().repaint();

			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeroPowerAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, e1.getBurned(),
						"Burnt Card", JOptionPane.PLAIN_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				clickCount = 0;
			}
			
		}
		
		
		if ((b.getActionCommand().equals("ATTACK") || (b.getActionCommand()
				.equals("PLAY CARD") && playFieldCardButtons.contains(b)))
				&& clickCount == 1 && spellAttack != null) {
			try {
				if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Siphon Soul") {
					if (b.getActionCommand().equals("PLAY CARD")) {
						int k = playFieldCardButtons.indexOf(e.getSource());
						Minion target = model.getCurrentHero().getField().get(k);
						model.getCurrentHero().castSpell((LeechingSpell) spellAttack, target);
						view.getCurrentField().remove(currentFieldCards.get(k));
						view.getCurrentField().update(view.getCurrentField().getGraphics());
						currentFieldCards.remove(k);
						playFieldCardButtons.remove(k);

					} 
					else {
						int k = attackButtons.indexOf(e.getSource());
						Minion target = model.getOpponent().getField().get(k);
						model.getCurrentHero().castSpell((LeechingSpell) spellAttack, target);
						view.getOpponentField().remove(opponentFieldCards.get(k));
						view.getOpponentField().update(view.getOpponentField().getGraphics());
						opponentFieldCards.remove(k);
						attackButtons.remove(k);
					}
					
					try {
						new Sound("src/sounds/media.io_divine.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} 
				else {
					if (b.getActionCommand().equals("PLAY CARD")) {
						int k = playFieldCardButtons.indexOf(e.getSource());
						Minion target = model.getCurrentHero().getField().get(k);
						if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Polymorph") {
							model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
							try {
								new Sound("src/sounds/sheep.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
						else {
							if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Seal of Champions"
									|| model.getCurrentHero().getHand().get(spellIndex).getName() == "Divine Spirit") {
								model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
								try {
									new Sound("src/sounds/media.io_divine.wav");

								} catch (UnsupportedAudioFileException| IOException
										| LineUnavailableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} 
							else {
								model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
								try {
									new Sound("src/sounds/Blast.wav");

								} catch (UnsupportedAudioFileException| IOException
										| LineUnavailableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
							
						}

						if (target.getCurrentHP() <= 0) {
							view.getCurrentField().remove(opponentFieldCards.get(k));
							view.getCurrentField().update(view.getCurrentField().getGraphics());
							currentFieldCards.remove(k);
							playFieldCardButtons.remove(k);
						} else {
							currentFieldCards.get(k).setText(target.toString());
							view.getCurrentField().update(view.getCurrentField().getGraphics());
							// view.getOpponentField().updateUI();
							view.getCurrentField().revalidate();
							view.getCurrentField().repaint();
						}
					}

					else {
						int k = attackButtons.indexOf(e.getSource());
						Minion target = model.getOpponent().getField().get(k);

						if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Polymorph") {
							model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
							try {
								new Sound("src/sounds/sheep.wav");

							} catch (UnsupportedAudioFileException
									| IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
						else {
							if (model.getCurrentHero().getHand().get(spellIndex).getName() == "Seal of Champions"
									|| model.getCurrentHero().getHand().get(spellIndex).getName() == "Divine Spirit") {
								model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
								try {
									new Sound("src/sounds/media.io_divine.wav");

								} catch (UnsupportedAudioFileException| IOException
										| LineUnavailableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} 
							else {
								model.getCurrentHero().castSpell((MinionTargetSpell) spellAttack, target);
								
								try {
									new Sound("src/sounds/Blast.wav");

								} catch (UnsupportedAudioFileException| IOException
										| LineUnavailableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
							
						}
						
						if (target.getCurrentHP() <= 0) {
							view.getOpponentField().remove(opponentFieldCards.get(k));
							view.getOpponentField().update(view.getOpponentField().getGraphics());
							opponentFieldCards.remove(k);
							attackButtons.remove(k);
						} else {
							opponentFieldCards.get(k).setText(target.toString());
							view.getOpponentField().update(view.getOpponentField().getGraphics());
							// view.getOpponentField().updateUI();
							view.getOpponentField().revalidate();
							view.getOpponentField().repaint();
						}

					}
					
					
				}

				view.getCurrentCards().remove(heroCards.get(spellIndex));
				heroCards.remove(spellIndex);
				playHandCardButtons.remove(spellIndex);
				view.getCurrentInfo().setText(model.getCurrentHero().toString());

			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
				spellAttack = null;
				clickCount = 0;
			}
			

		}
		
		if (b.getActionCommand().equals("PLAY CARD")
				&& playHandCardButtons.contains(b)) { // SUMMON MINION
			int i = playHandCardButtons.indexOf(e.getSource());
			try {
				if (model.getCurrentHero().getHand().get(i) instanceof Minion) {
					model.getCurrentHero().playMinion(
							(Minion) model.getCurrentHero().getHand().get(i));
					try {
						new Sound("src/sounds/card summon.wav");

					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					view.getCurrentCards().remove(heroCards.get(i));
					view.getCurrentField().add(heroCards.get(i));
					currentFieldCards.add(heroCards.get(i));
					heroCards.remove(i);
					playFieldCardButtons.add(playHandCardButtons.get(i));
					playHandCardButtons.remove(i);
					// ??????????????? PLAY FIELD BUTTONS
					// view.getCurrentInfo().update(view.getCurrentInfo().getGraphics());
					view.getCurrentInfo().setText(model.getCurrentHero().toString());
					view.getCurrentInfo().updateUI();
					view.repaint();
					view.revalidate();
				}

			} catch (NotYourTurnException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);

			} catch (NotEnoughManaException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);

			} catch (FullFieldException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		

		if ( (b.getActionCommand().equals("ATTACK OPPONENT") || b.getActionCommand().equals("ATTACK HERO") ) && clickCount == 1
				&& attacker != null) {
			try {
				if(b.getActionCommand().equals("ATTACK OPPONENT")) {
					model.getCurrentHero().attackWithMinion(attacker,model.getOpponent());
				}
				else {
					model.getCurrentHero().attackWithMinion(attacker,model.getCurrentHero());
				}
				
				try {
					new Sound("src/sounds/punch (2).wav");

				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				view.getOpponentInfo().setText(model.getOpponent().toString());
				view.getCurrentInfo().setText(model.getCurrentHero().toString());
				currentFieldCards.get(j).setText(attacker.toString());
				//view.getCurrentField().update(view.getCurrentField().getGraphics());
				view.getCurrentField().updateUI();

				view.revalidate();
				view.repaint();

			} catch (CannotAttackException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TauntBypassException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotSummonedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			finally {
				clickCount = 0;
				attacker = null;
				
			}

		}

	}

	public void endingTurn() {
		try {
			model.endTurn();
			try {
				new Sound("src/sounds/Button Click.wav");

			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (FullHandException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, e.getBurned(), "Burnt Card",
					JOptionPane.PLAIN_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.getCurrentInfo().setText(model.getCurrentHero().toString());
		view.getOpponentInfo().setText(model.getOpponent().toString());
		view.getCurrentCards().removeAll();
		view.getOpponentCards().removeAll();

		view.getCurrentField().removeAll();
		view.getCurrentField().revalidate();
		view.getCurrentField().repaint();

		view.getOpponentField().removeAll();
		view.getOpponentField().revalidate();
		view.getOpponentField().repaint();

		heroCards.clear();
		opponentCards.clear();
		playHandCardButtons.clear();
		playFieldCardButtons.clear();
		attackButtons.clear();
		currentFieldCards.clear();
		opponentFieldCards.clear();

		for (int i = 0; i < model.getCurrentHero().getHand().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
			cardInfo.setEditable(false);
			cardInfo.setLayout(new BorderLayout());
			heroCards.add(cardInfo);
			cardInfo.setText(model.getCurrentHero().getHand().get(i).toString());
			JButton playCard = new JButton("PLAY CARD");
			playCard.addActionListener(this);
			playHandCardButtons.add(playCard);
			cardInfo.add(playCard, BorderLayout.PAGE_END);
			view.getCurrentCards().add(cardInfo);
		}

		for (int i = 0; i < model.getOpponent().getHand().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.black));
			cardInfo.setEditable(false);
			cardInfo.setLayout(null);
			try {
				TextAreaBackground img = new TextAreaBackground("src/images/Cardback.png", 120, 200);
				img.setBounds(0, 0, 120, 200);
				img.setOpaque(true);
				cardInfo.add(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			opponentCards.add(cardInfo);
			view.getOpponentCards().add(cardInfo);

		}

		for (int i = 0; i < model.getCurrentHero().getField().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
			cardInfo.setEditable(false);
			cardInfo.setLayout(new BorderLayout());
			cardInfo.setText(model.getCurrentHero().getField().get(i).toString());
			currentFieldCards.add(cardInfo);
			JButton playCard = new JButton("PLAY CARD");
			playCard.addActionListener(this);
			playFieldCardButtons.add(playCard); // IMPPPPPPPPPPP
			cardInfo.add(playCard, BorderLayout.PAGE_END);
			view.getCurrentField().add(cardInfo);
		}

		for (int i = 0; i < model.getOpponent().getField().size(); i++) {
			JTextArea cardInfo = new JTextArea();
			cardInfo.setPreferredSize(new Dimension(120, 200));
			cardInfo.setBackground(Color.LIGHT_GRAY);
			cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
			cardInfo.setEditable(false);
			cardInfo.setLayout(new BorderLayout());
			cardInfo.setText(model.getOpponent().getField().get(i).toString());
			opponentFieldCards.add(cardInfo);
			JButton attack = new JButton("ATTACK");
			attack.addActionListener(this);
			attackButtons.add(attack);
			cardInfo.add(attack, BorderLayout.PAGE_END);
			view.getOpponentField().add(cardInfo);
		}
		
//		try {
//			TextAreaBackground img = new TextAreaBackground("src/images/field4.jpg",
//					Toolkit.getDefaultToolkit().getScreenSize().width , Toolkit.getDefaultToolkit().getScreenSize().height);
//			view.getCurrentField().add(img);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	public void heroPowerListener() {
		try {

			if (model.getCurrentHero() instanceof Hunter) {
				model.getCurrentHero().useHeroPower();
				try {
					new Sound("src/sounds/Small Fireball.wav");

				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.getCurrentInfo().setText(model.getCurrentHero().toString());
				view.getOpponentInfo().setText(model.getOpponent().toString());

			}
			if (model.getCurrentHero() instanceof Mage
					|| model.getCurrentHero() instanceof Priest) {
				clickCount = 1;
				JOptionPane.showMessageDialog(null,
						"Please Select A Target Minion Or A Target Hero",
						"Notification", JOptionPane.PLAIN_MESSAGE);
			}

			if (model.getCurrentHero() instanceof Paladin) {
				model.getCurrentHero().useHeroPower();
				try {
					new Sound("src/sounds/media.io_silverhand.wav");

				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				view.getCurrentInfo().setText(model.getCurrentHero().toString());
				JTextArea cardInfo = new JTextArea();
				cardInfo.setPreferredSize(new Dimension(120, 200));
				cardInfo.setBackground(Color.LIGHT_GRAY);
				cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
				cardInfo.setEditable(false);
				cardInfo.setLayout(new BorderLayout());

				cardInfo.setText(model.getCurrentHero().getField()
						.get(model.getCurrentHero().getField().size() - 1).toString());
				currentFieldCards.add(cardInfo);
				JButton playCard = new JButton("PLAY CARD");
				playCard.addActionListener(this);
				playFieldCardButtons.add(playCard);
				cardInfo.add(playCard, BorderLayout.PAGE_END);
				view.getCurrentField().add(cardInfo);
				view.repaint();
				view.revalidate();

			}
			if (model.getCurrentHero() instanceof Warlock) {
				((Warlock) model.getCurrentHero()).useHeroPower();
				try {
					new Sound("src/sounds/Button Click.wav");

				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.getCurrentInfo().setText(model.getCurrentHero().toString());
				Boolean found = model.getCurrentHero().fieldContains("Chromaggus");
				int x = 1;
				if (found == true) {
					x = 2;
				}
				for (int i = 0; i < x; i++) {
					JTextArea cardInfo = new JTextArea();
					cardInfo.setPreferredSize(new Dimension(120, 200));
					cardInfo.setBackground(Color.LIGHT_GRAY);
					cardInfo.setBorder(BorderFactory.createLineBorder(Color.cyan));
					cardInfo.setEditable(false);
					cardInfo.setLayout(new BorderLayout());

					cardInfo.setText(model.getCurrentHero().getHand()
							.get(model.getCurrentHero().getHand().size() - 1).toString());
					heroCards.add(cardInfo);
					JButton playCard = new JButton("PLAY CARD");
					playCard.addActionListener(this);
					playHandCardButtons.add(playCard);
					cardInfo.add(playCard, BorderLayout.PAGE_END);
					view.getCurrentCards().add(cardInfo);
					view.repaint();
					view.revalidate();
				}
			}

		} catch (NotEnoughManaException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeroPowerAlreadyUsedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotYourTurnException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullHandException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, e.getBurned(), "Burnt Card",
					JOptionPane.PLAIN_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FullFieldException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!",
					JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onGameOver() {
		String d = "";
		try {
			new Sound("src/sounds/am-i-totally-screwed-or.wav");
			TimeUnit.SECONDS.sleep(3);
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (model.getCurrentHero().getCurrentHP() <= 0) {
			if (model.getOpponent() == firstHero) {
				d = "PLAYER ONE- " + model.getOpponent().getName();
			} else {
				d = "PLAYER TWO- " + model.getOpponent().getName();
			}

		} else {
			if (model.getCurrentHero() == firstHero) {
				d = "PLAYER ONE- " + model.getCurrentHero().getName();
			} else {
				d = "PLAYER TWO- " + model.getCurrentHero().getName();
			}

		}
		
		view.getPlayMode().dispose();
		
		new FinishController(d); 
		

	}

	public Hero getWinner() {
		return winner;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public JButton getPowers() {
		return powers;
	}

}

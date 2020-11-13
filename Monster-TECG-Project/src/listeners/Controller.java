package listeners;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import board.Board;
import board.player.Phase;
import cards.Card;
import cards.Location;
import cards.MonsterCard;
import cards.spsr.SpSrCard;
import exceptions.DefenseMonsterAttackException;
import exceptions.MonsterMultipleAttackException;
import exceptions.MultipleMonsterAdditionException;
import exceptions.NoMonsterSpaceException;
import exceptions.UnexpectedFormatException;
import exceptions.WrongPhaseException;
import gui.CardButton;
import gui.EndTurnBut;
import gui.Gui;
import gui.HandPanel;
import gui.HiddenHandPanel;
import gui.MonsterButton;
import gui.MonstersPanel;
import gui.NextPhBut;
import gui.SpSrButton;
import gui.SpSrPanel;

public class Controller implements ActionListener,MouseListener {

	private JButton fc;
	private JButton sc;
	private JButton tc;
	private Board board;
	private Gui gui;
	private int summonset;

	public Controller(Board board,Gui gui) {
		this.board=board;
		this.gui=gui;
		addActionListeners();
		gui.getEndturn().addActionListener(this);
		gui.getNextphase().addActionListener(this);
	}

	public void addActionListeners(){
		ArrayList<MonsterButton> handp1 = this.gui.getHandp1().getMonsterbuttons();
		ArrayList<MonsterButton> handp2 = this.gui.getHandp2().getMonsterbuttons();
		ArrayList<SpSrButton> handp1spsr = this.gui.getHandp1().getSpSrbuttons();
		ArrayList<SpSrButton> handp2spsr = this.gui.getHandp2().getSpSrbuttons();
		ArrayList<MonsterButton> monstersp1 = this.gui.getMonsterareap1().getMonsters();
		ArrayList<JButton> spsrp1 = this.gui.getSpSrAreap1().getSpSr();
		ArrayList<MonsterButton> monstersp2 = this.gui.getMonsterAreap2().getMonsters();
		ArrayList<JButton> spsrp2 = this.gui.getSpSrAreap2().getSpSr();
		ArrayList<CardButton> hidp1 = this.gui.getP1hid().getHandButtons();
		ArrayList<CardButton> hidp2 = this.gui.getP2hid().getHandButtons();



		for (int i = 0; i < hidp1.size(); i++) {
			hidp1.get(i).addActionListener(this);
			hidp1.get(i).addMouseListener(this);
		}
		for (int i = 0; i < hidp2.size(); i++) {
			hidp2.get(i).addActionListener(this);
			hidp2.get(i).addMouseListener(this);
		}



		for (int i = 0; i < handp1.size(); i++) {
			handp1.get(i).addActionListener(this);
			handp1.get(i).addMouseListener(this);
		}
		for (int i = 0; i < handp2.size(); i++) {
			handp2.get(i).addActionListener(this);
			handp2.get(i).addMouseListener(this);
		}
		for (int i = 0; i < handp1spsr.size(); i++) {
			handp1spsr.get(i).addActionListener(this);
			handp1spsr.get(i).addMouseListener(this);
		}
		for (int i = 0; i < handp2spsr.size(); i++) {
			handp2spsr.get(i).addActionListener(this);
			handp2spsr.get(i).addMouseListener(this);
		}
		for (int i = 0; i < monstersp1.size(); i++) {
			monstersp1.get(i).addActionListener(this);
			monstersp1.get(i).addMouseListener(this);
		}
		for (int i = 0; i < spsrp1.size(); i++) {
			spsrp1.get(i).addActionListener(this);
			spsrp1.get(i).addMouseListener(this);
		}
		for (int i = 0; i < monstersp2.size(); i++) {
			monstersp2.get(i).addActionListener(this);
			monstersp2.get(i).addMouseListener(this);
		}
		for (int i = 0; i < spsrp2.size(); i++) {
			spsrp2.get(i).addActionListener(this);
			spsrp2.get(i).addMouseListener(this);
		}

	}

	private void updatefield() {
		
		if(board.isGameOver()){
			Object[] options = {"End Game!","Start New Game"};
			int choice = JOptionPane.showOptionDialog(gui, "GAME Over!,The winner is "+board.getWinner().getName()+"",null, JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
			if(choice==0){
				
				
				System.exit(0);
			}else{
				try {
					Gui.main(null);
					gui.setVisible(false);
				} catch (IOException | UnexpectedFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		gui.getDeckp1().setText("" + gui.getP1().getField().getDeck().getDeck().size());
		gui.getDeckp2().setText("" + gui.getP2().getField().getDeck().getDeck().size());
		gui.getLifep1().setText("Life Points: " + gui.getP1().getLifePoints());
		gui.getLifep2().setText("Life Points: " + gui.getP2().getLifePoints());
		gui.getCurrphase().setText(Card.getBoard().getActivePlayer().getField().getPhase().name());
		if(gui.getP1()==board.getActivePlayer()){
			gui.getSp1().remove(gui.getP1hid());
			gui.getSp1().remove(gui.getHandp1());
			gui.getPanel1().remove(gui.getSp1());
			gui.setHandp1(new HandPanel(gui.getP1()));
			JScrollPane sp1 = new JScrollPane(gui.getHandp1());
			sp1.setPreferredSize(new Dimension(500,150));
			sp1.setBorder(null);
			sp1.getViewport().setOpaque(false);
			sp1.setOpaque(false);
			sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			gui.setSp1(sp1);
			gui.getPanel1().add(gui.getSp1(),BorderLayout.SOUTH);
			gui.revalidate();
		}else{
			gui.getSp1().remove(gui.getP1hid());
			gui.getSp1().remove(gui.getHandp1());
			gui.getPanel1().remove(gui.getSp1());
			gui.setP1hid(new HiddenHandPanel(gui.getP1()));
			JScrollPane sp1 = new JScrollPane(gui.getP1hid());
			sp1.setBorder(null);
			sp1.getViewport().setOpaque(false);
			sp1.setPreferredSize(new Dimension(500,150));
			sp1.setOpaque(false);
			sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			gui.setSp1(sp1);
			gui.getPanel1().add(gui.getSp1(),BorderLayout.SOUTH);
			gui.revalidate();
		}


		if(gui.getP2()==board.getActivePlayer()){
			gui.getSp2().remove(gui.getP2hid());
			gui.getSp2().remove(gui.getHandp2());
			gui.getPanel2().remove(gui.getSp2());
			gui.setHandp2(new HandPanel(gui.getP2()));
			JScrollPane sp2 = new JScrollPane(gui.getHandp2());
			sp2.setBorder(null);
			sp2.getViewport().setOpaque(false);
			sp2.setPreferredSize(new Dimension(500,150));
			sp2.setOpaque(false);
			sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			gui.setSp2(sp2);
			gui.getPanel2().add(gui.getSp2(),BorderLayout.NORTH);
			gui.revalidate();
		}else{
			gui.getSp2().remove(gui.getP2hid());
			gui.getSp2().remove(gui.getHandp2());
			gui.getPanel2().remove(gui.getSp2());
			gui.setP2hid(new HiddenHandPanel(gui.getP2()));
			JScrollPane sp2 = new JScrollPane(gui.getP2hid()); 
			sp2.setBorder(null);
			sp2.getViewport().setOpaque(false);
			sp2.setPreferredSize(new Dimension(500,150));
			sp2.setOpaque(false);
			sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			gui.setSp2(sp2);
			gui.getPanel2().add(gui.getSp2(),BorderLayout.NORTH);
			gui.revalidate();
		}

		gui.getPanel1().remove(gui.getMonsterareap1());
		gui.setMonsterareap1(new MonstersPanel(gui.getP1()));
		gui.getPanel1().add(gui.getMonsterareap1(),BorderLayout.NORTH);


		gui.getPanel2().remove(gui.getMonsterAreap2());
		gui.setMonsterAreap2(new MonstersPanel(gui.getP2()));
		gui.getPanel2().add(gui.getMonsterAreap2(),BorderLayout.SOUTH);



		gui.getPanel1().remove(gui.getSpSrAreap1());
		gui.setSpSrAreap1(new SpSrPanel(gui.getP1()));
		gui.getPanel1().add(gui.getSpSrAreap1(),BorderLayout.CENTER);



		gui.getPanel2().remove(gui.getSpSrAreap2());
		gui.setSpSrAreap2(new SpSrPanel(gui.getP2()));
		gui.getPanel2().add(gui.getSpSrAreap2(),BorderLayout.CENTER);

		if(gui.getP1().getField().getGraveyard().size()>0){
			String url;
			if(gui.getP1().getField().getGraveyard().get(gui.getP1().getField().getGraveyard().size()-1) instanceof MonsterCard){
				url = "Cards Images Database/Monsters/"+gui.getP1().getField().getGraveyard().get(gui.getP1().getField().getGraveyard().size()-1).getName()+".png";
			}else{
				url = "Cards Images Database/SpSr/"+gui.getP1().getField().getGraveyard().get(gui.getP1().getField().getGraveyard().size()-1).getName()+".png";
			}
			ImageIcon img = new ImageIcon(url);
			Image img2 = img.getImage();
			Image newimg = img2.getScaledInstance(62, 91,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			gui.getGravep1().setIcon(newIcon);
		}

		if(gui.getP2().getField().getGraveyard().size()>0){
			String url;
			if(gui.getP2().getField().getGraveyard().get(gui.getP2().getField().getGraveyard().size()-1) instanceof MonsterCard){
				url = "Cards Images Database/Monsters/"+gui.getP2().getField().getGraveyard().get(gui.getP2().getField().getGraveyard().size()-1).getName()+".png";
			}else{
				url = "Cards Images Database/SpSr/"+gui.getP2().getField().getGraveyard().get(gui.getP2().getField().getGraveyard().size()-1).getName()+".png";
			}
			ImageIcon img = new ImageIcon(url);
			Image img2 = img.getImage();
			Image newimg = img2.getScaledInstance(62, 91,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			gui.getGravep2().setIcon(newIcon);
		}

		addActionListeners();
		//gui.getHandp1().update(gui.getP1());
		gui.revalidate();

	}

	public void seticons(){

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof MonsterButton){

			MonsterButton b = (MonsterButton) e.getSource();
			MonsterCard c = b.getMonster();
			if(c!=null){
				String url = "Cards Images Database/Monsters/"+c.getName()+".png";

				ImageIcon img = new ImageIcon(url);
				gui.getDescription().setIcon(img);
				gui.getDescription().revalidate();
				gui.revalidate();
			}
		}
		if(e.getSource() instanceof SpSrButton){
			SpSrButton b = (SpSrButton) e.getSource();
			SpSrCard c = b.getSpSr();
			if(c!=null){
				String url = "Cards Images Database/SpSr/"+c.getName()+".png";

				ImageIcon img = new ImageIcon(url);
				gui.getDescription().setIcon(img);
				gui.getDescription().revalidate();
				gui.revalidate();
			}
		}
		if(e.getSource() instanceof CardButton){



			ImageIcon img = new ImageIcon("Cards Images Database/Card Back.png");
			gui.getDescription().setIcon(img);
			gui.getDescription().revalidate();
			gui.revalidate();
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof NextPhBut){
			board.getActivePlayer().endPhase();
			gui.getCurrphase().setText("Current Phase: " + Card.getBoard().getActivePlayer().getField().getPhase());
			updatefield();
			//addActionListeners();
		}
		if(arg0.getSource() instanceof EndTurnBut){
			board.getActivePlayer().endTurn();
			updatefield();
			//addActionListeners();
		}
		if(arg0.getSource() instanceof MonsterButton){

			try{
				if(fc==null){

					MonsterCard monster = ((MonsterButton) arg0.getSource()).getMonster();
					//fc = button;

					if(monster.getLocation()==Location.HAND){
						fc = (MonsterButton) arg0.getSource();
						monster = ((MonsterButton) fc).getMonster();
						//fc = button;
						Object[] options = {"Summon","Set","Cancel"};
						summonset = JOptionPane.showOptionDialog(gui, "What is your action?",null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[2]);
						if(summonset==2){
							fc = null;
							return;
						}
						if(monster.getLevel()<=4){
							if(summonset==0){
								Card.getBoard().getActivePlayer().summonMonster(monster);
							}
							else{
								Card.getBoard().getActivePlayer().setMonster(monster);
							}

							fc = null;
							updatefield();
							return;
						}
						else{
							if(monster.getLevel()<=6){
								if(Card.getBoard().getActivePlayer().getField().getMonstersArea().size()==0){
									JOptionPane.showMessageDialog(gui, "No sufficient monsters");
									fc = null;
									//updatefield();
									return;
								}
								Object[] options4 = {"OK","Cancel"};
								int y = JOptionPane.showOptionDialog(gui, "Choose one sacrifice",null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[1]);
								if(y==0){
									fc = (MonsterButton) arg0.getSource();
									//JOptionPane.showMessageDialog(gui, "nnnnjjs");
									//updatefield();
									return;
								}
								else{
									fc = null;
									//updatefield();
									return;
								}


							}
							else{
								if(Card.getBoard().getActivePlayer().getField().getMonstersArea().size()<=1){
									JOptionPane.showMessageDialog(gui, "No sufficient monsters");
									fc = null;
									updatefield();
									return;
								}
								Object[] options4 = {"OK","Cancel"};
								int y = JOptionPane.showOptionDialog(gui, "Choose the first sacrifice",null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[1]);
								if(y==0){
									//updatefield();
									return;
								}
								else{
									fc = null;
									//updatefield();
									return;
								}

							}


						}

					}else{
						if(board.getActivePlayer().getField().getPhase()!=Phase.BATTLE){
							Object[] options4 = {"OK","Cancel"};
							int y = JOptionPane.showOptionDialog(gui, "Change Monster's Mode ?",null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[1]);
							if(y==0){
								board.getActivePlayer().switchMonsterMode(monster);
								updatefield();
								fc=null;
								sc=null;
								tc=null;
							}
						}else{
							fc = (MonsterButton)arg0.getSource();
							monster = ((MonsterButton) fc).getMonster();
							Object[] options4 = {"OK","Cancel"};
							int y = JOptionPane.showOptionDialog(gui, "Attack ?",null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[1]);
							if(y==1){
								fc=null;
								sc=null;
								tc=null;
								return;
							}
							if(board.getOpponentPlayer().getField().getMonstersArea().size()==0){
								board.getActivePlayer().declareAttack(((MonsterButton)fc).getMonster());
								fc=null;
								updatefield();
								return;
							}
							JOptionPane.showMessageDialog(gui, "Choose the monster you wish to attack");
							return;

						}
					}
				}




				else{


					if(sc==null){

						if(fc instanceof MonsterButton){

							MonsterCard monster = ((MonsterButton)arg0.getSource()).getMonster();
							if(board.getActivePlayer().getField().getPhase()!= Phase.BATTLE && !board.getActivePlayer().getField().getMonstersArea().contains(monster)){
								JOptionPane.showMessageDialog(gui, "You must choose monster cards from your field");
								fc=null;
								sc=null;
								return;
							}
							if(((MonsterButton) fc).getMonster().getLocation()==Location.HAND && monster.getLocation()==Location.FIELD
									&& board.getActivePlayer().getField().getMonstersArea().contains(monster)
									&& board.getActivePlayer().getField().getPhase()!= Phase.BATTLE){
								if(((MonsterButton) fc).getMonster().getLevel()<=6){

									sc = (MonsterButton) arg0.getSource();
									monster = ((MonsterButton) sc).getMonster();

									if(monster.getLocation()==Location.FIELD){
										ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
										sacrifices.add(((MonsterButton)sc).getMonster());
										if(summonset == 0){
											Card.getBoard().getActivePlayer().summonMonster(((MonsterButton) fc).getMonster(), sacrifices);
										}
										else{
											board.getActivePlayer().setMonster(((MonsterButton)fc).getMonster(), sacrifices);
										}
										updatefield();
										fc=null;
										sc=null;
										return;
									}
								}
								else{
									MonsterButton button = (MonsterButton) arg0.getSource();
									MonsterCard card = button.getMonster();
									sc = button;
									Object[] options4 = {"OK","Cancel"};
									int y = JOptionPane.showOptionDialog(gui, "Choose the second sacrifice",null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options4, options4[1]);
									if(y==0){
										//updatefield();
										return;
									}
									else{
										//updatefield();
										fc = null;
										sc = null;
										return;
									}
								}
							}
							else{
								sc = (MonsterButton) arg0.getSource();
								monster = ((MonsterButton) sc).getMonster();
								board.getActivePlayer().declareAttack(((MonsterButton)fc).getMonster(), ((MonsterButton)sc).getMonster());
								fc=null;
								sc=null;
								tc = null;
								updatefield();
								return;
							}
						}
						else{//fc is a spellbutton
							MonsterCard monster = ((MonsterButton)arg0.getSource()).getMonster();
							
							if(((SpSrButton)fc).getSpSr().getName().equalsIgnoreCase("Bola Infernal Hechizo")){
								if(!board.getOpponentPlayer().getField().getMonstersArea().contains(monster)){
									JOptionPane.showMessageDialog(gui, "You must choose monster cards from your opponent's field");
									fc=null;
									sc=null;
									return;
								}
								sc = (MonsterButton)arg0.getSource();
								MonsterCard mons = ((MonsterButton)sc).getMonster();
								board.getActivePlayer().activateSpSr(((SpSrButton)fc).getSpSr(), ((MonsterButton)sc).getMonster());
								fc=null;
								sc=null;
								updatefield();
								return;
							}
							else{
								if(!board.getActivePlayer().getField().getMonstersArea().contains(monster)){
									JOptionPane.showMessageDialog(gui, "You must choose monster cards from your field");
									fc=null;
									sc=null;
									return;
								}
								sc = (MonsterButton)arg0.getSource();
								MonsterCard mons = ((MonsterButton)sc).getMonster();
								board.getActivePlayer().activateSpSr(((SpSrButton)fc).getSpSr(), ((MonsterButton)sc).getMonster());
								fc=null;
								sc=null;
								updatefield();
								return;
							}
						}
					}
					else{
						if(arg0.getSource() instanceof MonsterButton){
							MonsterCard monster = ((MonsterButton)arg0.getSource()).getMonster();

							if(fc instanceof MonsterButton && ((MonsterButton) fc).getMonster().getLocation()==Location.HAND && monster.getLocation()==Location.FIELD && board.getActivePlayer().getField().getMonstersArea().contains(monster)){

								MonsterButton button = (MonsterButton) arg0.getSource();
								monster = button.getMonster();
								tc = button;
								if(tc==sc){
									JOptionPane.showMessageDialog(gui, "you have to choose two different monsters");
									fc=null;
									sc=null;
									tc=null;
									return;
								}
								ArrayList<MonsterCard> sacrifices = new ArrayList<MonsterCard>();
								sacrifices.add(((MonsterButton)sc).getMonster());
								sacrifices.add(((MonsterButton)tc).getMonster());
								if(summonset == 0){
									Card.getBoard().getActivePlayer().summonMonster(((MonsterButton) fc).getMonster(), sacrifices);
								}
								else{
									board.getActivePlayer().setMonster(((MonsterButton) fc).getMonster(), sacrifices);
								}
								updatefield();
								fc=null;
								sc=null;
								tc=null;
								return;

							}
							else{

							}
						}
					}

				}
			}

			catch(MultipleMonsterAdditionException e){
				fc = null;
				sc = null;
				tc = null;
				JOptionPane.showMessageDialog(gui, "you can't play more than one card");
			}
			catch(WrongPhaseException e){
				fc = null;
				sc = null;
				tc = null;
				JOptionPane.showMessageDialog(gui, "you can't set or summon a monster in this phase");
			}
			catch(NoMonsterSpaceException e){
				fc = null;
				sc = null;
				tc = null;
				JOptionPane.showMessageDialog(gui, "There is no avaialble space in monster Area");
			}
			catch(MonsterMultipleAttackException e){
				fc = null;
				sc = null;
				tc = null;
				JOptionPane.showMessageDialog(gui, "You Can Attack Only Once");
			}
			catch(DefenseMonsterAttackException e){
				fc = null;
				sc = null;
				tc = null;
				JOptionPane.showMessageDialog(gui, "You Can't Attack in Defense Mode");
			}

		}

		if(arg0.getSource() instanceof SpSrButton){
			if(fc instanceof MonsterButton){
				fc = null;
				sc=null;
				JOptionPane.showMessageDialog(gui, "you must sacrifice a monster card");
				return;
			}
			if(fc!=null &&((SpSrButton)fc).getName().equalsIgnoreCase("Bola Infernal Hechizo")){
				JOptionPane.showMessageDialog(gui, "you must choose a monster card");
				fc = null;
				sc=null;
				return;
			}

			if(fc==null){
				if(board.getActivePlayer().getField().getSpSrArea().contains(((SpSrButton)arg0.getSource()).getSpSr())
						|| board.getActivePlayer().getField().getHand().contains(((SpSrButton)arg0.getSource()).getSpSr())){
					if(((SpSrButton)arg0.getSource()).getSpSr().getLocation()==Location.HAND){
						String[] buttons = { "Activate", "Set", "cancel"};

						int rc = JOptionPane.showOptionDialog(null, "Activate or set card?", "Spell or Secret Card",
								JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);
						SpSrButton button = (SpSrButton) arg0.getSource();
						SpSrCard card = button.getSpSr();
						fc=button;
						if(rc==1){
							Card.getBoard().getActivePlayer().setSpSr(card);
							fc=null;
							updatefield();
							return;

						}
						if(rc==2){
							fc=null;
							return;
						}
						else{
							switch (card.getName()) {

							case "Arrepentimiento Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Bola Infernal Hechizo":
								String[] options = { "ok", "cancel"};

								int x = JOptionPane.showOptionDialog(null, "Choose the monster you wish to control", "SpellCard",
										JOptionPane.WARNING_MESSAGE, 0, null, options, options[1]);
								if(x==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Curar Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Poder Divino Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Poder Supremo Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Robar Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Rabia Hechizo":
								String[] options1 = { "ok", "cancel"};

								int x1 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to enhance", "SpellCard",
										JOptionPane.WARNING_MESSAGE, 0, null, options1, options1[1]);
								if(x1==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Monster Reborn":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Rayo Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Raigeki":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
								
							case "Baja Dano":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
								
							case "Bloqueo Hechizo":
								String[] options2 = { "ok", "cancel"};

								int x2 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to control", "SpellCard",
										JOptionPane.WARNING_MESSAGE, 0, null, options2, options2[1]);
								if(x2==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Bomba":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Cementerio":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Confusion":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Doble Dano":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Escudo":
								String[] options3 = { "ok", "cancel"};

								int x3 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to enhance", "SpellCard",
										JOptionPane.WARNING_MESSAGE, 0, null, options3, options3[1]);
								if(x3==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Mas Vida":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Terremoto":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Ultimo Recurso":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							default:
								board.getActivePlayer().activateSpSr(((SpSrButton)fc).getSpSr(), null);
								updatefield();

							}
						}
					}
					else{
						String[] buttons = { "ok", "cancel"};

						int rc = JOptionPane.showOptionDialog(null, "Activate card ?", "Spell or Secret Card",
								JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
						SpSrButton button = (SpSrButton) arg0.getSource();
						SpSrCard card = button.getSpSr();
						fc=button;
						if(rc==1){
							Card.getBoard().getActivePlayer().setSpSr(card);
							fc=null;
							updatefield();
							return;
						}
						else{
							switch (card.getName()) {

							case "Arrepentimiento Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Bola Infernal Hechizo":
								String[] options = { "ok", "cancel"};

								int x = JOptionPane.showOptionDialog(null, "Choose the monster you wish to control", "Spell or Secret Card",
										JOptionPane.WARNING_MESSAGE, 0, null, options, options[1]);
								if(x==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Curar Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Poder Divino Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Poder Supremo Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Robar Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Rabia Hechizo":
								String[] options1 = { "ok", "cancel"};

								int x1 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to enhance", "Spell or Secret Card",
										JOptionPane.WARNING_MESSAGE, 0, null, options1, options1[1]);
								if(x1==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Monster Reborn":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Rayo Hechizo":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Raigeki":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
								
							case "Baja Dano":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Bloqueo Hechizo":
								String[] options2 = { "ok", "cancel"};

								int x2 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to control", "Spell or Secret Card",
										JOptionPane.WARNING_MESSAGE, 0, null, options2, options2[1]);
								if(x2==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Bomba":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Cementerio":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Confusion":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Doble Dano":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Escudo":
								String[] options3 = { "ok", "cancel"};

								int x3 = JOptionPane.showOptionDialog(null, "Choose the monster you wish to enhance", "Spell or Secret Card",
										JOptionPane.WARNING_MESSAGE, 0, null, options3, options3[1]);
								if(x3==0){
									fc = button;
									return;
								}
								fc=null;
								return;
							case "Mas Vida":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Terremoto":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							case "Ultimo Recurso":
								board.getActivePlayer().activateSpSr(card, null);
								updatefield();
								fc = null;
								return;
							default:
								board.getActivePlayer().activateSpSr(((SpSrButton)fc).getSpSr(), null);
								updatefield();

							}
						}
					}
				}
			}
		}

	}
}

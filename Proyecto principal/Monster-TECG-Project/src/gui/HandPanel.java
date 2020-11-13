package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import board.player.Player;
import cards.Card;
import cards.MonsterCard;
import cards.spsr.SpSrCard;

public class HandPanel extends JPanel {
	private ArrayList<MonsterButton> monsterbuttons;
	private ArrayList<SpSrButton> spsrbuttons;
	public HandPanel(Player p) {
		super();
		update(p);
		
			
		
	}
	public ArrayList<MonsterButton> getHandButtons(){
		return this.monsterbuttons;
	}
	public void setHandButtons(ArrayList<MonsterButton> hb){
		this.monsterbuttons = hb;
	}
	
	public void update(Player p){
		this.removeAll();
		this.revalidate();
		monsterbuttons = new ArrayList<MonsterButton>(20);
		spsrbuttons = new ArrayList<SpSrButton>(20);
		//setPreferredSize(new Dimension(500,150));
		ArrayList<Card> hand = p.getField().getHand();
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		
		for (int i = 0; i <20; i++) {
			MonsterButton b = new MonsterButton();
			b.setVisible(false);
			this.add(b);
			monsterbuttons.add(b);		
			
		}
		for (int i = 0; i < 20; i++) {
			SpSrButton s = new SpSrButton();
			s.setVisible(false);
			this.add(s);
			spsrbuttons.add(s);
		}
		for(int i = 0; i <hand.size();i++){
			if(hand.get(i) instanceof MonsterCard){
				//monsterbuttons.get(i).setText(hand.get(i).getName());
				monsterbuttons.get(i).setMonster((MonsterCard) hand.get(i));
				monsterbuttons.get(i).setVisible(true);
				ImageIcon img = new ImageIcon("Cards Images Database/Monsters/"+hand.get(i).getName()+".png");
				Image img2 = img.getImage();
				Image newimg = img2.getScaledInstance(100, 146,  java.awt.Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(newimg);
				monsterbuttons.get(i).setIcon(newIcon);
				monsterbuttons.get(i).setPreferredSize(new Dimension(100,146));
				monsterbuttons.get(i).revalidate();
				monsterbuttons.get(i).setOpaque(false);
				monsterbuttons.get(i).repaint();
				//monsterbuttons.add(mb);
				//this.add(mb);
			}
			else{
				//spellbuttons.get(i).setText(hand.get(i).getName());
				spsrbuttons.get(i).setSpSr((SpSrCard) hand.get(i));
				spsrbuttons.get(i).setVisible(true);
				ImageIcon img = new ImageIcon("Cards Images Database/SpSr/"+hand.get(i).getName()+".png");
				Image img2 = img.getImage();
				Image newimg = img2.getScaledInstance(100, 146,  java.awt.Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(newimg);
				spsrbuttons.get(i).setIcon(newIcon);
				spsrbuttons.get(i).setPreferredSize(new Dimension(100,146));
				spsrbuttons.get(i).revalidate();
				spsrbuttons.get(i).setOpaque(false);
				spsrbuttons.get(i).repaint();
			}
		}
	}
	public ArrayList<MonsterButton> getMonsterbuttons() {
		return monsterbuttons;
	}
	public void setMonsterbuttons(ArrayList<MonsterButton> monsterbuttons) {
		this.monsterbuttons = monsterbuttons;
	}
	public ArrayList<SpSrButton> getSpSrbuttons() {
		return spsrbuttons;
	}
	public void setSpSrbuttons(ArrayList<SpSrButton> spsrbuttons) {
		this.spsrbuttons = spsrbuttons;
	}

}

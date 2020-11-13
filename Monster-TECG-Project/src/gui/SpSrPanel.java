package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import board.player.Player;

public class SpSrPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JButton> spsr;
	private Player p;
	
	public SpSrPanel(Player p) {
		super();
		setPreferredSize(new Dimension(500,100));
	update(p);
	}

	public ArrayList<JButton> getSpSr() {
		return spsr;
	}

	public void setSpSr(ArrayList<JButton> spsr) {
		this.spsr = spsr;
	}
	
	public void update(Player p){
		spsr = new ArrayList<JButton>();
		this.setLayout(new GridLayout(1,5));
		this.setOpaque(false);
		this.setVisible(true);
		for(int i = 0;i<p.getField().getSpSrArea().size();i++){
			if(p.getField().getSpSrArea().get(i)!=null){
			SpSrButton spsrbutton = new SpSrButton();
			ImageIcon img = new ImageIcon("Cards Images Database/Card Back  Set.png");
			Image img2 = img.getImage();
			Image newimg = img2.getScaledInstance(91, 62,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			spsrbutton.setIcon(newIcon);
			spsrbutton.setSpSr(p.getField().getSpSrArea().get(i));
			spsrbutton.setOpaque(false);
			spsr.add(spsrbutton);
			this.add(spsrbutton);
		
		}
		}
		for (int i = 0; i < 5-p.getField().getSpSrArea().size(); i++) {
		
			JButton but = new JButton();
			but.setOpaque(false);
			but.setOpaque(false);
			spsr.add(but);
			this.add(but);
		}
	}

}

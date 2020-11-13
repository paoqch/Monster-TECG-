package gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class NextPhBut extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NextPhBut(String string) {
		super(string);
		this.setPreferredSize(new Dimension(214,53));
		ImageIcon ic = new ImageIcon("NP.png");
		this.setIcon(ic);
		this.setOpaque(false);
	}

}

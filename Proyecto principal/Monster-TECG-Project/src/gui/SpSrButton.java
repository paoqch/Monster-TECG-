package gui;

import javax.swing.JButton;

import cards.spsr.SpSrCard;

public class SpSrButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SpSrCard spsr;
		public SpSrCard getSpSr() {
		return spsr;
	}
		
	public void setSpSr(SpSrCard spsr) {
		this.spsr = spsr;
	}
	
	public SpSrButton() {
			super();
			this.setVisible(true);
			
	}
	
	public SpSrButton(SpSrCard spsr){
			this.setVisible(true);
			this.setName(spsr.getName());
			this.setOpaque(false);
	}
	
	public SpSrButton(String name){
			
			super(name);
	}
}

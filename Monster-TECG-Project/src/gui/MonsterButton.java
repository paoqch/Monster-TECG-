package gui;

import javax.swing.JButton;

import cards.MonsterCard;

public class MonsterButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MonsterCard monster;
		public MonsterCard getMonster() {
		return monster;
	}
		
	public void setMonster(MonsterCard monster) {
		this.monster = monster;
	}
	
	public MonsterButton() {
			this.setVisible(true);
	}
	
	public MonsterButton(MonsterCard monster){
			this.setVisible(true);
			this.setName(monster.getName());
			
	}
	
	public MonsterButton(String name){
			
			super(name);
	}

}

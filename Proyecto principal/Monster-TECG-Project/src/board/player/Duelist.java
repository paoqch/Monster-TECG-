package board.player;

import java.util.ArrayList;

import cards.MonsterCard;
import cards.spsr.SpSrCard;

public interface Duelist {

	public boolean summonMonster(MonsterCard monster);
	 
	public boolean summonMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices);
	 
	public boolean setMonster(MonsterCard monster);

	public boolean setMonster(MonsterCard monster, ArrayList<MonsterCard> sacrifices);
	 
	public boolean setSpSr(SpSrCard spsr);
	 
	public boolean activateSpSr(SpSrCard spsr, MonsterCard monster);

	public boolean declareAttack(MonsterCard monster);
	 
	public boolean declareAttack(MonsterCard activeMonster, MonsterCard opponentMonster);
	 
	public void addCardToHand();
	 
	public void addNCardsToHand(int n);
	 
	public void endPhase();
	 
	public boolean endTurn();
	 
	public boolean switchMonsterMode(MonsterCard monster);
}

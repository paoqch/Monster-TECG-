package board.player;

import java.io.IOException;
import java.util.ArrayList;

import cards.Card;
import cards.Mode;
import cards.MonsterCard;
import cards.spsr.SpSrCard;
import exceptions.MultipleMonsterAdditionException;
import exceptions.UnexpectedFormatException;

public class Player implements Duelist {

	private final String name;
	private int lifePoints;
	private Field field;
	private boolean addedMonsterThisTurn;

	public Player(String name) throws IOException, UnexpectedFormatException {

		this.name = name;
		this.lifePoints = 1000;
		this.field = new Field();
		addedMonsterThisTurn = false;

	}

	@Override
	public boolean summonMonster(MonsterCard monster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		if (addedMonsterThisTurn)
			throw new MultipleMonsterAdditionException();

		boolean monsterAdded = this.field.addMonsterToField(monster,
				Mode.ATTACK, false);

		if (!monsterAdded)
			return false;

		addedMonsterThisTurn = true;

		return true;

	}

	@Override
	public boolean summonMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		if (addedMonsterThisTurn)
			throw new MultipleMonsterAdditionException();

		boolean monsterAdded = this.field.addMonsterToField(monster,
				Mode.ATTACK, sacrifices);

		if (!monsterAdded)
			return false;

		addedMonsterThisTurn = true;

		return true;

	}

	@Override
	public boolean setMonster(MonsterCard monster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		if (addedMonsterThisTurn)
			throw new MultipleMonsterAdditionException();

		boolean monsterAdded = this.field.addMonsterToField(monster,
				Mode.DEFENSE, true);

		if (!monsterAdded)
			return false;

		addedMonsterThisTurn = true;

		return true;

	}

	@Override
	public boolean setMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		if (addedMonsterThisTurn)
			throw new MultipleMonsterAdditionException();

		boolean monsterAdded = this.field.addMonsterToField(monster,
				Mode.DEFENSE, sacrifices);

		if (!monsterAdded)
			return false;

		addedMonsterThisTurn = true;

		return true;

	}

	@Override
	public boolean setSpSr(SpSrCard spsr) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		boolean spsrAdded = this.field.addSpSrToField(spsr, null, true);

		return spsrAdded;

	}

	@Override
	public boolean activateSpSr(SpSrCard spsr, MonsterCard monster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		boolean spsrActivated;

		if (this.field.getSpSrArea().contains(spsr))
			spsrActivated = this.field.activateSetSpSr(spsr, monster);
		else
			spsrActivated = this.field.addSpSrToField(spsr, monster, false);

		return spsrActivated;

	}

	@Override
	public boolean declareAttack(MonsterCard monster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		boolean monsterAttacked = this.field.declareAttack(monster, null);

		return monsterAttacked;

	}

	@Override
	public boolean declareAttack(MonsterCard activeMonster, MonsterCard opponentMonster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		boolean monsterAttacked = this.field.declareAttack(activeMonster, opponentMonster);

		return monsterAttacked;

	}

	@Override
	public void endPhase() {

		if (Card.getBoard().isGameOver())
			return;

		if (this != Card.getBoard().getActivePlayer())
			return;

		this.getField().endPhase();

	}

	@Override
	public boolean endTurn() {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		addedMonsterThisTurn = false;
		this.getField().endTurn();

		return true;

	}

	@Override
	public boolean switchMonsterMode(MonsterCard monster) {

		if (Card.getBoard().isGameOver())
			return false;

		if (this != Card.getBoard().getActivePlayer())
			return false;

		boolean monsterSwitched = this.field.switchMonsterMode(monster);

		return monsterSwitched;

	}

	@Override
	public void addCardToHand() {

		this.field.addCardToHand();

	}

	@Override
	public void addNCardsToHand(int n) {

		this.field.addNCardsToHand(n);

	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public String getName() {
		return name;
	}

	public Field getField() {
		return field;
	}

}

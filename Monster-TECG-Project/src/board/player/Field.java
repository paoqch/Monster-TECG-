package board.player;

import cards.Card;
import cards.Location;
import cards.Mode;
import cards.MonsterCard;
import cards.spsr.SpSrCard;
import exceptions.DefenseMonsterAttackException;
import exceptions.MonsterMultipleAttackException;
import exceptions.NoMonsterSpaceException;
import exceptions.NoSpSrSpaceException;
import exceptions.UnexpectedFormatException;
import exceptions.WrongPhaseException;

import java.io.IOException;
import java.util.ArrayList;

public class Field {

	private Phase phase = Phase.MAIN1;
	private final Deck deck;
	private ArrayList<MonsterCard> monstersArea;
	private ArrayList<SpSrCard> spsrArea;
	private ArrayList<Card> hand;
	private ArrayList<Card> graveyard;

	public Field() throws IOException, UnexpectedFormatException {

		monstersArea = new ArrayList<MonsterCard>();
		spsrArea = new ArrayList<SpSrCard>();
		hand = new ArrayList<Card>();
		graveyard = new ArrayList<Card>();
		deck = new Deck();

	}

	public boolean addMonsterToField(MonsterCard monster, Mode m,
			boolean isHidden) {

		if (!(hand.contains(monster) && monster.getLocation() == Location.HAND))
			return false;

		if (monstersArea.size() >= 5)
			throw new NoMonsterSpaceException();

		if (phase == Phase.BATTLE)
			throw new WrongPhaseException();

		hand.remove(monster);
		monster.setHidden(isHidden);
		monster.setMode(m);
		monster.setLocation(Location.FIELD);
		monstersArea.add(monster);
		return true;

	}

	public boolean addMonsterToField(MonsterCard monster, Mode m, ArrayList<MonsterCard> sacrifices) {

		if (!(hand.contains(monster) && monster.getLocation() == Location.HAND))
			return false;

		if (monster.getLevel() <= 4) {
			if (sacrifices != null)
				return false;
		} else if (monster.getLevel() <= 6) {
			if (sacrifices.size() != 1)
				return false;
		} else {
			if (sacrifices.size() != 2)
				return false;
		}

		boolean hidden = (m == Mode.DEFENSE);

		boolean monsterAdded = addMonsterToField(monster, m, hidden);

		if (!monsterAdded)
			return false;

		if (sacrifices != null) {
			removeMonsterToGraveyard(sacrifices);
		}
		return true;

	}

	public void removeMonsterToGraveyard(MonsterCard monster) {

		if (monstersArea.contains(monster)) {

			monstersArea.remove(monster);
			graveyard.add(monster);
			monster.setLocation(Location.GRAVEYARD);

		}

	}

	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters) {

		for (int i = 0; i < monsters.size(); i++)
			removeMonsterToGraveyard(monsters.get(i));

	}

	public boolean addSpSrToField(SpSrCard spsr, MonsterCard monster, boolean hidden) {

		if (!hand.contains(spsr))
			return false;

		if (spsrArea.size() >= 5)
			throw new NoSpSrSpaceException();

		if (phase == Phase.BATTLE)
			throw new WrongPhaseException();

		hand.remove(spsr);
		spsrArea.add(spsr);
		spsr.setLocation(Location.FIELD);

		if (!hidden)
			return activateSetSpSr(spsr, monster);

		return true;

	}

	public boolean activateSetSpSr(SpSrCard spsr, MonsterCard monster) {

		if (!spsrArea.contains(spsr))
			return false;

		if (phase == Phase.BATTLE)
			throw new WrongPhaseException();

		spsr.action(monster);
		removeSpSrToGraveyard(spsr);

		return true;

	}

	public void removeSpSrToGraveyard(SpSrCard spsr) {

		if (!spsrArea.contains(spsr))
			return;

		spsrArea.remove(spsr);
		graveyard.add(spsr);
		spsr.setLocation(Location.GRAVEYARD);

	}

	public void removeSpSrToGraveyard(ArrayList<SpSrCard> spsr) {

		for (int i = 0; i < spsr.size(); i++) {

			SpSrCard c = spsr.get(i);

			if (!spsrArea.contains(c))
				continue;

			spsrArea.remove(c);
			graveyard.add(c);
			c.setLocation(Location.GRAVEYARD);

		}

	}

	public boolean declareAttack(MonsterCard m1, MonsterCard m2) {

		if (phase != Phase.BATTLE)
			throw new WrongPhaseException();

		if (m1.getMode() != Mode.ATTACK)
			throw new DefenseMonsterAttackException();

		if (m1.isAttacked())
			throw new MonsterMultipleAttackException();

		ArrayList<MonsterCard> oppMonstersArea = Card.getBoard().getOpponentPlayer().getField().monstersArea;

		if (m2 == null && oppMonstersArea.size() == 0)
			m1.action();
		else if (m2 != null && oppMonstersArea.contains(m2))
			m1.action(m2);
		else
			return false;

		if (Card.getBoard().getActivePlayer().getLifePoints() <= 0) {
			Card.getBoard().getActivePlayer().setLifePoints(0);
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
		}
		if (Card.getBoard().getOpponentPlayer().getLifePoints() <= 0) {
			Card.getBoard().getOpponentPlayer().setLifePoints(0);
			Card.getBoard().setWinner(Card.getBoard().getActivePlayer());
		}

		return true;

	}

	public void endPhase() {

		switch (phase) {

		case MAIN1:
			setPhase(Phase.BATTLE);
			break;

		case BATTLE:
			setPhase(Phase.MAIN2);
			break;

		case MAIN2:
			endTurn();
			break;

		}

	}

	public void endTurn() {

		phase = Phase.MAIN1;

		for (MonsterCard m : monstersArea) {
			m.setAttacked(false);
			m.setSwitchedMode(false);
		}

		Card.getBoard().nextPlayer();

	}

	public boolean switchMonsterMode(MonsterCard monster) {

		if (!monstersArea.contains(monster))
			return false;

		if (phase == Phase.BATTLE)
			throw new WrongPhaseException();

		if (monster.isSwitchedMode())
			return false;

		monster.switchMode();
		monster.setSwitchedMode(true);

		return true;

	}

	public void addCardToHand() {

		if (deck.getDeck().size() == 0) {

			if (this == Card.getBoard().getActivePlayer().getField())
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			else
				Card.getBoard().setWinner(Card.getBoard().getActivePlayer());

			return;
		}

		Card temp = deck.drawOneCard();
		hand.add(temp);
		temp.setLocation(Location.HAND);

	}

	public void addNCardsToHand(int n) {

		for (int j = 0; j < n; j++)
			addCardToHand();

	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Deck getDeck() {
		return deck;
	}

	public ArrayList<MonsterCard> getMonstersArea() {
		return monstersArea;
	}

	public ArrayList<SpSrCard> getSpSrArea() {
		return spsrArea;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getGraveyard() {
		return graveyard;
	}

	public int discardHand() {

		int discardedCards = hand.size();
		for (int i = 0; i < hand.size();)
			graveyard.add(hand.remove(i));
		return (discardedCards);

	}

	public MonsterCard strongestMonsterInGraveyard() {

		MonsterCard strongest = new MonsterCard("", "", 0, 0, 0);
		int strongestValue = 0;
		for (int i = 0; i < graveyard.size(); i++) {

			if (graveyard.get(i) instanceof MonsterCard) {

				if (((MonsterCard) graveyard.get(i)).getAttackPoints() > strongestValue) {

					strongest = (MonsterCard) graveyard.get(i);
					strongestValue = ((MonsterCard) graveyard.get(i))
							.getAttackPoints();

				}

			}

		}

		return (strongest);

	}

}

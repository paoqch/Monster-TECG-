package cards.spells;


import cards.Card;
import cards.MonsterCard;

public class CurarHechizo extends SpellCard {

	public CurarHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(2);

	}

}

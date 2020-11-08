package cards.spells;

import cards.Card;
import cards.MonsterCard;


public class RobarHechizo extends SpellCard {

	public RobarHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(1);


	}

}

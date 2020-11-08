package cards.spells;

import cards.Card;
import cards.MonsterCard;

public class RayoHechizo extends SpellCard {

	public RayoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(2);

	}

}

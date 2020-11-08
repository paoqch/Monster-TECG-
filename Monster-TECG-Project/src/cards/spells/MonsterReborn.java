package cards.spells;

import cards.Card;
import cards.MonsterCard;

public class MonsterReborn extends SpellCard {

	public MonsterReborn(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(2);
		}


}

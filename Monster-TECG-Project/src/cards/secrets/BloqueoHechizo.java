package cards.secrets;

import cards.Card;
import cards.MonsterCard;


public class BloqueoHechizo extends SecretCard {

	public BloqueoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(1);


	}

}

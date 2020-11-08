package cards.secrets;

import cards.Card;
import cards.MonsterCard;

public class MásVida extends SecretCard {

	public MásVida(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(2);
		}


}

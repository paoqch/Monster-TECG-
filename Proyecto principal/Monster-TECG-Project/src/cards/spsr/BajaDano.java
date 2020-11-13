package cards.spsr;

import cards.Card;
import cards.MonsterCard;

public class BajaDano extends SpSrCard {

	public BajaDano(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard m) {

		int discardedCards = Card.getBoard().getActivePlayer().getField().discardHand();
		Card.getBoard().getActivePlayer().addNCardsToHand(discardedCards);

		discardedCards = Card.getBoard().getOpponentPlayer().getField().discardHand();
		Card.getBoard().getOpponentPlayer().addNCardsToHand(discardedCards);

	}

}

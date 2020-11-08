package cards.spells;

import cards.Card;
import cards.MonsterCard;

public class ArrepentimientoHechizo extends SpellCard {

	public ArrepentimientoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard m) {

		int discardedCards = Card.getBoard().getActivePlayer().getField().discardHand();
		Card.getBoard().getActivePlayer().addNCardsToHand(discardedCards);

		discardedCards = Card.getBoard().getOpponentPlayer().getField().discardHand();
		Card.getBoard().getOpponentPlayer().addNCardsToHand(discardedCards);

	}

}


package cards.spsr;


import cards.Card;
import cards.MonsterCard;

public class Terremoto extends SpSrCard {

	public Terremoto(String name, String desc) {

		super(name, desc);
	}

	public void action(MonsterCard monster) {

		Card.getBoard().getActivePlayer().addNCardsToHand(2);

	}

}

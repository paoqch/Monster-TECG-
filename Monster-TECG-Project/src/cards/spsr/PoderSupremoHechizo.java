package cards.spsr;

import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class PoderSupremoHechizo extends SpSrCard {

	public PoderSupremoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		ArrayList<SpSrCard> spsr = Card.getBoard().getOpponentPlayer()
				.getField().getSpSrArea();
		
		Card.getBoard().getOpponentPlayer().getField()
				.removeSpSrToGraveyard(new ArrayList<SpSrCard>(spsr));
		

	}

}

package cards.spsr;


import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class RabiaHechizo extends PoderSupremoHechizo {

	public RabiaHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		
		super.action(monster);
		
		ArrayList<SpSrCard> spsr = Card.getBoard().getActivePlayer()
				.getField().getSpSrArea();
		
		Card.getBoard().getActivePlayer().getField()
				.removeSpSrToGraveyard(new ArrayList<SpSrCard>(spsr));

	}

}


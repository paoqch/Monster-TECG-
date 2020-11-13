package cards.spsr;

import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class Confusion extends SpSrCard {

	public Confusion(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		ArrayList<MonsterCard> monsters = Card.getBoard().getOpponentPlayer()
				.getField().getMonstersArea();
		
		Card.getBoard().getOpponentPlayer().getField()
				.removeMonsterToGraveyard(new ArrayList<MonsterCard>(monsters));
	}

}

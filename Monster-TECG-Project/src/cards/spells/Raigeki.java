package cards.spells;

import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class Raigeki extends SpellCard {

	public Raigeki(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		ArrayList<MonsterCard> monsters = Card.getBoard().getOpponentPlayer()
				.getField().getMonstersArea();
		
		Card.getBoard().getOpponentPlayer().getField()
				.removeMonsterToGraveyard(new ArrayList<MonsterCard>(monsters));

	}

}

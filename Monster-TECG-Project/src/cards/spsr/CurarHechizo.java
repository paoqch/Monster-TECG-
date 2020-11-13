package cards.spsr;


import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class CurarHechizo extends Raigeki {

	public CurarHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		super.action(monster);

		ArrayList<MonsterCard> monsters = Card.getBoard().getActivePlayer()
				.getField().getMonstersArea();

		Card.getBoard().getActivePlayer().getField()
				.removeMonsterToGraveyard(new ArrayList<MonsterCard>(monsters));

	}

}


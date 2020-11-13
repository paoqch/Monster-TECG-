package cards.spsr;

import cards.Card;
import cards.MonsterCard;

public class BolaInfernalHechizo extends SpSrCard {

	public BolaInfernalHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		Card.getBoard().getOpponentPlayer().getField().getMonstersArea()
		.remove(monster);

		monster.setHidden(false);
		
		Card.getBoard().getActivePlayer().getField().getMonstersArea()
		.add(monster);
	}
}


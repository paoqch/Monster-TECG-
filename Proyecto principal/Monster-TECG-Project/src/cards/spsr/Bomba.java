package cards.spsr;

import cards.MonsterCard;

public class Bomba extends SpSrCard {

	public Bomba(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int monsterCardsCount = BajaDano.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (300 * monsterCardsCount));

	}

}

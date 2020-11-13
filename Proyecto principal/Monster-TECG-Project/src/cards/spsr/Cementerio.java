package cards.spsr;

import cards.MonsterCard;

public class Cementerio extends SpSrCard {

	public Cementerio(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int monsterCardsCount = BajaDano.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (150 * monsterCardsCount));

		}

}

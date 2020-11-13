package cards.spsr;

import cards.MonsterCard;

public class Escudo extends SpSrCard {

	public Escudo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spellCardsCount = BajaDano.getBoard().getActivePlayer()
				.getField().getSpSrArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (200 * spellCardsCount));
		

	}

}

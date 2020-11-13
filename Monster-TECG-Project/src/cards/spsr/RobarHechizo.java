package cards.spsr;

import cards.MonsterCard;


public class RobarHechizo extends SpSrCard {

	public RobarHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spsrCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer()
				.getField().getSpSrArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (500 * spsrCardsCount));
		
		monster.setDefensePoints(monster.getDefensePoints()
				+ (500 * spsrCardsCount));
	}
}

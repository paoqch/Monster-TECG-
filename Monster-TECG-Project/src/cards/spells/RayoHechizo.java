package cards.spells;

import cards.MonsterCard;

public class RayoHechizo extends SpellCard {

	public RayoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int monsterCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (300 * monsterCardsCount));

	}

}

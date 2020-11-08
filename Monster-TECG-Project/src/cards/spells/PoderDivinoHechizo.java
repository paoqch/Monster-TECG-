package cards.spells;

import cards.MonsterCard;

public class PoderDivinoHechizo extends SpellCard {

	public PoderDivinoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int monsterCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer().getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (150 * monsterCardsCount));

		}

}

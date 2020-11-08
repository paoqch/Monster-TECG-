package cards.spells;

import cards.MonsterCard;


public class RobarHechizo extends SpellCard {

	public RobarHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spellCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer()
				.getField().getSpellArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (500 * spellCardsCount));
		
		monster.setDefensePoints(monster.getDefensePoints()
				+ (500 * spellCardsCount));
	}
}

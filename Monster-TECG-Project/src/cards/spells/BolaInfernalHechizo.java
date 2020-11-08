package cards.spells;

import cards.MonsterCard;

public class BolaInfernalHechizo extends SpellCard {

	public BolaInfernalHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spellCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer()
				.getField().getSpellArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints() + (50 * spellCardsCount));
		

	}

}

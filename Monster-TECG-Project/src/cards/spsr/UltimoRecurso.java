package cards.spsr;

import cards.MonsterCard;

public class UltimoRecurso extends SpSrCard {

	public UltimoRecurso(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spellCardsCount = BajaDaño.getBoard().getActivePlayer()
				.getField().getSpellArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints() + (50 * spellCardsCount));
		

	}

}

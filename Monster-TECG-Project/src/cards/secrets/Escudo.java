package cards.secrets;

import cards.MonsterCard;

public class Escudo extends SecretCard {

	public Escudo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int spellCardsCount = BajaDaño.getBoard().getActivePlayer()
				.getField().getSpellArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (200 * spellCardsCount));
		

	}

}

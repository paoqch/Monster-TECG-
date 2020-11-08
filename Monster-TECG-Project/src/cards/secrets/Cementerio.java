package cards.secrets;

import cards.MonsterCard;

public class Cementerio extends SecretCard {

	public Cementerio(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		int monsterCardsCount = BajaDaño.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (150 * monsterCardsCount));

		}

}

package cards.secrets;


import cards.MonsterCard;

public class DobleDaño extends SecretCard {

	public DobleDaño(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		
		int monsterCardsCount = BajaDaño.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (125 * monsterCardsCount));

	}

}

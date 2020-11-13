package cards.spsr;


import cards.MonsterCard;

public class DobleDano extends SpSrCard {

	public DobleDano(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		
		int monsterCardsCount = BajaDano.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (125 * monsterCardsCount));

	}

}

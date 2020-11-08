package cards.spells;


import cards.MonsterCard;

public class RabiaHechizo extends SpellCard {

	public RabiaHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		
		int monsterCardsCount = ArrepentimientoHechizo.getBoard().getActivePlayer()
				.getField().getMonstersArea().size();
		
		monster.setAttackPoints(monster.getAttackPoints()
				+ (125 * monsterCardsCount));

	}

}

package cards.spells;


import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class RabiaHechizo extends PoderSupremoHechizo {

	public RabiaHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {
		
		super.action(monster);
		
		ArrayList<SpellCard> spells = Card.getBoard().getActivePlayer()
				.getField().getSpellArea();
		
		Card.getBoard().getActivePlayer().getField()
				.removeSpellToGraveyard(new ArrayList<SpellCard>(spells));

	}

}


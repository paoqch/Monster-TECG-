package cards.spells;

import java.util.ArrayList;

import cards.Card;
import cards.MonsterCard;

public class PoderSupremoHechizo extends SpellCard {

	public PoderSupremoHechizo(String name, String desc) {

		super(name, desc);

	}

	public void action(MonsterCard monster) {

		ArrayList<SpellCard> spells = Card.getBoard().getOpponentPlayer()
				.getField().getSpellArea();
		
		Card.getBoard().getOpponentPlayer().getField()
				.removeSpellToGraveyard(new ArrayList<SpellCard>(spells));
		

	}

}

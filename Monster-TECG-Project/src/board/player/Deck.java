package board.player;

import cards.Card;
import cards.Location;
import cards.MonsterCard;
import cards.spells.ArrepentimientoHechizo;
import cards.spells.BolaInfernalHechizo;
import cards.spells.CurarHechizo;
import cards.spells.PoderDivinoHechizo;
import cards.spells.PoderSupremoHechizo;
import cards.spells.RobarHechizo;
import cards.spells.RabiaHechizo;
import cards.spells.MonsterReborn;
import cards.spells.RayoHechizo;
import cards.spells.Raigeki;
import cards.spells.SpellCard;
import exceptions.EmptyFieldException;
import exceptions.MissingFieldException;
import exceptions.UnexpectedFormatException;
import exceptions.UnknownCardTypeException;
import exceptions.UnknownSpellCardException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Deck {

	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;

	private static String monstersPath = "Database-Monsters.csv";
	private static String spellsPath = "Database-Spells.csv";

	private final ArrayList<Card> deck;
	int trials = 0;

	public Deck() throws IOException, NumberFormatException,
			UnexpectedFormatException {

		if ((monsters == null) || (spells == null)) {

			Scanner sc = new Scanner(System.in);

			while (true) {

				try {

					monsters = loadCardsFromFile(Deck.getMonstersPath());
					spells = loadCardsFromFile(Deck.getSpellsPath());
					break;

				} catch (UnexpectedFormatException e) {

					if (trials >= 3) {
						sc.close();
						throw e;
					}

					System.out.println("Error in reading from file "
							+ e.getSourceFile() + " at line "
							+ e.getSourceLine());
					System.out.println(e.getMessage());
					System.out.println("Please enter another path:");

					trials++;

					if (e.getSourceFile().equalsIgnoreCase(
							Deck.getMonstersPath())) {
						Deck.setMonstersPath(sc.nextLine());
					}
					if (e.getSourceFile()
							.equalsIgnoreCase(Deck.getSpellsPath())) {
						Deck.setSpellsPath(sc.nextLine());
					}

				} catch (FileNotFoundException e) {

					if (trials >= 3) {
						sc.close();
						throw e;
					}

					String s = (monsters == null) ? Deck.getMonstersPath()
							: Deck.getSpellsPath();

					System.out.println("The file \"" + s + "\" is not found.");
					System.out.println("Please enter another path:");

					trials++;
					String path = sc.nextLine();

					if (monsters == null)
						Deck.setMonstersPath(path);
					else
						Deck.setSpellsPath(path);

				}

			}

			sc.close();

		}

		deck = new ArrayList<Card>();
		buildDeck(monsters, spells);
		shuffleDeck();

	}

	public ArrayList<Card> loadCardsFromFile(String path) throws IOException,
			UnexpectedFormatException {

		ArrayList<Card> temp = new ArrayList<Card>();

		String line;

		BufferedReader br = new BufferedReader(new FileReader(path));

		int lineNumber = 0;

		while ((line = br.readLine()) != null) {

			lineNumber++;

			String[] cardInfo = line.split(",");

			if (cardInfo.length == 0) {

				br.close();
				throw new MissingFieldException(
						"The number of fields in the line did not match the expected.",
						path, lineNumber);

			} else {

				if (cardInfo[0].equalsIgnoreCase("Monster")
						&& cardInfo.length != 6) {

					br.close();
					throw new MissingFieldException(
							"The number of fields in the line did not match the expected.",
							path, lineNumber);

				} else if (cardInfo[0].equalsIgnoreCase("Spell")
						&& cardInfo.length != 3) {

					br.close();
					throw new MissingFieldException(
							"The number of fields in the line did not match the expected.",
							path, lineNumber);

				}

			}

			for (int i = 0; i < cardInfo.length; i++)
				if (cardInfo[i].equals("") || cardInfo[i].equals(" ")) {

					br.close();
					throw new EmptyFieldException("Empty Field.", path,
							lineNumber, i + 1);

				}

			if (cardInfo[0].equalsIgnoreCase("Monster")) {

				temp.add(new MonsterCard(cardInfo[1], cardInfo[2], Integer
						.parseInt(cardInfo[5]), Integer.parseInt(cardInfo[3]),
						Integer.parseInt(cardInfo[4])));

			} else {

				if (!cardInfo[0].equalsIgnoreCase("Spell")) {

					br.close();
					throw new UnknownCardTypeException("Unknown Card type.",
							path, lineNumber, cardInfo[0]);

				}

				switch (cardInfo[1]) {

				case "Arrepentimiento Hechizo":
					temp.add(new ArrepentimientoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Bola Infernal Hechizo":
					temp.add(new BolaInfernalHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Curar Hechizo":
					temp.add(new CurarHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Poder Divino Hechizo":
					temp.add(new PoderDivinoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Poder Supremo Hechizo":
					temp.add(new PoderSupremoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Robar Hechizo":
					temp.add(new RobarHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Rabia Hechizo":
					temp.add(new RabiaHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Monster Reborn":
					temp.add(new MonsterReborn(cardInfo[1], cardInfo[2]));
					break;
				case "Rayo Hechizo":
					temp.add(new RayoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Raigeki":
					temp.add(new Raigeki(cardInfo[1], cardInfo[2]));
					break;
				default:
					throw new UnknownSpellCardException("Unknown Spell card",
							path, lineNumber, cardInfo[1]);

				}

			}

		}

		br.close();

		return (temp);

	}

	private void buildDeck(ArrayList<Card> Monsters, ArrayList<Card> Spells) {

		int monstersQouta = 16;
		int spellsQouta = 3;

		Random r = new Random();

		for (; monstersQouta > 0; monstersQouta--) {

			MonsterCard monster = (MonsterCard) monsters.get(r.nextInt(monsters
					.size()));

			MonsterCard clone = new MonsterCard(monster.getName(), monster.getDescription(), monster.getLevel(),
					monster.getAttackPoints(), monster.getDefensePoints());
			clone.setMode(monster.getMode());
			clone.setHidden(monster.isHidden());
			clone.setLocation(Location.DECK);

			deck.add(clone);

		}

		for (; spellsQouta > 0; spellsQouta--) {

			Card spell = spells.get(r.nextInt(spells.size()));

			SpellCard clone;

			if (spell instanceof ArrepentimientoHechizo) {

				clone = new ArrepentimientoHechizo(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof BolaInfernalHechizo) {

				clone = new BolaInfernalHechizo(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof CurarHechizo) {

				clone = new CurarHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof PoderDivinoHechizo) {

				clone = new PoderDivinoHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof PoderSupremoHechizo) {

				clone = new PoderSupremoHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof RobarHechizo) {

				clone = new RobarHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof RabiaHechizo) {

				clone = new RabiaHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof MonsterReborn) {

				clone = new MonsterReborn(spell.getName(),
						spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof RayoHechizo) {

				clone = new RayoHechizo(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spell instanceof Raigeki) {

				clone = new Raigeki(spell.getName(), spell.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

		}

	}

	private void shuffleDeck() {

		Collections.shuffle(deck);

	}

	public ArrayList<Card> drawNCards(int n) {

		ArrayList<Card> cards = new ArrayList<Card>(n);

		for (int i = 0; i < n; i++)
			cards.add(deck.remove(0));

		return (cards);

	}

	public Card drawOneCard() {

		return (deck.remove(0));

	}

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static void setMonsters(ArrayList<Card> monsters) {
		Deck.monsters = monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public static void setSpells(ArrayList<Card> spells) {
		Deck.spells = spells;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}

}

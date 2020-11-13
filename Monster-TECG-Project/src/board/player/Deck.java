package board.player;

import cards.Card;
import cards.Location;
import cards.MonsterCard;

import cards.spsr.ArrepentimientoHechizo;
import cards.spsr.BolaInfernalHechizo;
import cards.spsr.CurarHechizo;
import cards.spsr.PoderDivinoHechizo;
import cards.spsr.PoderSupremoHechizo;
import cards.spsr.RobarHechizo;
import cards.spsr.RabiaHechizo;
import cards.spsr.MonsterReborn;
import cards.spsr.RayoHechizo;
import cards.spsr.Raigeki;
import cards.spsr.SpSrCard;

import cards.spsr.BajaDano;
import cards.spsr.BloqueoHechizo;
import cards.spsr.Bomba;
import cards.spsr.Cementerio;
import cards.spsr.Confusion;
import cards.spsr.DobleDano;
import cards.spsr.Escudo;
import cards.spsr.MasVida;
import cards.spsr.Terremoto;
import cards.spsr.UltimoRecurso;

import exceptions.EmptyFieldException;
import exceptions.MissingFieldException;
import exceptions.UnexpectedFormatException;
import exceptions.UnknownCardTypeException;
import exceptions.UnknownSpSrCardException;

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
	private static ArrayList<Card> spsrs;

	private static String monstersPath = "Database-Monsters.csv";
	
	//SpSr is the name for Spells and Secrets
	private static String spsrPath = "Database-SpSr.csv";

	private final ArrayList<Card> deck;
	int trials = 0;

	public Deck() throws IOException, NumberFormatException,
			UnexpectedFormatException {

		if ((monsters == null) || (spsrs == null)) {

			Scanner sc = new Scanner(System.in);

			while (true) {

				try {

					monsters = loadCardsFromFile(Deck.getMonstersPath());
					spsrs = loadCardsFromFile(Deck.getSpSrPath());
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
							.equalsIgnoreCase(Deck.getSpSrPath())) {
						Deck.setSpSrPath(sc.nextLine());
					}

				} catch (FileNotFoundException e) {

					if (trials >= 3) {
						sc.close();
						throw e;
					}

					String s = (monsters == null) ? Deck.getMonstersPath()
							: Deck.getSpSrPath();

					System.out.println("The file \"" + s + "\" is not found.");
					System.out.println("Please enter another path:");

					trials++;
					String path = sc.nextLine();

					if (monsters == null)
						Deck.setMonstersPath(path);
					else
						Deck.setSpSrPath(path);

				}

			}

			sc.close();

		}

		deck = new ArrayList<Card>();
		buildDeck(monsters, spsrs);
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

				if (!cardInfo[0].equalsIgnoreCase("SpSr")) {

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
				case "Baja Dano":
					temp.add(new ArrepentimientoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Bloqueo Hechizo":
					temp.add(new BolaInfernalHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Bomba":
					temp.add(new CurarHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Cementerio":
					temp.add(new PoderDivinoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Confusion":
					temp.add(new PoderSupremoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Doble Dano":
					temp.add(new RobarHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Escudo":
					temp.add(new RabiaHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Mas Vida":
					temp.add(new MonsterReborn(cardInfo[1], cardInfo[2]));
					break;
				case "Terremoto":
					temp.add(new RayoHechizo(cardInfo[1], cardInfo[2]));
					break;
				case "Ultimo Recurso":
					temp.add(new Raigeki(cardInfo[1], cardInfo[2]));
					break;
				default:
					throw new UnknownSpSrCardException("Unknown Spell and Secret card",
							path, lineNumber, cardInfo[1]);

				}

			}

		}

		br.close();

		return (temp);

	}

	private void buildDeck(ArrayList<Card> Monsters, ArrayList<Card> SpSr) {

		int monstersQouta = 16;
		int spsrsQouta = 3;

		Random r = new Random();

		for (; monstersQouta > 0; monstersQouta--) {

			MonsterCard monster = (MonsterCard) monsters.get(r.nextInt(monsters.size()));

			MonsterCard clone = new MonsterCard(monster.getName(), monster.getDescription(), monster.getLevel(),
					monster.getAttackPoints(), monster.getDefensePoints());
			clone.setMode(monster.getMode());
			clone.setHidden(monster.isHidden());
			clone.setLocation(Location.DECK);

			deck.add(clone);

		}

		for (; spsrsQouta > 0; spsrsQouta--) {

			SpSrCard spsr = (SpSrCard) spsrs.get(r.nextInt(spsrs.size()));
			
			SpSrCard clone;

			if (spsr instanceof ArrepentimientoHechizo) {

				clone = new ArrepentimientoHechizo(spsr.getName(),spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof BolaInfernalHechizo) {

				clone = new BolaInfernalHechizo(spsr.getName(),spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof CurarHechizo) {

				clone = new CurarHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr  instanceof PoderDivinoHechizo) {

				clone = new PoderDivinoHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof PoderSupremoHechizo) {

				clone = new PoderSupremoHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof RobarHechizo) {

				clone = new RobarHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof RabiaHechizo) {

				clone = new RabiaHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof MonsterReborn) {

				clone = new MonsterReborn(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof RayoHechizo) {

				clone = new RayoHechizo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof Raigeki) {

				clone = new Raigeki(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}
			
			if (spsr instanceof BajaDano) {

				clone = new BajaDano(spsr.getName(),spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof BloqueoHechizo) {

				clone = new BloqueoHechizo(spsr.getName(),spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof Bomba) {

				clone = new Bomba(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr  instanceof Cementerio) {

				clone = new Cementerio(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof Confusion) {

				clone = new Confusion(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof DobleDano) {

				clone = new DobleDano(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof Escudo) {

				clone = new Escudo(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof MasVida) {

				clone = new MasVida(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof Terremoto) {

				clone = new Terremoto(spsr.getName(), spsr.getDescription());
				clone.setLocation(Location.DECK);
				deck.add(clone);
				continue;

			}

			if (spsr instanceof UltimoRecurso) {

				clone = new UltimoRecurso(spsr.getName(), spsr.getDescription());
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

	public static ArrayList<Card> getSpSr() {
		return spsrs;
	}

	public static void setSpSr(ArrayList<Card> spsrs) {
		Deck.spsrs = spsrs;
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

	public static String getSpSrPath() {
		return spsrPath;
	}

	public static void setSpSrPath(String spsrPath) {
		Deck.spsrPath = spsrPath;
	}

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Deck {
	private Player player, dealer;
	private ArrayList<Card> deck;
	private int size = 52;
	private double pot;
	private int difficulty;
	final private static Random rand = new Random();

	public Deck(String playerName, double money, int difficulty) {
		player = new Player(playerName, money);
		dealer = new Player();
		if (difficulty > 3 || difficulty < 1) {
			difficulty = Game.EASY;
		}
		this.difficulty = difficulty;
		deck = new ArrayList<>();
		int numOfDecks = rand.nextInt(difficulty) + 1;
		final int SUITS = 4;
		final int CARDS_IN_SUIT = 13;
		size = numOfDecks * 52;
		for (int decks = 0; decks < numOfDecks; decks++) {
			for (int i = 0; i < SUITS; i++) {
				for (int j = 1; j <= CARDS_IN_SUIT; j++) {
					deck.add(new Card(j, i));
				}
			}
		}

	}

	public Player getPlayer() {
		return (player);
	}

	private void shuffle(ArrayList<Card> deck) throws InterruptedException {
		int mix = (rand.nextInt(15) + 5) - difficulty;
		for (int i = 1; i <= mix; i++) {
			Collections.shuffle(deck);
			cut(rand.nextInt(deck.size()));
		}
		Thread.sleep(Game.WAIT_TIME);
		System.out.printf("The deck was shuffled and cut %d times.\n", mix);
	}

	public void firstRound(int bet) throws InterruptedException {
		player.wager(bet);
		// dealer.wager(bet);
		Thread.sleep(Game.WAIT_TIME);
		pot = player.getBet();
		shuffle(deck);
		for (int i = 0; i < 2; i++) {
			player.hit(deck.get(0));
			deck.remove(deck.get(0));
			Thread.sleep(Game.WAIT_TIME);
		}
		check21(player);
		checkNatural(player);
		dealer.hit(deck.get(0));
		deck.remove(deck.get(0));
		Thread.sleep(Game.WAIT_TIME);
		dealer.addCard(deck.get(0));
		deck.remove(deck.get(0));
	}

	/*
	 * returns true if player has a 21
	 */
	private static void check21(Player player) {
		if (player.getTotal() == 21) {
			player.didNotBust = true;
		}

	}

	/*
	 * returns true if player has a natural
	 */
	private static void checkNatural(Player p) {
		if (p.getTotal() == 20) {
			p.natural();
		}
	}

	/*
	 * if player chooses to hit instead of stand after the first round, this
	 * method is invoked
	 */
	public void otherRounds() {
		Scanner sc = new Scanner(System.in); // can't be closed
		boolean choice = true;
		while (player.getTotal() < 21 && choice) {
			player.hit(deck.get(0));
			deck.remove(0);
			if (player.getTotal() < 21) {
				System.out.println("\n(1)Stand, (2)Hit");
				if (sc.nextInt() == 1) {
					choice = !choice;
					player.didNotBust = true;
				}
			}
		}
		if (player.getTotal() == 21) {
			System.out.println(player.getName() + " got 21");
			player.didNotBust = true;
		} else if (player.getTotal() > 21) {
			System.out.println(player.getName() + " busted!");
			player.didNotBust = false;
		} else {
			System.out.println(player.getName() + " chose to stand at "
					+ player.getTotal());
			player.didNotBust = true;
		}
	}

	/*
	 * executes CPU move during turn, reveals cards, and decides whether to hit
	 * or stand
	 */
	public void cpuTurns() throws InterruptedException {
		System.out.println(dealer.getName() + " is revealing his cards:\n");
		Thread.sleep(Game.WAIT_TIME);
		System.out.println(dealer.getCards());
		System.out.println(dealer.getName() + "'s total: " + dealer.getTotal());
		System.out.println();
		if (!player.didNotBust || dealer.getTotal() >= 17
				|| dealer.getTotal() > player.getTotal()) {
			System.out.println(dealer.getName() + " chose to stand.\n");
		} else {
			while (dealer.getTotal() < 17) {
				dealer.hit(deck.get(0));
				deck.remove(0);
			}
			if (rand.nextInt(difficulty + 2) == 0) { // make dealer act kind of
														// crazy
				while (dealer.getTotal() < 21) {
					dealer.hit(deck.get(0));
					deck.remove(0);
				}
			}
			if (dealer.getTotal() >= 17 && dealer.getTotal() <= 21) {
				System.out.println(dealer.getName() + " chose to stand.\n");
			}
		}
		check();
	}

	public int getTotal(Player p) {
		return p.getTotal();
	}

	/*
	 * checks to see if a player won or lost distributes earnings before
	 * cancelling program
	 */
	private void check() {
		if (player.didNotBust) {
			if (dealer.getTotal() == player.getTotal()) {
				System.out.println(
						"Dealer total matches player total,\nall bets are returned.");
			} else if (dealer.getTotal() > 21) {
				System.out.println(dealer.getName() + " busted!\n");
				player.earn(pot);
			} else if (dealer.getTotal() < 21) {
				if (dealer.getTotal() > player.getTotal()) {
					player.lose(pot);
					dealer.earn(pot);
					System.out.println(dealer.getName() + " won!\n");
				} else if (dealer.getTotal() < player.getTotal()) {
					dealer.lose(pot);
					player.earn(pot);
					System.out.println(player.getName() + " won!\n");
				}
			} else if (dealer.getTotal() == 21) {
				System.out.println("The House Always Wins");
				player.lose(pot);
				dealer.earn(pot);
			} else if (player.getTotal() == 21) {
				dealer.earn(pot);
				System.out.println(player.getName() + " is cashing in!");
			}
		} else {
			/*
			 * if player does bust
			 */
			System.out.println("Should have stood.\n");
			player.lose(pot);
			System.out.println();
			dealer.earn(pot);
			System.out.println();
		}
	}

	private void cut(int position) {
		// two arrays of cards representing top and bottom halves
		// w/ position in middle

		Card cards[] = new Card[size];
		for (int i = 0; i < deck.size(); i++) {
			cards[i] = deck.get(i);
		}
		Card[] top = new Card[position],
				bottom = new Card[cards.length - position];
		// top and bottom are filled with cards
		for (int i = 0; i < cards.length; i++) {
			if (i < position) {
				top[i] = cards[i];
			} else {
				bottom[i - position] = cards[i];
			}
		}
		// 'cutting' of cards,
		for (int i = 0; i < cards.length; i++) {
			if (i < bottom.length) {
				cards[i] = bottom[i];// cards from bottom half are placed on top
			} else {
				cards[i] = top[i - bottom.length];
				// cards from top half are placed at bottom
			}
		}
		deck = new ArrayList<>(Arrays.asList(cards));
	}

	public double getMoney() {
		return player.getMoney();
	}
}

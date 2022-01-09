

import java.util.ArrayList;

public class Player {
	private String name;
	private double money;
	private int total;
	private double bet;
	private ArrayList<Card> cards;
	boolean didNotBust;

	private static final int ACE = 1;

	public Player(String name, double startMoney) {
		if (name == null || name.isEmpty()) {
			System.out.println("Loser");
			// losers type in null values
		}
		this.name = name;
		money = startMoney;
		total = 0;
		cards = new ArrayList<>();
		didNotBust = false;
	}

	public Player() {
		this("The CPU", 100000);
	}

	public void stand() {
		didNotBust = true;
	}

	// copy constructor ~ just to make sure no cheating might happen nothing
	// weird
	public Player(Player other) {
		this(other.name, other.money);
		this.total = other.total;
		this.cards = new ArrayList<>(other.cards);
		this.didNotBust = other.didNotBust;
	}

	// hidden card add method for dealer
	public void addCard(Card card) {
		if (isFaceCard(cards.get(0)) && card.getValue() == ACE) {
			total += 11;
		} else if (isFaceCard(card)) {
			total += 10;
		} else {
			total += card.getValue();
		}
		cards.add(card);

	}

	public Card getCard(int i) {
		return cards.get(i);
	}

	public void wager(int bet) {
		this.bet = bet;
		System.out.println(name + " bet $" + bet + '\n');
		if (bet == money) {
			System.out.println(name + " is going all in!\n");
		}
	}

	private boolean isFaceCard(Card c) {
		return c.getValue() <= Card.KING && c.getValue() >= Card.JACK;
	}

	public void lose(double round) {
		money -= round;
		System.out.println(name + " lost $" + round);
	}

	public void natural() {
		money += (bet * 1.5);
		System.out.println(
				name + " gets $" + (bet * 1.5) + " for getting a natural");
	}

	public void earn(double round) {
		money += round;
		System.out.println(name + " won $" + round);
	}

	public String getName() {
		return name;
	}

	public void hit(Card card) {
		System.out.println(name + " was dealt a " + card + "\n");
		cards.add(card);
		if (card.getValue() >= 11 && card.getValue() <= 13) {
			total += 10;
		} else if (card.getValue() == ACE) {
			if (total < 11) {
				total += 11;
			} else {
				total += 1;
			}
		} else {
			total += card.getValue();
		}
		System.out.println(name + "'s total: " + total);
	}

	public double getBet() {
		return bet;
	}

	public int getTotal() {
		return total;
	}

	public double getMoney() {
		return money;
	}

	public String getCards() {
		String ans = "";
		for (Card c : cards) {
			ans += c;
			ans += "\n";

		}
		return ans;
	}

}

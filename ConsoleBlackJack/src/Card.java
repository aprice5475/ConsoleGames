public class Card {
	private final int suit; // 0, 1, 2, 3 represent Spades, Hearts, Clubs,
	// Diamonds, respectively

	private final int value; // 1 through 13 (1 is Ace, 11 is jack, 12 is
	// queen, 13 is king, 2 through 10 represent
	// card values 2 through 10.)

	/*
	 * Strings for use in toString method and also for identifying card images
	 */
	private final static String suitNames[] = { "s", "h", "c", "d" };
	private final static String valueNames[] = { "Unused", "A", "2", "3", "4",
			"5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	public final static int KING = 13, QUEEN = 12, JACK = 11;

	/**
	 * Standard constructor.
	 *
	 * @param value 1 through 13; 1 represents Ace, 2 through 10 for numerical
	 *              cards, 11 is Jack, 12 is Queen, 13 is King
	 * @param suit  0 through 3; represents Spades, Hearts, Clubs, or Diamonds
	 */
	public Card(int value, int suit) {
		this.suit = suit;
		this.value = value;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public int getSuit() {
		return suit;
	}

	/**
	 * Returns the name of the card as a String. For example, the 2 of hearts
	 * would be "2 of h", and the Jack of Spades would be "J of s".
	 *
	 * @return string that looks like: value "of" suit
	 */
	@Override
	public String toString() {
		return valueNames[value] + " of " + suitNames[suit];
	}

}

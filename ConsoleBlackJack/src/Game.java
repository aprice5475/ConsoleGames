
import java.util.Scanner;

public class Game {
	public final static int WAIT_TIME = 500;

	public final static int EASY = 100, MEDIUM = 50, HARD = 25;
	public final static int EASY_MODE = 1, MEDIUM_MODE = 2, HARD_MODE = 3;
	final private static Scanner sc = new Scanner(System.in);
	private static boolean keepPlaying = true;
	private static double money = EASY, startMoney;

	private static boolean canPlay(double money) {
		return money > 0;
	}

	public static void main(String[] args) throws Exception {

		Deck play;

		System.out.print("Enter your name to begin: ");
		String name = sc.nextLine();
		int difficulty;
		do {
			System.out.print("Difficulty? (Easy=1/Medium=2/Hard=3): ");
			difficulty = sc.nextInt();
			if (difficulty != EASY_MODE && difficulty != MEDIUM_MODE
					&& difficulty != HARD_MODE) {
				System.out.println("Please choose 1, 2, or 3");
			}
		} while (difficulty != EASY_MODE && difficulty != MEDIUM_MODE
				&& difficulty != HARD_MODE);
		switch (difficulty) {
		case 1:
			money = EASY;
			difficulty = EASY_MODE;
			System.out.println("Nice and Easy");
			break;
		case 2:
			money = MEDIUM;
			difficulty = MEDIUM_MODE;
			System.out.println("Intermediate Mode");
			break;
		case 3:
			money = HARD;
			difficulty = HARD_MODE;
			System.out.println("Hard Mode");
			break;
		}
		startMoney = money;
		Thread.sleep(WAIT_TIME);
		System.out.println("Welcome to Blackjack, " + name
				+ "!\nYou will start every new game with $" + money + "\n"
				+ "and will be playing against the CPU.\nGood Luck!");
		while (keepPlaying) {
			int bet;
			play = new Deck(name, money, difficulty);
			boolean canBet = true;
			do {
				System.out.print("Enter a bet (1-" + money + "): ");
				bet = sc.nextInt();

				if (bet < 0) {
					System.out.println(
							"You have to bet a little more than that...\n");
					canBet = false;
				} else if (bet > money) {
					System.out.println(
							"You shouldn't bet what you don't have!\n");
					canBet = false;
				} else {
					canBet = true;
				}

			} while (!canBet);
			if (canPlay(money)) {
				System.out.println();
				Thread.sleep(WAIT_TIME);
				play.firstRound(bet);
				if (play.getTotal(play.getPlayer()) != 21) {
					System.out.print("(1)Stand or (2)Hit?: ");
					int choice = sc.nextInt();
					if (choice == 1) {
						System.out.println(name + " chose to stand.\n");
						play.getPlayer().didNotBust = true;
					}
					if (choice == 2) {
						play.otherRounds();
					}
				} else {
					System.out.println("Player stood at 21.\n");
				}
				play.cpuTurns();
				if (canPlay(play.getMoney())) {
					System.out
							.print("Do you want to keep playing? (Y=1/N=2): ");
					int playChoice = sc.nextInt();
					if (playChoice == 2) {
						keepPlaying = false;
						break;
					}
					money = play.getMoney();
				}
			} else {
				break;
			}
		}
		System.out.println("Total earnings: $" + (startMoney - 100));
		System.out.println("See you again!");

		sc.close();
	}

}

import java.util.Random;
import java.util.Scanner;

public class Game {
//Simple hand game where two players throw either one or two fingers
//players make a bet on either even or odd, which is the parity of the sum of all fingers
	final private static Random rand = new Random();
	private static int totalRounds = 0;
	private static int totalPlayWins = 0, totalCPUWins = 0;
	final private static int TIME = 500;

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		int rounds = 1;// number of rounds in game
		int cpuWins = 0, playerWins = 0;
		boolean playerChoice;// who chooses
		while (true) {
			System.out.println("Welcome to Odds and Evens!");
			System.out.println("Best 2 out of 3 wins.");
			Thread.sleep(TIME);
			System.out.print("To start choose (1)Heads or (2)Tails: ");
			int firstGuess = sc.nextInt();
			int coinToss = rand.nextInt(2) + 1;
			Thread.sleep(TIME);
			if (coinToss == 1) {
				System.out.println("It was Heads!");
			} else {
				System.out.println("It was Tails!");
			}
			Thread.sleep(TIME);
			if (coinToss == firstGuess) {
				System.out.println("You get the first pick.");
				playerChoice = true;
			} else {
				System.out.println("The CPU gets the first pick.");
				playerChoice = false;
			}
			int cpuValue, playerValue, sum;

			int decision = 0;// Evens or odds
			while (rounds <= 3) {
				if (rounds == 3 && (cpuWins == 2 || playerWins == 2)) {
					break;
				}
				System.out.println();
				System.out.printf("Round No.%d\n", rounds);
				Thread.sleep(TIME);
				System.out.println();
				if (playerChoice) {
					do {
						System.out.print("(1)Odds or (2)Evens? ");
						decision = sc.nextInt();
					} while (decision != 1 && decision != 2);
					if (decision == 1) {
						System.out.println("Odds, eh?");
					} else {
						System.out.println("Evens, then?");
					}
					System.out.println();
				} else {
					decision = rand.nextInt(2) + 1;
					System.out.print("The CPU chose ");
					if (decision == 1) {
						System.out.println("odds");
					} else {
						System.out.println("evens");
					}
				}
				if (decision == 2) {
					decision = 0;
				}
				Thread.sleep(TIME);
				System.out.println();
				// End of odds-evens decision
				System.out.print("1 or 2? ");
				playerValue = sc.nextInt();
				cpuValue = rand.nextInt(2) + 1;
				System.out.println();
				Thread.sleep(TIME);
				System.out.println("Player chose " + playerValue);
				System.out.println("The CPU chose " + cpuValue);
				System.out.println();
				Thread.sleep(TIME);
				sum = (playerValue + cpuValue);
				int parity = sum % 2;
				// System.out.println("Sum: " + sum + ", Parity: " + parity);
				if (parity == decision) {
					if (playerChoice) {
						System.out.println("Player wins");
						playerWins++;
					} else {
						System.out.println("CPU wins");
						cpuWins++;
					}
				} else {
					if (playerChoice) {
						System.out.println("CPU wins");
						cpuWins++;
					} else {
						System.out.println("Player wins");
						playerWins++;
					}
				}
				Thread.sleep(TIME);
				rounds++;
				totalRounds++;
			}
			rounds = 0;
			totalCPUWins += cpuWins;
			totalPlayWins += playerWins;
			cpuWins = 0;
			playerWins = 0;

			System.out.println("Play Again? (Yes/No)");
			System.out.println("(1)Yes (2)No");

			if (sc.nextInt() == 2) {
				break;
			}
		}
		sc.close();
		System.out.println();
		Thread.sleep(TIME);
		System.out.println("******Game Data******");
		System.out.printf("Player) Rounds Win Perc: %s\n",
				winPercent(totalPlayWins));
		Thread.sleep(TIME);
		System.out.printf("CPU) Rounds won: %s\n", winPercent(totalCPUWins));
	}

	private static String winPercent(int wins) {
		double result = (wins * 1.0) / totalRounds;
		return String.valueOf(result * 100) + "%";

	}
}

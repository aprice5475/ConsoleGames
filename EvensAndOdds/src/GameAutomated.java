import java.util.Random;
import java.util.Scanner;

public class GameAutomated {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		System.out.println("*-----Odds and Evens Simulation-----*");
		System.out.print("Enter number of rounds (*100): ");
		int rounds = sc.nextInt();
		if (rounds < 0) {
			rounds *= -1;
		}
		// rounds *= 100;
		int evens = 0, odds = 0;
		for (int i = 0; i < rounds * 10; i++) {
			int cpu1Guess = rand.nextInt(2), cpu2Guess = rand.nextInt(2);
			int sum = cpu1Guess + cpu2Guess;
			if (sum % 2 == 0) {
				evens++;
			} else {
				odds++;
			}
			System.out.println("*------- Round (" + (i + 1) + ") -------*");
			System.out.println(
					"CPU1 Guess: " + cpu1Guess + ", CPU2 Guess: " + cpu2Guess);
		}
		sc.close();
		System.out.printf("Even wins:%d\n", evens);
		System.out.printf("Odd wins:%d\n", odds);

	}

}

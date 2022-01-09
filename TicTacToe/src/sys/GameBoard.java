package sys;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {
	Player player;
	Player cpu;
	String[][] board;
	static Random random = new Random();
	private static ArrayList<Integer> unavailableSpot = new ArrayList<>();

	public GameBoard() {
		player = new Player();

		cpu = new Player();
		board = new String[3][3];
		int counter = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = String.valueOf(counter++);
			}
		}
		printBoard();
	}

	public GameBoard(char choice) {
		this();
		this.player = new Player(choice);
		this.cpu = new Player(choice == 'X' ? 'O' : 'X');

	}

	/*
	 * private void castSpot(int choice) { String playSym = player.getSymbol();
	 * int row = 0; choice--; // board is 0 index if (choice <= 3) { row = 0; }
	 * else if (choice <= 6) { row = 1; } else if (choice <= 9) { row = 2; }
	 * else { System.err.println("Unsupported Operation"); } board[row][choice]
	 * = playSym; }
	 */

	private void castSpot(Player player, int choice) {
		String symbol = "" + player.getSymbol();
		switch (choice) {
		case 1:
			board[0][0] = symbol;
			unavailableSpot.add(1);
			break;
		case 2:
			board[0][1] = symbol;
			unavailableSpot.add(2);
			break;
		case 3:
			board[0][2] = symbol;
			unavailableSpot.add(3);
			break;
		case 4:
			board[1][0] = symbol;
			unavailableSpot.add(4);
			break;
		case 5:
			board[1][1] = symbol;
			unavailableSpot.add(5);
			break;
		case 6:
			board[1][2] = symbol;
			unavailableSpot.add(6);
			break;
		case 7:
			board[2][0] = symbol;
			unavailableSpot.add(7);
			break;
		case 8:
			board[2][1] = symbol;
			unavailableSpot.add(8);
			break;
		case 9:
			board[2][2] = symbol;
			unavailableSpot.add(9);
			break;
		}
	}

	public void playGame() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			int choice;
			do {
				System.out.println("Unavailable spots: " + unavailableSpot);
				System.out.print("To choose a spot, pick a number(1-9): ");
				choice = sc.nextInt();
			} while (unavailableSpot.contains(choice));
			final char PLAYER_SYM = player.getSymbol(),
					CPU_SYM = cpu.getSymbol();
			castSpot(player, choice);
			printBoard();
			if (check() != null) {
				if (check().equals(String.valueOf(PLAYER_SYM))) {
					System.out.println("Player Wins");
					break;
				} else if (check().equals(String.valueOf(CPU_SYM))) {
					System.out.println("CPU Wins");
					break;
				}
			}
			cpuTurn(choice);
			printBoard();
			if (check() != null) {
				if (check().equals(String.valueOf(PLAYER_SYM))) {
					System.out.println("Player Wins");
					break;
				} else if (check().equals(String.valueOf(CPU_SYM))) {
					System.out.println("CPU Wins");
					break;
				}
			} else if (check() == null && unavailableSpot.size() == 9) {
				/* board is full,no winners */
				System.out.println("Good Game\nThere are no winners");
			}
		}
		sc.close();

	}

	private String check() {
		if (board[0][0].equals(board[1][1])
				&& board[2][2].equals(board[0][0])) {
			return board[0][0];
		}
		if (board[2][0].equals(board[1][1])
				&& board[1][1].equals(board[0][2])) {
			return board[1][1];
		}
		for (int i = 0; i < 3; i++) {
			if (board[i][0].equals(board[i][1])
					&& board[i][0].equals(board[i][2])) {
				return board[i][0];
			}
			if (board[0][i].equals(board[1][i])
					&& board[2][i].equals(board[0][i])) {
				return board[i][0];
			}
		}
		return null;
	}

	private void cpuTurn(int choice) {
		int CPUchoice;
		do {
			CPUchoice = random.nextInt(9) + 1;
		} while (unavailableSpot.contains(CPUchoice));
		castSpot(cpu, CPUchoice);
		System.out.println("CPU chose " + CPUchoice);
	}

	private void printBoard() {
		StringBuffer ans = new StringBuffer();
		for (int row = 0; row < board.length + 2; row++) {
			for (int col = 0; col < 3; col++) {
				if (row % 2 == 0) {
					ans.append(" " + board[row / 2][col] + " ");
					if (col % 3 != 2) {
						ans.append("|");
					}
				} else {
					ans.append(" -- ");
				}
			}
			ans.append("\n");
		}
		System.out.println(ans);
	}

}

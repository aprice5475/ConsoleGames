package sys;

public class Main {

	public static void main(String[] args) {
		System.out.println("-----***-----");

		System.out.println("Welcome to Tic-Tac-Toe!");
		System.out.println("-----***-----");
		System.out.print("You are the symbol 'X' ");
		System.out.println();
		GameBoard gb = new GameBoard('X');
		gb.playGame();

	}

}

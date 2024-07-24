import java.util.Scanner;

public class playGame {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		while (true)
		{
			System.out.println("Welcome to Skyjo, how many players will be participating? ");
			int players = scan.nextInt();
			if (players >= 2 && players <= 8) 
			{
				skyjoGame game = new skyjoGame(players);
				break;
			}
			else System.out.println("That is not a valid number. ");
		}
		scan.close();
	}

}

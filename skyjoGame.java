import java.util.*;

public class skyjoGame {
	
	// These variables represent the deck, the hands of all the players, the place
	// in the deck we are drawing from, and the top of the discard pile. 
	private int[] deck;
	private player[] playerArr;
	private int place;
	private int top;
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	
	// This creates the game 
	public skyjoGame(int players) 
	{
		this.deck = createDeck();
		this.place = 0;
		this.playerArr = new player[players];
		for (int i = 0; i < players; i++)
		{
			this.playerArr[i] = new player();
			this.playerArr[i].getHand(Arrays.copyOfRange(deck, this.place, this.place + 12));
			this.place += 12;
			this.firstTurn(i);
		}
		this.top = this.deck[this.place];
		this.place++;
		int max = 25, starter = -1, num = 0;
		for (player p : this.playerArr)
		{
			if (starter == -1)
			{
				starter = num;
				max = p.getValue();
			}
			else if (max < p.getValue())
			{
				starter = num;
				max = p.getValue();
			}
			num++;
		}
		System.out.println("Player number " + starter + " will go first. ");
		while (true)
		{
			this.turn(starter);
			if (this.playerArr[starter].isDone())
			{
				for (int i = starter + 1; i < starter + players; i++)
				{
					this.turn(i % players);
				}
				break;
			}
			starter = (starter + 1) % players;
		}
		
		int min = this.playerArr[starter].getValue();
		boolean best = true;
		for (player p : this.playerArr)
		{
			if (min >= p.getValue()) 
			{
				best = false;
				break;
			}
		}
		
		this.updateScoreBoard(best, starter);
		
		scan.close();
	}
	
	private int[] createDeck()
	{
		// This puts all the values that should be in the deck into it
		int[] r = new int[150];
		for (int i = 0; i < 150; i++)
		{
			if (i < 5) r[i] = -2;
			else if (i < 15) r[i] = -1;
			else if (i < 30) r[i] = 0;
			else if (i < 40) r[i] = 1;
			else if (i < 50) r[i] = 2;
			else if (i < 60) r[i] = 3;
			else if (i < 70) r[i] = 4;
			else if (i < 80) r[i] = 5;
			else if (i < 90) r[i] = 6;
			else if (i < 100) r[i] = 7;
			else if (i < 110) r[i] = 8;
			else if (i < 120) r[i] = 9;
			else if (i < 130) r[i] = 10;
			else if (i < 140) r[i] = 11;
			else r[i] = 12;
			
		}
		
		// This shuffles the deck
		for (int i = 0; i <= 2000; i++)
		{
			int num1 = rand.nextInt(150);
			int num2 = rand.nextInt(150);
			int temp = r[num1];
			r[num1] = r[num2];
			r[num2] = temp;
		}
		
		return r;
	}
	
	public void turn(int player)
	{
		int val;
		
		System.out.println("This is your current hand, player number " + player + "\n" + this.playerArr[player]);
		System.out.println("The top card on the discard pile is " + this.top);
		System.out.println("Type 1 for the top card or 2 to draw.");
		while (true)
		{
			int choice = scan.nextInt();
			
			if (choice == 1) 
			{
				val = this.top;
				break;
			}
			else if (choice == 2) 
			{
				val = this.deck[this.place];
				this.place++;
				System.out.println("You drew a " + val);
				break;
			}
			else 
			{
				System.out.println("That is not a valid choice. ");
				continue;
			}
		}
		
		while (true) 
		{
			System.out.println("You now have a " + val + " Type 1 to replace a card or 2 to discard and flip. ");
			int choice = scan.nextInt();
			if (choice == 1) 
			{
				System.out.println("What position would you like to place this card in? ");
				while (true)
				{
					choice = scan.nextInt();
					if (choice >= 1 && choice <= 12)
					{
						int card = this.playerArr[player].replace(choice - 1, val);
						System.out.println("You replaced a " + card);
						this.top = card;
						break;
					}
					else 
					{
						System.out.println("That is not a valid choice. ");
						continue;
					}
				}
				break;
			}
			else if (choice == 2) 
			{
				this.top = val;
				System.out.println("Choose a card to flip over. ");
				while (true)
				{
					choice = scan.nextInt();
					if (choice >= 1 && choice <= 12)
					{
						int card = this.playerArr[player].reveal(choice - 1);
						if (card != 14) 
						{
							System.out.println("You flip over to reveal a " + card);
							break;
						}
						else 
						{
							System.out.println("That card has already been revealed. Please choose a non-revealed card. ");
							continue;
						}
					}
					else 
					{
						System.out.println("That is not a valid choice. ");
						continue;
					}
				}
				break;
			}
			else 
			{
				System.out.println("That is not a valid choice. ");
				continue;
			}
		}
		
		System.out.println("This is what your hand is now:\n" + this.playerArr[player]);
		
	}

	private void firstTurn(int player) {
		
		System.out.println("Player number " + player);
		System.out.println("Please choose the first card you want to flip. Enter a number from 1 to 12. ");
		int choice = scan.nextInt();
		while (true) 
		{
			if (choice >= 1 && choice <= 12) 
			{
				this.playerArr[player].reveal(choice - 1);
				break;
			}
			else 
			{
				System.out.println("That is not a valid choice. ");
				continue;
			}
			
		}
		
		System.out.println("Please choose the second card you want to flip. Enter a number from 1 to 12. ");
		int choice2 = scan.nextInt();
		while (true) 
		{
			if (choice2 >= 1 && choice2 <= 12 && choice2 != choice) 
			{
				this.playerArr[player].reveal(choice2 - 1);
				break;
			}
			else 
			{
				System.out.println("That is not a valid choice. ");
				continue;
			}
			
		}
		
		System.out.println("Here is your starting board:\n" + this.playerArr[player]);
		
	}
	
	public void updateScoreBoard(boolean best, int first)
	{
		for (int i = 0; i < this.playerArr.length; i++)
		{
			if (i == first && !best) 
			{
				int score = this.playerArr[first].getValue() * 2;
				this.playerArr[first].updateScore(score);
			}
			else this.playerArr[i].updateScore(this.playerArr[i].getValue());
		}
		int num = 0;
		for (player p : this.playerArr)
		{
			System.out.println("Player Number " + num + " has " + p.getScore() + " points.");
			num++;
		}
	}

}

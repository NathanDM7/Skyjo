
public class player {
	
	private boolean[] reveals;
	private int[][] hand;
	private int value;
	private int score;
	
	// This creates a player object
	public player()
	{
		this.reveals = new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false};
		this.hand = new int[3][4];
		this.value = 0;
		this.score = 0;
	}
	
	// This gets the players initial setup
	public void getHand(int[] vals)
	{
		for (int i = 0; i < 12; i++) 
		{
			this.hand[i / 4][i % 4] = vals[i];
		}
	}
	
	// This reveals one new card in the players setup, the numbers returned are:
	// 13 if the card was replaced successfully, 14 if the card was an invalid choice, and -2 through 12 if a row was completed.
	public int reveal(int num)
	{
		if (!this.reveals[num]) 
		{
			this.reveals[num] = true;
			int check = 13, clear = 0;
			for (int i = 0; i < 3; i++)
			{
				if (check == 13 && this.reveals[(i * 4) + (num % 4)]) 
				{
					check = this.hand[i][num % 4];
					clear++;
				}
				else if (check == this.hand[i][num % 4] && this.reveals[(i * 4) + (num % 4)]) 
				{
					clear++;
				}
			}
			if (clear == 3)
			{
				System.out.println("You cleared a column!!");
				for (int i = 0; i < 3; i++)
				{
					this.hand[i][num % 4] = 13;
				}
				return check;
			}
		}
		else return 14;
		this.updateValue();
		return this.hand[num / 4][num % 4];
	}
	
	public int replace(int num, int card)
	{
		this.reveals[num] = true;
		int r = this.hand[num / 4][num % 4];
		this.hand[num / 4][num % 4] = card;
		int check = 13, clear = 0;
		for (int i = 0; i < 3; i++)
		{
			if (check == 13 && this.reveals[(i * 4) + (num % 4)]) 
			{
				check = this.hand[i][num % 4];
				clear++;
			}
			else if (check == this.hand[i][num % 4] && this.reveals[(i * 4) + (num % 4)]) 
			{
				clear++;
			}
		}
		if (clear == 3)
		{
			System.out.println("You cleared a column!!");
			for (int i = 0; i < 3; i++)
			{
				this.hand[i][num % 4] = 13;
			}
			return check;
		}
		this.updateValue();
		return r;
	}
	
	public void updateValue()
	{
		this.value = 0;
		for (int i = 0; i < 12; i++)
		{
			if (this.reveals[i]) 
			{
				if (this.hand[i / 4][i % 4] != 13) this.value += this.hand[i / 4][i % 4];
			}
		}
	}
	
	public void updateScore(int n)
	{
		this.score += n;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public boolean isDone()
	{
		for (boolean b : this.reveals)
		{
			if (!b) return false;
		}
		
		return true;
	}		
	
	public String toString()
	{
		String str = "";
		for (int i = 0; i < 12; i++)
		{
			if (this.reveals[i]) 
			{
				if (this.hand[i / 4][i % 4] >= 0 && this.hand[i / 4][i % 4] <= 9) str += " " + this.hand[i / 4][i % 4] + "  ";
				else if (this.hand[i / 4][i % 4] == 13) str += "**  ";
				else str += this.hand[i / 4][i % 4] + "  ";
			}
			else str += "__  ";
			
			if ((i + 1) % 4 == 0) str += "\n";
		}
		
		return str;
	}
	

}

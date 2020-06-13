/**
 * This class is a subclass of the Hand class which is used to model a Pair hand in a BigTwo card game
 * @author siddharth
 *
 */
public class Pair extends Hand{
	
	/**
	 * This constructor for building a Pair with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * A getter method for retrieving the top card of Pair.
	 * 
	 * @return card type object which consists of the top card.
	 */
	public Card getTopCard() {
		Card topcard = this.getCard(0);
		for(int i = 1 ; i < 2 ; i++)
		{
			if(this.getCard(i).suit > topcard.suit)
			{
				topcard = this.getCard(i);
			}
		}
		return topcard;
	}
	
	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return a string type object which consists of the name of this class which is Pair.
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid Pair hand. 
	 *
	 * @return a boolean value which tells if the Pair hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() != 2)
		{
			return false;
		}
		
		else if(this.getCard(0).rank == this.getCard(1).rank) {
			return true;
		}
			return false;
	}
}



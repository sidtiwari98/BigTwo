/**
 * This class is a subclass of the Hand class which is used to model a Flush hand in a BigTwo card game
 * @author siddharth
 *
 */

public class Flush extends Hand {
	
	/**
	 * This constructor for building a Flush with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * A getter method for retrieving the top card of Flush.
	 * 
	 * @return card type object which consists of the top card.
	 */
	public Card getTopCard() {
		Card topcard = this.getCard(0);
		for(int i = 1 ; i < 5 ; i++)
		{
			if(this.getCard(i).rank > topcard.rank)
			{
				topcard = this.getCard(i);
			}
		}
		return topcard;
	}
	
	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return a string type object which consists of the name of this class which is Flush.
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid Flush hand. 
	 *
	 * @return a boolean value which tells if the Flush hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() != 5)
		{
			return false;
		}
		
		for(int i = 0 ; i < 4 ; i++) {
			if(this.getCard(i+1).suit != this.getCard(i).suit)
			{
				return false;
			}
		}
		
		return true;
	}
}

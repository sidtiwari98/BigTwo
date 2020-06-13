/**
 * This class is a subclass of the Hand class which is used to model a FullHouse hand in a BigTwo card game
 * @author siddharth
 *
 */

public class FullHouse extends Hand{
	
	/**
	 * This constructor for building a FullHouse with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the top card of FullHouse.
	 * 
	 * @return card type object which consists of the top card.
	 */
	public Card getTopCard() {
		this.sort();
		Card topcard;
		if(this.getCard(0) == this.getCard(2)) {
			topcard = this.getCard(2);
		}
		else
		{
			topcard = this.getCard(4);
			
		}
		return topcard;
	}

	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return a string type object which consists of the name of this class which is FullHouse.
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid FullHouse hand. 
	 *
	 * @return a boolean value which tells if the FullHouse hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() != 5)
		{
			return false;
		}
		
		this.sort();
		
		if(this.getCard(0).rank == this.getCard(2).rank)
		{
			if(this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).rank == this.getCard(2).rank && this.getCard(3).rank == this.getCard(4).rank)
			{
				return true; 
			}
		}
		
		else if(this.getCard(2).rank == this.getCard(4).rank)
		{
			if(this.getCard(2).rank == this.getCard(3).rank && this.getCard(2).rank == this.getCard(4).rank && this.getCard(0).rank == this.getCard(1).rank)
			{
				return true;
			}
		}
		
		return false;
	
}
}

/**
 * This class is a subclass of the Hand class which is used to model a Single hand in a BigTwo card game
 * @author siddharth
 *
 */
public class Single extends Hand{
	
	/**
	 * This constructor for building a Single with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the top card of Single.
	 * 
	 * @return card type object which consists of the top card.
	 */
	public Card getTopCard() {
		return this.getCard(0);
	}
	
	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return a string type object which consists of the name of this class which is Single
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid Single hand. 
	 *
	 * @return a boolean value which tells if the Single hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() == 1)
		{
			return true;
		}
		return false;
	}
}

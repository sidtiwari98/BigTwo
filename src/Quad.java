/**
 * This class is a subclass of the Hand class which is used to model a Quad hand in a BigTwo card game
 * @author siddharth
 *
 */

public class Quad extends Hand{
	
	/**
	 * This constructor for building a Quad with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the top card of Quad.
	 * 
	 * @return card type object which consists of the top card.
	 */
	public Card getTopCard() {
		this.sort();
		Card topcard;
		if(this.getCard(0) == this.getCard(3)) {
			topcard = this.getCard(3);
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
	 * @return a string type object which consists of the name of this class which is Quad.
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid Quad hand. 
	 *
	 * @return a boolean value which tells if the Quad hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() != 5)
		{
			return false;
		}
		
		this.sort(); // 5,5,5,5,7 	4,5,5,5,5
		
		boolean flag = false;
		
		for(int i = 0 ; i < 2 ; i++) {
			Card tempcard = this.getCard(i);
			if(tempcard.getRank() == this.getCard(i+1).getRank() && tempcard.getRank() == this.getCard(i+2).getRank() && tempcard.getRank() == this.getCard(i+3).getRank()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}

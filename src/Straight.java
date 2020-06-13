import java.util.*;

/**
 * This class is a subclass of the Hand class which is used to model a Straight hand in a BigTwo card game
 * @author siddharth
 *
 */

public class Straight extends Hand {
	
	/**
	 * This constructor for building a Straight with the specified player and list of cards
	 * @param player
	 * 		A CardGamePlayer object which contains specified player to whom the current hand belongs to
	 * @param cards
	 * 		A CardList object which contains list of cards played by the active player
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the top card of Straight.
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
	 * @return a string type object which consists of the name of this class which is Straight.
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * A method for checking if this is a valid Straight hand. 
	 *
	 * @return a boolean value which tells if the Straight hand is valid(true) or not(false).
	 */
	public boolean isValid() {
		
		if(this.size() != 5)
		{
			return false;
		}
		
		ArrayList<Integer> _rank = new ArrayList<Integer>() ;
		
		for(int i = 0 ; i < 5 ; i++) {
			if(this.getCard(i).rank == 0 )
			{
				_rank.add(13);
			}
			else if(this.getCard(i).rank == 1)
				_rank.add(14);
			else
				_rank.add(this.getCard(i).rank);
		}
		
		Collections.sort(_rank);
		
		for(int i = 0 ; i < 4 ; i++) {
			if(_rank.get(i+1) - _rank.get(i) != 1)
			{
				return false;
			}
		}
		
		return true;
	}
}

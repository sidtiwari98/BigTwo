/**
 * This is an abstract class is a subclass of the CardList class, and is used to model a hand of cards/
 * @author siddharth
 *
 */
public abstract class Hand extends CardList {
	
	private CardGamePlayer player;
	
	/**
	 * This constructor for building a hand with the specified player and list of cards.
	 * @param player
	 * 		A CardGamePlayer object which contains the list of players of the game
	 * @param cards
	 * 		A CardList object which contains list of cards played by the current player
	 */
	public Hand (CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0 ; i < cards.size() ; i++)
		{
			this.addCard(cards.getCard(i));
		}
		
	}
	
	/**
	 * A getter method for retrieving the player of this hand.
	 * @return instance variable CardGamePlayer player containing the player of the current hand 
	 */

	public CardGamePlayer getPlayer() {
		return player;
	}
	
	/**
	 * A getter method for retrieving the top card of this hand.
	 * This method gets overwritten in the subclasses of Hand
	 * @return null 
	 */
	public Card getTopCard() { // to be inherited 
		return null;
	}
	
	/**
	 * A boolean method for checking if this hand beats a specified hand.
	 * @param hand type object 
	 * @return a boolean value indicating if the this hand beats the specified hand(true) or not(false).
	 */
	public boolean beats(Hand hand) {
		
		if(this.size()== 1 && hand.size()==1 && this.isValid()) //single
		{
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
				return true;
			
		}
		
		else if(this.size()== 2 && hand.size()==2 && this.isValid()) { //pair
			
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
			return true;
		}
		
		else if(this.size()== 3 && hand.size()==3 && this.isValid()) { // triple
			
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
				return true;
		}
		
		else if(this.size()== 5 ) { 
			
			if(this instanceof Straight) {
				if(hand.size() == 5 && hand.getType() == this.getType()) { //straight
					if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
						return true;
				}
				else
					return false;
			}
			
			
			if(this instanceof Flush)
				if(hand.size() == 5 && hand.getType() == this.getType()) { //flush
					if(hand.getType() ==  "Straight" ) {
						return true;
					}
					if(this.getTopCard().compareTo(hand.getTopCard()) == 1 && hand.getType() == this.getType())
						return true;
					else
						return false;
			}
			
			if (this instanceof FullHouse){
				if(hand.size() == 5 && hand instanceof FullHouse) { //Full House
					if( hand.getType()== "Straight" || hand.getType()== "Flush" ) {
						return true;
					}
					if(this.getTopCard().compareTo(hand.getTopCard()) == 1 && hand.getType() == this.getType())
						return true;
					else
						return false;
				}
			}
			

			
			if (this instanceof Quad) {
				if(hand.size() == 5 && hand instanceof Quad) { //Quad
					if(hand.getType()== "Straight" || hand.getType()== "Flush" || hand.getType() == "FullHouse" ) {
						return true;
					}
					if(this.getTopCard().compareTo(hand.getTopCard()) == 1 && hand.getType() == this.getType())
						return true;
					else
						return false;
				}
			}
			
			if(this instanceof StraightFlush) {
				if(hand.size() == 5 && hand instanceof StraightFlush) { //straight flush
					if(hand.getType()== "Straight" || hand.getType()== "Flush" || hand.getType() == "FullHouse"|| hand.getType() == "Quad") {
						return true;
					}
					if(this.getTopCard().compareTo(hand.getTopCard()) == 1 && hand.getType() == this.getType())
						return true;
					else
						return false;
				}
			}
		}
		
		return false;
		
		
		
	}
	
	/**
	 * An abstract method for checking if this is a valid hand. 
	 * To be overwritten in the subclasses of hand.
	 * 
	 */
	public abstract boolean isValid();
	
	/**
	 * An abstract method for returning a string specifying the type of this hand.
	 * To be overwritten in the subclasses of hand
	 * 
	 */
	public abstract String getType();
}

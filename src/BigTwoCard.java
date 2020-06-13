/**
 * This class is a subclass of the Card class, and is used to model a card used in a Big Two card game.
 * @author siddharth
 *
 */
public class BigTwoCard extends Card {

	/**
	 * a constructor for building a card with the specified suit and rank. suit is an integer between 0 and 3, 
	 * and rank is an integer between 0 and 12.
	 * @param suit
	 * 		an int value between 0 and 3 representing the suit of a card:
	 *            <p>
	 *            0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank
	 * 		an int value between 0 and 12 representing the rank of a card:
	 *            <p>
	 *            0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11
	 *            = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	

	/**
	 * Compares this card with the specified card for order of the BigTwo Card game.
	 * The order of ranks from high to low is 2,A,K,Q,J,10,9,8,7,6,5,4,3.
	 * 
	 * @param card
	 *            the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card
	 */
	public int compareTo(Card card) {
		
		
//		if(this.rank == 1) {// '2'
//			System.out.println("Bhosdhike1");
//			if(card.rank == 1) {
//				return super.compareTo(card);
//			}
//			else
//				return 1;
//		}
//		else if (this.rank == 0) { // 'A'
//			System.out.println("Bhosdhike2");
//			if(card.rank > 1) { // '3','4'...
//				return 1;
//			}else if(card.rank == 1) { // '2'
//				return -1;
//			}
//			else
//				return super.compareTo(card);
//				
//		}
//		
//		else
//			return super.compareTo(card);

		if(this.rank == card.rank)
			return super.compareTo(card);
		else if (this.rank == 1)
			return 1;
		else if (card.rank == 1)
			return -1;
		else if (this.rank == 0)
			return 1;
		else if (card.rank == 0)
			return -1;
		else
			return super.compareTo(card);
	}
	
//	public static void main(String[] args) {
//		BigTwoCard card= new BigTwoCard(1,8);
//		System.out.println(card.compareTo(new  BigTwoCard(0,1)));
//	}
//	
}

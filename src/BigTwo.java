//import java.util.ArrayList;
//import java.util.StringTokenizer;
//
///**
// * The BigTwo class is a public class used to model a Big Two card game.
// * @author siddharth
// * 
// */
//public class BigTwo implements CardGame {
//	private Deck deck;
//	private ArrayList<CardGamePlayer> playerList;
//	private ArrayList<Hand> handsOnTable;
//	private int currentIdx;
//	private BigTwoTable bigTwoTable;
//	
//	/**
//	 * A constructor for creating a Big Two card game
//	 */
//	
//	public BigTwo() {
//		playerList = new ArrayList<CardGamePlayer> ();
//		
//		for (int i = 0; i < 4 ; i++)
//		{
//			CardGamePlayer player = new CardGamePlayer();
//			playerList.add(player);
//		}
//		
//		
//		
//		handsOnTable = new ArrayList<Hand>();
//		bigTwoTable = new BigTwoTable(this);
//	}
//	
//	@Override
//	public int getNumOfPlayers() {
//		return playerList.size();// TODO Auto-generated method stub
//	}
//
//	
//	/**
//	 * This is a getter method for retrieving the deck of cards being used.
//	 * @return
//	 * 		Returns a deck type object which contains the current deck to play the game
//	 */
//	
//	public Deck getDeck() {
//		return this.deck;
//	}
//	
//	/**
//	 * This is a getter method for retrieving the list of hands played on the table.
//	 * @return
//	 * 		Returns arraylist containing the players
//	 */
//	public ArrayList<CardGamePlayer> getPlayerList(){
//		return playerList;
//	}
//	
//	/**
//	 * Gets the current cards that have been played on the table by the previous player 
//	 * @return an arraylist containing the cards played by the previous player
//	 */
//	
//	public ArrayList<Hand> getHandsOnTable(){
//		return handsOnTable;
//	}
//	
//	/**
//	 * A getter method for retrieving the index of the current player.
//	 * @return an integer which tells the player number of the current player ranging in from 0 to 3
//	 */
//	
//	public int getCurrentIdx() {
//		return currentIdx;
//	}
//	
//	
//	
//	
//	
//	/**
//	 * A method for starting/restarting the game with a given shuffled deck of cards
//	 * @param deck 
//	 * 		the BigTwoDeck object of shuffled deck of cards
//	 */
//	public void start(Deck deck) {
//		
//		
//		
//		for(int i = 0; i < 4 ; i++) {
//			playerList.get(i).removeAllCards();
//		}// removing all cards from playerList
//		
//		handsOnTable.clear(); //removing hands from table
//		
//		Card threeD = new Card(0,2); // three of diamonds
//		
//		for(int i = 0; i < 52 ; i++)
//		{
//			int j = i / 13;
//			playerList.get(j).addCard(deck.getCard(i)); // adding cards to each player
//			if(deck.getCard(i).rank == threeD.rank && deck.getCard(i).suit == threeD.suit) 
//			{
//				bigTwoTable.setActivePlayer(j);
//				currentIdx = j;
//			}
//			
//		}
//		
//		for(int j = 0 ; j < 4; j++)
//		{
//			playerList.get(j).sortCardsInHand();
//		}
//		
//		bigTwoTable.repaint();
//		// cards distributed and sorted
//		bigTwoTable.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:");
//
//		
//	}
//	
//	@Override
//	public void makeMove(int playerID, int[] cardIdx) {
//		this.checkMove(playerID, cardIdx);
//		
//	}
//	
//	boolean valid_move = false; // declaring global variable
//	
//	@Override
//	public void checkMove(int playerID, int[] cardIdx) {
//		
//		int lastPlayer;
//		
//		
//		if(handsOnTable.isEmpty() == true) {
//			lastPlayer = -1;
//		}
//		
//		else {
//			lastPlayer = getPlayerList().indexOf(handsOnTable.get(handsOnTable.size()-1).getPlayer());
//		}
//		
//		
//			boolean valid = false;
//			
//				
//				if(!valid_move) { //using global variable to repaint
//					bigTwoTable.repaint();
//				}
//				valid_move = false;
//				//int[] currentHand = bigTwoTable.getSelected(); 
//				
//				if(cardIdx !=null) {
////					System.out.println("AAYA");
//					CardList currentCardList = playerList.get(playerID).play(cardIdx); //storing the current hand
//					Hand thisHand = BigTwo.composeHand(playerList.get(playerID), currentCardList);
//					
//					//checking valid moves
//					if(handsOnTable.isEmpty() && thisHand !=null) { // checking three of diamonds
//						valid = thisHand.contains(new Card(0,2));
//					}
//					else if(lastPlayer != playerID && thisHand !=null) {
//						if(thisHand.getType() == handsOnTable.get(handsOnTable.size()-1).getType())
//						valid = thisHand.beats(handsOnTable.get(handsOnTable.size()-1));
//
//					}
//					else
//						valid = false;
//					
//					if(lastPlayer == playerID && thisHand !=null ) {
//						valid= true;
//					}
//					
//					if(valid) {
//						for(int i = 0 ; i< currentCardList.size(); i++) {
//							playerList.get(playerID).getCardsInHand().removeCard(currentCardList.getCard(i)); 
//						}
//						
//						bigTwoTable.printMsg("{" + thisHand.getType() + "}" + thisHand);
//						handsOnTable.add(thisHand);
//
//						if(playerList.get(playerID).getCardsInHand().isEmpty())
//						{
////							bigTwoTable.printMsg("{" + thisHand.getType() + "}" + thisHand);
//							
//							this.endOfGame();
//						}
//						else
//						{
//							lastPlayer = playerID;
//							playerID = (playerID + 1)%4;
//							bigTwoTable.setActivePlayer(playerID);
//							
//						}
//
//					}
//					
//					else {
//						bigTwoTable.printMsg("Not a valid move!!!");
//						valid_move = true;
//					}
//						
//					
//				}
//				
//				else // pass
//				{
//					if(handsOnTable.isEmpty() == false && lastPlayer != playerID)
//					{
//						playerID = (playerID + 1)%4;
//						bigTwoTable.setActivePlayer(playerID);
//						bigTwoTable.printMsg("Pass");
//						valid = true;
//					}
//					else {
//						bigTwoTable.printMsg("Not a valid move!!!");
//						valid_move = true;
//					}
//						
//				}
//		
//		
//	}
//
//
//
//	/**
//	 * A method for starting a Big Two card game.
//	 * @param args
//	 */
//	
//	public static void main(String[] args) {
//		BigTwo game = new BigTwo();
//		BigTwoDeck deck = new BigTwoDeck();
//		deck.initialize();
//		deck.shuffle();
//		game.start(deck); 
//		
//	}
//	
//	/**
//	 * A method for returning a valid hand from the specified list of cards of the player
//	 * @param player
//	 * 		A CardGamePlayer object which contains the list of players 
//	 * @param cards
//	 * 		A CardList object which contains list of cards played by the current player
//	 * @return the type of valid hand composed from the specified list of cards
//	 */
//	public static Hand composeHand(CardGamePlayer player, CardList cards) {
//		
//		//creating instances of the classes
//		
//		Single single = new Single(player,cards);
//		
//		Pair pair = new Pair(player,cards);
//		
//		Triple triple = new Triple(player,cards);
//		
//		Straight straight = new Straight(player,cards);
//		
//		Flush flush = new Flush(player,cards);
//		
//		FullHouse fullhouse = new FullHouse(player,cards);
//		
//		Quad quad = new Quad(player,cards);
//		
//		StraightFlush straightflush = new StraightFlush(player,cards);
//		
//		
//		//checking isValid of each class and returning that hand else returning null
//		
//		if(straightflush.isValid()) 
//			return straightflush;
//		
//		else if(quad.isValid()) 
//			return quad;
//		
//		else if(fullhouse.isValid()) 
//			return fullhouse;
//		
//		else if(flush.isValid()) 
//			return flush;
//		
//		
//		else if(straight.isValid()) 
//			return straight;
//			
//		else if(triple.isValid()) 
//			return triple;
//		
//		else if(pair.isValid())
//			return pair;
//		
//		else if(single.isValid())
//			return single;
//		
//		else
//			return null;
//		
//	}
//
//	
//	@Override
//	public boolean endOfGame() {
//
//		//bigTwoTable.repaint();
//		bigTwoTable.printMsg("Game ends");
//		
//		
//		for(int i = 0; i < playerList.size();i++)
//		{
//			if(playerList.get(i).getCardsInHand().size() == 0)
//			{
//				bigTwoTable.printMsg("Player " + i + " wins the game");
//				// declare that player as winner
//			}
//			
//			
//			else
//			{
//				bigTwoTable.printMsg("Player " + i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand");
//				 // list the number of cards left in the other players' hand
//			}
//		}
//		
//		bigTwoTable.disable();
//		return true;
//	}
//		
//}
//	
//
//
//
//	
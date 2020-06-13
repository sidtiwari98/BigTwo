import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.io.IOException;

public class BigTwoClient implements CardGame, NetworkGame {

    private int numOfPlayers;
    private Deck deck;
    private ArrayList<CardGamePlayer> playerList;
    private ArrayList<Hand> handsOnTable;
    private int playerID;
    private String playerName;
    private String serverIP;
    private int serverPort;
    private Socket sock;
    private ObjectOutputStream oos;
    private int currentIdx;
    private BigTwoTable table;


    public BigTwoClient() {
        playerList = new ArrayList<>();
        this.numOfPlayers = 4;
        for (int i = 0; i < 4; i++) {
            CardGamePlayer player = new CardGamePlayer();
            playerList.add(player);
        }

        handsOnTable = new ArrayList<>();
        table = new BigTwoTable(this);
        table.disable();

        String playerName = JOptionPane.showInputDialog("Enter your name: ");

        if (playerName != null) {
            this.setPlayerName(playerName);
            this.makeConnection();
            //table.disable();
        } else {
            this.table.printMsg("Pleae enter a valid name. Start Again");
            table.disable();
        }

        table.repaint();

    }

    private class ServerHandler implements Runnable {

        public void run() {
            try {
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());

                while (!sock.isClosed()) {

                    CardGameMessage messageFromServer = (CardGameMessage) is.readObject();
                    if (messageFromServer != null) {
                        parseMessage(messageFromServer);
                    }
                } // close while
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void setPlayerID(int playerID) {
        this.playerID = playerID;

    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
//		playerList.get(playerID).setName(playerName);

    }

    @Override
    public String getServerIP() {
        return serverIP;
    }

    @Override
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;

    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;

    }

    @Override
    public void makeConnection() {
        try {
            this.setServerIP("127.0.0.1");
            this.setServerPort(2396);	
            sock = new Socket(this.getServerIP(), this.getServerPort());
            oos = new ObjectOutputStream(sock.getOutputStream());

            Thread thread = new Thread(new ServerHandler());
            thread.start();

            CardGameMessage join = new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName());
            sendMessage(join);
            CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
            sendMessage(ready);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void parseMessage(GameMessage message) {

        if (message.getType() == CardGameMessage.PLAYER_LIST) {
            this.setPlayerID(message.getPlayerID());
            for (int i = 0; i < this.getNumOfPlayers(); i++) {
                if (((String[]) message.getData())[i] != null) {
                    this.playerList.get(i).setName(((String[]) message.getData())[i]);
                }
            }
            table.repaint();
        } else if (message.getType() == CardGameMessage.JOIN) {
            playerList.get(message.getPlayerID()).setName((String) message.getData());
            table.repaint();
            table.printMsg("Player " + playerList.get(message.getPlayerID()).getName() + " has joined the game!\n");
        } else if (message.getType() == CardGameMessage.FULL) {
            table.printMsg("This game is full,Sorry!\n");
            try {
                this.sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            table.repaint();
        } else if (message.getType() == CardGameMessage.QUIT) {
            table.printMsg("Player " + message.getPlayerID() + " " + playerList.get(message.getPlayerID()).getName() + " has left the game.");
            playerList.get(message.getPlayerID()).setName("");
            if (endOfGame()) {
                table.disable();
                this.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
            }

            table.repaint();
        } else if (message.getType() == CardGameMessage.READY) {
            table.repaint();
            table.enable();
            table.printMsg("Player " + message.getPlayerID() + " is ready\n");
        } else if (message.getType() == CardGameMessage.START) {
            table.printMsg("Lets start\n");
            table.enable();
            table.repaint();
            start((BigTwoDeck) message.getData());
        } else if (message.getType() == CardGameMessage.MOVE) {
            checkMove(message.getPlayerID(), (int[]) message.getData());
            table.repaint();
        } else if (message.getType() == CardGameMessage.MSG) {
            this.table.printChatBoxMsg((String) message.getData());
        }


        this.table.repaint();

    }

    @Override
    public void sendMessage(GameMessage message) {
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// TODO Auto-generated method stub

    }

    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public Deck getDeck() {
        return deck;
    }

    @Override
    public ArrayList<CardGamePlayer> getPlayerList() {
        return playerList;
    }

    @Override
    public ArrayList<Hand> getHandsOnTable() {
        return handsOnTable;
    }

    @Override
    public int getCurrentIdx() {
        return currentIdx;
    }

    @Override
    public void start(Deck deck) {

        this.deck = deck;

        for (int i = 0; i < 4; i++) {
            playerList.get(i).removeAllCards();
        }// removing all cards from playerList

        handsOnTable.clear(); //removing hands from table

        Card threeD = new Card(0, 2); // three of diamonds

        for (int i = 0; i < 52; i++) {
            int j = i / 13;
            playerList.get(j).addCard(deck.getCard(i)); // adding cards to each player
            if (deck.getCard(i).rank == threeD.rank && deck.getCard(i).suit == threeD.suit) {
                table.setActivePlayer(j);
                currentIdx = j;
//                System.out.println(currentIdx);
            }

        }

        for (int j = 0; j < 4; j++) {
            playerList.get(j).sortCardsInHand();
        }

        // cards distributed and sorted
        table.enable();
        table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName() + "'s turn:");
        table.repaint();



    }

    @Override
    public void makeMove(int playerID, int[] cardIdx) {
        CardGameMessage cardGamemessage = new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx);
        sendMessage(cardGamemessage);

    }


    boolean valid_move = false; // declaring global variable

    @Override
    public void checkMove(int playerID, int[] cardIdx) {
        int lastPlayer;


        if (handsOnTable.isEmpty() == true) {
            lastPlayer = -1;
        } else {
            lastPlayer = getPlayerList().indexOf(handsOnTable.get(handsOnTable.size() - 1).getPlayer());
        }


        boolean valid = false;
        boolean end = true;

        if (!valid_move) { //using global variable to repaint
            table.repaint();
        }
        valid_move = false;
        //int[] currentHand = bigTwoTable.getSelected();

        if (cardIdx != null) {
//					System.out.println("AAYA");
            CardList currentCardList = playerList.get(playerID).play(cardIdx); //storing the current hand
            Hand thisHand = BigTwoClient.composeHand(playerList.get(playerID), currentCardList);

            //checking valid moves
            if (handsOnTable.isEmpty() && thisHand != null) { // checking three of diamonds
                valid = thisHand.contains(new Card(0, 2));
            } else if (lastPlayer != playerID && thisHand != null) {
                if (thisHand.getType() == handsOnTable.get(handsOnTable.size() - 1).getType())
                    valid = thisHand.beats(handsOnTable.get(handsOnTable.size() - 1));

            } else
                valid = false;

            if (lastPlayer == playerID && thisHand != null) {
                valid = true;
            }

            if (valid) {
                for (int i = 0; i < currentCardList.size(); i++) {
                    playerList.get(playerID).getCardsInHand().removeCard(currentCardList.getCard(i));
                }

                table.printMsg("{" + thisHand.getType() + "}" + thisHand);
                handsOnTable.add(thisHand);

                if (playerList.get(playerID).getCardsInHand().isEmpty()) {
//							bigTwoTable.printMsg("{" + thisHand.getType() + "}" + thisHand);

                    end = true;
                } else {
                    lastPlayer = playerID;
                    playerID = (playerID + 1) % 4;
                    table.setActivePlayer(playerID);
                    table.printMsg("Player " + playerList.get(playerID).getName() + "'s turn\n");
                }

            } else {
                table.printMsg("Not a valid move!!!");
                valid_move = true;
            }


        } else // pass
        {
            if (handsOnTable.isEmpty() == false && lastPlayer != playerID) {
                playerID = (playerID + 1) % 4;
                table.setActivePlayer(playerID);
                table.printMsg("Pass");
                table.printMsg("Player " + playerList.get(playerID).getName() + "'s turn\n");
                valid = true;
            } else {
                table.printMsg("Not a valid move!!!");
                valid_move = true;
            }

        }


        if (this.endOfGame() == true && end == true) {
        	System.out.println("Here");
            String end_message = "Game ends\n";
            for (int i = 0; i < this.getNumOfPlayers(); i++) {

                if (this.getPlayerList().get(i).getNumOfCards() == 0) {
                    end_message += this.getPlayerList().get(i).getName() + " wins the game.";
                } else {
                    end_message += this.getPlayerList().get(i).getName() + " has " + this.getPlayerList().get(i).getNumOfCards() + " cards in hand.";
                }
            }

            table.disable();
            JOptionPane.showMessageDialog(null, end_message);
            CardGameMessage ready = new CardGameMessage(CardGameMessage.READY, -1, null);
            sendMessage(ready);
        }

    }

    @Override
    public boolean endOfGame() {
    	boolean end_of_game = false;
    	for(int i=0;i<4;i++){ //selecting the active player
            CardList a=playerList.get(i).getCardsInHand();
            if(a.size() == 0)
            	end_of_game = true;
        }
    	return end_of_game;
    }

    public static void main(String[] args) {
        new BigTwoClient();

    }


    public static Hand composeHand(CardGamePlayer player, CardList cards) {

        //creating instances of the classes

        Single single = new Single(player, cards);

        Pair pair = new Pair(player, cards);

        Triple triple = new Triple(player, cards);

        Straight straight = new Straight(player, cards);

        Flush flush = new Flush(player, cards);

        FullHouse fullhouse = new FullHouse(player, cards);

        Quad quad = new Quad(player, cards);

        StraightFlush straightflush = new StraightFlush(player, cards);


        //checking isValid of each class and returning that hand else returning null

        if (straightflush.isValid())
            return straightflush;

        else if (quad.isValid())
            return quad;

        else if (fullhouse.isValid())
            return fullhouse;

        else if (flush.isValid())
            return flush;


        else if (straight.isValid())
            return straight;

        else if (triple.isValid())
            return triple;

        else if (pair.isValid())
            return pair;

        else if (single.isValid())
            return single;

        else
            return null;

    }


}
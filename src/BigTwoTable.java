import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class BigTwoTable implements CardGameTable {
	
	private BigTwoClient game;
	private boolean[] selected = new boolean[13];
	private int activePlayer;
	private JFrame frame ;
	private JPanel bigTwoPanel ;
	private JButton playButton;
	private JButton passButton; 
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField messageBox;
	private Image[][] cardImages = new Image[4][13];
	private Image cardBackImage;
	private Image[] avatars =  new Image[4];
	private boolean MouseClick;
	
	
	/**
	 * a constructor for creating a BigTwoTable
	 * @param game
	 * 			 game is a reference to a card game associates with this table.
	 */
	
	public BigTwoTable (BigTwoClient game) { 
		this.game = game;
		this.build();
		msgArea.setEditable(false);
	}


	/**
	 * This method helps create the GUI by including the menubar, Panels, reading images of avatars and cards, text area, buttons.
	 */
	
	public void build() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game"); 
		JMenuItem menuItem2 = new JMenuItem("Quit");
		JMenuItem Connect = new JMenuItem("Connect");
		menuItem2.addActionListener(new QuitMenuItemListener());
		Connect.addActionListener(new ConnectMenuItemListener());
		menu.add(Connect);
		menu.add(menuItem2);
		menuBar.add(menu); 
		JMenu menu2 = new JMenu("Message");
		menuBar.add(menu2); 
		frame.setJMenuBar(menuBar);
		
	
		avatars[0] = new ImageIcon(new ImageIcon("Images/Comics-Batman-icon.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
		avatars[1] = new ImageIcon(new ImageIcon("Images/Comics-Captain-America-icon.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
		avatars[2] = new ImageIcon(new ImageIcon("Images/Comics-Ironman-Red-icon.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
		avatars[3] = new ImageIcon(new ImageIcon("Images/Superman-icon.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH)).getImage();
		cardBackImage = new ImageIcon(new ImageIcon("cards/b.gif").getImage().getScaledInstance(70, -1, Image.SCALE_SMOOTH)).getImage();
		
		
//		System.out.println(cardBackImage.getHeight(null));
//		 File f = new File("cards/b.gif");
//		 try {
//			 BufferedImage image = ImageIO.read(f);
//				int width          = image.getWidth();
//				int height         = image.getHeight(); 
//				
//				System.out.println(width);
//				System.out.println(height);
//				 
//		 }catch (IOException ioe) {
//			   ioe.printStackTrace();
//		 }
		
		
		char[] suits = {'d','c','h','s'};
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		
		String location = new String();
		for (int i = 0; i < 4; i++)
		    {
				for(int j = 0 ; j < 13;j++)
				{
					location = "cards/" + rank[j] + suits[i] + ".gif";
			        cardImages[i][j] =new ImageIcon(new ImageIcon(location).getImage().getScaledInstance(70, -1, Image.SCALE_SMOOTH)).getImage();
				}
		        
		    }
		
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BoxLayout(bigTwoPanel, BoxLayout.Y_AXIS));
		
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
		
		msgArea = new JTextArea(20, 25);
		msgArea.setLineWrap(true);
		msgArea.setBackground(Color.BLACK);
		msgArea.setForeground(Color.white);
		JScrollPane scroller = new JScrollPane(msgArea); 
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		textPanel.add(scroller); // add scroller, but not text
		
		chatArea = new JTextArea(20,25);
		chatArea.setLineWrap(true);
//		chatArea.setBackground(Color.BLACK);
//		chatArea.setForeground(Color.white);
		JScrollPane scroller1 = new JScrollPane(chatArea); 
		scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		textPanel.add(scroller1); // add scroller, but not text
		

		
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Message:");
		chatPanel.add(label);
		messageBox = new JTextField();
		messageBox.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		messageBox.addActionListener(new EnterListener());
		messageBox.setPreferredSize( new Dimension( 200, 24 ) );
		chatPanel.add(messageBox);
		textPanel.add(chatPanel);
		
		JPanel bottomPanel = new JPanel();
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		bottomPanel.add(playButton);
		bottomPanel.add(passButton);
		
		frame.add(textPanel, BorderLayout.EAST);
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		//bigTwoPanel.add(bottomPanel);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	
	

	private class BigTwoPanel extends JPanel implements MouseListener {
		
		
		public BigTwoPanel()
		{
			this.addMouseListener(this);
		}
		
		
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			this.setBackground(Color.GREEN.darker().darker().darker());
	        
	      
			int starting_x = 150;
			int starting_y = 30;
			for(int i = 0; i < 4 ; i++) {
				
				if( i == activePlayer) {
					g2d.setColor(Color.WHITE);
					g2d.drawString(game.getPlayerList().get(i).getName(), 5 , 20 + 150*i);	
					g2d.drawImage(avatars[i], 5, 30 + 150*i , this);
					g2d.setColor(Color.BLACK);
				}
				
				else {
					g2d.drawString(game.getPlayerList().get(i).getName(), 5 , 20 + 150*i);	
					g2d.drawImage(avatars[i], 5, 30 + 150*i , this);
					g2d.setColor(Color.BLACK);
					
				}
				
				
				if( i == game.getPlayerID()) {
					
					for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) 
				    	{
				    		if (selected[j] == false)
				    		{
				    			g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], starting_x+36*j, starting_y + 150*i, this);
				    		}
				    			
				    		else
				    		{
				    			g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], starting_x+ 36*j, starting_y + 150*i-15, this);
				    			
				    		}
				    	}
				}
				
				else {
					for (int j = 0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++)
			    	{
			    		g.drawImage(cardBackImage, starting_x+36*j, starting_y + 150*i, this);
			    	}
				}
			}

		    
		    g.drawString("Hand on Table", 5,  660);
		     
		    if (!game.getHandsOnTable().isEmpty())
		    {
//		    	System.out.println("AAYA");
		    	g2d.drawString("Played by " + game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName(), 5, 670);
		    	 	Hand handOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
			    		for (int i = 0; i < handOnTable.size(); i++)
			    		{
			    			g2d.drawImage(cardImages[handOnTable.getCard(i).getSuit()][handOnTable.getCard(i).getRank()], 150 + 74*i, 625, this);
			    		}
			    		
			    		
//			    		g.drawString(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getType() , 10, 745);
			    	
		    }
//		    	this.repaint();
		}
		
		
		public void mouseClicked(MouseEvent e) {
			
			int starting_x = 150;
			int starting_y = 30;
			
				for(int i = 0; i < game.getNumOfPlayers() ; i++) {
					if(i == activePlayer) {
						
						for (int j = game.getPlayerList().get(i).getNumOfCards() -1 ; j >= 0; j--) 
				    	{
							if(selected[j] == true) {

								if (e.getX() >= starting_x + 73*((j)/2.0) && e.getX() < starting_x+73*((j+2)/2.0) && e.getY() >= starting_y + 150*i -15 && e.getY() < starting_y + 150*i + 93 -15 )
					    		{
					    			selected[j] = !selected[j];
					    			break;
					    		}
							}
							else
							{
								if (e.getX() >= starting_x + 73*((j)/2.0) && e.getX() < starting_x+73*((j+2)/2.0) && e.getY() >= starting_y + 150*i && e.getY() < starting_y + 150*i + 93 )
					    		{
					    			//System.out.println("click");
					    			selected[j] = !selected[j];
					    			break;
					    		}
							}
		
				    			
				    	}// end of for
						
					}//end of if
					
					 
				    else // i is not equal to active player
				    {
				    	continue;
				    }
					
				}
			
			
			
			this.repaint();
		}



	
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



	
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



	
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



	
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



		
	}// end of inner class
	
	private class PlayButtonListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(getSelected() !=null) {
				game.makeMove(game.getCurrentIdx(), getSelected());
				resetSelected();
				repaint();
			}
			else
				printMsg("Not a valid Move!!!");
			
		}
		
	}
	
	
	private class PassButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			game.makeMove(game.getCurrentIdx(), null);
			resetSelected();
			repaint();
		}
		
	}
	
	private class EnterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1,messageBox.getText());
			game.sendMessage(message);
			messageBox.setText("");
		}
	}
	
	private class QuitMenuItemListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(activePlayer);	
		}
		
		
	}
	
	private class ConnectMenuItemListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			if (game.getPlayerID() == -1) 
			{
				game.makeConnection();
			} 	
		}
	}
	
	
	
	@Override
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
		
	}
	
	

	@Override
	public int[] getSelected() {
		
		int c = 0;
		
		for(int i = 0; i < selected.length;i++)
		{
			if(selected[i] == true)
			{
				c++;
			}
		}
		
		int [] getSelected = null;
		int c1 = 0;
		
		if(c != 0)
		{	
			getSelected = new int[c];
			for(int i = 0; i < selected.length;i++)
			{
				if(selected[i] == true)
				{
					getSelected[c1] = i;
					c1++;
				}	
			}
		}
		
		
		
			return getSelected;
	}

	@Override
	public void resetSelected() {
		for(int i = 0 ; i < 13 ; i++)
		{
			selected[i] = false;
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repaint() {
		for(int i = 0 ; i < 13 ; i++)
		{
			selected[i] = false;
		}
		bigTwoPanel.repaint();
//		bigTwoPanel.revalidate();
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMsg(String msg) {
		msgArea.append(msg + "\n");
		// TODO Auto-generated method stub
		
	}
	
	public void printChatBoxMsg(String msg) 
	{
		chatArea.append(msg+"\n");
	}


	@Override
	public void clearMsgArea() {
		msgArea.setText("");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		this.playButton.setEnabled(true);
		this.passButton.setEnabled(true);
		MouseClick = true;
		//bigTwoPanel.setEnabled(true);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		MouseClick = false;
		//bigTwoPanel.setEnabled(false);
		// TODO Auto-generated method stub
		
	}

	
}

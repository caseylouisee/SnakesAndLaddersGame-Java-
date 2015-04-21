package Display;
/**
 * @file DisplaySnL.java
 * 
 * @brief Class to display the SnL game
 * 
 * @date 29/03/2015
 * 
 * @author Casey Denner
 *  
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.commons.lang3.time.StopWatch;

import Board.BoardSnL;
import Game.GameSnL;
import Menu.GameSelector;
import Menu.MenuSnL;
import Player.PlayerSnL;
/**
 * @class DisplaySnL
 * @brief This class displays everything for the SnL game
 *
 */
public class DisplaySnL extends JPanel implements Runnable{

	/* ------ CONSTANTS ----- */
	/** Sets shape's width */
	public static final int SQUARE_WIDTH = 50;
	
	/** Sets shape's height */
	public static final int SQUARE_HEIGHT = 50;
	
	/** Sets shape's diameter */
	public static final int PIECE_DIAMETER = 20;

	/* ------ VARIABLES ------ */
	/** response to keep track of play again/not */
	int m_dialogResponse;
	
	/** roll dice initialised to 1 */
	int roll = 1;

	/** Label to display who's turn it is */
	JLabel m_playerTurn;

	/** Snake head buffered image */
	private BufferedImage m_snakeHead;
	
	/** Snake tail buffered image */
	private BufferedImage m_snakeTail;
	
	/** Ladder buffered image */
	private BufferedImage ladder;

	/** Array lists to hold snakes positions */
	ArrayList<Integer> m_snakeLocations = new ArrayList<Integer>();
	
	/** Array lists to hold ladders positions */
	ArrayList<Integer> m_ladderLocations = new ArrayList<Integer>();

	/**Array list to hold players */
	ArrayList<PlayerSnL> m_players = new ArrayList<PlayerSnL>();

	/** Stop watch variable to display timer **/
	private StopWatch m_stopWatch;

	/** JLabel to display timer **/
	JLabel m_lblTimer = new JLabel("Timer");

	/** Boolean to test if game is running */
	private boolean gameRunning = false;

	/** Game object set as null, when initiated set to GameSnL */
	GameSnL m_gameSnL = null;

	/** JButton to roll dice */
	final JButton m_rollDiceBtn = new JButton("Roll dice");

	/** Label for dice image */
	private JLabel m_dicePicLabel = new JLabel(new ImageIcon("null"));

	/** Button to save game */
	private JButton m_saveGameButton = new JButton();
	
	/** Button to go back to main menu */
	private JButton m_backButton = new JButton();

	/** JPanel to contain player information */
	private JPanel m_playerInfoPanel;

	/** ArrayList to contain player information (names) */ 
	private ArrayList<JLabel> m_playerInfo;

	/** Arraylist to contain player positions */
	private ArrayList<JLabel> m_playerPos;

	/** Arraylist to contain player colours */
	private ArrayList<JLabel> m_playerColour;

	/** JPanel used for winning visual feedback */
	JPanel boardOverlay = null;

	/** Boolean used if visualization is selected */
	Boolean m_visualize;

	/** gets the co_ordinates on the board for a particular square
	 * @param squareNo
	 */
	public int[] getCoordinates(int squareNo) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::getCoordinates");
		}
		int yTranslation = 9 - getYVal(squareNo);
		int xTranslation;
		if (squareNo % 20 > 10 || squareNo % 20 == 0) {
			xTranslation = 11 - getXVal(squareNo);
		} else {
			xTranslation = getXVal(squareNo);
		}
		int x = (xTranslation * Display.OFFSET50);
		int y = (yTranslation * Display.OFFSET50) - 0;

		int[] coordinates = { x, y };
		return coordinates;
	}

	/** 
	 * Gets the X Value co-ordinate for the squareNo
	 * @param squareNo
	 * @return squareNo
	 */
	public int getXVal(int squareNo) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::getXVal");
		}
		if (squareNo % 10 == 0) {
			return 10;
		} else {
			return squareNo % 10;
		}
	}

	/** 
	 * Gets the Y Value co-ordinate for the squareNo
	 * @param squareNo
	 * @return squareNo
	 */
	public int getYVal(int squareNo) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::getYVal");
		}
		return ((squareNo - getXVal(squareNo)) / 10);
	}

	/** 
	 * Constructor for DisplaySnL
	 *
	 * @param gameSnL set to m_GameSnL
	 * @param snakesList set to m_snakeLocations
	 * @param laddersList set to m_laddersList
	 * @param players set to m_players
	 */
	public DisplaySnL(GameSnL gameSnL, ArrayList<Integer> snakesList,
			ArrayList<Integer> laddersList, ArrayList<PlayerSnL> players, 
			final Boolean visualization) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::ConstructorCalled");
		}
		m_visualize = visualization;
		m_gameSnL = gameSnL;
		m_snakeLocations = snakesList;
		m_ladderLocations = laddersList;
		m_players = players;
	}

	/** 
	 * Adds the player name, who's turn it is and the roll dice button to
	 *  the display 
	 *  
	 * @param frame displays the screen output
	 **/
	public void addPlayInterface(JFrame frame) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::addPlayInterface");
		}
		m_playerTurn = new JLabel();
		m_playerTurn.setText("It is player "
				+ m_players.get(m_gameSnL.getIterator()).getPlayerName()
				+ "'s turn!");
		m_playerTurn.setBounds(Display.XPOS_COL200, 
				Display.YPOS_ROW500+Display.OFFSET15, 
				Display.COMPONENT_WIDTH200, Display.COMPONENT_HEIGHT20);
		m_playerTurn.setForeground(Color.WHITE);

		add(m_playerTurn);
		addRollDice();
		addSaveButton();
		addBackButton();
		addPlayerInformation();

		createTimer();

		gameRunning = true;	

		Thread thread = new Thread(){
			public void run(){
				while(gameRunning){
					displayTimer();
					printPosition();
				}
			}
		};

		thread.start();

	}

	/**
	 * Sets the bounds for the m_rollDiceBtn
	 */
	private void addRollDice() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::addRollDice");
		}
		m_rollDiceBtn.setBounds(Display.XPOS_COL450-Display.OFFSET15, 
				Display.YPOS_ROW500+Display.OFFSET15, 
				Display.COMPONENT_WIDTH100, Display.COMPONENT_HEIGHT40);

		if(m_players.get(m_gameSnL.getIterator()).getPlayerName()
				.endsWith(".AI")){
			m_rollDiceBtn.setText("Start game");
			m_rollDiceBtn.doClick();	
		}

		addDiceActionListener();
	}

	/**
	 * Adds the actionListener for the m_rollDiceBtn
	 */
	private void addDiceActionListener() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::addDiceActionListener");
		}
		m_rollDiceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(m_players.get(m_gameSnL.getIterator()).getPlayerName()
						.endsWith(".AI")){

					ActionListener listen = new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							m_rollDiceBtn.setText("Roll Dice");
							System.out.println("AI player found");
							roll = m_gameSnL.buttonPush();

							if(m_players.get(m_gameSnL.getIterator())
									.getPlayerName().endsWith(".AI")){
								m_rollDiceBtn.doClick();

							}
							m_playerTurn.setText("It is player "
									+ m_players.get(m_gameSnL.getIterator())
									.getPlayerName() + "'s turn!");
						}
					};	
					Timer timer = new Timer(1000, listen);
					timer.setRepeats(false);
					timer.restart();

				} else {
					m_rollDiceBtn.setText("Roll Dice");
					roll = m_gameSnL.buttonPush();

					m_playerTurn.setText("It is player "
							+ m_players.get(m_gameSnL.getIterator())
							.getPlayerName()
							+ "'s turn!");

					if(m_players.get(m_gameSnL.getIterator()).getPlayerName()
							.endsWith(".AI")){
						m_rollDiceBtn.doClick();


					}
				}

				m_playerTurn.setText("It is player "
						+ m_players.get(m_gameSnL.getIterator()).getPlayerName()
						+ "'s turn!");

				m_dicePicLabel.setIcon(new ImageIcon("res/Dice"+roll+".png"));

				//Prints out next players current location.
				System.out.println("player position " +
						m_players.get(m_gameSnL.getIterator())
						.getPlayerLocation());

			}
		});

		m_dicePicLabel.setIcon(new ImageIcon("res/Dice"+roll+".png"));

		m_dicePicLabel.setBounds(Display.XPOS_COL400+Display.OFFSET10,
				Display.YPOS_ROW550, Display.COMPONENT_WIDTH150,
				Display.COMPONENT_HEIGHT100);

		add(m_rollDiceBtn);
		add(m_dicePicLabel);

	}

	/** 
	 * Sets the playerInfoPanel's bounds and adds the player name's and 
	 * position's to it in the form of JLabels.
	 */
	private void addPlayerInformation() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::addPlayerInformation");
		}
		m_playerInfoPanel = new JPanel();
		m_playerInfoPanel.setBounds(Display.XPOS_COL50, 
				Display.YPOS_ROW550-Display.OFFSET15, 
				Display.COMPONENT_WIDTH300, Display.COMPONENT_HEIGHT80);
		m_playerInfoPanel.setLayout(null);
		m_playerInfoPanel.setBackground(Color.BLACK);

		m_playerInfo = new ArrayList<JLabel>();
		m_playerPos = new ArrayList<JLabel>();
		m_playerColour = new ArrayList<JLabel>();

		for(int i = 0; i<m_players.size(); i++){
			m_playerInfo.add(new JLabel(m_gameSnL.getPlayer(i)
					.getPlayerName()));
			m_playerInfo.get(i).setBounds(0, Display.OFFSET15*i, 
					Display.COMPONENT_WIDTH75, Display.COMPONENT_HEIGHT20);
			m_playerInfo.get(i).setForeground(Color.WHITE);

			Integer location = m_gameSnL.getPlayerLocation(i);
			m_playerPos.add(new JLabel(location.toString()));
			m_playerPos.get(i).setBounds(Display.XPOS_COL100, 
					Display.OFFSET15*i, Display.COMPONENT_WIDTH20, 
					Display.COMPONENT_HEIGHT20);
			m_playerPos.get(i).setForeground(Color.WHITE);

			Color colour = m_players.get(i).getPlayerColor();
			String colorName = null;
			if (Color.BLUE.equals(colour)){
					colorName = "Blue";
			} else if (Color.RED.equals(colour)) {
					colorName = "Red";
			} else if (Color.YELLOW.equals(colour)){
					colorName = "Yellow";
			} else if (Color.GREEN.equals(colour)){
					colorName = "Green";
			}
	
			m_playerColour.add(new JLabel(colorName));
			m_playerColour.get(i).setBounds(Display.XPOS_COL200, 
				Display.OFFSET15*i, Display.COMPONENT_WIDTH50, 
				Display.COMPONENT_HEIGHT20);
			m_playerColour.get(i).setForeground(Color.WHITE);

			m_playerInfoPanel.add(m_playerInfo.get(i));
			m_playerInfoPanel.add(m_playerPos.get(i));
			m_playerInfoPanel.add(m_playerColour.get(i));
		}
		add(m_playerInfoPanel);
	}

	/**
	 * Prints the players position in a JLabel
	 */
	private void printPosition() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::printPosition");
		}
		Integer location = m_gameSnL.getPlayerLocation(m_gameSnL.getIterator());
		m_playerPos.get(m_gameSnL.getIterator()).setText(location.toString());	
	}
	
	/** adds the Back button to screen setting bounds etc */
	private void addBackButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::addBackButton");
		}
		// add back button
		m_backButton.setIcon(new ImageIcon("res/BACKBTN.png"));
		m_backButton.setBorderPainted(false); 
		m_backButton.setFocusPainted(false);
		m_backButton.setContentAreaFilled(false);
		m_backButton.setBounds(Display.XPOS_COL50, Display.YPOS_ROW600, 
				Display.COMPONENT_WIDTH160, Display.COMPONENT_HEIGHT85);
		m_backButton.setVisible(true);
		// listen for clicks
		m_backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_gameSnL.frameDispose();
				gameRunning = false;
				m_gameSnL.reset();
				new GameSelector(GameSelector.m_TRACE); 
			}
		});
		add(m_backButton);
	}
	
	/**
	 * Sets the save button text and bounds and creates action listener
	 */
	private void addSaveButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::addSaveButton");
		}
		m_saveGameButton.setIcon(new ImageIcon("res/SAVEBTN.png"));
		m_saveGameButton.setBorderPainted(false); 
		m_saveGameButton.setFocusPainted(false);
		m_saveGameButton.setContentAreaFilled(false);
		m_saveGameButton.setBounds(Display.XPOS_COL200 + Display.OFFSET20, 
				Display.YPOS_ROW600, Display.COMPONENT_WIDTH160, 
				Display.COMPONENT_HEIGHT85);
		m_saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Game saved");
				saveGame();
			}
		});	
		add(m_saveGameButton);
	}

	/** method to save game to the file res/saveSnL.csv */
	public void saveGame(){
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::saveGame");
		}
		try {
			FileWriter writer = new FileWriter("res/saveSnL.csv");

			/*
			 * Format: 
			 * Number of players\n
			 * playername,r,g,b,playerposition\n
			 * playername,playercolour,playerposition\n
			 * ....
			 * number of snakes,number of ladder\n
			 * position of snakes\n
			 * position of ladders
			 */
			writer.append(m_gameSnL.getNumberOfPlayers()+"\n");
			for(int i=0; i<m_gameSnL.getNumberOfPlayers();i++){
				writer.append(m_players.get(i).getPlayerName()+",");
				Integer red = m_players.get(i).getPlayerColor().getRed();
				Integer green = m_players.get(i).getPlayerColor().getGreen();
				Integer blue = m_players.get(i).getPlayerColor().getBlue();

				writer.append(red.toString()+","+ green.toString()+","
						+blue.toString()+",");

				Integer playerLocation = m_players.get(i).getPlayerLocation();
				writer.append(playerLocation.toString()+"\n");
			}

			Integer snakes = m_snakeLocations.size()/2;
			Integer ladders = m_ladderLocations.size()/2;
			writer.append(snakes.toString()+",");
			writer.append(ladders.toString()+"\n");

			Integer snakeLocation;

			snakeLocation = m_snakeLocations.get(0);
			writer.append(snakeLocation.toString());

			for(int i = 1; i< m_snakeLocations.size();i++){
				snakeLocation = m_snakeLocations.get(i);
				writer.append(","+snakeLocation.toString());
			}
			writer.append("\n");

			Integer ladderLocation;
			ladderLocation = m_ladderLocations.get(0);
			writer.append(ladderLocation.toString());

			for(int i = 1; i< m_ladderLocations.size();i++){
				ladderLocation = m_ladderLocations.get(i);
				writer.append(","+ladderLocation.toString());
			}

			writer.flush();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		} 
	}

	/** 
	 * Calls the method to draw the board and snakes and ladders, display timer
	 * @param graphics
	 */
	public void paintComponent(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::paintComponent");
		}
		super.paintComponent(graphics);

		this.setBackground(Color.WHITE);
		this.drawBoard(graphics);

		if (m_snakeLocations.size() > 0) {
			this.printSnakes(graphics, m_snakeLocations);
		}
		if (m_ladderLocations.size() > 0) {
			this.printLadders(graphics, m_ladderLocations);
		}

		paintPlayer(graphics);

		repaint();
	}

	/**
	 * Paints the player according to their colour and location
	 * @param graphics
	 */
	public void paintPlayer(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::paintPlayer");
		}
		for (int i = 0; i < m_players.size(); i++) {
			this.printPlayer(graphics, m_players.get(i).getPlayerColor(),
					m_players.get(i).getPlayerLocation(),
					m_players.get(i).getLastLocation());
		}

		repaint();
	}

	/**
	 * Paints the player to the screen
	 */
	public void updateGraphics(){
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::updateGraphics");
		}
		paintPlayer(getGraphics());
	}

	/**
	 * Updates the graphics on the screen particularly the player location
	 * @param coordinates holds the coordinates of the location of the player
	 */
	public void updateGraphics(int[] coordinates){
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::updateGraphics");
		}
		for (int i = 0; i < m_players.size(); i++) {
			this.printPlayer(getGraphics(), m_players.get(i).getPlayerColor(),
					m_players.get(i).getPlayerLocation(),
					m_players.get(i).getLastLocation());
		}
		repaint();
	}

	/** 
	 * Prints the player to the board 
	 * @param graphics
	 * @param playerColor
	 * @param squareNo
	 */
	public void printPlayer(Graphics graphics, Color playerColor, int squareNo,
			int lastLocation){
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::printPlayer");
		}
		graphics.setColor(playerColor);
		int[] coordinates = getCoordinates(squareNo);
		graphics.fillOval(coordinates[0] + Display.OFFSET15, coordinates[1] +
				Display.OFFSET20, PIECE_DIAMETER, PIECE_DIAMETER);

		if(m_visualize){
			if(GameSelector.m_TRACE){
				System.out.println("DisplaySnL::printPlayer"
						+ " with visualization");
			}

			for(int i = 0; i<m_players.size(); i++){
				for(int j = 1; 
						j<m_players.get(i).getAllLocations().size(); j++){
					int number = m_players.get(i).getAllLocations().get(j);
					int line = m_players.get(i).getAllLocations().get(j-1);
					int [] lastCoordinates = getCoordinates(number);
					int [] lineCoordinates = getCoordinates(line);
					graphics.setColor(m_players.get(i).getPlayerColor());
					graphics.fillOval(lastCoordinates[0]+Display.OFFSET15+(i*2), 
							lastCoordinates[1] + Display.OFFSET20+(i*2),
							PIECE_DIAMETER, PIECE_DIAMETER);
					graphics.drawLine(lastCoordinates[0]+Display.OFFSET20+(i*2), 
							lastCoordinates[1]+Display.OFFSET25+(i*2),
							lineCoordinates[0]+Display.OFFSET20+(i*2), 
							lineCoordinates[1]+Display.OFFSET25+(i*2));
				}
			}
		}
	}

	/** 
	 * Draws the board 
	 * @param graphics
	 */
	public void drawBoard(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::drawBoard");
		}
		this.setBackground(Color.BLACK);

		graphics.setColor(Color.WHITE);
		int l = 100;
		for (int j = 0; j < 10; j++) {
			for (int i = 1; i <= 10; i++) {
				graphics.drawRect((i * SQUARE_WIDTH), (j * SQUARE_HEIGHT),
						SQUARE_WIDTH, SQUARE_HEIGHT);
				graphics.drawString(String.valueOf(l), i * SQUARE_WIDTH + 
						Display.OFFSET5, j*SQUARE_HEIGHT + Display.OFFSET15);
				l--;
			}
			j++;
			l = l - 9;
			for (int i = 1; i <= 10; i++) {
				graphics.drawRect((i * SQUARE_WIDTH), (j * SQUARE_HEIGHT),
						SQUARE_WIDTH, SQUARE_HEIGHT);
				graphics.drawString(String.valueOf(l), i * SQUARE_WIDTH + 
						Display.OFFSET5, j*SQUARE_HEIGHT + Display.OFFSET15);
				l++;
			}
			l = l - 11;
		}

	}


	/** 
	 * Prints the ladders on the board 
	 * @param graphics
	 * @param squares
	 */
	public void printLadders(Graphics graphics, ArrayList<Integer> squares) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::printLadders");
		}
		//Pulls image from file
		try {
			ladder = ImageIO.read(new File("res/ladder.png"));
		} catch (IOException e) {
			System.out.println("wrong path");
		}

		for (int i = 0; i < squares.size(); i++) {

			int startSquare = squares.get(i);
			i++;
			int endSquare = squares.get(i);

			//gets coordinates for ladder square locations
			int[] startCoordinates = getCoordinates(startSquare);
			int[] endCoordinates = getCoordinates(endSquare);

			graphics.drawImage(ladder, startCoordinates[0],
					startCoordinates[1], Display.OFFSET40, 
					Display.OFFSET40, null);
			graphics.drawImage(ladder, endCoordinates[0], endCoordinates[1],
					Display.OFFSET40, Display.OFFSET40, null);

			Graphics2D graphics2 = (Graphics2D) graphics;
			graphics2.setColor(new Color(139, 90, 43));
			graphics2.setStroke(new BasicStroke(5));
			graphics2.drawLine(startCoordinates[0] + 5,
					startCoordinates[1] + Display.OFFSET30, 
					endCoordinates[0] + Display.OFFSET5, endCoordinates[1]);
			graphics2.drawLine(startCoordinates[0] + Display.OFFSET35,
					startCoordinates[1] + Display.OFFSET30, 
					endCoordinates[0] + Display.OFFSET35, endCoordinates[1]);

		}

	}

	/** 
	 * Prints the snakes on the board
	 * @param graphics
	 * @param snakes
	 */
	public void printSnakes(Graphics graphics, ArrayList<Integer> snakes) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::printSnakes");
		}
		try {
			m_snakeHead = ImageIO.read(new File("res/SnakeHead.png"));
			m_snakeTail = ImageIO.read(new File("res/SnakeTail.png"));
		} catch (IOException e) {
			System.out.println("wrong path");
		}

		for (int i = 0; i < snakes.size(); i++) {

			int startSquare = snakes.get(i);
			i++;
			int endSquare = snakes.get(i);

			int[] startCoordinates = getCoordinates(startSquare);
			int[] endCoordinates = getCoordinates(endSquare);

			graphics.drawImage(m_snakeHead, startCoordinates[0],
					startCoordinates[1], Display.OFFSET40, Display.OFFSET40,
						null);
			graphics.drawImage(m_snakeTail, endCoordinates[0],
					endCoordinates[1] + Display.OFFSET10, Display.OFFSET40,
						Display.OFFSET40, null);

			Graphics2D graphics2 = (Graphics2D) graphics;
			graphics2.setColor(Color.GREEN);
			graphics2.setStroke(new BasicStroke(10));
			graphics2.drawLine(startCoordinates[0] + Display.OFFSET20,
					startCoordinates[1] + Display.OFFSET35, endCoordinates[0] +
					Display.OFFSET20, endCoordinates[1] + Display.OFFSET10);
		}
	}

	/**
	 * Creates the stop watch timer
	 */
	public void createTimer() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::createTimer");
		}
		if (m_stopWatch == null) {
			m_lblTimer.setBounds(Display.XPOS_COL50, 
					Display.YPOS_ROW500+Display.OFFSET15,
					Display.COMPONENT_WIDTH100, Display.COMPONENT_HEIGHT20);
			m_lblTimer.setForeground(Color.WHITE);
			add(m_lblTimer);

			/* Creating timer */
			m_stopWatch = new StopWatch();
			m_stopWatch.start();
			m_lblTimer.setText(m_stopWatch.toString());

		} else {
			m_stopWatch.reset();
			m_stopWatch.start();
		}
	}

	/** Displays the stopwatch timer **/
	public void displayTimer() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::displayTimer");
		}
		m_lblTimer.setText(m_stopWatch.toString());
	}

	/** 
	 * When there is a winner a frame pops up to show winner 
	 * @Param frame the frame for the display
	 */
	public void winner(JFrame frame) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplaySnL::winner");
		}

		for(int i = 0; i<m_players.size(); i++){
		System.out.println("Player" + i + " " + m_gameSnL.getPlayerLocation(i));
		}
		
		/*boardOverlay = new JPanel();
		boardOverlay.setBounds(0, 0, Display.XPOS_COL500, 
				Display.XPOS_COL500);
		boardOverlay.setVisible(true);
		frame.add(boardOverlay);
		
		Thread flash = new Thread(){
			public void run(){
				int sleep = 50;
				while(true){
					boardOverlay.setOpaque(true);
					boardOverlay.setBackground(Color.WHITE);
					try {Thread.sleep(sleep);}
					catch (InterruptedException e) {}
					boardOverlay.setBackground(Color.RED);
					try {Thread.sleep(sleep);}
					catch (InterruptedException e) {}
					boardOverlay.setBackground(Color.BLUE);
					try {Thread.sleep(sleep);}
					catch (InterruptedException e) {}
				}
			}
		};
		flash.start();*/

		m_dialogResponse = JOptionPane.showConfirmDialog(frame,	
				m_players.get(m_gameSnL.getIterator()).getPlayerName() +
				" won! Would you like to play again?", "Snakes and Ladders",
				JOptionPane.YES_NO_OPTION);
		if (m_dialogResponse == 0) {
			frame.dispose();
			new MenuSnL();
		}
		else if (m_dialogResponse == 1) {
			frame.dispose();
			System.exit(0);
		}
	}

	/** 
	 * Overrides the run method in this runnable class to repeatedly 
	 * paintComponents as the timer needs to be continuously updated 
	 */
	@Override
	public void run() {
		while (gameRunning) {
			paintComponents(getGraphics());
		}
	}

}

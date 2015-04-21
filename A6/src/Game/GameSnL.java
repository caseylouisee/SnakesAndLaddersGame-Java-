/**
 * @file GameSnL.java
 * @author Casey Denner
 * @date 29/03/2015
 * @brief Creates all the user interface for Snakes and Ladders, as well as 
 * contains all the functions to play the game
 * @see MenuSNL
 */

package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Board.BoardSnL;
import Display.Display;
import Display.DisplaySnL;
import Menu.GameSelector;
import Player.AIPlayerSnL;
import Player.HumanPlayerSnL;
import Player.PlayerSnL;

/**
 * @class GameSnL
 * @brief Creates all the user interface for Snakes and Ladders, as well as
 *        contains all the functions to play the game
 */
public class GameSnL {

	/** Arraylist for snakes */
	private static ArrayList<Integer> m_snakesList = new ArrayList<Integer>();
	
	/** Arraylist for ladders */
	private static ArrayList<Integer> m_laddersList = new ArrayList<Integer>();
	
	/** Arraylist for human player names */
	private static ArrayList<PlayerSnL> players = new ArrayList<PlayerSnL>();
	
	/**
	 * Arraylist for the squares the player has to move to before and after a
	 * snake or a ladder object
	 */
	private static ArrayList<Integer> m_movementSquares = 
			new ArrayList<Integer>();
	
	/** Width of Snakes and Ladders Board */
	private final int BOARD_WIDTH = 10;
	
	/** Height of Snakes and Ladders Board */
	private final int BOARD_HEIGHT = 10;
	
	/** Height of frame */
	private final int FRAME_HEIGHT = 600;
	
	/** Width of frame */
	private final int FRAME_WIDTH = 800;
	
	/** JFrame for displaying the Snakes and Ladders board */
	private JFrame m_frame = new JFrame("Snakes & Ladders");
	
	/** Panel for player names and positions as well as the dice image */
	private final JPanel m_contentPanel = new JPanel();
	
	/** Dice image */
	private JLabel m_dicePicLabel = new JLabel(new ImageIcon("null"));
	
	/** Determines what dice photo to display */
	private BufferedImage m_myPicture = null;
	
	/** Pop up for initial roll */
	private JFrame m_initialRollFrame = new JFrame();
	
	/** Initial roll array for player iteration */
	private int[] m_initialRolls;
	
	/** Total number of players */
	final int m_numberOfPlayers;
	
	/** All player names, whether human or AI */
	private ArrayList<String> m_playerNames;
	
	/** All player colours, whether human or AI */
	private ArrayList<Color> m_playerColors;
	
	/** Displays the player turn */
	private JTextField m_txtField;
	
	/** Displays the roll dice button */
	private JButton m_btnRollDice;
	
	/** Displays the continue button */
	private JButton m_btnContinue;
	
	/** Board object */
	private BoardSnL m_board;
	
	/** Iterator for all players */
	private int m_playerIterator = 0;
	
	/** Panel to display everything */
	private DisplaySnL m_shapesPanel;
	
	/** Boolean for visualization */
	 Boolean m_visualize;

	/**
	 * Returns the number of players
	 * @return m_numberOfPlayers the number of players
	 */
	public int getNumberOfPlayers() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getNumberOfPlayers() no parameters"
					+ " needed, returns: " + m_numberOfPlayers);
		}
		return m_numberOfPlayers;
	}

	/**
	 * Returns list of ladders
	 * @return m_laddersList list of laddders
	 */
	public ArrayList<Integer> getLadders() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getLadders() no parameters"
					+ " needed, returns: " + m_laddersList);
		}
		return m_laddersList;
	}

	/**
	 * Returns list of snakes
	 * @return m_snakesList list of snakes
	 */
	public ArrayList<Integer> getSnakes() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getSnakes() no parameters"
					+ " needed, returns: " + m_snakesList);
		}
		return m_snakesList;
	}

	/**
	 * Returns current player's location
	 * @param playerIndex
	 *            - index of the current player
	 * @return the current player's location
	 */
	public int getPlayerLocation(int playerIndex) {
		if (GameSelector.m_TRACE) {
				System.out.println("GameSnL:: getPlayerLocation()");		
		}
		return players.get(playerIndex).getPlayerLocation();
	}

	/**
	 * Returns the number of players
	 * @return players.size() the number of players
	 */
	public int getPlayersSize() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getPlayersSize() no parameters"
					+ " needed, returns: " + players.size());
		}
		return players.size();
	}

	/**
	 * Returns the current turn
	 * @return m_playerIterator whose turn it is
	 */
	public int getIterator() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getIterator() no parameters"
					+ " needed, returns: " + m_playerIterator);
		}
		return m_playerIterator;
	}

	/**
	 * Returns a player name
	 * @param playerIndex
	 *            - index of player
	 * @return player
	 */
	public PlayerSnL getPlayer(int playerIndex) {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getPlayer(int playerIndex)");		
		}
		return players.get(playerIndex);
	}

	/**
	 * Returns the squares the player has to move to before and after a snake or
	 * a ladder object
	 * @return m_movementSquares the squares the player has to move to before 
	 * 		and after a snake or a ladder object
	 */
	public ArrayList<Integer> getMovementSquares() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getMovementSquares() no parameters"
					+ " needed, returns: " + m_movementSquares);
		}
		return m_movementSquares;
	}

	/**
	 * Generates the squares the player has to move to before and after a snake
	 * or a ladder object
	 * @param original - player's initial position
	 * @return the squares the player has to move to before and after a snake or
	 *         a ladder object
	 */
	public static int getMovementPair(int original) {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getMovementPair()");
		}

		if (m_laddersList.contains(original)) {
			int movementPair = m_laddersList.lastIndexOf(original) - 1;

			return m_laddersList.get(movementPair);
		} else if (m_snakesList.contains(original)) {
			int movementPair = m_snakesList.lastIndexOf(original) + 1;

			return m_snakesList.get(movementPair);
		} else {
			System.out.println("error");

			return -1;
		}
	}
	
	/**
	 * Sets the position of the snakes
	 * @param i - board position to add snake to
	 */
	public void setSnakes(int i){
		m_snakesList.add(i);
	}
	
	/**
	 * Sets the position of the ladders
	 * @param i - board position to add ladder to
	 */
	public void setLadders(int i){
		m_laddersList.add(i);
	}
	
	public void setMovementSquares(int i){
		m_movementSquares.add(i);
	}

	/**
	 * Loads data into game
	 * @param playerNames stores the player names.
	 * @param playerColors shows list of colours.
	 * @param playerPositions shows list of player positions.
	 * @param snakes adds snakes to various squares.
	 * @param ladders adds ladders to various squares.
	 */
	public GameSnL(ArrayList<String> playerNames,
			ArrayList<Color> playerColors, ArrayList<Integer> playerPositions,
			ArrayList<Integer> snakes, ArrayList<Integer> ladders,
			Boolean visualization) {

		m_numberOfPlayers = playerNames.size();
		m_playerNames = playerNames;
		m_playerColors = playerColors;
		m_visualize = visualization;

		m_laddersList = ladders;
		m_snakesList = snakes;

		for (int i = 0; i < m_snakesList.size()/2; i++) {
			m_movementSquares.add(m_snakesList.get(i*2));
		}

		for (int i = 1; i < m_laddersList.size()/2; i++) {
			m_movementSquares.add(m_laddersList.get(i*2));
		}

		for (int i = 0; i < playerNames.size(); i++) {
			if (playerNames.get(i).endsWith(".AI")) {
				players.add(new AIPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i), playerPositions.get(i)));
			} else {
				players.add(new HumanPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i), playerPositions.get(i)));

			}
		}

		m_board = new BoardSnL(BOARD_WIDTH, BOARD_HEIGHT, m_movementSquares,
				m_laddersList, m_snakesList);
		initDisplay();
	}

	/**
	 * Stores passed data into the game
	 * @param playerNames all player names.
	 * @param playerColors colour of each player.
	 * @param snakes number of snakes to be generated.
	 * @param ladders number of ladders to be generated.
	 */
	public GameSnL(final ArrayList<String> playerNames,
			final ArrayList<Color> playerColors, final int snakes,
			final int ladders, final Boolean visualization) {

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: GameSnL() valid params");
		}

		m_playerNames = playerNames;
		m_playerColors = playerColors;
		m_numberOfPlayers = playerNames.size();
		m_visualize = visualization;
		m_initialRolls = new int[m_numberOfPlayers];
		
		m_initialRollFrame.setSize(Display.COMPONENT_WIDTH160, 
				Display.COMPONENT_HEIGHT200);
		m_initialRollFrame.getContentPane().setLayout(new BorderLayout());
		m_initialRollFrame.setTitle("Initial Roll");
		m_initialRollFrame.getContentPane().add(m_contentPanel, 
				BorderLayout.CENTER);
		m_contentPanel.setLayout(null);
		m_contentPanel.setBackground(Color.BLACK);

		try {
			m_myPicture = ImageIO.read(new File("res/Dice1.png"));
			m_dicePicLabel.setIcon((Icon) new ImageIcon(m_myPicture));
		} catch (IOException f) {
			f.printStackTrace();
		}
		m_dicePicLabel.setBounds(Display.OFFSET40, Display.OFFSET20,
				Display.COMPONENT_HEIGHT80, Display.COMPONENT_WIDTH80);
		m_contentPanel.add(m_dicePicLabel);

		m_txtField = new JTextField();
		m_txtField.setText(playerNames.get(m_playerIterator)
				+ " Roll the dice.");
		m_txtField.setBounds(Display.OFFSET5, Display.YPOS_ROW100, 
				Display.COMPONENT_HEIGHT150, Display.COMPONENT_WIDTH20);
		m_contentPanel.add(m_txtField);
		m_txtField.setColumns(10);
		m_txtField.setEditable(false);
		m_txtField.setBackground(Color.BLACK);
		m_txtField.setForeground(Color.WHITE);
		m_txtField.setHorizontalAlignment(JTextField.CENTER);

		m_btnContinue = new JButton("Next Player");
		m_btnContinue.setBounds(Display.OFFSET30, Display.YPOS_ROW150,
				Display.COMPONENT_HEIGHT100, Display.COMPONENT_HEIGHT20);
		m_contentPanel.add(m_btnContinue);
		m_btnContinue.setVisible(false);
		m_btnContinue.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (m_playerIterator <= m_numberOfPlayers - 1) {
					m_txtField.setText(playerNames.get(m_playerIterator)
							+ " Roll the dice.");
					m_btnContinue.setVisible(false);
					m_btnRollDice.setVisible(true);
				} else {
					sortPlayers();
					m_initialRollFrame.dispose();
					m_playerIterator = 0;
					initVariables(playerNames, playerColors, snakes, ladders);
					initDisplay();
				}
			}
		});
		m_btnRollDice = new JButton("Roll Dice");
		m_btnRollDice.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				final int MAX_VALUE = 6;
				final int MIN_VALUE = 1;
				if (m_playerIterator <= m_numberOfPlayers - MIN_VALUE) {
					int roll = (int) ((Math.random() * MAX_VALUE) + MIN_VALUE);
					try {
						m_myPicture = ImageIO.read(new File("res/Dice" + roll
								+ ".png"));
					} catch (IOException e) {
					}
					m_dicePicLabel.setIcon((Icon) new ImageIcon(m_myPicture));
					m_initialRolls[m_playerIterator] = roll;
					m_txtField.setText(playerNames.get(m_playerIterator)
							+ " rolled a " + roll + ".");
					m_btnContinue.setVisible(true);
					m_btnRollDice.setVisible(false);
					m_playerIterator++;

					if (m_playerIterator == m_numberOfPlayers) {
						m_btnContinue.setText("Play Game.");
					}
				}
			}
		});
		m_btnRollDice.setBounds(Display.OFFSET40, Display.YPOS_ROW150,
				Display.COMPONENT_WIDTH80,Display.COMPONENT_HEIGHT20);
		m_contentPanel.add(m_btnRollDice);
		m_initialRollFrame.setDefaultCloseOperation
			(JDialog.DO_NOTHING_ON_CLOSE);
		m_initialRollFrame.setVisible(true);
	}

	/**
	 * Determines who takes which turn
	 */
	private void sortPlayers() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: sortPlayers() no parameters"
					+ " needed, doesn't return anything ");
		}

		boolean bubbleBool = true;
		int temp;
		while (bubbleBool) {
			bubbleBool = false;
			for (int i = 0; i < m_numberOfPlayers - 1; i++) {
				if (m_initialRolls[i] < m_initialRolls[i + 1]) {
					temp = m_initialRolls[i];
					m_initialRolls[i] = m_initialRolls[i + 1];
					m_initialRolls[i + 1] = temp;
					bubbleBool = true;
					Collections.swap(m_playerNames, i, i + 1);
					Collections.swap(m_playerColors, i, i + 1);
				}
			}
		}
	}

	/**
	 * Initialises all the variables and generates the positions of the snakes
	 * and ladders
	 * 
	 * @param playerNames all player names.
	 * @param playerColors colour of each player.
	 * @param snakes number of snakes to be generated.
	 * @param ladders number of ladders to be generated.
	 */
	private void initVariables(ArrayList<String> playerNames,
			ArrayList<Color> playerColors, int snakes, int ladders) {

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: initVariables()");
		}

		/* Adds a player object to the player object array list */
		for (int i = 0; i < playerNames.size(); i++) {
			if (playerNames.get(i).endsWith(".AI")) {
				players.add(new AIPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i)));
			} else {
				players.add(new HumanPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i)));

			}
		}
		
		/* Generates snake positions */
		for (int i = 0; i < snakes; i++) {
			int[] snakeSquares = generateSquares();
			setSnakes(snakeSquares[0]);
			//m_snakesList.add(snakeSquares[0]);
			setMovementSquares(snakeSquares[0]);
			//movementSquares.add(snakeSquares[0]);
			setSnakes(snakeSquares[1]);
			//m_snakesList.add(snakeSquares[1]);

		}

		System.out.println("snakes are at: " + m_snakesList);

		/* Generates ladder positions */
		for (int i = 0; i < ladders; i++) {
			int[] ladderSquares = generateSquares();
			setLadders(ladderSquares[0]);
			//m_laddersList.add(ladderSquares[0]);
			setLadders(ladderSquares[1]);
			//m_laddersList.add(ladderSquares[1]);
			setMovementSquares(ladderSquares[1]);
			//m_movementSquares.add(ladderSquares[1]);
		}
		System.out.println("ladders are at: " + m_laddersList);
		System.out.println("Movement Squares are: " + m_movementSquares);

		m_board = new BoardSnL(BOARD_WIDTH, BOARD_HEIGHT, m_movementSquares,
				m_laddersList, m_snakesList);
	}

	/** Creates the display */
	private void initDisplay() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: initDisplay() no parameters"
					+ " needed, no returns: ");
		}

		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_shapesPanel = new DisplaySnL(this, m_snakesList, m_laddersList,
				players, m_visualize);
		m_shapesPanel.setBackground(Color.WHITE);

		m_shapesPanel.addPlayInterface(m_frame);
		m_shapesPanel.setLayout(null);

		m_frame.add(m_shapesPanel);
		m_frame.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		m_frame.setVisible(true);

	}

	/**
	 * Checks if the player has won and checks if the destination is valid
	 * @param player current player
	 * @return a random number from 1-6
	 */
	private int checkMove(PlayerSnL player) {
		int roll = rollDice();

		if (GameSelector.m_TRACE) {
				System.out.println("GameSnL:: initVariables()");
		}

		final int MAX_VALUE = 100;
		// Winning
		if ((player.getPlayerLocation() + roll) >= MAX_VALUE) {
			player.setPlayerLocation(m_shapesPanel, m_board.getSquare(99),
					false);
			movePlayer(player, player.getPlayerLocation());
			winner(player);
		}

		else {
			// adds current location as last location before moving
			player.setLastLocation(player.getPlayerLocation());
			// checks whether the player has hit a snake/ladder
			boolean move = player.isMovementSquare(m_board.getSquare(player
					.getPlayerLocation() + roll));
			// sets last location to where the player landed with the dice roll
			player.setLastLocation(player.getPlayerLocation()+roll);
			// sets player location to where the player landed with dice roll
			player.setPlayerLocation(m_shapesPanel,
					m_board.getSquare(player.getPlayerLocation()+roll),false);
			// sets new location if move is true
			player.setPlayerLocation(m_shapesPanel,
					m_board.getSquare(player.getPlayerLocation()), move);
			
			System.out.println("Move: " + move);
			if (move) {
				movePlayer(player, m_board.getSquare(player.getPlayerLocation())
						.getDestination());
			}

		}
		return roll;
	}

	/**
	 * Moves the player 
	 * @param player current player
	 * @param destination square player will get moved to
	 */
	private void movePlayer(PlayerSnL player, int destination) {
		boolean move = player.isMovementSquare(m_board.getSquare(destination));
		player.setPlayerLocation(m_shapesPanel, m_board.getSquare(destination),
				move);

		System.out.println("Move: " + move);
		if (move) {
			movePlayer(player, m_board.getSquare(player.getPlayerLocation())
					.getDestination());
		}

		if (GameSelector.m_TRACE) {
				System.out.println("GameSnL:: movePlayer()");
		}
	}

	/**
	 * Displays winner information in a pop-up
	 * @param player current player
	 */
	private void winner(PlayerSnL player) {
		if (GameSelector.m_TRACE) {
				System.out.println("GameSnL:: winner()");
		}
		m_shapesPanel.winner(m_frame);
	}

	/**
	 * Iterates through player turns
	 * @return a random number between 1-6
	 */

	public int buttonPush() {
		int roll = checkMove(players.get(m_playerIterator));
		m_shapesPanel.repaint();

		if (m_playerIterator >= players.size() - 1) {
			m_playerIterator = 0;
		} else {
			m_playerIterator++;
		}

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: winner() " + "no parameters needed"
					+ "returns:" + roll);
		}

		return roll;
	}

	/**
	 * Rolls the dice
	 * @return a random number between 1-6
	 */
	private int rollDice() {
		final int MAX_VALUE = 6;
		final int MIN_VALUE = 1;
		int roll = (int) (Math.random() * (MAX_VALUE - MIN_VALUE) + MIN_VALUE);

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: rollDice() "
					+ "no parameters needed " + "returns:" + roll);
		}
		System.out.println("Rolled " + roll);
		return roll;
	}

	/**
	 * Generates snake and ladder starting and ending positions
	 * @return snake and ladder starting and ending positions
	 */
	private int[] generateSquares() {

		boolean validated = false;
		final int MAX_VALUE = 99;
		final int MIN_VALUE = 10;
		int startSquareNo = 0;
		int endSquareNo = 0;

		while (!validated) {

			startSquareNo = (int) (MIN_VALUE + Math.random()
					* ((MAX_VALUE - MIN_VALUE) + 1));

			endSquareNo = (int) (MIN_VALUE + Math.random()
					* ((MAX_VALUE - MIN_VALUE) + 1));
			if ((m_snakesList.contains(startSquareNo))
					|| (m_laddersList.contains(startSquareNo))
					|| (m_snakesList.contains(endSquareNo))
					|| (m_laddersList.contains(endSquareNo))
					|| (endSquareNo > startSquareNo)
					|| ((startSquareNo + MIN_VALUE) < endSquareNo)) {
				validated = false;
			} else {
				validated = true;
			}
		}
		int[] squares = { startSquareNo, endSquareNo };

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: generateSquares() "
					+ "no parameters " + "needed " + "returns:" + squares);
		}

		return squares;
	}
	
	/** method to dispose the frame */
	public void frameDispose() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: frameDispose() "
					+ "no parameters needed, no returns");
		}
		m_frame.dispose();
	}

	/** 
	 * This method resets the board etc to be able to use the back button 
	 * during game play.
	 */
	public void reset() {
		m_snakesList = new ArrayList<Integer>();
		m_laddersList = new ArrayList<Integer>();
		players = new ArrayList<PlayerSnL>();
		m_movementSquares = new ArrayList<Integer>();
		m_frame = new JFrame("Snakes And Ladders");
		m_playerIterator = 0;
	}

	/**
	 * Test method. Default values are set for names, colours and
	 * number of snakes and ladders.
	 */
	public static void main(String[] args) {

		ArrayList<String> m_names = new ArrayList<String>();
		m_names.add("bob");
		m_names.add("terry");

		ArrayList<Color> m_colors = new ArrayList<Color>();
		m_colors.add(Color.RED);
		m_colors.add(Color.BLUE);

		int SNAKES = 1;
		int LADDERS = 5;
		boolean visualize = false;

		GameSnL gameSnL = new GameSnL(m_names, m_colors, SNAKES, LADDERS, 
				visualize);
		gameSnL.sortPlayers();
		gameSnL.initVariables(m_names, m_colors, SNAKES, LADDERS);
		gameSnL.initDisplay();
		gameSnL.m_playerIterator = 0;
		gameSnL.getNumberOfPlayers();
		gameSnL.getLadders();
		gameSnL.getSnakes();
		gameSnL.getPlayersSize();

		gameSnL.rollDice();
		gameSnL.buttonPush();
		gameSnL.getIterator();
		gameSnL.getPlayerLocation(gameSnL.m_playerIterator);

		gameSnL.getMovementSquares();

		gameSnL.checkMove(players.get(gameSnL.getIterator()));
		gameSnL.movePlayer(
				players.get(gameSnL.getIterator()),
				gameSnL.m_board.getSquare(
						players.get(gameSnL.getIterator()).getPlayerLocation())
						.getDestination());

		getMovementPair(1);
		gameSnL.generateSquares();

		gameSnL.winner(players.get(gameSnL.getIterator()));
	}

}

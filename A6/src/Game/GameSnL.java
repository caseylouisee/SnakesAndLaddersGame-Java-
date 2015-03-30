/**
 * @file GameSnL.java
 * @author Genalyn Estrada 
 * @date 16/02/2015
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

	/************************** VARIABLES *****************************/

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
	private static ArrayList<Integer> movementSquares = new ArrayList<Integer>();
	/** Width of Snakes and Ladders Board */
	private int BOARD_WIDTH = 10;
	/** Height of Snakes and Ladders Board */
	private int BOARD_HEIGHT = 10;
	/** JFrame for displaying the Snakes and Ladders board */
	private JFrame m_frame = new JFrame("Snakes & Ladders");
	/** Panel for player names and positions as well as the dice image */
	private final JPanel m_contentPanel = new JPanel();
	/** Dice image */
	private JLabel m_dicePicLabel = new JLabel(new ImageIcon("null"));
	/** Determines what dice photo to display */
	private BufferedImage m_myPicture = null;
	/** Pop up for initial roll */
	private JFrame m_popUp = new JFrame();
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

	/************************** ACCESSOR METHODS *****************************/

	/**
	 * Returns the number of players
	 * 
	 * @return the number of players
	 * */

	public int getNumberOfPlayers() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getNumberOfPlayers() no parameters"
					+ " needed, returns: " + m_numberOfPlayers);
		}
		return m_numberOfPlayers;
	}

	/**
	 * Returns list of ladders
	 * 
	 * @return list of laddders
	 * */

	public ArrayList<Integer> getLadders() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getLadders() no parameters"
					+ " needed, returns: " + m_laddersList);
		}
		return m_laddersList;
	}

	/**
	 * Returns list of snakes
	 * 
	 * @return list of snakes
	 * */

	public ArrayList<Integer> getSnakes() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getSnakes() no parameters"
					+ " needed, returns: " + m_snakesList);
		}
		return m_snakesList;
	}

	/**
	 * Returns current player's location
	 * 
	 * @param playerIndex
	 *            - index of the current player
	 * @return the current player's location
	 * */

	public int getPlayerLocation(int playerIndex) {
		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: getPlayerLocation() "
						+ "valid parameter:" + playerIndex + "returns: "
						+ players.get(playerIndex).getPlayerLocation());
			}
		}
		return players.get(playerIndex).getPlayerLocation();
	}

	/**
	 * Returns the number of players
	 * 
	 * @return the number of players
	 * */

	public int getPlayersSize() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getPlayersSize() no parameters"
					+ " needed, returns: " + players.size());
		}
		return players.size();
	}

	/**
	 * Returns the current turn
	 * 
	 * @return whose turn it is
	 * */

	public int getIterator() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getIterator() no parameters"
					+ " needed, returns: " + m_playerIterator);
		}
		return m_playerIterator;
	}

	/**
	 * Returns a player name
	 * 
	 * @param playerIndex
	 *            - index of player
	 * @return player name
	 **/

	public PlayerSnL getPlayer(int playerIndex) {
		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: getPlayerLocation() "
						+ "valid parameter:" + playerIndex + "returns: "
						+ players.get(playerIndex).getPlayerLocation());
			}
		}
		return players.get(playerIndex);
	}

	/**
	 * Returns the squares the player has to move to before and after a snake or
	 * a ladder object
	 * 
	 * @return the squares the player has to move to before and after a snake or
	 *         a ladder object
	 * */

	public ArrayList<Integer> getMovementSquares() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: getMovementSquares() no parameters"
					+ " needed, returns: " + movementSquares);
		}
		return movementSquares;
	}

	/**
	 * Generates the squares the player has to move to before and after a snake
	 * or a ladder object
	 * 
	 * @param original
	 *            - player's initial position
	 * @return the squares the player has to move to before and after a snake or
	 *         a ladder object
	 * */

	public static int getMovementPair(int original) {

		if (m_laddersList.contains(original)) {
			int movementPair = m_laddersList.lastIndexOf(original) - 1;
			if (GameSelector.m_TRACE) {
				if (GameSelector.m_VALID) {
					System.out.println("GameSnL:: getMovementPair() "
							+ "valid parameter:" + original + "returns: "
							+ m_laddersList.get(movementPair));
				}
			}
			return m_laddersList.get(movementPair);
		} else if (m_snakesList.contains(original)) {
			int movementPair = m_snakesList.lastIndexOf(original) + 1;
			if (GameSelector.m_TRACE) {
				if (GameSelector.m_VALID) {
					System.out.println("GameSnL:: getMovementPair() "
							+ "valid parameter:" + original + "returns: "
							+ m_snakesList.get(movementPair));
				}
			}
			return m_snakesList.get(movementPair);
		} else {
			System.out.println("error");
			if (GameSelector.m_TRACE) {
				if (!GameSelector.m_VALID) {
					System.out.println("GameSnL:: getMovementPair() "
							+ "invalid parameter:" + original + "returns: "
							+ -1);
				}
			}
			return -1;
		}
	}

	/************************** METHODS *****************************/

	/**
	 * Loads data into game
	 * 
	 * @param playerNames
	 *            stores the player names.
	 * @param playerColors
	 *            shows list of colours.
	 * @param playerPositions
	 *            shows list of player positions.
	 * @param snakes
	 *            adds snakes to various squares.
	 * @param ladders
	 *            adds ladders to various squares.
	 */

	public GameSnL(ArrayList<String> playerNames,
			ArrayList<Color> playerColors, ArrayList<Integer> playerPositions,
			ArrayList<Integer> snakes, ArrayList<Integer> ladders) {

		m_numberOfPlayers = playerNames.size();
		m_playerNames = playerNames;
		m_playerColors = playerColors;

		m_laddersList = ladders;
		System.out.println(" \n\n\n\n" + m_laddersList);
		m_snakesList = snakes;

		for (int i = 0; i < m_snakesList.size() / 2; i++) {
			movementSquares.add(m_snakesList.get(i * 2));
		}

		for (int i = 1; i < m_laddersList.size() / 2; i++) {
			movementSquares.add(m_laddersList.get(i * 2));
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

		m_board = new BoardSnL(BOARD_WIDTH, BOARD_HEIGHT, movementSquares,
				m_laddersList, m_snakesList);
		initDisplay();
	}

	/**
	 * Stores passed data into the game
	 * 
	 * @param playerNames
	 *            - all player names.
	 * @param playerColors
	 *            - colour of each player.
	 * @param snakes
	 *            - number of snakes to be generated.
	 * @param ladders
	 *            - number of ladders to be generated.
	 */

	public GameSnL(final ArrayList<String> playerNames,
			final ArrayList<Color> playerColors, final int snakes,
			final int ladders) {

		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: GameSnL() " + "valid parameters:"
						+ playerNames + " " + playerColors + " " + snakes + " "
						+ ladders + " no returns");
			}
		}

		/** Displays the number of snakes and ladders to the console */

		m_playerNames = playerNames;
		m_playerColors = playerColors;
		m_numberOfPlayers = playerNames.size();
		m_initialRolls = new int[m_numberOfPlayers];

		m_popUp.setBounds(100, 100, 175, 200);
		m_popUp.getContentPane().setLayout(new BorderLayout());
		m_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		m_popUp.getContentPane().add(m_contentPanel, BorderLayout.CENTER);
		m_contentPanel.setLayout(null);

		try {
			m_myPicture = ImageIO.read(new File("res/Dice1.png"));
			m_dicePicLabel.setIcon((Icon) new ImageIcon(m_myPicture));
		} catch (IOException f) {
			f.printStackTrace();
		}
		m_dicePicLabel.setBounds(40, 20, 80, 80);
		m_contentPanel.add(m_dicePicLabel);

		m_txtField = new JTextField();
		m_txtField.setText(playerNames.get(m_playerIterator)
				+ " Roll the dice.");
		m_txtField.setBounds(5, 105, 150, 20);
		m_contentPanel.add(m_txtField);
		m_txtField.setColumns(10);
		m_txtField.setEditable(false);
		m_txtField.setHorizontalAlignment(JTextField.CENTER);

		m_btnContinue = new JButton("Next Player");
		m_btnContinue.setBounds(30, 130, 100, 25);
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
					m_popUp.dispose();
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
					// txtField.setText(playerNames.get(playerIterator)+"
					// Roll the dice.");

					if (m_playerIterator == m_numberOfPlayers) {
						m_btnContinue.setText("Play Game.");
					}
				}
			}
		});
		m_btnRollDice.setBounds(35, 130, 90, 25);
		m_contentPanel.add(m_btnRollDice);

		m_popUp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		m_popUp.setVisible(true);
	}

	/**
	 * Determines who takes which turn
	 * */

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
	 * @param playerNames
	 *            - all player names.
	 * @param playerColors
	 *            - colour of each player.
	 * @param snakes
	 *            - number of snakes to be generated.
	 * @param ladders
	 *            - number of ladders to be generated.
	 * */

	private void initVariables(ArrayList<String> playerNames,
			ArrayList<Color> playerColors, int snakes, int ladders) {

		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: initVariables() "
						+ "valid parameter:" + playerNames + " " + playerNames
						+ " " + playerColors + " " + snakes + " " + ladders
						+ "no returns: ");
			}
		}

		/** Adds a player object to the player object array list */
		for (int i = 0; i < playerNames.size(); i++) {
			if (playerNames.get(i).endsWith(".AI")) {
				players.add(new AIPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i)));
			} else {
				players.add(new HumanPlayerSnL(m_playerNames.get(i),
						m_playerColors.get(i)));

			}
		}
		//
		/** Generates snake positions */
		for (int i = 0; i < snakes; i++) {
			int[] snakeSquares = generateSquares();
			m_snakesList.add(snakeSquares[0]);
			movementSquares.add(snakeSquares[0]);
			m_snakesList.add(snakeSquares[1]);
		}

		System.out.println("snakes are at: " + m_snakesList);

		/** Generates ladder positions */
		for (int i = 0; i < ladders; i++) {
			int[] ladderSquares = generateSquares();
			m_laddersList.add(ladderSquares[0]);
			m_laddersList.add(ladderSquares[1]);
			movementSquares.add(ladderSquares[1]);
		}
		System.out.println("ladders are at: " + m_laddersList);
		System.out.println("Movement Squares are: " + movementSquares);

		m_board = new BoardSnL(BOARD_WIDTH, BOARD_HEIGHT, movementSquares,
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
				players);
		m_shapesPanel.setBackground(Color.WHITE);

		m_shapesPanel.addPlayInterface(m_frame);
		m_shapesPanel.setBounds(70, 600, 200, 40);
		m_shapesPanel.setLayout(null);

		m_frame.add(m_shapesPanel);
		m_frame.setSize(600, 700);
		m_frame.setVisible(true);

	}

	/**
	 * Checks if the player has won and checks if the destination is valid
	 * 
	 * @param player
	 *            - current player
	 * @return a random number from 1-6
	 */

	private int checkMove(PlayerSnL player) {
		int roll = rollDice();

		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: initVariables() "
						+ "valid parameter:" + player + "returns: " + roll);
			}
		}

		final int MAX_VALUE = 99;
		// Winning
		if ((player.getPlayerLocation() + roll) >= MAX_VALUE) {
			winner(player);
		}
		// System.out.println("Player Location will be " +
		// (p.getPlayerLocation() + roll));
		else {

			boolean move = player.isMovementSquare(m_board.getSquare(player
					.getPlayerLocation() + roll));
			player.setPlayerLocation(m_shapesPanel,
					m_board.getSquare(player.getPlayerLocation() + roll), move);
			System.out.println("Move: " + move);
			if (move) {
				movePlayer(player, m_board
						.getSquare(player.getPlayerLocation())
						.getDestination());
			}

		}
		return roll;
	}

	/**
	 * Moves the player
	 * 
	 * @param player
	 *            - current player
	 * @param destination
	 *            - destination square player will get moved to
	 * */

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
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: movePlayer() "
						+ "valid parameter:" + player + " " + destination
						+ "returns: " + move);
			}
		}
	}

	/**
	 * Displays winner information in a pop-up
	 * 
	 * @param player
	 *            - current player
	 * */

	private void winner(PlayerSnL player) {
		if (GameSelector.m_TRACE) {
			if (GameSelector.m_VALID) {
				System.out.println("GameSnL:: winner() " + "valid parameter:"
						+ player + "returns: the winner frame");
			}
		}
		m_shapesPanel.winner(m_frame);
	}

	/**
	 * Iterates through player turns
	 * 
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
	 * 
	 * @return a random number between 1-6
	 * */

	private int rollDice() {
		final int MAX_VALUE = 6;
		final int MIN_VALUE = 1;
		int roll = (int) (Math.random() * (MAX_VALUE - MIN_VALUE) + MIN_VALUE);

		if (GameSelector.m_TRACE) {
			System.out.println("GameSnL:: rollDice() "
					+ "no parameters needed " + "returns:" + roll);
		}

		return roll;
	}

	/**
	 * Generates snake and ladder starting and ending positions
	 * 
	 * @return snake and ladder starting and ending positions
	 **/

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
					|| ((startSquareNo + 10) < endSquareNo)) {
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

	/**
	 * This is the test method. Default values are set for names, colours and
	 * number of snakes and ladders.
	 **/

	public static void main(String[] args) {

		ArrayList<String> m_names = new ArrayList<String>();
		m_names.add("bob");
		m_names.add("terry");

		ArrayList<Color> m_colors = new ArrayList<Color>();
		m_colors.add(Color.RED);
		m_colors.add(Color.BLUE);

		int SNAKES = 1;
		int LADDERS = 5;

		GameSnL gameSnL = new GameSnL(m_names, m_colors, SNAKES, LADDERS);
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

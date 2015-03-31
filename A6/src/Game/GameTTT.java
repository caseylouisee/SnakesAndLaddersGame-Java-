/**
 * @file GameTTT.java
 * @author Casey Denner 
 * @date 23/03/2015
 * @brief Creates some of the user interface for TicTacToe, as well as 
 * contains general information about human and AI players.
 * @see MenuTTT
 */

package Game;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import Board.BoardTTT;
import Display.DisplayTTT;
import Menu.GameSelector;
import Player.AIPlayerTTT;
import Player.HumanPlayerTTT;
import Player.PlayerTTT;

/**
 * @class GameTTT
 * @brief Creates some of the user interface for TicTacToe, as well as contains
 *        general information about human and AI players.
 */

public class GameTTT {

	/************************** VARIABLES *****************************/

	/** Arraylist for all player names */
	private ArrayList<PlayerTTT> m_players;
	/** Determines whose turn it is */
	private int m_turn;
	/** Board object */
	private BoardTTT m_board;
	/** JFrame used for displaying TicTacToe */
	private JFrame m_frame = new JFrame("TTT");
	/** Panel to display everything */
	private DisplayTTT m_shapesPanel;
	/** Holds the count of number of X's and O's **/
	private int[] m_count;

	/************************** ACCESSOR METHODS *****************************/

	/**
	 * Checks if the human player's next move is valid, if it is, add the piece
	 * to the square
	 * 
	 * @param Sqindex
	 *            - is the square the piece will be put to
	 * @return if the move was successful or not
	 * */

	public boolean setMovementHuman(int Sqindex) {
		if (m_board.accessSquare(Sqindex).getValue() != ' ') {
			if (GameSelector.m_TRACE) {
					System.out.println("GameTTT:: setMovementHuman() -"
							+ " Square unavailable");
			}
			return false;
		}

		char piece = getPlayerTurn().isX() ? 'X' : 'O';
		m_board.accessSquare(Sqindex).setValue(piece);

		if (GameSelector.m_TRACE) {
				System.out.println("GameTTT:: setMovementHuman() -"
						+ " Square available");
		}
		return true;
	}

	/**
	 * Checks if the AI player's next move is valid, if it is, add the piece to
	 * the square
	 * 
	 * @param Sqindex
	 *            - is the square the piece will be put to
	 * @return if the move was successful or not
	 * */

	public boolean setMovementAI(int Sqindex) {

		if (m_board.accessSquare(Sqindex).getValue() != ' ') {
			if (GameSelector.m_TRACE) {
					System.out.println("GameTTT:: setMovementHuman() -"
							+ " Square unavailable");
			}
			return false;
		}

		char piece = getPlayerTurn().isX() ? 'X' : 'O';
		m_board.accessSquare(Sqindex).setValue(piece);

		if (GameSelector.m_TRACE) {
				System.out.println("GameTTT:: setMovementHuman() - "
						+ "Square available");
		}
		return true;
	}

	/**
	 * Returns the list of players.
	 * 
	 * @return the list of players
	 */

	public ArrayList<PlayerTTT> getPlayers() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: getPlayers() no parameters"
					+ " needed, returns: " + m_players);
		}
		return m_players;
	}

	/**
	 * Checks if someone has won the game
	 * 
	 * @return if someone has won or not
	 */

	public int winner() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: winner() no parameters"
					+ " needed, returns: " + m_board.detectEndGame());
		}
		return m_board.detectEndGame();
	}

	/**
	 * Determines whose turn it is
	 */

	public void nextTurn() {
		m_turn = m_turn == 1 ? 0 : 1;
		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: winner() no parameters"
					+ " needed, no returns, turn = " + m_turn);
		}
	}

	/**
	 * Returns whose turn it is
	 * 
	 * @return whose turn it is
	 */

	public PlayerTTT getPlayerTurn() {
		if (m_turn == 0) {
			if (GameSelector.m_TRACE) {
				System.out.println("GameTTT:: getPlayerTurn() no parameters"
						+ " needed, returns: " + m_players.get(0));
			}
			return m_players.get(0);
		} else {
			if (GameSelector.m_TRACE) {
				System.out.println("GameTTT:: getPlayerTurn() no parameters"
						+ " needed, returns: " + m_players.get(1));
			}
			return m_players.get(1);
		}
	}

	/**
	 * Returns the position of the winning move, from starting square to ending
	 * square
	 * 
	 * @return the position of the winning move, from starting square to ending
	 *         square
	 */

	public int[] getCoordinatesWinningSquares() {
		if (GameSelector.m_TRACE) {
			System.out
					.println("GameTTT:: getCoordinatesWinningSquares() no "
							+ "parameters" + "needed, returns: "
							+ m_board.getCoordinatesWinningSquares());
		}
		return m_board.getCoordinatesWinningSquares();
	}

	/**
	 * Returns the board object
	 * 
	 * @return the board object
	 * */

	public BoardTTT getBoard() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: getBoard() no parameters"
					+ " needed, returns: " + m_board);
		}
		return m_board;
	}

	/************************** METHODS *****************************/

	/**
	 * Stores passed data into the game and calls all display methods, also
	 * initialises variables
	 * 
	 * @param players2
	 *            - all player names.
	 */

	public GameTTT(ArrayList<PlayerTTT> players2) {
		m_players = players2;
		m_board = new BoardTTT(8, 8);
		m_turn = 0;
		init();

		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: GameTTT() " + "valid paramaters"
					+ players2 + " " + "no returns");

		}
	}

	/**
	 * Loads data into game
	 * 
	 * @param players2
	 *            - all player names.
	 * @param squareValues
	 *            - positions of te pieces
	 */

	public GameTTT(ArrayList<PlayerTTT> players2, char[] squareValues) {
		m_players = players2;
		m_count = new int[2];
		for (int i = 0; i < squareValues.length; i++) {
			if (squareValues[i] == 'X') {
				m_count[0]++;
			} else if (squareValues[i] == 'O') {
				m_count[1]++;
			}
		}
		m_turn = m_count[0] + m_count[1] + 1;
		init();
		m_board = new BoardTTT(8, 8, squareValues, m_shapesPanel);

		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: GameTTT() " + "valid paramaters"
					+ players2 + " " + squareValues.toString() + "no returns");

		}
	}

	/** Closes the frame */

	public void frameDispose() {
		if (GameSelector.m_TRACE) {
			System.out.println("GameTTT:: frameDispose() "
					+ "no parameters needed, no returns");
		}
		m_frame.dispose();
	}

	/** Creates the display */

	private void init() {
		if (m_count != null) {
			m_shapesPanel = new DisplayTTT(this, m_board, m_count);
		} else {
			m_shapesPanel = new DisplayTTT(this, m_board);
		}
		m_shapesPanel.setBackground(Color.BLACK);
		m_shapesPanel.setBounds(10, 10, 435, 640);
		m_shapesPanel.setLayout(null);

		m_frame.add(m_shapesPanel);

		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setSize(470, 650);
		m_frame.setResizable(false);
		m_frame.setLayout(null);
		m_frame.getContentPane().setBackground(Color.BLACK);
		m_frame.setVisible(true);
		m_frame.repaint();
	}

	/**
	 * This is the test method. Default values are set for names and pieces.
	 **/

	public static void main(String[] args) {
		ArrayList<PlayerTTT> players = new ArrayList<PlayerTTT>();

		players.add(new HumanPlayerTTT("bob", 'X'));
		players.add(new AIPlayerTTT("terry.AI", 'O'));

		GameTTT gameTTT = new GameTTT(players);
		gameTTT.init();
		gameTTT.getBoard();
		gameTTT.getCoordinatesWinningSquares();
		gameTTT.getPlayerTurn();
		gameTTT.nextTurn();
		gameTTT.setMovementAI(5);
		gameTTT.setMovementHuman(10);
		
		gameTTT.winner();
		gameTTT.getPlayers();
		gameTTT.frameDispose();
	}

}

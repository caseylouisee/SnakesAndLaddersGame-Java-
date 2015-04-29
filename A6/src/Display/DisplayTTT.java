package Display;
/**
 * @file DisplayTTT.java
 * 
 * @brief This class displays everything for the TTT game
 * 
 * @date 23/02/2015 - 13/03/2015
 * 
 * @author RB2 - A5, Casey Denner - A6
 *  
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.apache.commons.lang3.time.StopWatch;

import Board.Board;
import Game.GameTTT;
import Menu.GameSelector;
import Menu.MenuTTT;
import Player.PlayerTTT;
/**
 * @class DisplayTTT
 * @brief This class displays everything for the TTT game
 *
 */
public class DisplayTTT extends JPanel implements Runnable {

	/* ------ VARIABLES ------ */
	/** Variable to set width of buttons */
	public static final int SHAPE_WIDTH = 50;
	
	/** Variable to set width of buttons */
	public static final int SHAPE_HEIGHT = 50;

	/** Variable to set grid width */
	public static final int GRID_WIDTH = 8;
	
	/** Variable to set grid height */
	public static final int GRID_HEIGHT = 8;
	
	/** integer used in ai movement */
	private static final int MAX_RAND = 63;
	
	/** integer used in ai movement */
	private static final int SURROUND_SQUARES = 8;

	/** JFrame for tic tac toe game **/
	private JFrame m_frame = new JFrame("Tic Tac Toe");

	/** Label to display player turn **/
	private JLabel m_dispTurn = new JLabel();

	/** Label to display player name */
	private JLabel m_dispPlayers = new JLabel();

	/** Label to display count of pieces on board */
	private JLabel m_dispCount = new JLabel();

	/** Array list of Jbuttons to store buttons rendered to the board */
	private ArrayList<JButton> gridSquares = new ArrayList<JButton>();

	/** A numbering for all the squares from 1 to GRID_WIDTH*GRID_HEIGHT */
	private int m_squareRef = 0;

	/** Button to save game */
	private JButton m_saveGameButton = new JButton();

	/** Integer to hold the count of number of X's */
	int m_countO = 0;
	
	/** Integer to hold the count of number of O's */
	int m_countX = 0;

	/** Holds the animated gif for button animation */
	private ImageIcon m_animatedGif = new ImageIcon("res/btnClickedAnim.gif");
	
	/** Holds the idle gif for button animation */
	private ImageIcon m_idleImage = new ImageIcon("res/btnIdleImage.gif");

	/** JButton to go back **/
	private JButton m_goBackButton = new JButton();

	/** Stop watch variable to display timer **/
	private StopWatch m_stopWatch;

	/** JLabel to display timer **/
	private JLabel m_lblTimer = new JLabel("Timer");

	/** Boolean to test if game is running */
	private boolean m_gameRunning = true;

	/** Game object */
	private GameTTT m_game;

	/** Arraylist of the players */
	private ArrayList<PlayerTTT> m_players = new ArrayList<PlayerTTT>();

	/** boolean to ignore human input when ai's turn*/
	private boolean m_ignoreEvents = false;

	/** event handler for human click */
	private GUIEventHandler m_handler = new GUIEventHandler();

	/** boolean to set whether the game is being played or not */
	private boolean m_playing = true;

	/** boolean to detect whether it is the ai's first turn */
	private boolean m_initialPlay = true;

	/** random used for the ai's turn */
	private static final Random RAND = new Random();
	
	/** random int used for ai's turn */
	private int m_Random = RAND.nextInt(63);

	/** array list used for the ai moves */
	private ArrayList<Integer> m_aiMoves = new ArrayList<Integer>();
	
	/** Array list used to hold X values that have been drawn */
	private ArrayList<Integer> Xs = new ArrayList<Integer>();
	
	/** Array list used to hold O values that have been drawn */
	private ArrayList<Integer> Os = new ArrayList<Integer>();

	/** instance of the board */
	private Board m_boardGame;

	/** Boolean to detect visualization */
	private Boolean m_visualize = false;
	

	/** method to set button icon to X or O, used for the load game */
	public void setButtonIcon(int position, char value){
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::setButtonIcon");
		}
		String icon;
		if(value=='X'){
			icon="CROSS";
		}else if(value=='O'){
			icon="NAUGHT";
		}else{
			icon=null;
		}
		if(icon!=null){
			gridSquares.get(position).setIcon
			(new ImageIcon("res/"+icon+".png"));
		}
	}

	/**
	 * Constructor for displayTTT when load game is called.
	 * @param mygame
	 * @param board
	 * @param count
	 */
	public DisplayTTT(GameTTT mygame, Board board, int[] count) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::Constructor called - "
					+ "params(GameTTT, Board, int[] count)");
		}
		m_countX=count[0];
		m_countO=count[1];
		m_game = mygame;
		m_boardGame = board;
		m_players = m_game.getPlayers();
		Border buttonBorder = new LineBorder(Color.RED, 1);
		// begin rendering grid

		for (int j = 0; j < GRID_HEIGHT; j++) { // rows
			for (int i = 0; i < GRID_WIDTH; i++) { // columns
				// create new JButton,
				gridSquares.add(new JButton());
				// set position of buttons to appear in grid formation, with
				// 15px margin on left and top.
				gridSquares.get(m_squareRef).setBounds(SHAPE_WIDTH * i + 
						Display.OFFSET15, SHAPE_HEIGHT * j + Display.OFFSET15, 
						SHAPE_WIDTH, SHAPE_HEIGHT);
				gridSquares.get(m_squareRef).setBorder(buttonBorder);
				gridSquares.get(m_squareRef).setIcon(m_idleImage); // reset buttons
				gridSquares.get(m_squareRef).setBackground(Color.BLACK);
				gridSquares.get(m_squareRef).setPressedIcon(m_animatedGif);
				gridSquares.get(m_squareRef).setHorizontalAlignment(
						SwingConstants.LEFT);
				gridSquares.get(m_squareRef).setVerticalAlignment(
						SwingConstants.TOP);

				gridSquares.get(m_squareRef).addActionListener(m_handler);

				add(gridSquares.get(m_squareRef));
				// add prepared buttons above to frame
				m_squareRef++; // increments squareRef for next square.

			}
		}

		refreshUserUI(m_game.getPlayerTurn().getPlayerName());
		displayTurn();
		addBackButton();
		addSaveButton();
		createTimer();
		playGame();
	}

	/** 
	 * Constructor for the displayTTT 
	 * @param mygame
	 * @param boardGame
	 */
	public DisplayTTT(GameTTT mygame, Board boardGame, Boolean visualization) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::Constructor called - "
					+ "params (GameTTT, Board, Boolean visualization)");
		}
		m_game = mygame;
		m_boardGame = boardGame;
		m_visualize = visualization;
		m_players = m_game.getPlayers();
		// create red border
		Border buttonBorder = new LineBorder(Color.RED, 1);
		// begin rendering grid

		for (int j = 0; j < GRID_HEIGHT; j++) { // rows
			for (int i = 0; i < GRID_WIDTH; i++) { // columns
				// create new JButton,
				gridSquares.add(new JButton());
				// set position of buttons to appear in grid formation, with
				// 15px margin on left and top.
				gridSquares.get(m_squareRef).setBounds(SHAPE_WIDTH * i +
						Display.OFFSET15, SHAPE_HEIGHT * j + Display.OFFSET15,
						SHAPE_WIDTH, SHAPE_HEIGHT);
				gridSquares.get(m_squareRef).setBorder(buttonBorder);
				gridSquares.get(m_squareRef).setIcon(m_idleImage); // reset buttons
				gridSquares.get(m_squareRef).setBackground(Color.BLACK);
				gridSquares.get(m_squareRef).setPressedIcon(m_animatedGif);
				gridSquares.get(m_squareRef).setHorizontalAlignment(
						SwingConstants.LEFT);
				gridSquares.get(m_squareRef).setVerticalAlignment(
						SwingConstants.TOP);

				gridSquares.get(m_squareRef).addActionListener(m_handler);

				add(gridSquares.get(m_squareRef));
				// add prepared buttons above to frame
				m_squareRef++; // increments squareRef for next square.

			}
		}

		refreshUserUI(m_game.getPlayerTurn().getPlayerName());
		displayTurn();
		addBackButton();
		addSaveButton();
		createTimer();
		playGame();
	}

	/** Action listener for the human player click */
	private class GUIEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (m_ignoreEvents == false) {
				doMovementHuman(gridSquares.indexOf(event.getSource()));
				if (m_playing) {
					playGame();
				}
			}
		}
	}

	/** plays the game and delays the AI turn */
	private void playGame() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::playGame - no params");
		}
		if (m_game.getPlayerTurn().getPlayerName().endsWith(".AI")) {
			m_ignoreEvents = true;

			if (m_playing) {
				ActionListener listen = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						aiMovementStrategy();
						playGame();
					}	
				};
				Timer timer = new Timer(1000, listen);
				timer.setRepeats(false);
				timer.start();
			}
		} else {
			m_ignoreEvents = false;
		}
		System.out.println(m_game.getPlayerTurn().getPlayerName());
	}

	/** Contains the strategy for the ai player */
	private void aiMovementStrategy() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::aiMovementStrategy - no params");
		}
		if (m_initialPlay) {
			m_aiMoves.add(m_Random);
			doMovementAI(m_Random);
			System.out.println("Computer move placed at:" + (m_Random));
			m_initialPlay = false;
		} else if (!m_initialPlay) {
			try{
				if ((m_game.getBoard().accessSquare(m_Random + 1).getValue() ==' ')
					&& ((m_Random + 1) <= MAX_RAND) 
					&& (m_Random % SURROUND_SQUARES != (SURROUND_SQUARES-1))){
						doMovementAI(m_Random + 1);
						m_Random = m_Random + 1;
				} else if ((m_game.getBoard().accessSquare(m_Random - 1)
							.getValue() == ' ') && ((m_Random - 1)>=0) 
							&& (m_Random % SURROUND_SQUARES != 0)) {
								doMovementAI(m_Random - 1);
								m_Random = m_Random - 1;
				} else if (((m_Random + (SURROUND_SQUARES-1) )<= MAX_RAND) 
							&&(m_game.getBoard()
							.accessSquare(m_Random + (SURROUND_SQUARES-1))
							.getValue() == ' ')
							&& (m_Random % SURROUND_SQUARES != 0)) {
								doMovementAI(m_Random + (SURROUND_SQUARES-1));
								m_Random = m_Random + (SURROUND_SQUARES-1);
				} else if (((m_Random - (SURROUND_SQUARES-1))>=0) 
							&&(m_game.getBoard()
							.accessSquare(m_Random - (SURROUND_SQUARES-1))
							.getValue() == ' ')
						    &&(m_Random % SURROUND_SQUARES != 
						    (SURROUND_SQUARES-1))){
								doMovementAI(m_Random - (SURROUND_SQUARES-1));
								m_Random = m_Random - (SURROUND_SQUARES-1);
				} else if ((m_game.getBoard()
							.accessSquare(m_Random + SURROUND_SQUARES)
							.getValue() == ' ') && ((m_Random + SURROUND_SQUARES)
							<= MAX_RAND)) {
								doMovementAI(m_Random + SURROUND_SQUARES);
								m_Random = m_Random + SURROUND_SQUARES;
				} else if ((m_game.getBoard()
							.accessSquare(m_Random - SURROUND_SQUARES)
							.getValue() == ' ') 
							&& ((m_Random - SURROUND_SQUARES)>=0)) {
								doMovementAI(m_Random - SURROUND_SQUARES);
								m_Random = m_Random - SURROUND_SQUARES;
				} else if (((m_Random + 4) <= MAX_RAND) &&(m_game.getBoard()
							.accessSquare(m_Random + 4).getValue() == ' ')
							&& m_Random % SURROUND_SQUARES != 5 
							&& m_Random % SURROUND_SQUARES != 6
							&& m_Random % SURROUND_SQUARES != 7 
							&& m_Random % SURROUND_SQUARES != 4) {
								doMovementAI(m_Random + 4);
								m_Random = m_Random + 4;
				} else if (((m_Random - 4)>=0) && (m_game.getBoard()
							.accessSquare(m_Random - 4).getValue() == ' ')
							&& m_Random % SURROUND_SQUARES != 0 
							&& m_Random % SURROUND_SQUARES != 1
							&& m_Random % SURROUND_SQUARES != 2 
							&& m_Random % SURROUND_SQUARES != 3) {
								doMovementAI(m_Random - 4);
								m_Random = m_Random - 4;
				} else if (((m_Random - 28)>=0)&&(m_game.getBoard()
							.accessSquare(m_Random - 28).getValue() == ' ')) {
							doMovementAI(m_Random - 28);
							m_Random = m_Random - 28;
				} else if (((m_Random + 28) <= MAX_RAND) &&(m_game.getBoard()
							.accessSquare(m_Random + 28).getValue() == ' ')) {
							doMovementAI(m_Random + 28);
							m_Random = m_Random + 28;
				} else if (((m_Random - 32)>=0)&&(m_game.getBoard()
							.accessSquare(m_Random - 32).getValue() == ' ')) {
							doMovementAI(m_Random - 32);
							m_Random = m_Random - 32;
				} else if ((m_Random + 32) <= MAX_RAND&&(m_game.getBoard()
							.accessSquare(m_Random + 32).getValue() == ' ')) {
								doMovementAI(m_Random + 32);
								m_Random = m_Random + 32;
				} else {
					for(int i=0;i<=MAX_RAND;i++) {
						if (m_game.getBoard().accessSquare(i).getValue() == ' ') {
								doMovementAI(i);
								m_Random = i;
								break;
						}
					}
				}System.out.println("Computer move placed at:" + (m_Random));
			}catch (ArrayIndexOutOfBoundsException ex){
				while(true) {
					int random=RAND.nextInt(MAX_RAND);
					if (m_game.getBoard().accessSquare(random).getValue() == ' '){
						doMovementAI(random);
						System.out.println("i am random :"+ random);
						m_Random = random;
						break;
					}
				}
			}	
		}
	}


	/** Sets image for any button on the grid dependent on the player moving 
	 * @param squareRef this is the reference of the clicked square 
	 */
	private void doMovementHuman(int squareRef) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::doMovementHuman - "
					+ "params(int squareRef - the square clicked");
		}
		System.out.println("Movement human reached");
		int dialogResponse; // initialises variable to store dialog response
		if (!m_game.setMovementHuman(squareRef))
			return; // do nothing if square is valid move but doesn't trigger a
		// win or a draw or an invalid move.
		if(m_visualize){
			if(GameSelector.m_TRACE){
				System.out.println("DisplayTTT: doMovementHuman "
						+ "- Visualization true");
			}
			Font myFont = new Font("Impact", Font.CENTER_BASELINE, 20);
			gridSquares.get(squareRef).setFont(myFont);

			if (m_game.getPlayerTurn().isX()) { // draws a cross
				gridSquares.get(squareRef).setForeground(Color.WHITE);
				Xs.add(squareRef);
				m_countX++;
				int k = 0;
				for(int j = m_countX; j!=0; j--){
					if(j==1){
						gridSquares.get(Xs.get(k)).setText("X-C");
						gridSquares.get(Xs.get(k)).revalidate();
						k++;
					}else{
						gridSquares.get(Xs.get(k)).setText("X-"+(j-1));
						gridSquares.get(Xs.get(k)).revalidate();
						k++;
					}
				}
				System.out.println("An X has been placed at" + squareRef);	
			} else {
				gridSquares.get(squareRef).setForeground(Color.RED);
				Os.add(squareRef);
				m_countO++;
				int k = 0;
				for(int j = m_countO; j!=0; j--){
					if(j==1){
						gridSquares.get(Os.get(k)).setText("O-C");
						gridSquares.get(Os.get(k)).revalidate();
						k++;
					}else{
						gridSquares.get(Os.get(k)).setText("O-"+(j-1));
						gridSquares.get(Os.get(k)).revalidate();
						k++;
					}
				}
				System.out.println("An O has been placed at" + squareRef);
			}			
		}else{
			if (m_game.getPlayerTurn().isX()) { // draws a cross
				gridSquares.get(squareRef).setIcon(
						new ImageIcon("res/CROSS.png"));
				m_countX++;
				System.out.println("An X has been placed at" + squareRef);
			} else {
				gridSquares.get(squareRef).setIcon(
						new ImageIcon("res/NAUGHT.png"));
				m_countO++;
				System.out.println("An O has been placed at" + squareRef);
			}
		}

		switch (m_game.winner()) {
		case -1: // a draw

			System.out.println("Human Draw reached");
			m_playing = false;
			m_gameRunning = false;
			dialogResponse = JOptionPane.showConfirmDialog(m_frame,
					"Draw! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				m_playing = false;
				new MenuTTT();
				m_game.frameDispose();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);

			}
			break;

		case 1: // Supposedly P1 wins
			m_gameRunning = false;
			m_playing = false;
			System.out.println("Human Case 1 Game won reached");
			int[] coord = m_game.getCoordinatesWinningSquares();
			
			//Use this for animation for win.
			gridSquares.get(coord[0]).setIcon(new ImageIcon("res/WIN.png"));
			gridSquares.get(coord[1]).setIcon(new ImageIcon("res/WIN.png"));
			
			dialogResponse = JOptionPane.showConfirmDialog(m_frame, m_game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				m_playing = false;
				new MenuTTT();
				m_game.frameDispose();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);
			}
			break;

		case 2:

			System.out.println("Human Case 2 Game won reached ");
			m_playing = false;
			m_gameRunning = false;
			int[] coord2 = m_game.getCoordinatesWinningSquares();
			
			//Use this for animation for win.
			gridSquares.get(coord2[0]).setIcon(new ImageIcon("res/WIN.png"));
			gridSquares.get(coord2[1]).setIcon(new ImageIcon("res/WIN.png"));
			
			dialogResponse = JOptionPane.showConfirmDialog(m_frame, m_game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				m_playing = false;
				new MenuTTT();
				m_game.frameDispose();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);

			}
			break;

		default:
			m_game.nextTurn();
			this.refreshUserUI(m_game.getPlayerTurn().getPlayerName());
		}

		System.out.println("End of human reached ");
	}

	/**
	 * This is the method that carries out the random ai movement
	 * @param randomNumber
	 */
	private void doMovementAI(int randomNumber) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::doMovementAI");
		}
		System.out.println("Movement AI reached");

		int dialogResponse;

		if (!m_game.setMovementAI(randomNumber))
			return;

		else {

			if(m_visualize){
				if(GameSelector.m_TRACE){
					System.out.println("DisplayTTT: doMovementHuman "
							+ "- Visualization true");
				}
				Font myFont = new Font("Impact", Font.CENTER_BASELINE, 20);
				gridSquares.get(randomNumber).setFont(myFont);
				if (m_game.getPlayerTurn().isX()) { // draws a cross
					gridSquares.get(randomNumber).setForeground(Color.WHITE);
					Xs.add(randomNumber);
					m_countX++;
					int k = 0;
					for(int j = m_countX; j!=0; j--){
						if(j==1){
							gridSquares.get(Xs.get(k)).setText("X-C");
							gridSquares.get(Xs.get(k)).revalidate();
							k++;
						}else{
							gridSquares.get(Xs.get(k)).setText("X-"+(j-1));
							gridSquares.get(Xs.get(k)).revalidate();
							k++;
						}
					}
					System.out.println("An X has been placed at" +randomNumber);	
				} else {
					gridSquares.get(randomNumber).setForeground(Color.RED);
					Os.add(randomNumber);
					m_countO++;
					int k = 0;
					for(int j = m_countO; j!=0; j--){
						if(j==1){
							gridSquares.get(Os.get(k)).setText("O-C");
							gridSquares.get(Os.get(k)).revalidate();
							k++;
						}else{
							gridSquares.get(Os.get(k)).setText("O-"+(j-1));
							gridSquares.get(Os.get(k)).revalidate();
							k++;
						}
					}
					System.out.println("An O has been placed at" +randomNumber);
				}
			}else{
				if (m_game.getPlayerTurn().isX()) { // draws a cross

					gridSquares.get(randomNumber).setIcon(
							new ImageIcon("res/CROSS.png"));
					m_countX++;
				} else {
					gridSquares.get(randomNumber).setIcon(
							new ImageIcon("res/NAUGHT.png"));
					m_countO++;
				}
			}
		}
		
		switch (m_game.winner()) {
		
		case -1: // a draw
			System.out.println("AI Draw reached");
			m_gameRunning = false;
			dialogResponse = JOptionPane.showConfirmDialog(m_frame,
					"Draw! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				m_playing = false;
				new MenuTTT();
				m_game.frameDispose();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);

			}
			break;

		case 1: // a player won
			m_playing = false;
			m_gameRunning = false;
			System.out.println("AI Case 1 Game won reached");

			dialogResponse = JOptionPane.showConfirmDialog(m_frame, m_game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				m_playing = false;
				m_game.frameDispose();
				new MenuTTT();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);

			}
			break;

		case 2:
			System.out.println("AI Case 2 Game won reached ");
			m_playing = false;
			m_gameRunning = false;

			dialogResponse = JOptionPane.showConfirmDialog(m_frame, m_game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again

				m_playing = false;
				m_game.frameDispose();
				new MenuTTT();
			} else {
				m_playing = false;
				m_game.frameDispose();
				System.exit(0);

			}

			break;

		default:
			// iterates to next turn
			m_game.nextTurn();
			// updates turn label
			this.refreshUserUI(m_game.getPlayerTurn().getPlayerName());
		}
		System.out.println("End of AI reached ");
	}

	/**
	 * Calls the method to display the timer
	 * @param graphics
	 **/
	public void paintComponent(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::paintComponent - params(Graphics");
		}
		super.paintComponent(graphics);
		displayTimer();
		repaint();
	}

	/**
	 * Creates the stop watch timer
	 */
	private void createTimer() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::createTimer - no params");
		}
		if (m_stopWatch == null) {

			m_lblTimer.setBounds(Display.XPOS_COL200-Display.OFFSET20,
					Display.YPOS_ROW550, Display.COMPONENT_WIDTH150, 
					Display.COMPONENT_HEIGHT40);
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
	private void displayTimer() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::displayTimer - no params");
		}
		m_lblTimer.setText(m_stopWatch.toString());
	}

	/** adds the Back button to screen setting bounds etc */
	private void addBackButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::addBackButton - no params");
		}
		m_goBackButton.setIcon(new ImageIcon("res/BACKBTN.png"));
		m_goBackButton.setBorderPainted(false); 
		m_goBackButton.setFocusPainted(false);
		m_goBackButton.setContentAreaFilled(false);
		m_goBackButton.setBounds(0, Display.YPOS_ROW500, 
				Display.COMPONENT_WIDTH160, Display.COMPONENT_HEIGHT85);
		m_goBackButton.setVisible(true);
		m_goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_game.frameDispose();
				new GameSelector(GameSelector.m_TRACE); 
			}
		});
		add(m_goBackButton);
	}

	/** adds the saveButton to the frame */
	private void addSaveButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::addSaveButton - no params");
		}
		m_saveGameButton.setIcon(new ImageIcon("res/SAVEBTN.png"));
		m_saveGameButton.setBorderPainted(false); 
		m_saveGameButton.setFocusPainted(false);
		m_saveGameButton.setContentAreaFilled(false);
		m_saveGameButton.setBounds(Display.XPOS_COL250+Display.OFFSET20, 
				Display.YPOS_ROW500, Display.COMPONENT_WIDTH160,
				Display.COMPONENT_HEIGHT85);
		m_saveGameButton.setVisible(true);
		m_saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Game saved");
				saveGame();
			}
		});
		add(m_saveGameButton);
	}

	/** method to save the game to the file res/saveTTT.csv */
	private void saveGame(){
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::saveGame - no params");
		}
		/*
		 * Format:
		 * <player 1 name>,<player 1 piece>,<player 2 name>,<player 2 piece>
		 * <char value as pos 0>,<char value as pos 1>,..,<char value at pos 63>
		 */

		try{
			Files.deleteIfExists(Paths.get("res/saveTTT.csv"));
			
			FileWriter writer = new FileWriter("res/saveTTT.csv");

			writer.append(m_players.get(0).getPlayerName()+",");
			writer.append(m_players.get(0).getPlayerPieceType()+",");

			writer.append(m_players.get(1).getPlayerName()+",");
			writer.append(m_players.get(1).getPlayerPieceType()+"\n");
			
			writer.append(m_boardGame.accessSquare(0).getValue());
			
			for(int i = 1;i < GRID_WIDTH*GRID_HEIGHT;i++){
				writer.append(","+m_boardGame.accessSquare(i).getValue());
			}

			writer.flush();
			writer.close();
		
		}catch(IOException e){
			e.printStackTrace();
		} 
	}

	/** 
	 * displays the turn of the player, the names of the players playing, and
	 * the player's position
	 */
	private void displayTurn() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::displayTurn - no params");
		}
		// sets colours and bounds for turn and list of players labels.
		m_dispTurn.setForeground(Color.WHITE);
		m_dispTurn.setBounds(Display.OFFSET10, 
				Display.YPOS_ROW450-Display.OFFSET20, 
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		m_dispPlayers.setForeground(Color.WHITE);
		m_dispPlayers.setBounds(Display.OFFSET10, Display.YPOS_ROW450,
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		m_dispPlayers.setText("Players playing: "
				+ m_game.getPlayers().get(0).getPlayerName() + ","
				+ m_game.getPlayers().get(1).getPlayerName());
		m_dispCount.setForeground(Color.WHITE);
		m_dispCount.setBounds(Display.OFFSET10, 
				Display.YPOS_ROW450 + Display.OFFSET20, 
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		m_dispCount.setText(""); // blank for initialisation

		add(m_dispPlayers);
		add(m_dispTurn);
		add(m_dispCount);
	}

	/** Updates the players turn */
	private void refreshUserUI(String currentTurn) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::refreshUserUI "
					+ "- params(String current turn");
		}
		m_dispTurn.setText(currentTurn + ", Take your turn!");
		m_dispCount.setText("Number of X's = " + m_countX + " Number of O's = "
				+ m_countO);
	}

	/** override of the run method to paint components while playing game */
	@Override
	public void run() {
		while (m_gameRunning) {
			paintComponents(getGraphics());
		}
	}

}
package Display;

/**
 * @file DisplayTTT.java
 * 
 * @brief This class displays everything for the TTT game
 * 
 * @date 23/02/2015 - 13/03/2015
 * 
 * @author RB2
 *  
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1964065502848062227L;
	/* ------ CONSTANTS ------ */
	/** Variables to set size of buttons **/
	public final int SHAPE_WIDTH = 50;
	public final int SHAPE_HEIGHT = 50;

	/** Variables to set size of grid **/
	public static final int GRID_WIDTH = 8;
	public static final int GRID_HEIGHT = 8;

	/* ------ VARIABLES ------ */
	/** JFrame for tic tac toe game **/
	private JFrame frame = new JFrame("Tic Tac Toe");

	/** Label to display player turn **/
	private JLabel dispTurn = new JLabel();

	/** Label to display player name **/
	private JLabel dispPlayers = new JLabel();

	/** Label to display count of pieces on board **/
	private JLabel dispCount = new JLabel();

	/** Array list of Jbuttons to store buttons rendered to the board **/
	private ArrayList<JButton> gridSquares = new ArrayList<JButton>();

	/** A numbering for all the squares from 1 to GRID_WIDTH*GRID_HEIGHT **/
	private int squareRef = 0;

	private JButton m_saveGameButton = new JButton();

	/** Integers to hold the count of number of X's and O's **/
	int m_countO = 0;
	int m_countX = 0;

	/** Holds the gif for button animation */
	private ImageIcon animatedGif = new ImageIcon("res/btnClickedAnim.gif");
	private ImageIcon idleImage = new ImageIcon("res/btnIdleImage.gif");

	/** JButton to go back **/
	private JButton goBackButton = new JButton();

	/** Stop watch variable to display timer **/
	private StopWatch m_stopWatch;

	/** JLabel to display timer **/
	private JLabel m_lblTimer = new JLabel("Timer");

	/** Boolean to test if game is running */
	private boolean gameRunning = true;

	/** Game object */
	private GameTTT game;

	/** Arraylist of the players */
	private ArrayList<PlayerTTT> m_players = new ArrayList<PlayerTTT>();

	/** boolean to ignore human input when ai's turn*/
	private boolean ignoreEvents = false;

	/** event handler for human click */
	private GUIEventHandler handler = new GUIEventHandler();

	/** boolean to set whether the game is being played or not */
	private boolean playing = true;

	/** boolean to detect whether it is the ai's first turn */
	private boolean initialPlay = true;

	/** random used for the ai's turn */
	private final Random RAND = new Random();
	private int RANDOM = RAND.nextInt(63);

	/** array list used for the ai moves */
	private ArrayList<Integer> m_aiMoves = new ArrayList<Integer>();

	/** instance of the board */
	private Board m_boardGame;

	/** Boolean to detect visualization */
	private Boolean m_visualize;

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

	/* ------ METHODS ------ */
	/**
	 * Constructor for displayTTT when load game is called.
	 * @param mygame
	 * @param board
	 * @param count
	 */
	public DisplayTTT(GameTTT mygame, Board board, int[] count) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::Constructor called");
		}
		m_countX=count[0];
		m_countO=count[1];
		game = mygame;
		m_boardGame = board;
		m_players = game.getPlayers();
		// create red border
		Border buttonBorder = new LineBorder(Color.RED, 1);
		// begin rendering grid

		for (int j = 0; j < GRID_HEIGHT; j++) { // rows
			for (int i = 0; i < GRID_WIDTH; i++) { // columns
				// create new JButton,
				gridSquares.add(new JButton());
				// set position of buttons to appear in grid formation, with
				// 15px margin on left and top.
				gridSquares.get(squareRef).setBounds(SHAPE_WIDTH * i + 
						Display.OFFSET2, SHAPE_HEIGHT * j + Display.OFFSET2, 
						SHAPE_WIDTH, SHAPE_HEIGHT);
				gridSquares.get(squareRef).setBorder(buttonBorder);
				gridSquares.get(squareRef).setIcon(idleImage); // reset buttons
				gridSquares.get(squareRef).setBackground(Color.BLACK);
				gridSquares.get(squareRef).setPressedIcon(animatedGif);
				gridSquares.get(squareRef).setHorizontalAlignment(
						SwingConstants.LEFT);
				gridSquares.get(squareRef).setVerticalAlignment(
						SwingConstants.TOP);

				gridSquares.get(squareRef).addActionListener(handler);

				add(gridSquares.get(squareRef));
				// add prepared buttons above to frame
				squareRef++; // increments squareRef for next square.

			}
		}

		refreshUserUI(game.getPlayerTurn().getPlayerName());
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
			System.out.println("DisplayTTT::Constructor called");
		}
		game = mygame;
		m_boardGame = boardGame;
		m_visualize = visualization;
		m_players = game.getPlayers();
		// create red border
		Border buttonBorder = new LineBorder(Color.RED, 1);
		// begin rendering grid

		for (int j = 0; j < GRID_HEIGHT; j++) { // rows
			for (int i = 0; i < GRID_WIDTH; i++) { // columns
				// create new JButton,
				gridSquares.add(new JButton());
				// set position of buttons to appear in grid formation, with
				// 15px margin on left and top.
				gridSquares.get(squareRef).setBounds(SHAPE_WIDTH * i +
						Display.OFFSET2, SHAPE_HEIGHT * j + Display.OFFSET2,
						SHAPE_WIDTH, SHAPE_HEIGHT);
				gridSquares.get(squareRef).setBorder(buttonBorder);
				gridSquares.get(squareRef).setIcon(idleImage); // reset buttons
				gridSquares.get(squareRef).setBackground(Color.BLACK);
				gridSquares.get(squareRef).setPressedIcon(animatedGif);
				gridSquares.get(squareRef).setHorizontalAlignment(
						SwingConstants.LEFT);
				gridSquares.get(squareRef).setVerticalAlignment(
						SwingConstants.TOP);

				gridSquares.get(squareRef).addActionListener(handler);

				add(gridSquares.get(squareRef));
				// add prepared buttons above to frame
				squareRef++; // increments squareRef for next square.

			}
		}

		refreshUserUI(game.getPlayerTurn().getPlayerName());
		displayTurn();
		addBackButton();
		addSaveButton();
		createTimer();
		playGame();
	}

	/** Action listener for the human player click */
	private class GUIEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (ignoreEvents == false) {
				doMovementHuman(gridSquares.indexOf(event.getSource()));
				if (playing) {
					playGame();
				}
			}

		}
	}

	/** plays the game and delays the AI turn */
	private void playGame() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::playGame");
		}
		if (game.getPlayerTurn().getPlayerName().endsWith(".AI")) {
			ignoreEvents = true;

			if (playing) {
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
			ignoreEvents = false;
		}
		System.out.println(game.getPlayerTurn().getPlayerName());
	}

	/** Contains the strategy for the ai player */
	private void aiMovementStrategy() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::aiMovementStrategy");
		}
		if (initialPlay) {
			m_aiMoves.add(RANDOM);
			doMovementAI(RANDOM);
			System.out.println("Computer move placed at:" + (RANDOM));
			initialPlay = false;
		} else if (!initialPlay) {
			try{
				if ((game.getBoard().accessSquare(RANDOM + 1).getValue() == ' ')
						&& ((RANDOM + 1) <= 63) && (RANDOM % 8 != 7)) {
					doMovementAI(RANDOM + 1);
					RANDOM = RANDOM + 1;
				} else if ((game.getBoard().accessSquare(RANDOM - 1)
						.getValue() == ' ') && ((RANDOM - 1)>=0) 
						&& (RANDOM % 8 != 0)) {
					doMovementAI(RANDOM - 1);
					RANDOM = RANDOM - 1;
				} else if (((RANDOM + 7 )<= 63) &&(game.getBoard()
						.accessSquare(RANDOM + 7).getValue() == ' ')
						&&  (RANDOM % 8 != 0)) {
					doMovementAI(RANDOM + 7);
					RANDOM = RANDOM + 7;
				} else if (((RANDOM - 7)>=0) &&(game.getBoard()
						.accessSquare(RANDOM - 7).getValue() == ' ')
						&&  (RANDOM % 8 != 7)) {
					doMovementAI(RANDOM - 7);
					RANDOM = RANDOM - 7;
				} else if ((game.getBoard().accessSquare(RANDOM + 8)
						.getValue() == ' ') && ((RANDOM + 8) <= 63)) {
					doMovementAI(RANDOM + 8);
					RANDOM = RANDOM + 8;
				} else if ((game.getBoard().accessSquare(RANDOM - 8)
						.getValue() == ' ') && ((RANDOM - 8)>=0)) {
					doMovementAI(RANDOM - 8);
					RANDOM = RANDOM - 8;
				} else if (((RANDOM + 4) <= 63) &&(game.getBoard()
						.accessSquare(RANDOM + 4) .getValue() == ' ')
						&& RANDOM % 8 != 5 && RANDOM % 8 != 6
						&& RANDOM % 8 != 7 && RANDOM % 8 != 4) {
					doMovementAI(RANDOM + 4);
					RANDOM = RANDOM + 4;
				} else if (((RANDOM - 4)>=0) && (game.getBoard()
						.accessSquare(RANDOM - 4).getValue() == ' ')
						&& RANDOM % 8 != 0 && RANDOM % 8 != 1
						&& RANDOM % 8 != 2 && RANDOM % 8 != 3) {
					doMovementAI(RANDOM - 4);
					RANDOM = RANDOM - 4;
				} else if (((RANDOM - 28)>=0)&&(game.getBoard()
						.accessSquare(RANDOM - 28).getValue() == ' ')) {
					doMovementAI(RANDOM - 28);
					RANDOM = RANDOM - 28;
				} else if (((RANDOM + 28) <= 63) &&(game.getBoard()
						.accessSquare(RANDOM + 28).getValue() == ' ')) {
					doMovementAI(RANDOM + 28);
					RANDOM = RANDOM + 28;
				} else if (((RANDOM - 32)>=0)&&(game.getBoard()
						.accessSquare(RANDOM - 32).getValue() == ' ')) {
					doMovementAI(RANDOM - 32);
					RANDOM = RANDOM - 32;
				} else if ((RANDOM + 32) <= 63&&(game.getBoard()
						.accessSquare(RANDOM + 32).getValue() == ' ')) {
					doMovementAI(RANDOM + 32);
					RANDOM = RANDOM + 32;
				} else {
					for(int i=0;i<=63;i++) {
						if (game.getBoard().accessSquare(i).getValue() == ' ') {
							doMovementAI(i);
							RANDOM = i;
							break;
						}
					}
				}System.out.println("Computer move placed at:" + (RANDOM));
			}catch (ArrayIndexOutOfBoundsException ex){
				while(true) {
					int random=RAND.nextInt(63);
					if (game.getBoard().accessSquare(random).getValue() == ' '){
						doMovementAI(random);
						System.out.println("i am random :"+ random);
						RANDOM = random;
						break;
					}
				}
			}	
		}
	}


	/** Sets image for any button on the grid dependent on the player moving **/
	private void doMovementHuman(int squareRef) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::doMovementHuman");
		}
		System.out.println("Movement human reached");

		int dialogResponse; // initialises variable to store dialog response
		if (!game.setMovementHuman(squareRef))
			return; // do nothing if square is valid move but doesn't trigger a
		// win or a draw or an invalid move.
		ArrayList<Integer> Xs = new ArrayList<Integer>();
		if(m_visualize){
			if (game.getPlayerTurn().isX()) { // draws a cross
				m_countX++;
				//gridSquares.get(squareRef).setText("X"+m_countX);
				gridSquares.get(squareRef).setForeground(Color.WHITE);
				
				Xs.add(squareRef);
				
				for(int i = 0; i<Xs.size(); i++){
					gridSquares.get(Xs.get(i)).setText("X-"+m_countX);
				}
				
				System.out.println("An X has been placed at" + squareRef);
			} else {
				m_countO++;
				gridSquares.get(squareRef).setText("O"+m_countO);
				gridSquares.get(squareRef).setForeground(Color.WHITE);
				System.out.println("An O has been placed at" + squareRef);
			}
		}else{
			if (game.getPlayerTurn().isX()) { // draws a cross
				gridSquares.get(squareRef).setIcon(new ImageIcon("res/CROSS.png"));
				m_countX++;
				System.out.println("An X has been placed at" + squareRef);
			} else {
				gridSquares.get(squareRef).setIcon(new ImageIcon("res/NAUGHT.png"));
				m_countO++;
				System.out.println("An O has been placed at" + squareRef);
			}
		}

		switch (game.winner()) {
		case -1: // a draw

			System.out.println("Human Draw reached");
			playing = false;
			gameRunning = false;
			dialogResponse = JOptionPane.showConfirmDialog(frame,
					"Draw! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				new MenuTTT();
				game.frameDispose();
			}
			break;

		case 1: // Supposedly P1 wins
			gameRunning = false;
			playing = false;
			System.out.println("Human Case 1 Game won reached");
			int[] coord = game.getCoordinatesWinningSquares();
			dialogResponse = JOptionPane.showConfirmDialog(frame, game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				playing = false;
				new MenuTTT();
				game.frameDispose();
			} else {
				playing = false;
				game.frameDispose();
				System.exit(0);
			}
			break;

		case 2:

			System.out.println("Human Case 2 Game won reached ");
			playing = false;
			gameRunning = false;
			int[] coord2 = game.getCoordinatesWinningSquares();
			dialogResponse = JOptionPane.showConfirmDialog(frame, game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				playing = false;
				new MenuTTT();
				game.frameDispose();
			} else {
				playing = false;
				game.frameDispose();
				System.exit(0);

			}
			break;

		default:
			game.nextTurn();
			this.refreshUserUI(game.getPlayerTurn().getPlayerName());
		}

		System.out.println("End of human reached ");
	}

	private void doMovementAI(int randomNumber) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::doMovementAI");
		}
		System.out.println("Movement AI reached");

		int dialogResponse;

		if (!game.setMovementAI(randomNumber))
			return;

		else {

			if (game.getPlayerTurn().isX()) { // draws a cross
				gridSquares.get(randomNumber).setIcon(
						new ImageIcon("res/CROSS.png"));
				m_countX++;
			} else {
				gridSquares.get(randomNumber).setIcon(
						new ImageIcon("res/NAUGHT.png"));
				m_countO++;
			}
		}

		switch (game.winner()) {
		case -1: // a draw

			System.out.println("AI Draw reached");
			gameRunning = false;
			dialogResponse = JOptionPane.showConfirmDialog(frame,
					"Draw! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again
				playing = false;
				new MenuTTT();
				game.frameDispose();
			} else {
				playing = false;
				game.frameDispose();
				System.exit(0);

			}
			break;

		case 1: // a player won

			playing = false;
			gameRunning = false;
			System.out.println("AI Case 1 Game won reached");
			int[] coord = game.getCoordinatesWinningSquares();
			//JOptionPane.showMessageDialog(null, coord[0] + " - " + coord[1]);
			dialogResponse = JOptionPane.showConfirmDialog(frame, game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again

				playing = false;
				game.frameDispose();
				new MenuTTT();
			} else {
				playing = false;
				game.frameDispose();
				System.exit(0);

			}
			break;

		case 2:

			System.out.println("AI Case 2 Game won reached ");
			playing = false;
			gameRunning = false;
			int[] coord2 = game.getCoordinatesWinningSquares();
			dialogResponse = JOptionPane.showConfirmDialog(frame, game
					.getPlayerTurn().getPlayerName()
					+ " won!! Would you like to play again?", "TTT",
					JOptionPane.YES_NO_OPTION);
			if (dialogResponse == 0) {
				// play again

				playing = false;
				game.frameDispose();
				new MenuTTT();
			} else {
				playing = false;
				game.frameDispose();
				System.exit(0);

			}

			break;

		default:
			// iterates to next turn
			game.nextTurn();

			// updates turn label
			this.refreshUserUI(game.getPlayerTurn().getPlayerName());

		}

		System.out.println("End of AI reached ");
	}

	/**
	 * Calls the method to display the timer
	 * 
	 * @param graphics
	 **/
	public void paintComponent(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::paintComponent");
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
			System.out.println("DisplayTTT::createTimer");
		}
		if (m_stopWatch == null) {

			m_lblTimer.setBounds(Display.XPOS_COL200-Display.OFFSET4, Display.YPOS_ROW550,
					Display.COMPONENT_WIDTH150, Display.COMPONENT_HEIGHT40);
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
			System.out.println("DisplayTTT::displayTimer");
		}
		m_lblTimer.setText(m_stopWatch.toString());
	}

	/** adds the Back button to screen setting bounds etc */
	private void addBackButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::addBackButton");
		}
		// add back button
		goBackButton.setIcon(new ImageIcon("res/BACKBTN.png"));
		goBackButton.setBorderPainted(false); // this stuff hides button chrome
		goBackButton.setFocusPainted(false);
		goBackButton.setContentAreaFilled(false);
		goBackButton.setBounds(0, Display.YPOS_ROW500, 
				Display.COMPONENT_WIDTH160, Display.COMPONENT_HEIGHT85);
		goBackButton.setVisible(true);
		// listen for clicks
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.frameDispose();
				new GameSelector(GameSelector.m_TRACE); 
				// create new gameselector
			}
		});
		add(goBackButton);
	}

	/** adds the saveButton to the frame */
	private void addSaveButton() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::addSaveButton");
		}
		m_saveGameButton.setIcon(new ImageIcon("res/SAVEBTN.png"));
		// this stuff hides button chrome
		m_saveGameButton.setBorderPainted(false); 
		m_saveGameButton.setFocusPainted(false);
		m_saveGameButton.setContentAreaFilled(false);
		m_saveGameButton.setBounds(Display.XPOS_COL250+Display.OFFSET4, 
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
			System.out.println("DisplayTTT::saveGame");
		}
		/*
		 * Format:
		 * <player 1 name>,<player 1 piece>,<player 2 name>,<player 2 piece>
		 * <char value as pos 0>,<char value as pos 1>,..,<char value at pos 63>
		 */

		try{
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
		}catch(IOException e)
		{
			e.printStackTrace();
		} 
	}

	/** displays the turn of the player, the names of the players playing, and
	 * the player's position
	 */
	private void displayTurn() {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::displayTurn");
		}
		// sets colours and bounds for turn and list of players labels.
		dispTurn.setForeground(Color.WHITE);
		dispTurn.setBounds(Display.OFFSET1, Display.YPOS_ROW450-Display.OFFSET4, 
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		dispPlayers.setForeground(Color.WHITE);
		dispPlayers.setBounds(Display.OFFSET1, Display.YPOS_ROW450,
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		dispPlayers.setText("Players playing: "
				+ game.getPlayers().get(0).getPlayerName() + ","
				+ game.getPlayers().get(1).getPlayerName());
		dispCount.setForeground(Color.WHITE);
		dispCount.setBounds(Display.OFFSET1, 
				Display.YPOS_ROW450 + Display.OFFSET4, 
				Display.COMPONENT_WIDTH350, Display.COMPONENT_HEIGHT20);
		dispCount.setText(""); // blank for initialisation

		add(dispPlayers);
		add(dispTurn);
		add(dispCount);
	}

	/** Updates the players turn */
	private void refreshUserUI(String currentTurn) {
		if(GameSelector.m_TRACE){
			System.out.println("DisplayTTT::refreshUserUI");
		}
		dispTurn.setText(currentTurn + ", Take your turn!");
		dispCount.setText("Number of X's = " + m_countX + " Number of O's = "
				+ m_countO);
	}

	/** override of the run method to paint components while playing game */
	@Override
	public void run() {
		while (gameRunning) {
			paintComponents(getGraphics());
		}
	}

}
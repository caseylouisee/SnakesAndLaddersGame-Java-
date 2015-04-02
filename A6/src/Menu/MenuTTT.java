package Menu;
/**
 * @file MenuTTT.java
 * 
 * @brief A generic class containing the display methods. 
 * This menu class is for the game TTT. The menu allows you to select whether 
 * you want to be X or O, as well as having their respective names
 * 
 * @date 29/03/2015
 * 
 * @author Casey Denner
 *  
 */


import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Display.Display;
import Display.DisplayTTT;
import Game.GameTTT;
import Player.AIPlayerTTT;
import Player.HumanPlayerTTT;
import Player.PlayerTTT;
/**
 * @class MenuTTT
 * @brief A generic class containing the display methods. 
 * This menu class is for the game TTT. The menu allows you to select whether 
 * you want to be X or O, as well as having their respective names
 */
public class MenuTTT {

	/* ----- CONSTANTS ------- */
	/** Title of the window */
	private final String WINDOW_TITLE = "Dans Crazy Tic Tac Toe";

	/** Width of the window */
	private final int WINDOW_WIDTH = 500;

	/** Height of the window */
	private final int WINDOW_HEIGHT = 370;

	/** Array to hold values X and O */
	private final String[] TTT_X_OR_O = { " ", "X", "O" };

	/** Array to hold player types */
	private final String[] HUMAN_OR_AI = {"Human", "AI"};

	/** Maximum number of players */
	private final int MAX_NUM_PLAYERS = 2;

	/* ----- VARIABLES ------- */
	/** Frame for the tic tac toe menu */
	private JFrame m_frame;

	/** Array list to hold the player's name text fields */
	private ArrayList<JTextField> m_playersNameTextField;

	/** Array list to hold player piece options (X or O)*/
	private ArrayList<JComboBox<String>> m_playersXorO;

	/** Array list to hold player type */
	private ArrayList<JComboBox<String>> m_playerTypeComboBox;

	/** Array list to hold the players name label */
	private ArrayList<JLabel> m_namePlayersLabel;

	/** Button to initialise game */
	private JButton m_initGameButton;

	/** Check box for visualization */
	private JCheckBox visualization;

	/** Boolean for visualization */
	private Boolean visualize = false;

	/* -------- METHODS --------- */

	/** This is the test method. 
	 * It calls the menuTTT constructor to add all the correct buttons, 
	 * all methods are voids so it's impossible to input invalid information
	 * All methods are called, if no load game file available it will try to 
	 * start the game with no player information throwing an error.
	 */
	public static void main(String[] args) { 
		MenuTTT test = new MenuTTT();
		test.addLabels();
		test.addPlayerType();
		test.addXorOComboBox();
		test.addPlayersNamesTextField();
		test.addLogo();
		test.addGameButtons();
		test.loadGame();
		test.sendForm();
	}

	/** This is the constructor for the Tic Tac Toe menu screen.
	 * It sets the frame size and adds all the objects.
	 **/
	public MenuTTT() {

		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::ConstructorCalled");
		}

		m_frame = new JFrame(WINDOW_TITLE);
		m_frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		m_frame.setLayout(null);
		m_frame.setResizable(false);
		m_frame.getContentPane().setBackground(Color.BLACK);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addLogo();
		addLabels();
		addXorOComboBox();
		addPlayerType();
		addPlayersNamesTextField();
		addVisualizationCheckBox();
		addGameButtons();
		m_frame.setVisible(true);

	}

	/** Adds the labels to the frame **/
	private void addLabels() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addLabels");
		}
		m_namePlayersLabel = new ArrayList<JLabel>();
		for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
			m_namePlayersLabel.add(new JLabel("Name " + (i + 1) + ": ",
					SwingConstants.RIGHT));
			m_namePlayersLabel.get(i).setBounds(Display.XPOS_COL50, 
					Display.YPOS_ROW150 + Display.OFFSET3 * i, 
					Display.COMPONENT_WIDTH100, Display.COMPONENT_HEIGHT20);
			m_namePlayersLabel.get(i).setForeground(Color.WHITE);
			m_namePlayersLabel.get(i).setVisible(true);
			m_frame.add(m_namePlayersLabel.get(i));
		}
	}

	/** Adds the combo box to set the player type to the frame**/
	private void addPlayerType(){
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addPlayerType");
		}
		m_playerTypeComboBox = new ArrayList<JComboBox<String>>();
		for(int i = 0; i < MAX_NUM_PLAYERS; i++){
			m_playerTypeComboBox.add(new JComboBox<String>(HUMAN_OR_AI));
			m_playerTypeComboBox.get(i).setBounds(Display.XPOS_COL400, 
					Display.YPOS_ROW150 + Display.OFFSET3 * i,
					Display.COMPONENT_WIDTH100, Display.COMPONENT_HEIGHT20);
			m_playerTypeComboBox.get(i).setVisible(true);
			m_frame.add(m_playerTypeComboBox.get(i));	
		}
	}

	/** Adds the combo box with the options X or O to the frame **/
	private void addXorOComboBox() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addXorOComboBox");
		}
		m_playersXorO = new ArrayList<JComboBox<String>>();
		for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
			m_playersXorO.add(new JComboBox<String>(TTT_X_OR_O));
			m_playersXorO.get(i).setBounds(Display.XPOS_COL300, 
					Display.YPOS_ROW150 + Display.OFFSET3 * i, 
					Display.COMPONENT_WIDTH100, Display.COMPONENT_HEIGHT20);
			m_playersXorO.get(i).setVisible(true);
			m_frame.add(m_playersXorO.get(i));
		}
		m_playersXorO.get(0).addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if (event.getItem().toString() == "O")
						m_playersXorO.get(1).setSelectedItem("X");
					else
						m_playersXorO.get(1).setSelectedItem("O");
				}
			}
		});
		m_playersXorO.get(1).addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if (event.getItem().toString() == "O")
						m_playersXorO.get(0).setSelectedItem("X");
					else
						m_playersXorO.get(0).setSelectedItem("O");
				}
			}
		});
	}

	/** Adds the player name text field to the frame **/
	private void addPlayersNamesTextField() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addPlayersNamesTextField");
		}
		m_playersNameTextField = new ArrayList<JTextField>();
		for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
			m_playersNameTextField.add(new JTextField());
			m_playersNameTextField.get(i).setText("Player " + (i + 1));
			m_playersNameTextField.get(i).setVisible(true);
			m_playersNameTextField.get(i).setBounds(Display.XPOS_COL150, 
					Display.YPOS_ROW150 + Display.OFFSET3 * i,
					Display.COMPONENT_WIDTH150, Display.COMPONENT_HEIGHT20);
			m_frame.add(m_playersNameTextField.get(i));
		}
	}

	/** Adds the visualization check box to the frame. */
	private void addVisualizationCheckBox(){
		if(GameSelector.m_TRACE){
			System.out.println("MenuSnL :: addVisualizationCheckBox");
		}
		visualization = new JCheckBox("Visualization?");
		visualization.setBounds(Display.XPOS_COL200, Display.YPOS_ROW200+Display.OFFSET4,
				Display.COMPONENT_WIDTH150, Display.COMPONENT_HEIGHT20);
		visualization.setText("Visualization?");
		visualization.setForeground(Color.WHITE);
		visualization.setVisible(true);

		visualization.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				Object source =  event.getItemSelectable();
				if (source == visualization) {
					visualize = true;
					System.out.println("MenuSnL :: addVisualizationCheckBox - Selected");
				}
				if (event.getStateChange() == ItemEvent.DESELECTED){
					visualize = false;
					System.out.println("MenuSnL :: addVisualizationCheckBox - Deselected");
				}
			}
		});

		m_frame.add(visualization);
	}

	/** Adds the logo to the frame **/
	private void addLogo() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addLogo");
		}
		JButton m_logoButton = new JButton();
		m_logoButton.setIcon(new ImageIcon("res/DCTTTLOGO.png"));
		m_logoButton.setBorderPainted(false);
		m_logoButton.setFocusPainted(false);
		m_logoButton.setContentAreaFilled(false);
		m_logoButton.setBounds(0, 0, WINDOW_WIDTH, 
				Display.COMPONENT_HEIGHT100);
		m_logoButton.setVisible(true);
		m_frame.add(m_logoButton);
	}

	/** Adds the back and start game button to the frame **/
	private void addGameButtons() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::addGameButtons");
		}
		JButton m_goBackButton = new JButton();
		m_goBackButton.setIcon(new ImageIcon("res/BACKBTN.png"));
		m_goBackButton.setBorderPainted(false);
		m_goBackButton.setFocusPainted(false);
		m_goBackButton.setContentAreaFilled(false);
		m_goBackButton.setBounds(Display.OFFSET1, Display.YPOS_ROW250, 
				Display.COMPONENT_WIDTH160, Display.COMPONENT_HEIGHT85);
		m_goBackButton.setVisible(true);
		m_goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_frame.dispose();
				new GameSelector(GameSelector.m_TRACE);
			}
		});

		m_initGameButton = new JButton();
		m_initGameButton.setIcon(new ImageIcon(("res/STARTBTN.png")));
		m_initGameButton.setBorderPainted(false);
		m_initGameButton.setFocusPainted(false);
		m_initGameButton.setContentAreaFilled(false);
		m_initGameButton.setBounds(Display.XPOS_COL150+Display.OFFSET5, 
				Display.YPOS_ROW250, Display.COMPONENT_WIDTH170, 
				Display.COMPONENT_HEIGHT85);
		m_initGameButton.setVisible(true);
		m_initGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendForm();
			}
		});


		JButton loadGame = new JButton();
		loadGame.setText("Load Game");
		loadGame.setIcon(new ImageIcon(("res/LOADBTN.png")));
		loadGame.setBorderPainted(false);
		loadGame.setFocusPainted(false);
		loadGame.setContentAreaFilled(false);
		loadGame.setBounds(Display.XPOS_COL350+Display.OFFSET1, Display.YPOS_ROW250,
				Display.COMPONENT_WIDTH130, Display.COMPONENT_HEIGHT85);
		loadGame.setVisible(true);
		loadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadGame();
			}
		});

		m_frame.add(loadGame);
		m_frame.add(m_initGameButton);
		m_frame.add(m_goBackButton);
	}

	/** Loads the saved game from the file res/saveTTT.csv */
	private void loadGame(){
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::loadGame");
		}
		String csvFile = "res/saveTTT.csv";
		BufferedReader br = null;
		String line="";

		String[] playerNames = new String[MAX_NUM_PLAYERS];
		char[] piece = new char[MAX_NUM_PLAYERS];
		char[] squareValues = new 
				char[DisplayTTT.GRID_HEIGHT * DisplayTTT.GRID_WIDTH];
		try{
			br = new BufferedReader(new FileReader(csvFile));
			String[] columns = 
					new String[DisplayTTT.GRID_HEIGHT * DisplayTTT.GRID_WIDTH];

			try {
				line = br.readLine();

				if(line==null){
					JOptionPane.showMessageDialog(null, "No save game data found");
				}else{
					//br.reset();
					//line = br.readLine();

					columns = line.split(",");

					playerNames[0] = columns[0];
					piece[0] = columns[1].charAt(0);
					playerNames[1] = columns[2];
					piece[1] = columns[3].charAt(0);

					line = br.readLine();
					columns = line.split(",");
					for(int i=0;i<DisplayTTT.GRID_HEIGHT * DisplayTTT.GRID_WIDTH;i++){
						squareValues[i] = columns[i].charAt(0);
					}
					ArrayList<PlayerTTT> players = new ArrayList<PlayerTTT>();
					br.close();
					if(playerNames[0].contains(".ai")){
						AIPlayerTTT p1 = 
								new AIPlayerTTT(playerNames[0],piece[0]);
						players.add(p1);
					}else{
						HumanPlayerTTT p1 = 
								new HumanPlayerTTT(playerNames[0],piece[0]);
						players.add(p1);
					}

					if(playerNames[1].contains(".ai")){
						AIPlayerTTT p2 = 
								new AIPlayerTTT(playerNames[1],piece[1]);
						players.add(p2);
					}else{
						HumanPlayerTTT p2 = 
								new HumanPlayerTTT(playerNames[1],piece[1]);
						players.add(p2);
					}
					br.close();
					new GameTTT(players,squareValues);
					m_frame.dispose();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			JOptionPane.showMessageDialog(null, "No save game data found");
		}finally{
			System.out.println("");
		}		
	}


	/** Sends all the player details (name, piece) to GameTTT */
	private void sendForm() {
		if(GameSelector.m_TRACE){
			System.out.println("MenuTTT::sendForm");
		}
		String name1 = m_playersNameTextField.get(0).getText();
		String name2 = m_playersNameTextField.get(1).getText();

		char piece1 = m_playersXorO.get(0).getSelectedItem() == 
				"X" ? 'X' : 'O';
		char piece2 = m_playersXorO.get(1).getSelectedItem() == 
				"X" ? 'X' : 'O';

		String pattern = "([ ]*+[0-9A-Za-z]++[ ]*+)+";

		if (name1.isEmpty() | name2.isEmpty() | 
				!name1.matches(pattern) | !name2.matches(pattern)){
			if(GameSelector.m_TRACE){
				System.out.println("MenuTTT::sendForm - name not valid");
			}
			JOptionPane.showMessageDialog(null,
					"Ensure both players have valid names: Letters,"
							+ " spaces and numbers are accepted");
		} else {
			System.out.println(piece1);
			if (piece1 == piece2) {
				if(GameSelector.m_TRACE){
					System.out.println("MenuTTT::sendForm - pieces the same");
				}
				JOptionPane.showMessageDialog(null,
						"Ensure both players have Pieces selected");
			} else {
				// success!
				String message = "";
				message += "Player " + m_playersNameTextField.get(0)
						.getText() + " is " + m_playersXorO.get(0)
						.getSelectedItem() + "\n";
				message += "Player " + m_playersNameTextField.get(1)
						.getText() + " is " + m_playersXorO.get(1)
						.getSelectedItem() + "\n";
				JOptionPane.showMessageDialog(null, message, "Form Data",
						JOptionPane.INFORMATION_MESSAGE);

				ArrayList<PlayerTTT> players = 
						new ArrayList<PlayerTTT>();

				if (m_playerTypeComboBox.get(0).getSelectedItem()
						.equals("Human")){	
					players.add(new HumanPlayerTTT(name1, piece1));
				}
				else{
					players.add(new AIPlayerTTT(name1+".AI",piece1));
				}

				if (m_playerTypeComboBox.get(1).getSelectedItem()
						.equals("Human")){	
					players.add(new HumanPlayerTTT(name2, piece2));
				}
				else{
					players.add(new AIPlayerTTT(name2+".AI",piece2));
				}

				m_frame.dispose();
				new GameTTT(players, visualize);
				System.out.println(visualize);
				if(GameSelector.m_TRACE){
					System.out.println("MenuTTT::sendForm - new GameTTT");
				}
			}
		}

	}
}

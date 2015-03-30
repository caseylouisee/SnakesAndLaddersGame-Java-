/**
 * @file GameSelector.java
 * @author Casey Denner 
 * @date 29/03/2015
 * @brief Select between games
 * 
 * Creates the starting menu depending on game choice
 * Also creates game specific settings such as the design of the menu, and
 *  the ability to choose either SnL or TTT.
 */

package Menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @class GameSelector
 * @brief Creates the starting menu depending on game choice
 * Also creates game specific settings such as the design of the menu, and
 * the ability to choose either SnL or TTT.
 */

public class GameSelector {
	/** Title of the window */
	private final static String WINDOW_TITLE = "GAME SELECTOR";
	/** Width of the window */
	private final static int WINDOW_WIDTH = 730;
	/** Length of the window */
	private final static int WINDOW_HEIGHT = 450;
	/** Testing variable */
	public static Boolean m_TRACE = true;
	/** If true inputs valid params to main methods, false = invalid params */
	public static Boolean m_VALID;
	/** Frame being used */
	private static JFrame m_frame;

	
	public GameSelector(boolean testing){
		m_TRACE = testing;
		createWindow();
		createUI();
	}
	
	/** Creates and displays interface to select between games */
	private static void createWindow() {
		if(GameSelector.m_TRACE){
			System.out.println("GameSelector::createWindow called method, "
					+ "no parameters needed") ;
		}
		m_frame = new JFrame(WINDOW_TITLE);
		m_frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		m_frame.getContentPane().setLayout(null);
		m_frame.setResizable(false);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.getContentPane().setBackground(Color.BLACK);
		JLabel title = new JLabel("Choose a game to begin!",
				SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setBounds(0, 20, WINDOW_WIDTH, 50);
		m_frame.getContentPane().add(title);
	}

	/** Handles what happens when a game is chosen */
	private static void createUI() {
		if(GameSelector.m_TRACE){
			System.out.println("GameSelector::createUI called method, "
					+ "no parameters needed") ;
		}
		JButton SnLButton = new JButton();
		SnLButton.setIcon(new ImageIcon("res/SNLIMG.jpg"));
		SnLButton.setBorderPainted(false);
		SnLButton.setFocusPainted(false);
		SnLButton.setContentAreaFilled(false);
		SnLButton.setBounds(80, 100, 236, 242);
		SnLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_frame.dispose();
				new MenuSnL();
			}
		});

		JLabel snakesAndLadders = new JLabel("Snakes and Ladders");
		snakesAndLadders.setForeground(Color.WHITE);
		snakesAndLadders.setBounds(115, 350, WINDOW_WIDTH, 50);
		snakesAndLadders.setForeground(Color.WHITE);

		JButton TTTButton = new JButton();
		TTTButton.setIcon(new ImageIcon("res/TTTIMG.jpg"));
		TTTButton.setBorderPainted(false);
		TTTButton.setFocusPainted(false);
		TTTButton.setContentAreaFilled(false);
		TTTButton.setBounds(400, 100, 236, 242);
		TTTButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				m_frame.dispose();
				new MenuTTT();
			}
		});

		JLabel ticTacToe = new JLabel("Dans Crazy Tic Tac Toe");
		ticTacToe.setForeground(Color.WHITE);
		ticTacToe.setBounds(450, 350, WINDOW_WIDTH, 50);
		ticTacToe.setForeground(Color.WHITE);
		m_frame.getContentPane().add(snakesAndLadders);
		m_frame.getContentPane().add(ticTacToe);
		m_frame.getContentPane().add(SnLButton);
		m_frame.getContentPane().add(TTTButton);
		m_frame.setVisible(true);
	}

	/**
	 * Calls methods to create and display the user interface, as well as
	 * handles what happens when a game is chosen
	 * 
	 * Unit tests are done here too
	 */
	public static void main(String[] args) {
		createWindow();
		createUI();
	}

}

/**
 * @file Board.java
 * 
 * @brief Board superclass for generating the board, this class contains all of
 * the generic information concerning board classes, such as initialising
 * the grids and detecting the end-game.
 *         
 * @author Casey Denner
 * 
 * @date 29/03/2015
 */
package Board;

import Menu.GameSelector;
import Square.Square;

/**
 * @class Board
 * @brief superclass for generating the board, this class contains all of
 * 	the generic information concerning board classes, such as initialising
 * the grids and detecting the end-game.
 *
 */
public abstract class Board {

	/**The array that stores all the squares used on the board*/
	protected Square[] m_boardGame;
	
	/** Stores the width of the board */
	protected int m_boardWidth;
	
	/** Stores the height of the board */
	protected int m_boardHeight;

	/**
	 * gets the width of the board
	 * @return int containing the board width
	 */
	public int getBoardWidth(){
		if(GameSelector.m_TRACE){
			System.out.println("Board::getBoardWidth() no parameters, "+
					" returns "+m_boardWidth);
		}
		return m_boardWidth;
	}

	/**
	 * gets the Heighth of the board
	 * @return int containing the board Height
	 */
	public int getBoardHeight(){
		if(GameSelector.m_TRACE){
			System.out.println("Board::getBoardHeight() no parameters, "+
					" returns "+m_boardHeight);
		}
		return m_boardWidth;
	}

	/**
	 * Constructor for creating a new board
	 * @param width int containing the width of the board to be created
	 * @param height int containing the height of the board to be created
	 */
	public Board(int width, int height) {
		if(GameSelector.m_TRACE){
			System.out.println("Board::Board() valid parameters: "+width+" "+
					height+" doesn't return anything");
		}
		m_boardWidth = width;
		m_boardHeight = height;
		m_boardGame = new Square[width * height];
	}

	/**
	 * Gives access to a specific square on the board, used for TTT
	 * @param x int containing the x position to access
	 * @param y int containing the y position to access
	 * @return Square type containing the information
	 *  on the square at coordinates (x,y)
	 */
	public Square accessSquare(int x, int y) {
		if(GameSelector.m_TRACE){
			System.out.println("Board::accessSquare() valid parameters: "+x+
					" "+y+" returns "+m_boardGame[x * m_boardWidth + y]);
		}
		return m_boardGame[x * m_boardWidth + y];
	}

	/**
	 * Gives access to a specific square on the board, used in SnL
	 * @param i int containing the position to access
	 * @return Square type containing the information
	 *  on the square at position i
	 */
	public Square accessSquare(int i) {
		if(GameSelector.m_TRACE){
			System.out.println("Board::accessSquare() valid parameters: "+i+
					" returns "+m_boardGame[i]);
		}
		return m_boardGame[i];
	}

	/**
	 * Used to detect when the end of the game has been reached,
	 * Implementation can be found in child classes.
	 * @return int depending on what type of win has been reached
	 */
	public abstract int detectEndGame();

}
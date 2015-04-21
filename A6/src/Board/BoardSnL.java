/**
 * @file BoardSnL.java
 * @see Board.java
 * 
 * @brief the board class for SnL which is a subclass of board, it contains
 *         specific methods for the SnL game, such as checking if there is a
 *         player on the 100th square
 *         
 * @author Casey Denner
 * 
 * @date 29/03/2015
 */
package Board;

import java.util.ArrayList;

import Menu.GameSelector;
import Square.*;
import Game.*;

/**
 * @class BoardSnL
 * @brief the board class for SnL which is a subclass of board, it contains
 *         specific methods for the SnL game, such as checking if there is a
 *         player on the 100th square
 *
 */
public class BoardSnL extends Board {

	/**The array that stores all the squares used on the board*/
	private SquareSnL[] m_boardGame;
	
	/**An integer storing the final square on the board*/
	private final int FINAL_SQUARE = 99;
	
	/**integer storing return value for the method detectEndGame*/
	private final int WIN = 1;
	
	/**integer storing return value for the method detectEndGame*/
	private final int LOSS = 0;

	/**
	 * Gives access to a specific square on the board
	 * @param i int containing the position to access
	 * @return SquareSnL pointer to the square located at position i
	 */
	public SquareSnL getSquare(int i) {
		if(GameSelector.m_TRACE){
			System.out.println("BoardSnL::getSquare() valid parameters: "+i+
					" returns "+m_boardGame[i]);
		}
		return m_boardGame[i];
	}

	/**
	 * Constructor for creating the new board, creates new Squares to fill the 
	 * 		board, the number of which is width*height
	 * @param width int containing the width of the board to be created
	 * @param height int containing the height of the board to be created
	 * @param movementSquares ArrayList of integers containing the position of
	 *  	the ladder bottoms and tail tops
	 *  	(the squares where addition movement occurs)
	 * @param laddersList ArrayList of integers containing the positions of
	 * 		both the tops and bottoms of ladders.
	 * @param snakesList ArrayList of integers containing the positions of
	 * 		both the tops and bottoms of snakes.
	 */
	public BoardSnL(int width, int height, ArrayList<Integer> movementSquares,
			ArrayList<Integer> laddersList, ArrayList<Integer> snakesList) {
		super(width, height);
		if(GameSelector.m_TRACE){
			System.out.println("BoardSnL::BoardSnL() valid parameters: "+width+
					" "+height+" "+movementSquares+" "+laddersList+" "+
					snakesList+" doesn't return anything");
		}
		initializeGrid(width, height);
		for (int i = 0; i < width * height; i++) {
			if (movementSquares.contains(i)) {

				m_boardGame[i] = new SquareSnL(i, GameSnL.getMovementPair(i));

				if(GameSelector.m_TRACE){
					System.out.println("Movement pair: "+i+
							" moves to " + GameSnL.getMovementPair(i));
				}
			} else {
				m_boardGame[i] = new SquareSnL(i, i);
			}
		}
	}
	
	/**
	 * Method that initialises the SnL grid with squares ready for their values 
	 * 		to be set
	 * @param width integer containing the width of board to set up
	 * @param height integer containing the height of board to set up
	 */
	protected void initializeGrid(int width, int height) {
		if(GameSelector.m_TRACE){
			System.out.println("BoardSnL::initializeGrid() valid parameters: "
					+width+" "+height+" doesn't return anything");
		}
		m_boardWidth = width;
		m_boardHeight = height;
		m_boardGame = new SquareSnL[width * height];
	}
	
	/**
	 * Method for detecting whether the end game has been reached
	 * @return int containing 1 if win detected 0 if not.
	 */
	public int detectEndGame() {
		if (((SquareSnL) accessSquare(FINAL_SQUARE)).existsPlayer()){
			if(GameSelector.m_TRACE){
				System.out.println("BoardSnL::detectEndGame() no parameters "
						+" returns "+WIN);
			}
			return WIN;
		}else{
			if(GameSelector.m_TRACE){
				System.out.println("BoardSnL::detectEndGame() no parameters "
						+" returns "+LOSS);
			}
			return LOSS;
		}
	}
	
	/** Test method */
	public static void main(String[] args){
		int width = 10;
		int height = 10;
		int randomPositionSL1 = 11;
		int randomPositionSL2 = 45;
		int randomPositionSL3 = 30;
		int randomPositionSL4 = 24;
		int randomPositionSL5 = 80;
		int randomPositionSL6 = 15;
		ArrayList<Integer> movementSquares = new ArrayList<>();
		movementSquares.add(randomPositionSL1);
		movementSquares.add(randomPositionSL2);
		movementSquares.add(randomPositionSL3);
		ArrayList<Integer> laddersList = new ArrayList<>();
		laddersList.add(randomPositionSL1);
		laddersList.add(randomPositionSL4);
		laddersList.add(randomPositionSL2);
		laddersList.add(randomPositionSL5);
		ArrayList<Integer> snakesList = new ArrayList<>();
		snakesList.add(randomPositionSL3);
		snakesList.add(randomPositionSL6);
		
		BoardSnL boardsnl = new BoardSnL(width, height, movementSquares,
				laddersList, snakesList);
		
		System.out.println("End game = "+boardsnl.detectEndGame());
		
	}

}
/**
 * @file BoardTTT.java
 * @see Board.java
 * 
 * @brief Specific board class for TTT game
 * board class for TTT which is a subclass of board, this subclass contains
 * methods specific to TTT, such as detecting the win scenario by working out
 * adjacent squares to the players current square.
 * 
 * @author Casey Denner
 * 
 * @date 29/03/2015
 */
package Board;

import Display.DisplayTTT;
import Menu.GameSelector;
import Square.SquareTTT;

/**
 * @class BoardTTT
 * @brief Specific board class for TTT game
 * board class for TTT which is a subclass of board, this subclass contains
 * methods specific to TTT, such as detecting the win scenario by working out
 * adjacent squares to the players current square.
 *
 */
public class BoardTTT extends Board {

	/** Integer array used to store the start and end of the winning chain for 
	 * output
	 */
	private int[] m_winningSquareCoordinates = new int[2];
	/** integer that stores the length of the chain needed to win*/
	private final int WIN_AMOUNT = 5;

	/** integers that store the return values for detectEndGame()*/
	public static final int DRAW_GAME = -1;
	public static final int NOTHING_HAPPENED = 0;
	public static final int O_WIN = 1;
	public static final int X_WIN = 2;
	


	/**
	 * Used to return the start and end of the winning chain for output.
	 * @return integer array containing the start and end of the winning chain
	 */
	public int[] getCoordinatesWinningSquares() {
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::getCoordinatesWinningSquares()"
					+ " no parameters "+" returns "+m_winningSquareCoordinates);
		}
		return m_winningSquareCoordinates;
	}

	/**
	 * Constructor for creating a board of length width*height 
	 * @param width integer that contains the width of the board to be created
	 * @param height integer that contains the height of the board to be 
	 * 		created
	 */
	public BoardTTT(int width, int height) {
		super(width, height);
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::BoardTTT() valid parameters: "+width+
					" "+height+" doesn't return anything");
		}
		for (int i = 0; i < width * height; i++) {
			m_boardGame[i] = new SquareTTT(i);
		}
	}


	public BoardTTT(int width, int height, char[] squareValues,
			DisplayTTT display) {
		super(width, height);
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::BoardTTT() valid parameters: "+width+
					" "+height+" "+squareValues.toString()+" "+display+
					"doesn't return anything");
		}
		for (int i = 0; i < width * height; i++) {
			m_boardGame[i] = new SquareTTT(i, squareValues[i],display);
		}
	}

	/**
	 * First checks for a draw game by looping though every square and checking
	 * if there are any empty squares, if there is not then the game is a draw.
	 * It then calls the win() method on both X and O 
	 */
	public int detectEndGame() {
		Boolean draw = true;
		for (int i = 0; i < m_boardHeight; i++)
			for (int j = 0; j < m_boardWidth; j++)
				if (accessSquare(i, j).getValue() == ' ') {
					draw = false;
					break;
				}

		if (draw){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::detectEndGame() no parameters"+
						" returns "+DRAW_GAME);
			}
			return DRAW_GAME;
		}else if (win('X')){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::detectEndGame() no parameters"+
						" returns "+X_WIN);
			}
			return X_WIN;
		}else if (win('O')){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::detectEndGame() no parameters"+
						" returns "+O_WIN);
			}
			return O_WIN;
		}else{
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::detectEndGame() no parameters"+
						" returns "+NOTHING_HAPPENED);
			}
			return NOTHING_HAPPENED;
		}
	}

	/**
	 * Checks (horizontally, vertically and then diagonally) if there are
	 * five Xs or Os (depending on the input) in a row.
	 * @param XorO char containing X or O depending on what type of piece to 
	 * 		check win for
	 * @return true of false depending on if a win is found.
	 */
	private Boolean win(char XorO) {
		if(winRow(XorO)){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::win() no parameters"+
						" returns "+true);
			}
			return true;
		}else if(winColumn(XorO)){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::win() no parameters"+
						" returns "+true);
			}
			return true;
		}else if(winDiagonalRight(XorO)){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::win() no parameters"+
						" returns "+true);
			}
			return true;
		}else if(winDiagonalLeft(XorO)){
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::win() no parameters"+
						" returns "+true);
			}
			return true;
		}else{
			if(GameSelector.m_TRACE){
				System.out.println("BoardTTT::win() no parameters"+
						" returns "+false);
			}
			return false;
		}

	}

	/**
	 * Checks if there is a horizontal win 
	 * @param XorO the char it is checking for
	 * @return true if win is found, false if not
	 */
	private Boolean winRow(char XorO){
		
		for (int i = 0; i < DisplayTTT.GRID_WIDTH; i++) {
			int m_counter = 0;
			for (int j = 0; j < DisplayTTT.GRID_HEIGHT; j++) {
				m_counter = 0;
				if (accessSquare(i, j).getValue() == XorO) {
					int temp = j;
					for (; j < DisplayTTT.GRID_WIDTH; j++) {
						if (accessSquare(i, j).getValue() == XorO)
							m_counter++;
						else
							break;
					}
					if (m_counter >= WIN_AMOUNT) {
						m_winningSquareCoordinates[0] = i * m_boardWidth + temp;
						m_winningSquareCoordinates[1] = i * m_boardWidth + j;
						if(GameSelector.m_TRACE){
							System.out.println("BoardTTT::winRow()"
									+ " valid parameters:"+XorO+
									" returns "+true);
						}
						return true;
					}
				}
			}
		}
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::winRow() valid parameters:"+XorO+
					" returns "+false);
		}
		return false;
	}
	/**
	 * Checks if there is a vertical win 
	 * @param XorO the char it is checking for
	 * @return true if win is found, false if not
	 */
	private Boolean winColumn(char XorO){
		for (int j = 0; j < DisplayTTT.GRID_WIDTH; j++) {
			int m_counter = 0;
			for (int i = 0; i < DisplayTTT.GRID_WIDTH; i++) {
				m_counter = 0;
				if (accessSquare(i, j).getValue() == XorO) {
					int temp = i;
					for (; i < DisplayTTT.GRID_HEIGHT; i++) {
						if (accessSquare(i, j).getValue() == XorO)
							m_counter++;
						else
							break;
					}
					if (m_counter >= WIN_AMOUNT) {
						m_winningSquareCoordinates[0] = temp * m_boardWidth + j;
						m_winningSquareCoordinates[1] = i * m_boardWidth + j;
						if(GameSelector.m_TRACE){
							System.out.println("BoardTTT::winColumn()"
									+ " valid parameters:"+XorO+
									" returns "+true);
						}
						return true;
					}
				}
			}
		}
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::winColumn()"
					+ " valid parameters:"+XorO+
					" returns "+false);
		}
		return false;
	}

	/**
	 * Checks if there is a diagonal right win 
	 * @param XorO the char it is checking for
	 * @return true if win is found, false if not
	 */
	private Boolean winDiagonalRight(char XorO){
		// Check win by diagonal
		for (int j = 0; j < DisplayTTT.GRID_WIDTH; j++) {
			int m_counter = 0;
			for (int i = 0; i < DisplayTTT.GRID_WIDTH; i++) {
				m_counter = 0;

				try {
					if (accessSquare(i, j).getValue() == XorO) {
						int temp = i;
						for (; j < DisplayTTT.GRID_HEIGHT; j++) {

							if (accessSquare(i, j).getValue() == XorO) {
								m_counter++;
								i--;
							} else
								break;
						}

						if (m_counter >= WIN_AMOUNT) {
							m_winningSquareCoordinates[0] =
									temp * m_boardWidth + j;

							m_winningSquareCoordinates[1] =
									i * m_boardWidth + j;
							if(GameSelector.m_TRACE){
								System.out.println("BoardTTT::"
										+ "winDiagonalRight()"
										+ " valid parameters:"+XorO+
										" returns "+true);
							}
							return true;
						}
					}
				} catch (Exception e) {
					break;
				}
			}
		}
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::winDiagonalRight()"
					+ " valid parameters:"+XorO+
					" returns "+false);
		}
		return false;
	}

	/**
	 * Checks if there is a diagonal left win 
	 * @param XorO the char it is checking for
	 * @return true if win is found, false if not
	 */
	private Boolean winDiagonalLeft(char XorO){

		for (int j = 0; j < DisplayTTT.GRID_WIDTH; j++) {
			int m_counter = 0;
			for (int i = 0; i < DisplayTTT.GRID_WIDTH; i++) {
				m_counter = 0;

				try {
					if (accessSquare(i, j).getValue() == XorO) {
						int temp = i;
						for (; j < DisplayTTT.GRID_HEIGHT; j++) {

							if (accessSquare(i, j).getValue() == XorO) {
								m_counter++;
								i++;
							} else
								break;
						}

						if (m_counter >= WIN_AMOUNT) {
							m_winningSquareCoordinates[0] = 
									temp * m_boardWidth + j;

							m_winningSquareCoordinates[1] = 
									i * m_boardWidth + j;
							if(GameSelector.m_TRACE){
								System.out.println("BoardTTT::winDiagonalLeft()"
										+ " valid parameters:"+XorO+
										" returns "+true);
							}
							return true;
						}
					}
				} catch (Exception e) {
					break;
				}
			}
		}
		if(GameSelector.m_TRACE){
			System.out.println("BoardTTT::windiagonalLeft()"
					+ " valid parameters:"+XorO+
					" returns "+false);
		}
		return false;
	}
	
	public static void main(String[] args){
		int width = 8;
		int height = 8;
		
		BoardTTT boardttt = new BoardTTT(width, height);
		int win = boardttt.detectEndGame();
		String winString = null;
		if(win == DRAW_GAME){
			winString = "DRAW";
		}else if (win == NOTHING_HAPPENED){
			winString = "No winner yet";
		}
		else if (win == O_WIN){
			winString = "O wins!";
		}
		else if (win == X_WIN){
			winString = "X wins!";
		}
		System.out.println("outcome is: "+winString);
		
	}

}

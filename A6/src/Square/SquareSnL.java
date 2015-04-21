/**
 * @file SquareSnL.java
 * @see Square.java
 * @author Casey Denner
 * @date 29/03/2015
 * @brief This class stores the information on each square for SnL
 */
package Square;

import Menu.GameSelector;

/**
 * @class SquareSnL
 * @brief This class stores the information on each square for SnL
 */
public class SquareSnL extends Square {

	/** Stores the destination of this square if it is a movement square*/
	private int m_destination;

	/**
	 * Sets up the square with the position and destination
	 * @param position
	 * @param destination
	 */
	public SquareSnL(int position, int destination) {
		if(GameSelector.m_TRACE){
			System.out.println("SqaureSnL::SquareSnL() valid parameters: "+
					position+" "+destination+" doesn't return anything");
		}
		this.m_position = position;
		this.m_destination = destination;
	}

	/**
	 * returns the destination of this square, possible that 
	 * destination == position
	 * @return destination
	 */
	public int getDestination() {
		if(GameSelector.m_TRACE){
			System.out.println("SqaureSnL::getDestination() no parameters"+
					" returns "+m_destination);
		}
		return m_destination;
	}

	/**
	 * Returns whether this square is a movement square
	 * @return true if this square is a movement square, false if it is not
	 */
	public boolean isMovementSquare() {
		if(GameSelector.m_TRACE){
			System.out.println("SquareSnL::isMovementSquare() no parameters"+
					"returns "+!(m_position == m_destination));
		}
		return !(m_position == m_destination);
	}

	/**
	 * returns whether a player is in the square
	 * @return true if there is a player in the square, false if not
	 */
	public boolean existsPlayer() {
		if(GameSelector.m_TRACE){
			System.out.println("SqaureSnL::existsPlayers() no parameters"+
					" returns "+false);
		}
		return false;
	}
	
	/** testing method */
	public static void main(String[] args){
		int position = 5;
		int destination = 10;
		SquareSnL squaresnl = new SquareSnL(position,destination);
		System.out.println("Position is: "+squaresnl.getPosition()+
				", destination is: "+squaresnl.getDestination());
	}
}

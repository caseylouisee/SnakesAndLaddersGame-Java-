/**
 * @file Square.java
 * @author Casey Denner
 * @date 29/03/2015
 * @brief This class stores the position of each square and its elements
 */

package Square;

import Menu.GameSelector;

/**
 * @class Square
 * @brief This class stores the position of each square and its elements
 */
public abstract class Square {
	
	/** Stores the position of the square on the board*/ 
	protected int m_position;
	/** stores what the square is filled with. possible values:
	 * " ", "O" or "X"
	 */
	protected char m_x;


	/** 
	 * Sets the values for x 
	 * @param x
	 **/
	public void setValue(char x) {
		if(GameSelector.m_TRACE){
			System.out.println("Square::setValue() valid parameter:"+x+
					" doesn't return anything.");
		}
		this.m_x = x;
	}

	/**
	 * Gets value for x
	 * @return x
	 */
	public char getValue() {
		if(GameSelector.m_TRACE){
			System.out.println("Square::getValue() no parameters needed,"+
					"returns: "+m_x);
		}
		return m_x;
	}

	/**
	 * Gets the position of the squares
	 * @return position
	 */
	public int getPosition() {
		if(GameSelector.m_TRACE){
			System.out.println("Square::getPosition() no parameters needed,"+
					"returns: "+m_position);
		}
		return m_position;
	}

	/**
	 * Gets the position of the square and converts it to a string
	 * 
	 * @return the position of s as a string
	 */
	public String toString() {
		String s = String.valueOf(m_position);
		if(GameSelector.m_TRACE){
			System.out.println("Square::toString() no parameters needed,"+
					"returns: "+m_position+" as a string, not int.");
		}
		return s;
	}
}

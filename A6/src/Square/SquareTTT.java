/**
 * @file SquareTTT.java
 * @see Square.java
 * @author Casey Denner
 * @date 29/03/2015
 * @brief This class stores the information on each square for TTT
 */
package Square;

import Display.DisplayTTT;
import Menu.GameSelector;

/**
 * @class SquareTTT
 * @brief This class stores the information on each square for TTT
 */
public class SquareTTT extends Square {

	/**
	 * Constructor for setting up the squares in TTT
	 * @param i
	 */
	public SquareTTT(int i) {
		super();
		super.m_position = i;
		setValue(' ');
		if(GameSelector.m_TRACE){
			System.out.println("SquareTTT::SquareTTT() valid parameters:"+i+
					" doesn't return anything.");
		}
	}
	
	public SquareTTT(int i,char value, DisplayTTT display) {
		super();
		super.m_position = i;
		setValue(value);
		display.setButtonIcon(i,value);
		if(GameSelector.m_TRACE){
			System.out.println("SquareTTT::SquareTTT() valid parameters:"+i+
					" "+value+" "+display+ ", doesn't return anything.");
		}
	}
	
	public static void main(String[] args){
		int i = 5;
		SquareTTT squarettt = new SquareTTT(i);
		squarettt.setValue('X');
		System.out.println("Position is: "+squarettt.getPosition()+
				", value is: "+squarettt.getValue());
	}

}

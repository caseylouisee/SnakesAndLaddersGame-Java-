/**
 * @file AIPlayerSnL.java
 * @author Casey Denner
 * @date 29/03/2015
 * @see PlayerSnL.java
 * @brief Creates a AI player for Snake and Ladders
 */
package Player;

import java.awt.Color;

import Menu.GameSelector;

/**
 * @class AIPlayerSnL
 * @brief  Creates a AI player for Snake and Ladders
 */

public class AIPlayerSnL extends PlayerSnL{ 


	/**
	 * Constructor that save the player name and piece 
	 *  to the class extends from PlayerSnL
	 * @param name is the player name passed 
	 * @param c is the player piece color passed 
	 */
	
	public AIPlayerSnL(String name, Color c) { 
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerSnL::AIPlayerSnL() create "
					+ "a new AI player for Snakes and Ladders "
					+ "with name: "+name+"and piece color: "+c);
		}
		super.m_playerColor = c;
		super.m_playerName = name;

	}
	
	/**
	 * Constructor that save the player name and piece color and positions 
	 *  to the class extends from PlayerSnL
	 * @param name is the player name passed 
	 * @param c is the player color passed 
	 * @param position is the player current piece position
	 */
	
	public AIPlayerSnL(String name, Color c, int position) { 
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerSnL::AIPlayerSnL() creates"
					+ "a new AI player for Snakes and Ladders "
					+ "with name: "+name+"and piece color: "+c+
					"and piece location: "+position);
		}
		super.m_playerColor = c;
		super.m_playerName = name;
		super.m_playerLocation = position;
	
	}
	
	/**
	 * main method created for unit testing 
	 * @param args
	 */
	
	public static void main(String [] args){
		String name="AiPlayerSnL1";
		Color c =Color.BLUE;
		int position=10;
		new AIPlayerSnL(name,c);
		new AIPlayerSnL(name,c,position);
		
	}
	

}




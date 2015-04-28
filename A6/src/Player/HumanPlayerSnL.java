/**
 * @file HumanPlayerSnL.java
 * @author Yangfan Jiang - A5, Casey Denner - A6
 * @date 29/03/2015
 * @see PlayerSnL.java
 * @brief Creates a Human player for Snake and Ladders
 */
package Player;


import java.awt.Color;

import Menu.GameSelector;

/**
 * @class HumanPlayerSnL
 * @brief  Creates a human player for Snake and Ladders
 */
public class HumanPlayerSnL extends PlayerSnL {

	/**
	 * Constructor that save the player name and piece color 
	 *  to the class extends from Player
	 * @param name is the player name passed 
	 * @param c is the player color passed 
	 */
	public HumanPlayerSnL(String name, Color c) {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerSnL::HumanPlayerSnL() creates "
					+ "a new AI player for Snakes and Ladders "
					+ "with name: "+name+"and piece color : "+c);
		}
		super.m_playerColor = c;
		super.m_playerName = name;
	}
	
	/**
	 * Constructor that save the player name and piece color and positions 
	 * to the class extends from PlayerSnL
	 * @param name is the player name passed 
	 * @param c is the player color passed 
	 * @param position is the player current piece position
	 */
	public HumanPlayerSnL(String name, Color c, int position) {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerSnL::HumanPlayerSnL() creates "
					+ "a new human player for Snakes and Ladders "
					+ "with name: "+name+", piece color : "+c+
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
		String name="HumanPlayerSnL1";
		Color c =Color.RED;
		int position=12;
		new HumanPlayerSnL(name,c);
		new HumanPlayerSnL(name,c,position);
		
	}

}
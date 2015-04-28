/**
 * @file Player.java
 * @author Yangfan Jiang - A5, Casey Denner - A6
 * @date 29/03/2015
 * @brief Acts as a template for all other player classes
 */
package Player;

import Menu.GameSelector;

/**
 * @class Player
 * @brief Creates a player template for both games
 */
public class Player {
	
	/** The name of the player */
	protected String m_playerName;

	/**
	 * Gets the player name 
	 * @return the player name
	 */
	public String getPlayerName() {
		if(GameSelector.m_TRACE){
			System.out.println("Player::getPlayerName()  "
					+ "get player name: "+m_playerName);
		}
		return m_playerName;
	}

	/**
	 * Sets the player name 
	 * @return the player name
	 */
	public void setPlayerName(String name) {
		if(GameSelector.m_TRACE){
			if(name.contains(".")||name.contains(",")){
				System.out.println("*** Warning Player::setPlayerName()  "
						+ "set player name to: invalid "
						+ "name containing symbol'.'or ','");
			}else{
				System.out.println("Player::setPlayerName()  "
						+ "set player name to: "+name);
			}
		}
		m_playerName = name;
	}
	
	/**
	 * main method created for unit testing 
	 * @param args
	 */
	public static void main(String [] args){
		String name1="Player1";
		String name2="Player.1";
		Player player=new Player();
		player.setPlayerName(name1);
		player.getPlayerName();
		player.setPlayerName(name2);	
	}
	
}

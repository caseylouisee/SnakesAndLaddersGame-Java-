/**
 * @file PlayerTTT.java
 * @author Casey Denner
 * @date 29/03/2015
 * @see Player.java
 * @brief Creates a player for TicTacToe
 */
package Player;

import java.util.ArrayList;

import Menu.GameSelector;
import Square.Square;
import Square.SquareTTT;


/**
 * @class PlayerTTT
 * @brief  Creates a player for TicTacToe
 */

public class PlayerTTT extends Player {
	/** The piece type of the player */
	private char m_pieceType;
	/** The piece location array of the player */
	private ArrayList<Integer> m_pieceLocations=new ArrayList<Integer>();

	
	/**
	 * Gets the player piece type 
	 * @return the player piece type is X or O
	 */
	public char getPlayerPieceType() {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::getPlayerPieceType()  "
					+ "get player piece type: "+m_pieceType);
		}
		return m_pieceType;
	}
	
	/**
	 * Sets the player piece type 
	 * @return the player piece type 
	 */
	public void setPlayerPieceType(char playerType) {
		if(GameSelector.m_TRACE){
			if(playerType=='X'||playerType=='O'){
				System.out.println("PlayerTTT::setPlayerPieceType()  "
						+ "set player piece type: "+playerType);
			}else{
				System.out.println("PlayerTTT::setPlayerPieceType()  "
						+ "set invalid player piece type,"
						+ " type only can be X or O");
			}
		}
		 m_pieceType=playerType;
	}
	

	/**
	* @return full set of piece locations
	*/
	public ArrayList<Integer> getPieceLocations() {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::getPieceLocations()  "
					+ "get player all "
					+ "piece locations: "+m_pieceLocations);
		}
		return m_pieceLocations;
	}
	
	/**get the every piece location integer from 0 to size-1
	* @return most recently placed piece
	*/
	public int getPieceLocation() {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::getPieceLocation()  "
					+ "get player current "
					+ "piece location: "
					+m_pieceLocations.get(m_pieceLocations.size() - 1));
		}
		return m_pieceLocations.get(m_pieceLocations.size() - 1);
	}
	
	/**
	 * Check if the player piece is X or not 
	 * @return the true if playerPieceType is X
	 */
	public boolean isX() {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::isX()  "
					+ "check piece is X that is: "+
					(m_pieceType=='X'));
		}
		char playerPieceType=m_pieceType;
		if(playerPieceType=='X'){
			return true;
		}else 
			return false;
	}
	
	/**
	 * Check if the player piece is O or not 
	 * @return the true if playerPieceType is O
	 */
	public boolean isO() {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::isO()  "
					+ "check piece is O that is: "+
					(m_pieceType=='O'));
		}
		char playerPieceType=m_pieceType;
		if(playerPieceType!='X'){
			return true;
		}else 
			return false;
	}
	
	/**
	 * Add Piece to Piece array 
	 * @param s the square location you put the piece on
	 */
	public void addPiece(Square s) {
		if(GameSelector.m_TRACE){
			System.out.println("PlayerTTT::addPiece()  "
					+ "add piece: "+s+" to the piece "
							+ "locations arraylist");
		}
		m_pieceLocations.add(s.getPosition());
	}
	
	/**
	 * main method created for unit testing 
	 * @param args
	 */
	public static void main(String [] args){
		int i =6;
		Square s=new SquareTTT(i); 
		char type='X';
		PlayerTTT playerTTT=new PlayerTTT();
		playerTTT.setPlayerPieceType(type);
		playerTTT.addPiece(s);
		playerTTT.getPieceLocations();
		playerTTT.getPieceLocation();
		playerTTT.isX();
		playerTTT.isO();
		playerTTT.getPlayerPieceType();
		playerTTT.getPlayerName();	
	}
}
	


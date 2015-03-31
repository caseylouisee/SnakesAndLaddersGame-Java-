/**
 * @file HumanPlayerTTT.java
 * @author Casey Denner
 * @date 29/03/2015
 * @see PlayerTTT.java
 * @brief Creates a human player for TicTacToe
 */
package Player;



import java.util.ArrayList;

import Menu.GameSelector;
import Square.Square;
import Square.SquareTTT;

/**
 * @class HumanPlayerTTT
 * @brief Creates a human player for TicTacToe
 */

public class HumanPlayerTTT extends PlayerTTT {
	
	/**
	 * Constructor that save the human player name and piece 
	 *  to the class extends from PlayerTTT
	 * @param name is the player name passed 
	 * @param piece is the player piece passed 
	 */
	public HumanPlayerTTT(String name, char piece) {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::HumanPlayerTTT() creates "
					+ "a new AI player for Tic Tac Toe "
					+ "with name: "+name+"and piece : "+piece);
		}
		super.setPlayerName(name);
		super.setPlayerPieceType(piece);
	}
	
	/**
	 * Gets the player name
	 * @return the player name extends from Player
	 */
	public String getPlayerName() {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::getPlayerName()  "
					+ "get player name: "+super.getPlayerName());
		}
		return super.getPlayerName();
	}
	
	/**
	 * Gets the player piece type extends from playerTTT
	 * @return the player piece type is X or O
	 */
	public char getPlayerPieceType() {
		if(GameSelector.m_TRACE){
			System.out.println("HUmanPlayerTTT::getPlayerPieceType()  "
					+ "get player piece type: "+super.getPlayerPieceType());
		}
		return super.getPlayerPieceType();
	}
	
	/**
	 * Check if the player piece is X or not 
	 * @return the true if playerPieceType is X
	 */
	public boolean isX() {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::isX()  "
					+ "check piece is X that is: "+
					(super.getPlayerPieceType()=='X'));
		}
		char playerPieceType=super.getPlayerPieceType();
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
			System.out.println("HumanPlayerTTT::isO()  "
					+ "check piece is O that is: "+
					(super.getPlayerPieceType()=='O'));
		}
		char playerPieceType=super.getPlayerPieceType();
		if(playerPieceType!='X'){
			return true;
		}else 
			return false;
	}
	
	/**
	 * Gets the player piece location extends from PlayerTTT
	 * @return the player one piece location 
	 */
	public int getPieceLocation() {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::getPieceLocation()  "
					+ "get player piece location: "+super.getPieceLocation());
		}
		return super.getPieceLocation();
	}
	
	/**
	 * Gets the player piece location  array extends from PlayerTTT
	 * @return the player one piece location array 
	 */
	public ArrayList<Integer> getPieceLocations() {
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::getPieceLocations()  "
					+ "get player all  piece locations: "+super.getPieceLocations());
		}
		return super.getPieceLocations();
	}
	
	/**
	 * Add Piece to Piece array extends from PlayerTTT
	 * @param s the square location you put the piece on
	 */
	public void addPiece(Square s){
		if(GameSelector.m_TRACE){
			System.out.println("HumanPlayerTTT::addPiece()  "
					+ "add piece: "+s+"to the piece "
							+ "locations arraylist");
		}
		super.addPiece(s);
	}
	
	/**
	 * main method created for unit testing 
	 * @param args
	 */
	public static void main(String [] args){
		String name="HumanPlayerTTT1";
		char piece ='O';
		int i =4;
		Square s=new SquareTTT(i); 
		HumanPlayerTTT humanPlayerTTT=new HumanPlayerTTT(name,piece);
		humanPlayerTTT.addPiece(s);
		humanPlayerTTT.getPieceLocations();
		humanPlayerTTT.getPieceLocation();
		humanPlayerTTT.isX();
		humanPlayerTTT.isO();
		humanPlayerTTT.getPlayerPieceType();
		humanPlayerTTT.getPlayerName();	
	}
	
}

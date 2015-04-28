/**
 * @file AIPlayerTTT.java
 * @author Yangfan Jiang - A5, Casey Denner - A6
 * @date 29/03/2015
 * @see PlayerTTT.java
 * @brief Creates a AI player for TicTacToe
 */
package Player;
import java.util.ArrayList;

import Menu.GameSelector;
import Square.Square;
import Square.SquareTTT;

/**
 * @class AIPlayerTTT
 * @brief  Creates a AI player for TicTacToe
 */
public class AIPlayerTTT extends PlayerTTT {

	/**
	 * Constructor that save the ai player name and piece 
	 *  to the class extends from PlayerTTT
	 * @param name is the player name passed 
	 * @param piece is the player piece passed 
	 */
	public AIPlayerTTT(String name, char piece) {
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerTTT::AIPlayerTTT() creates "
					+ "a new AI player for Tic Tac Toe "
					+ "with name: "+name+"and piece : "+piece);
		}
		super.setPlayerName(name);
		super.setPlayerPieceType(piece);
	}
	
	/**
	 * Gets the player name extends from Player
	 * @return the player name
	 */
	public String getPlayerName() {
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerTTT::getPlayerName()  "
					+ "get player name: "+super.getPlayerName());
		}
		return super.getPlayerName();
	}
	
	/**
	 * Gets the player piece type extends from PlayerTTT
	 * @return the player piece type X or O
	 */
	public char getPlayerPieceType() {
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerTTT::getPlayerPieceType()  "
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
			System.out.println("AIPlayerTTT::isX()  "
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
			System.out.println("AIPlayerTTT::isO()  "
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
			System.out.println("AIPlayerTTT::getPieceLocation()  "
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
			System.out.println("AIPlayerTTT::getPieceLocations()  "
					+ "get player all  piece locations: "+
					super.getPieceLocations());
		}
		return super.getPieceLocations();
	}
	
	/**
	 * Add Piece to Piece array extends from PlayerTTT
	 * @param s the square location you put the piece on
	 */
	public void addPiece(Square s){
		if(GameSelector.m_TRACE){
			System.out.println("AIPlayerTTT::addPiece()  "
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
		String name="AiPlayerTTT1";
		char piece ='X';
		int i =3;
		Square s=new SquareTTT(i); 
		AIPlayerTTT aiPlayerTTT=new AIPlayerTTT(name,piece);
		aiPlayerTTT.addPiece(s);
		aiPlayerTTT.getPieceLocations();
		aiPlayerTTT.getPieceLocation();
		aiPlayerTTT.isX();
		aiPlayerTTT.isO();
		aiPlayerTTT.getPlayerPieceType();
		aiPlayerTTT.getPlayerName();	
	}
	
}
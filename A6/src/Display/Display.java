package Display;
/** 
 * @file Display.java
 * 
 * @brief This class is the superclass display, which contains generic
 * display related methods which are to be used by the respective 
 * superclass display classes.
 * 
 * @date 29/03/2015
 * 
 * @author Casey Denner 
 *  
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Menu.GameSelector;
/**
 * @class Display
 * @brief This class is the superclass display, which contains generic
 * 	display related methods.
 */
public class Display extends JPanel {
	
	/** sets shape width */
	private final int SHAPE_WIDTH = 30;
	
	/** sets shape height */
	private final int SHAPE_HEIGHT = 30;

	/** Used to set object locations on the JFrames/JPanels**/
	public static final int XPOS_COL50 = 50;
	public static final int XPOS_COL100 = 100;
	public static final int XPOS_COL150 = 150;
	public static final int XPOS_COL200 = 200;
	public static final int XPOS_COL250 = 250;
	public static final int XPOS_COL300 = 300;
	public static final int XPOS_COL350 = 350;
	public static final int XPOS_COL400 = 400;
	public static final int XPOS_COL450 = 450;
	public static final int XPOS_COL500 = 500;
	public static final int XPOS_COL550 = 550;
	
	public static final int YPOS_ROW50 = 50;
	public static final int YPOS_ROW100 = 100;
	public static final int YPOS_ROW150 = 150;
	public static final int YPOS_ROW200 = 200;
	public static final int YPOS_ROW250 = 250;
	public static final int YPOS_ROW300 = 300;
	public static final int YPOS_ROW350 = 350;
	public static final int YPOS_ROW400 = 400;
	public static final int YPOS_ROW450 = 450;
	public static final int YPOS_ROW500 = 500;
	public static final int YPOS_ROW550 = 550;
	public static final int YPOS_ROW600 = 600;
	public static final int YPOS_ROW650 = 650;
	
	public static final int COMPONENT_WIDTH20 = 20;
	public static final int COMPONENT_WIDTH50 = 50;
	public static final int COMPONENT_WIDTH75 = 75;
	public static final int COMPONENT_WIDTH80 = 80;
	public static final int COMPONENT_WIDTH100 = 100;
	public static final int COMPONENT_WIDTH130 = 130;
	public static final int COMPONENT_WIDTH150 = 150;
	public static final int COMPONENT_WIDTH160 = 160;
	public static final int COMPONENT_WIDTH170 = 170;
	public static final int COMPONENT_WIDTH175 = 175;
	public static final int COMPONENT_WIDTH180 = 180;
	public static final int COMPONENT_WIDTH200 = 200;
	public static final int COMPONENT_WIDTH250 = 250;
	public static final int COMPONENT_WIDTH300 = 300;
	public static final int COMPONENT_WIDTH350 = 350;

	public static final int COMPONENT_HEIGHT20 = 20;
	public static final int COMPONENT_HEIGHT40 = 40;
	public static final int COMPONENT_HEIGHT50 = 50;
	public static final int COMPONENT_HEIGHT70 = 70;
	public static final int COMPONENT_HEIGHT80 = 80;
	public static final int COMPONENT_HEIGHT85 = 85;
	public static final int COMPONENT_HEIGHT100 = 100;
	public static final int COMPONENT_HEIGHT150 = 150;
	public static final int COMPONENT_HEIGHT200 = 200;
	public static final int COMPONENT_HEIGHT250 = 250;

	public static final int OFFSET5 = 5;
	public static final int OFFSET10 = 10;
	public static final int OFFSET15 = 15;
	public static final int OFFSET20 = 20;
	public static final int OFFSET25 = 25;
	public static final int OFFSET30 = 30;
	public static final int OFFSET35 = 35;
	public static final int OFFSET40 = 40; 
	public static final int OFFSET50 = 50; 

	/**
	 * This method draws rectangles on the board
	 * @param graphics
	 */
	public void paintComponent(Graphics graphics) {
		if(GameSelector.m_TRACE){
			System.out.println("Display::paintComponent");
		}
		super.paintComponent(graphics);
		this.setBackground(Color.WHITE);
		graphics.setColor(Color.BLACK);

		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				graphics.drawRect((i * SHAPE_WIDTH), (j * SHAPE_HEIGHT),
						SHAPE_WIDTH, SHAPE_HEIGHT);
			}
		}
		
	}
}

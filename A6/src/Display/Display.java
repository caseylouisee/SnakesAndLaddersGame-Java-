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
 * display related methods which are to be used by the respective 
 * superclass display classes.
 */
public class Display extends JPanel {
	
	/**
	 * online test
	 */
	private static final long serialVersionUID = -5669737807449551821L;
	/** sets shape width and height */
	private final int SHAPE_WIDTH = 30;
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
	
	public static final int COMPONENT_WIDTH50 = 50;
	public static final int COMPONENT_WIDTH75 = 75;
	public static final int COMPONENT_WIDTH100 = 100;
	public static final int COMPONENT_WIDTH150 = 150;
	public static final int COMPONENT_WIDTH180 = 180;
	public static final int COMPONENT_WIDTH200 = 200;
	public static final int COMPONENT_WIDTH300 = 300;
	public static final int COMPONENT_WIDTH350 = 350;

	public static final int COMPONENT_HEIGHT20 = 20;
	public static final int COMPONENT_HEIGHT40 = 40;
	public static final int COMPONENT_HEIGHT80 = 80;
	public static final int COMPONENT_HEIGHT100 = 100;
	
	public static final int OFFSET1 = 10;
	public static final int OFFSET2 = 15;
	public static final int OFFSET3 = 40;
	public static final int OFFSET4 = 20;
	public static final int OFFSET5 = 30;
	
	/** This method draws rectangles for the boards **/
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

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
	 * 
	 */
	private static final long serialVersionUID = -5669737807449551821L;
	/** sets shape width and height */
	private final int SHAPE_WIDTH = 30;
	private final int SHAPE_HEIGHT = 30;

	/** Used to set object locations on the JFrames/JPanels**/
	public static final int XPOS_COL1 = 50;
	public static final int XPOS_COL2 = 100;
	public static final int XPOS_COL3 = 150;
	public static final int XPOS_COL4 = 200;
	public static final int XPOS_COL5 = 250;
	public static final int XPOS_COL6 = 300;
	public static final int XPOS_COL7 = 350;
	public static final int XPOS_COL8 = 400;
	public static final int XPOS_COL9 = 450;
	public static final int XPOS_COL10 = 500;
	public static final int XPOS_COL11 = 550;
	
	public static final int YPOS_ROW1 = 50;
	public static final int YPOS_ROW2 = 100;
	public static final int YPOS_ROW3 = 150;
	public static final int YPOS_ROW4 = 200;
	public static final int YPOS_ROW5 = 250;
	public static final int YPOS_ROW6 = 300;
	public static final int YPOS_ROW7 = 350;
	public static final int YPOS_ROW8 = 400;
	public static final int YPOS_ROW9 = 450;
	public static final int YPOS_ROW10 = 500;
	public static final int YPOS_ROW11 = 550;
	public static final int YPOS_ROW12 = 600;
	public static final int YPOS_ROW13 = 650;
	
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

package com.nec.asia.applet;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;


/**
 * AWT object for image display.
 *
 * @version $Revision: 1.1.1.1 $
 * @author $Author: david $
 */
public class ImagePanel extends Canvas {
	 
 	/**
	 * 
	 */
	private static final long serialVersionUID = -200512869335140345L;

	/** The image. */
 	Image image = null;
	 
 	/** The image height. */
 	int imageHeight = 0;
	 
 	/** The image width. */
 	int imageWidth = 0;
	 
 	/** The x. */
 	int x = 0;
	 
 	/** The y. */
 	int y = 0;

	/**
	 * Instantiates a new image panel.
	 *
	 * @param imageWidth the image width
	 * @param imageHeight the image height
	 */
	public ImagePanel(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		  
        if (image != null) {
           	int width=image.getWidth(null);
        	int height=image.getHeight(null);
        	float ratio=(float)getHeight()/height;
        	int new_width=(int)(width * ratio);
        	
        	int new_heigth=getHeight();
        	
            g.drawImage(image, (getWidth() - new_width) / 2, 0, new_width, new_heigth, this);
        }		
		
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getX()
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getY()
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}
}
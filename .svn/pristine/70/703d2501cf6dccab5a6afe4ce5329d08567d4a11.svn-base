package com.nec.asia.nic.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * 
 * @author chris_wong
 *
 */
public class FontTest extends TestCase {
	private static final Logger logger = Logger.getLogger(FontTest.class);

	@Override
	protected void setUp(){
	}
	
	public void testFont(){
		String word = "I'm trying to write application which need to draw many strings using Java Graphics class in Java.";
		logger.info("Word: "+word);
		String fontFamily = "Verdana"; //"Verdana"
		Font font = new Font(fontFamily, Font.PLAIN, 12);  		
		int width= renderText(word, font);
		logger.info("Font: "+fontFamily+"\nlength:"+word.length()+"\twidth:"+width);

		fontFamily = "Haettenschweiler";
		font = new Font(fontFamily, Font.PLAIN, 12);  
		width= renderText(word, font);
		logger.info("Font: "+fontFamily+"\nlength:"+word.length()+"\twidth:"+width);
		
		fontFamily = "Century Gothic";
		font = new Font(fontFamily, Font.PLAIN, 12);  
		width= renderText(word, font);
		logger.info("Font: "+fontFamily+"\nlength:"+word.length()+"\twidth:"+width);
	}

	private int renderText(String text, Font font) {
		BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = image.getGraphics();
		g2.setFont(font);
        int width = SwingUtilities.computeStringWidth( g2.getFontMetrics(), text );  
		return width;
	}
}

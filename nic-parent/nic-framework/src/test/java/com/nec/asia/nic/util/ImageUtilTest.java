package com.nec.asia.nic.util;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.ImageUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import com.nec.asia.nic.utils.ImageUtil;

import junit.framework.TestCase;

/**
 * 
 * @author chris_wong
 *
 */
public class ImageUtilTest {
	private static final Logger logger = Logger.getLogger(ImageUtilTest.class);

	@Test
	public void testResize() throws IOException{
		byte[] image = loadImage("PH_CAP.jpg");
		logger.info("image: "+image+"\tlength:"+image.length);
		ByteArrayInputStream bis = new ByteArrayInputStream(image);
		Dimension dimension = ImageUtils.getImageDimension(bis, Workbook.PICTURE_TYPE_JPEG);
		logger.info("dimension.width: "+dimension.width+"\theight:"+dimension.height);
		
		byte[] downSizeImage = ImageUtil.resizeImageByWidthHeight(image, 240, 320, 0.6f);
		logger.info("image: "+downSizeImage+"\tlength:"+downSizeImage.length);
		bis = new ByteArrayInputStream(downSizeImage);
		dimension = ImageUtils.getImageDimension(bis, Workbook.PICTURE_TYPE_JPEG);
		logger.info("dimension.width: "+dimension.width+"\theight:"+dimension.height);
		
		FileUtils.writeByteArrayToFile(new File(rootFolder, "TH_CAP.jpg"), downSizeImage);
	}
	
	@Test
	public void testConvertToJPG() throws IOException {
		String readList = ImageUtil.getAllSupportedReadFormats();
		String writeList = ImageUtil.getAllSupportedWriteFormats();
		logger.info("supported input files type "+readList);
		logger.info("supported output files type "+writeList);
		
		File tifFile = new File(rootFolder, "photo.gif");
		File jpgFile = new File(rootFolder, "photo.jpg");
		
		ImageUtil.convertImage(tifFile, jpgFile, "JPG");
		
		logger.info("converted files: "+jpgFile);
		Assert.isTrue(jpgFile.exists());
	}
	
	//private String rootFolder = "C:/Users/chris_wong/workspace-vepp-svn/nic-parent/nic-framework/src/test/resources/candidate1/";
	private String rootFolder = "C:/tmp/photo/";
	private byte[] loadImage(String simpleFileName){
		try {
			String fullFileName = rootFolder + simpleFileName;
			byte[] data = FileUtils.readFileToByteArray(new File(fullFileName));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
